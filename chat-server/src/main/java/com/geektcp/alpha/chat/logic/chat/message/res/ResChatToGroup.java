package com.geektcp.alpha.chat.logic.chat.message.res;

import com.geektcp.alpha.chat.net.IoSession;
import com.geektcp.alpha.chat.net.message.AbstractPacket;
import com.geektcp.alpha.chat.net.message.PacketType;

import io.netty.buffer.ByteBuf;

public class ResChatToGroup extends AbstractPacket {

	private String content;

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
		return PacketType.ResChatToUser;
	}

	@Override
	public void execPacket(IoSession session) {
		// TODO Auto-generated method stub
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
