package com.geektcp.alpha.chat.logic.login.event;

import com.geektcp.alpha.chat.listener.EventType;
import com.geektcp.alpha.chat.listener.UserEvent;

public class UserLoginEvent extends UserEvent {

	public UserLoginEvent(EventType evtType, long userId) {
		super(evtType, userId);
	}

}
