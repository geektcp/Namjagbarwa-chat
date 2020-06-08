package com.geektcp.alpha.chat.base;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.geektcp.alpha.chat.ServerConfigs;
import com.geektcp.alpha.chat.asyncdb.AysncDbService;
import com.geektcp.alpha.chat.dispatch.MessageDispatcher;
import com.geektcp.alpha.chat.listener.EventDispatcher;
import com.geektcp.alpha.chat.logic.chat.ChatService;
import com.geektcp.alpha.chat.logic.friend.FriendService;
import com.geektcp.alpha.chat.logic.search.SearchService;
import com.geektcp.alpha.chat.logic.user.UserService;
import com.geektcp.alpha.chat.logic.util.IdService;

@Component
public class SpringContext implements ApplicationContextAware {

	private static SpringContext self;

	/** spring容器上下文 */
	private static ApplicationContext applicationContext = null;

	@PostConstruct
	private void init() {
		self = this;
	}

	private static UserService userService;

	private static FriendService friendService;

	private static SearchService searchService;

	/** 异步持久化服务 */
	private static AysncDbService aysncDbService;

	private static ChatService chatService;

	private static IdService idService;

	private static MessageDispatcher messageDispatcher;

	private static EventDispatcher eventDispatcher;

	private static ServerConfigs serverConfigs;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContext.applicationContext = applicationContext;
	}

	public final static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	public final static <T> Collection<T> getBeansOfType(Class<T> clazz) {
		return applicationContext.getBeansOfType(clazz).values();
	}

	public final static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}

	@Resource
	public void setUserService(UserService userService) {
		SpringContext.userService = userService;
	}

	public final static UserService getUserService() {
		return userService;
	}

	@Resource
	public void setFriendService(FriendService friendService) {
		SpringContext.friendService = friendService;
	}

	public final static FriendService getFriendService() {
		return friendService;
	}

	@Resource
	public void setSearchService(SearchService searchService) {
		SpringContext.searchService = searchService;
	}

	public final static SearchService getSearchService() {
		return searchService;
	}

	@Resource
	public void setIdService(IdService idService) {
		SpringContext.idService = idService;
	}

	public final static IdService getIdService() {
		return idService;
	}

	@Resource
	public void setAysncDbService(AysncDbService aysncDbService) {
		SpringContext.aysncDbService = aysncDbService;
	}

	public static AysncDbService getAysncDbService() {
		return aysncDbService;
	}

	@Resource
	public void setChatService(ChatService chatService) {
		SpringContext.chatService = chatService;
	}

	public final static ChatService getChatService() {
		return chatService;
	}

	@Resource
	public void setMessageDispatcher(MessageDispatcher messageDispatcher) {
		SpringContext.messageDispatcher = messageDispatcher;
	}

	public final static MessageDispatcher getMessageDispatcher() {
		return messageDispatcher;
	}

	@Resource
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		SpringContext.eventDispatcher = eventDispatcher;
	}

	public final static EventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}


	@Resource
	public void setServerConfigs(ServerConfigs serverConfigs) {
		SpringContext.serverConfigs = serverConfigs;
	}

	public final static ServerConfigs getServerConfigs() {
		return SpringContext.serverConfigs;
	}

}
