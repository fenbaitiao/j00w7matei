package com.fentb.ftbapisdk.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求
 *

 */
@Data
public class GenChatByAiRequest implements Serializable {

    /**
     * 名字
     */
    private String name;

    private String goal;

    /**
     * 图表类型
     */
    private String chartType;
    private static final long serialVersionUID = 1L;
}