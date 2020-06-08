package com.geektcp.alpha.chat.net;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.geektcp.alpha.chat.net.message.AbstractPacket;
import com.geektcp.alpha.chat.net.message.IllegalPacketException;
import com.geektcp.alpha.chat.net.message.PacketType;

public enum PacketManager {

	INSTANCE;

	public void execPacket(AbstractPacket pact) {
		if(pact == null) return;
		try {
			Method m = pact.getClass().getMethod("execPacket");
			m.invoke(pact, null);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public AbstractPacket createNewPacket(int packetType) {
		Class<? extends AbstractPacket> packetClass = PacketType.getPacketClassBy(packetType);
		if (packetClass == null) {
			throw new IllegalPacketException("类型为"+packetType+"的消息定义不存在");
		}
		AbstractPacket packet = null;
		try {
			packet = (AbstractPacket)packetClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalPacketException("类型为"+packetType+"的消息实例化失败");
		}

		return packet;
	}

}
