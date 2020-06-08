package com.geektcp.alpha.chat.logic.login.message.res;

import com.geektcp.alpha.chat.base.SessionManager;
import com.geektcp.alpha.chat.logic.login.message.req.ReqHeartBeat;
import com.geektcp.alpha.chat.net.message.AbstractPacket;
import com.geektcp.alpha.chat.net.message.PacketType;

public class ResHeartBeat extends AbstractPacket{

	@Override
	public PacketType getPacketType() {
		return PacketType.ResHeartBeat;
	}

	@Override
	public void execPacket() {
		System.err.println("收到服务端的ping包，回复pong包");
		SessionManager.INSTANCE.sendMessage(new ReqHeartBeat());
	}

}
