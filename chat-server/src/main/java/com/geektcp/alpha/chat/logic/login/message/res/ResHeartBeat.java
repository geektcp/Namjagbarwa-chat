package com.geektcp.alpha.chat.logic.login.message.res;

import com.geektcp.alpha.chat.net.IoSession;
import com.geektcp.alpha.chat.net.message.AbstractPacket;
import com.geektcp.alpha.chat.net.message.PacketType;

import io.netty.buffer.ByteBuf;

public class ResHeartBeat extends AbstractPacket{

	@Override
	public void writeBody(ByteBuf buf) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readBody(ByteBuf buf) {
		// TODO Auto-generated method stub

	}

	@Override
	public PacketType getPacketType() {
		return PacketType.RespHeartBeat;
	}

	@Override
	public void execPacket(IoSession session) {

	}

}
