package com.baymin.scaffold.config.netty;

import antlr.debug.MessageEvent;
import com.baymin.scaffold.utils.Base64Utils;
import com.baymin.scaffold.utils.ImageIO;
import com.baymin.scaffold.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    String fileByte;
    ByteBuf buf = Unpooled.buffer(502400);



    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channelActive----->");
//        fileByte="";
        buf.setZero(0,buf.capacity());
        buf.clear();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try{
            log.info("server channelRead......");
            log.info(ctx.channel().remoteAddress()+"----->Server :"+ msg.toString());
//        fileByte += msg.toString();
            buf.writeBytes(Utils.toByteArray(msg));
//        System.out.println("server channelRead......");
//        System.out.println(ctx.channel().remoteAddress()+"----->Server :"+ msg.toString());
            //将客户端的信息直接返回写入ctx
            ctx.write("server say :"+msg);
            //刷新缓存区
            ctx.flush();
        }
        catch (Exception e){
            e.printStackTrace();
            log.info("eeeeeeeeeeeeeeeeeeeeeeee"+e.getMessage());
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        byte[] data = fileByte.getBytes();
        ImageIO.byte2image(buf.array(),"/home/baymin/" + System.currentTimeMillis() + ".jpg");
//        Base64Utils.Base64ToImage(fileByte, "/home/baymin/" + System.currentTimeMillis() + ".jpg");
        log.info("server channelReadComplete......");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
