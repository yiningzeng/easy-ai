package com.baymin.scaffold.service.impl;

import com.baymin.scaffold.dao.LevelDao;
import com.baymin.scaffold.dao.UserDao;
import com.baymin.scaffold.dao.specs.UserSpecs;
import com.baymin.scaffold.entity.User;
import com.baymin.scaffold.ret.R;
import com.baymin.scaffold.ret.enums.ResultEnum;
import com.baymin.scaffold.ret.exception.MyException;
import com.baymin.scaffold.ret.model.tools.RetFtpConfig;
import com.baymin.scaffold.service.ToolsService;
import com.baymin.scaffold.utils.FtpServerUtils;
import com.baymin.scaffold.utils.ShellKit;
import com.baymin.scaffold.utils.StreamGobblerCallback;
import com.baymin.scaffold.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ToolsServiceImpl implements ToolsService {

    @Value("${ftp.ip}")
    private String ip;
    @Value("${ftp.port}")
    private String port;
    @Value("${ftp.path}")
    private String basePath;
    @Value("${linux.root-password}")
    private String rootPassword;

    @Autowired
    private UserDao userDao;


    @Override
    public Object setFtp(String username) throws MyException {
        StreamGobblerCallback.Work work = new StreamGobblerCallback.Work();
        try {
            String ftpPath = username + "/" + Utils.getDay();
            String cmd = "echo "+ rootPassword +" | sudo -S mkdir -p "+ basePath + ftpPath;
            log.info("=======cmd: {}", cmd);
            ShellKit.runShell(cmd, work);
//            long now = System.currentTimeMillis();
            while (work.isDoing()){
                Thread.sleep(100);
            }
            cmd = "echo "+ rootPassword +" | sudo -S chmod -R 777 "+ basePath + ftpPath;
            ShellKit.runShell(cmd, work);
            log.info("用户目录创建成功"+work.getRes());
            String ftpUrl = "ftp://" + ip + ":" + port + "/" + ftpPath;
            User user = userDao.findFirstByUsername(username).orElse(new User());
            user.setUsername(username);
            user.setFtpUrl(ftpUrl);
            userDao.save(user);
            return R.success(new RetFtpConfig(ip, port, ftpUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(ResultEnum.FAIL_ACTION_MESSAGE);
        }
    }


    @Override
    public Object download(String username) throws MyException {
        try {
            List<File> list = Utils.getFileSort(basePath + username);
            File newFile = new File(basePath + username);
            if (list.size() > 0) newFile = list.get(0);
            String path = newFile.getPath();
            String fileName = newFile.getName();

            String tarFileName =  username + "-" + fileName + ".tar.gz";
            StreamGobblerCallback.Work work = new StreamGobblerCallback.Work();
            String cmd = "cd " + basePath +" && echo " + rootPassword + " | sudo -S tar -czvf " + tarFileName+ " " + path.replace(basePath, "");
            log.info("=======cmd: {}", cmd);
            ShellKit.runShell(cmd, work);
            while (work.isDoing()) {
                Thread.sleep(100);
            }

            cmd = "cd " + basePath +" && echo " + rootPassword + " | sudo -S mv " + tarFileName + " /opt/lampp/htdocs";
            log.info("=======cmd: {}", cmd);
            ShellKit.runShell(cmd, work);
            while (work.isDoing()){
                Thread.sleep(100);
            }
            return R.success("http://" + ip + "/" + tarFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        for (File file : list) {
//            System.out.println(file.getName() + " : " + file.lastModified());
//        }
        return 0;
    }
}
