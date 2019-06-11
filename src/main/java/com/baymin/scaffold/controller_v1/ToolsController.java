package com.baymin.scaffold.controller_v1;

import com.baymin.scaffold.config.okhttp3.MyOkHttpClient;
import com.baymin.scaffold.service.ToolsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@Validated
@Api(description = "ftp")
public class ToolsController {

    @Autowired
    private ToolsService toolsService;
    /**
     * ftp
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "开始上传数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", required = true, dataType = "string",paramType = "path"),
    })
    @GetMapping(value = "/ftp/{user}")
    public Object setFtp(@PathVariable("user") String user) throws Exception {
        return toolsService.setFtp(user);
    }

    @ApiOperation(value = "打包下载最新日期数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", required = true, dataType = "string",paramType = "path"),
    })
    @GetMapping(value = "/download/{user}")
    public Object download(@PathVariable("user") String user) throws Exception {
        return toolsService.download(user);
    }
}
