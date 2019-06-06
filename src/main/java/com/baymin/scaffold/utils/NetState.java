package com.baymin.scaffold.utils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class NetState {

//    public static void main(String[] args) {
//
//        StreamGobblerCallback.Work work = new StreamGobblerCallback.Work();
//        try {
//            ShellKit.runShell("ping -w 1 192.168.31.1", work);
////            long now = System.currentTimeMillis();
//            while (work.isDoing()){
//                Thread.sleep(100);
//            }
//            log.info("结束"+work.getRes());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public static void main(String[] args){
        try {
            Socket socket=new Socket("localhost",8888);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(outputStream);
            printWriter.write("$tmb00035ET3318/08/22 11:5804029.94,027.25,20.00,20.00$");
            printWriter.flush();
            socket.shutdownOutput();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
