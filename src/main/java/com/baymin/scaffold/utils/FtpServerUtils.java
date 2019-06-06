package com.baymin.scaffold.utils;

import com.baymin.scaffold.ret.model.tools.RetFtpConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//一个用户对应一个ftp，通过用户名检索
public class FtpServerUtils {

    @Data
    @AllArgsConstructor
    static class MyFtpServerConfig{
        private FtpServer ftpServer;
        private FtpServerFactory ftpServerFactory;
    }

    static HashMap<String,MyFtpServerConfig> ftpServerHashMap = new HashMap<>();

//    public static RetFtpConfig startFtp(String user, String pass, String ip, Integer port, String path){
//        RetFtpConfig retFtpConfig = new RetFtpConfig(user,ip,port,1);
//        MyFtpServerConfig myFtpServerConfig=ftpServerHashMap.get(user);
//
//        if(myFtpServerConfig == null){
//            FtpServerFactory serverFactory = new FtpServerFactory();
//            ListenerFactory factory = new ListenerFactory();
//            //设置监听端口
//            factory.setPort(port);
//            //替换默认监听
//            serverFactory.addListener(user, factory.createListener());
//            //用户名
//            BaseUser baseUser = new BaseUser();
//            baseUser.setName(user);
//            //密码 如果不设置密码就是匿名用户
//            baseUser.setPassword(pass);
//            //用户主目录
//            baseUser.setHomeDirectory(path);
//            List<Authority> authorities = new ArrayList<Authority>();
//            //增加写权限
//            authorities.add(new WritePermission());
//            baseUser.setAuthorities(authorities);
//            //增加该用户
//            try {
//                serverFactory.getUserManager().save(baseUser);
//                FtpServer server = serverFactory.createServer();
//                server.start();
//                ftpServerHashMap.put(user, new MyFtpServerConfig(server,serverFactory));
//            } catch (FtpException e) {
//                e.printStackTrace();
//            }
//        }
//        else {
//            retFtpConfig.setIp(myFtpServerConfig.getFtpServerFactory().getListener(user).getServerAddress());
//            retFtpConfig.setPort(myFtpServerConfig.getFtpServerFactory().getListener(user).getPort());
//            retFtpConfig.setStatus(1);
//        }
//        return retFtpConfig;

//    }

//    public static RetFtpConfig stopFtp(String user){
//        MyFtpServerConfig myFtpServerConfig=ftpServerHashMap.get(user);
//        if(myFtpServerConfig == null){
//            return new RetFtpConfig(user,null,null,0);
//        }
//        else {
//            myFtpServerConfig.getFtpServer().stop();
//            ftpServerHashMap.remove(user);
//            return new RetFtpConfig(user, null, null, 0);
//        }
//    }

}
