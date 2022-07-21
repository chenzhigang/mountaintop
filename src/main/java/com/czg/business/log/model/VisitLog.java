package com.czg.business.log.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(description = "请求访问日志类", value = "VisitLog")
@Data
public class VisitLog {

    @ApiModelProperty(value = "年月：格式yyyyMM", name = "yearMonth")
    private String yearMonth;

    @ApiModelProperty(value = "主键id，自增", name = "id")
    private Integer id;

    @ApiModelProperty(value = "请求类型", name = "requestMethod")
    private String requestMethod;

    @ApiModelProperty(value = "请求地址", name = "requestUri")
    private String requestUri;

    @ApiModelProperty(value = "请求体", name = "body")
    private String body;

    @ApiModelProperty(value = "请求头（json）", name = "header")
    private String header;

    @ApiModelProperty(value = "请求参数（json）", name = "requestParam")
    private String requestParam;

    @ApiModelProperty(value = "返回参数", name = "response")
    private String response;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间（请求时间）", name = "createTime")
    private Date createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "响应时间（返回时间）", name = "returnTime")
    private Date returnTime;
}
