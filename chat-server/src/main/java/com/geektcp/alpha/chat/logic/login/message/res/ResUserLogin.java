package com.geektcp.alpha.chat.logic.login.message.res;

import com.geektcp.alpha.chat.base.Constants;
import com.geektcp.alpha.chat.net.IoSession;
import com.geektcp.alpha.chat.net.message.AbstractPacket;
import com.geektcp.alpha.chat.net.message.PacketType;

import io.netty.buffer.ByteBuf;

public class ResUserLogin extends AbstractPacket{

	private String alertMsg;
	private byte isValid;

	public static ResUserLogin valueOfFailed() {
		ResUserLogin response = new ResUserLogin();
		response.setIsValid(Constants.FAILED);

		return response;
	}

	@Override
	public void writeBody(ByteBuf buf) {
		writeUTF8(buf, alertMsg);
		buf.writeByte(isValid);
	}

	@Override
	public void readBody(ByteBuf buf) {
		this.alertMsg = readUTF8(buf);
		this.isValid = buf.readByte();
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ResUserLogin;
	}

	@Override
	public void execPacket(IoSession session) {
		// TODO Auto-generated method stub

	}

	public String getAlertMsg() {
		return alertMsg;
	}

	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}

	public byte getIsValid() {
		return isValid;
	}

	public void setIsValid(byte isValid) {
		this.isValid = isValid;
	}

}
