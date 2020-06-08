package com.geektcp.alpha.chat.logic.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.geektcp.alpha.chat.base.Constants;
import com.geektcp.alpha.chat.base.SessionManager;
import com.geektcp.alpha.chat.base.SpringContext;
import com.geektcp.alpha.chat.data.model.User;
import com.geektcp.alpha.chat.listener.EventType;
import com.geektcp.alpha.chat.logic.login.event.UserLoginEvent;
import com.geektcp.alpha.chat.logic.login.message.res.ResUserLogin;
import com.geektcp.alpha.chat.logic.user.UserService;
import com.geektcp.alpha.chat.net.ChannelUtils;
import com.geektcp.alpha.chat.net.IoSession;

import io.netty.channel.Channel;

@Component
public class LoginService {

	@Autowired
	private UserService userService;

	public void validateLogin(Channel channel, long userId, String password) {
		User user = userService.queryUser(userId, password);
		IoSession session = ChannelUtils.getSessionBy(channel);
		if (user == null) {
			SessionManager.INSTANCE.sendPacketTo(session,
					ResUserLogin.valueOfFailed());
			return;
		}

		onLoginSucc(user, session);
	}

	private void onLoginSucc(User user, IoSession session) {
		SessionManager.INSTANCE.registerSession(user, session);
		userService.addUser2Online(user);

		ResUserLogin loginPact = new ResUserLogin();
		loginPact.setIsValid(Constants.TRUE);
		SessionManager.INSTANCE.sendPacketTo(session, loginPact);

		SpringContext.getEventDispatcher().fireEvent(
				new UserLoginEvent(EventType.LOGIN, user.getUserId()));
	}

}
