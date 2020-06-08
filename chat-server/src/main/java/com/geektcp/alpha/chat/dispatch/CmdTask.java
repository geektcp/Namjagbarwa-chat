package com.geektcp.alpha.chat.dispatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.geektcp.alpha.chat.net.IoSession;
import com.geektcp.alpha.chat.net.message.AbstractPacket;
import com.geektcp.alpha.chat.net.message.PacketManager;

/**
 * 玩家请求消息任务
 *
 * @author tanghaiyang
 *
 */
public class CmdTask extends DispatchTask {

	private static Logger logger = LoggerFactory.getLogger(CmdTask.class);

	private long userId;
	private IoSession session;
	private AbstractPacket message;

	public static CmdTask valueOf(int distributeKey,
				IoSession session, AbstractPacket message) {
		CmdTask msgTask = new CmdTask();
		msgTask.dispatchKey = distributeKey;
		msgTask.session = session;
		msgTask.message  = message;

		return msgTask;
	}

	public long getUserId() {
		return userId;
	}

	@Override
	public void run() {
		try {
			PacketManager.INSTANCE.execPacket(session, message);
		}catch(Exception e) {
			logger.error("业务处理出现异常", e);
		}
	}

}
