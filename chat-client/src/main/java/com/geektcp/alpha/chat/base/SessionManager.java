package com.geektcp.alpha.chat.base;

import com.geektcp.alpha.chat.net.IoSession;
import com.geektcp.alpha.chat.net.message.AbstractPacket;

import io.netty.channel.Channel;

/**
 * 提供一些基础服务接口
 * @author tanghaiyang
 */
public enum SessionManager {

	INSTANCE;


	/** 通信会话 */
	private IoSession session;

	public void registerSession(Channel channel) {
		this.session = new IoSession(channel);
	}

	public void sendMessage(AbstractPacket request){
		this.session.sendPacket(request);
	}

	/**
	 * 是否已连上服务器
	 * @return
	 */
	public boolean isConnectedSever() {
		return this.session != null;
	}


}
