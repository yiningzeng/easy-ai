package com.baymin.scaffold.ret.model.tools;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RetFtpConfig {
    @ApiModelProperty(value = "ftp的ip")
    private String ip;
    @ApiModelProperty(value = "ftp端口")
    private String port;
    @ApiModelProperty(value = "需要上传的路径")
    private String path;
}
