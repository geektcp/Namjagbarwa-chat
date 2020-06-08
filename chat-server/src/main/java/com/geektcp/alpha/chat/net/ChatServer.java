package com.geektcp.alpha.chat.net;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.geektcp.alpha.chat.ServerConfigs;
import com.geektcp.alpha.chat.base.ServerNode;
import com.geektcp.alpha.chat.base.SpringContext;
import com.geektcp.alpha.chat.net.codec.PacketDecoder;
import com.geektcp.alpha.chat.net.codec.PacketEncoder;
import com.geektcp.alpha.chat.net.message.PacketType;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

public class ChatServer implements ServerNode {

	private Logger logger = LoggerFactory.getLogger(ChatServer.class);

	// 避免使用默认线程数参数
	private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	private EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());

	private int port;

	@Override
	public void init() {
		ServerConfigs serverConfigs = SpringContext.getServerConfigs();
		this.port = serverConfigs.getSocketPort();
	}

	@Override
	public void start() throws Exception {
		logger.info("服务端已启动，正在监听用户的请求......");
		// 协议初始化
		PacketType.initPackets();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler(new ChildChannelHandler());

			b.bind(new InetSocketAddress(port)).sync();
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
	}

	@Override
	public void shutDown() throws Exception {
		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}
	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
			ChannelPipeline pipeline = arg0.pipeline();
			pipeline.addLast(new PacketDecoder(1024 * 4, 0, 4, 0, 4));
			pipeline.addLast(new LengthFieldPrepender(4));
			pipeline.addLast(new PacketEncoder());
			// 客户端300秒没收发包，便会触发UserEventTriggered事件到MessageTransportHandler
			pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0, 300));
			pipeline.addLast(new IoHandler());
		}
	}

}
