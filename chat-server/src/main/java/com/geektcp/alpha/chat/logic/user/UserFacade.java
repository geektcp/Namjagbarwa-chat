package com.geektcp.alpha.chat.logic.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.geektcp.alpha.chat.base.SpringContext;
import com.geektcp.alpha.chat.data.model.User;
import com.geektcp.alpha.chat.listener.EventType;
import com.geektcp.alpha.chat.listener.annotation.EventHandler;
import com.geektcp.alpha.chat.logic.login.event.UserLoginEvent;

@Component
public class UserFacade {

	@Autowired
	private UserService userService;

	@EventHandler(value = { EventType.LOGIN })
	public void onUserLogin(UserLoginEvent loginEvent) {
		long userId = loginEvent.getUserId();
		User user = SpringContext.getUserService().getOnlineUser(userId);
		userService.refreshUserProfile(user);
	}

}
