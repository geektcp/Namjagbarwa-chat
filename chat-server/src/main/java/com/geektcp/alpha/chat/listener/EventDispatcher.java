package com.geektcp.alpha.chat.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.geektcp.alpha.chat.logs.LoggerUtils;
import com.geektcp.alpha.chat.common.thread.NamedThreadFactory;


@Component
public class EventDispatcher {

	@Autowired
	private ListenerManager listenerMgr;

	/** 事件类型与事件监听器列表的映射关系 */
	private final Map<EventType, Set<Object>> observers = new HashMap<>();
	/** 异步执行的事件队列 */
	private LinkedBlockingQueue<BaseEvent> eventQueue = new LinkedBlockingQueue<>();

	@PostConstruct
	private void init() {
		new NamedThreadFactory("event-dispatch").newThread(new EventWorker()).start();
	}

	/**
	 * 注册事件监听器
	 * @param evtType
	 * @param listener
	 */
	public void registerEvent(EventType evtType, Object listener) {
		Set<Object> listeners = observers.get(evtType);
		if(listeners == null){
			listeners = new CopyOnWriteArraySet<>();
			observers.put(evtType, listeners);
		}
		listeners.add(listener);
	}

	/**
	 * 分发事件
	 * @param event
	 */
	public void fireEvent(BaseEvent event) {
		if(event == null){
			throw new NullPointerException("event cannot be null");
		}
		//如果事件是同步的，那么就在消息主线程执行逻辑
		if (event.isSynchronized()) {
			triggerEvent(event);
		} else {
		//否则，就丢到事件线程异步执行
			eventQueue.add(event);
		}

	}

	private void triggerEvent(BaseEvent event) {
		EventType evtType = event.getEventType();
		Set<Object> listeners = observers.get(evtType);
		if(listeners != null){
			listeners.forEach(listener->{
				try{
					listenerMgr.fireEvent(listener, event);
				}catch(Exception e){
					LoggerUtils.error("triggerEvent failed", e);;  //防止其中一个listener报异常而中断其他逻辑
				}
			});
		}
	}

	private class EventWorker implements Runnable {
		@Override
		public void run() {
			while(true) {
				try {
					BaseEvent event = eventQueue.take();
					triggerEvent(event);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
