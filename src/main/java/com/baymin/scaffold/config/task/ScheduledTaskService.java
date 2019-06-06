package com.baymin.scaffold.config.task;

import com.baymin.scaffold.utils.ShellKit;
import com.baymin.scaffold.utils.StreamGobblerCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ScheduledTaskService {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    @Scheduled(fixedRate = 5000)//1
//    public void reportCurrentTime(){
////        System.out.println("每隔五秒执行一次 "+DATE_FORMAT.format(new Date()));
////        StreamGobblerCallback.Work work = new StreamGobblerCallback.Work();
////        try {
////            ShellKit.runShell("ping -w 1 192.168.31.1", work);
//////            long now = System.currentTimeMillis();
////            while (work.isDoing()){
////                Thread.sleep(100);
////            }
////            log.info("结束"+work.getRes());
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }

    Integer checkIsOnline(String ip){
        StreamGobblerCallback.Work work = new StreamGobblerCallback.Work();
        try {
            ShellKit.runShell("ping -w 5 "+ip, work);
//            long now = System.currentTimeMillis();
            while (work.isDoing()){
                Thread.sleep(100);
            }
            log.info("结束"+work.getRes());
            if(work.getRes().contains("icmp_seq=") && work.getRes().contains("ttl=")){
                return 1;
            }
            else return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //@Scheduled(cron = "0 0/30 * * * ? ")//
    @Scheduled(cron = "0 0/30 * * * ? ")//
    public void fixTimeExecution(){
        log.info("在指定时间 "+DATE_FORMAT.format(new Date())+"执行");
    }

    @Scheduled(cron = "0 0/30 * * * ? ")//
    public void mysqlBackup(){
        log.info("数据库备份 "+new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date())+"执行");
//        StreamGobblerCallback.Work work = new StreamGobblerCallback.Work();
//        try {
//            ShellKit.runShell("/opt/lampp/bin/mysqldump -u root restroom --result-file=\"/baymin/mysql-restroom-bak/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".sql\" ", work);
////            long now = System.currentTimeMillis();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
