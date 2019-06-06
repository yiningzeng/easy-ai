package com.baymin.scaffold.config.netty_client;

import com.baymin.scaffold.utils.Base64Utils;
import com.baymin.scaffold.utils.ImageIO;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Client {
    static final String HOST = System.getProperty("host", "0.0.0.0");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8888"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {
        ImageIO.byte2image(ImageIO.image2byte("/home/baymin/data/素材/x4/HD1.jpg"),"/home/baymin/aaaa.jpg");
        sendMessage(ImageIO.image2byte("/home/baymin/data/素材/x4/HD1.jpg"));
//        sendMessage(Base64Utils.ImageToBase64ByLocal("/home/baymin/data/素材/x4/HD1.jpg"));
    }

    public static void sendMessage(byte[] content) throws InterruptedException{
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast("decoder", new StringDecoder());
                            p.addLast("encoder", new StringEncoder());
                            p.addLast(new ClientHandler());
                        }
                    });

            ChannelFuture future = b.connect(HOST, PORT).sync();
            future.channel().writeAndFlush(content);
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
