package com.geektcp.alpha.chat.net.transport;

import com.geektcp.alpha.chat.base.SessionManager;
import com.geektcp.alpha.chat.net.PacketManager;
import com.geektcp.alpha.chat.net.message.AbstractPacket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientTransportHandler extends ChannelInboundHandlerAdapter {


	public ClientTransportHandler(){

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx){
		//注册session
		SessionManager.INSTANCE.registerSession(ctx.channel());

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception{
		AbstractPacket  packet = (AbstractPacket)msg;
		PacketManager.INSTANCE.execPacket(packet);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.err.println("客户端关闭1");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.err.println("客户端关闭3");
		Channel channel = ctx.channel();
		cause.printStackTrace();
		if(channel.isActive()){
			System.err.println("simpleclient"+channel.remoteAddress()+"异常");
		}
	}
}
