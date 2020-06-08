package com.geektcp.alpha.chat.net.message;

import com.geektcp.alpha.chat.net.IoSession;

/**
 * 抽象消息定义
 * @author tanghaiyang
 */
public abstract class AbstractPacket extends ByteBufBean {

	abstract public PacketType getPacketType();

	/**
	 * 业务处理
	 */
	abstract public void execPacket(IoSession session);

	/**
	 *  是否开启gzip压缩(默认关闭)
	 *  消息体数据大的时候才开启，非常小的包压缩后体积反而变大，而且耗时
	 */
	public boolean isUseCompression() {
		return false;
	}

}
