package com.baymin.scaffold.service;

import com.baymin.scaffold.entity.Level;
import com.baymin.scaffold.entity.User;
import com.baymin.scaffold.ret.exception.MyException;
import org.springframework.data.domain.Pageable;


/**
 * Created by baymin
 * 2018-08-08 11:43
 */
public interface ToolsService {


    /**
     * 通过用户名设置ftp
     * @param username
     * @return
     * @throws MyException
     */
    Object setFtp(String username)throws MyException;

}
