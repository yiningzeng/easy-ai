package com.baymin.scaffold.controller_v1;

import com.baymin.scaffold.config.okhttp3.MyOkHttpClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@Validated
@Api(description = "天气done")
public class WeatherController {

    @Value("${weather-url}")
    private String weatherUrl;

    /**
     * 天气
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询天气")
    @ApiImplicitParam(name = "authorization", value = "authorization token", required = true, dataType = "string", paramType = "header")
    @GetMapping(value = "/weather")
    public Object getWeather() throws Exception {
        return MyOkHttpClient.getInstance().get(weatherUrl);
    }
}
