package com.fentb.ftbapisdk.client;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.fentb.ftbapisdk.model.User;
import com.fentb.ftbapisdk.request.GenChatByAiRequest;
import fbt.sx.common.utils.BaseResponse;
import fbt.sx.common.utils.ErrorCode;
import fbt.sx.common.utils.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fentb.ftbapisdk.utils.SignUtils.genSign;


/**
 * 调用第三方接口的客户端
 *

 */
public class FbtApiClient {

    private final String SALT = "fenbaitiao";
    private static final String GATEWAY_HOST = "http://8.134.147.36:23452";

    private String accessKey;
    private String secretKey;

    public FbtApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getAnser(String question) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("question", question);
//        String result = HttpUtil.post(GATEWAY_HOST + "/spark/askqustion", paramMap);
        String result = HttpRequest.post(GATEWAY_HOST + "/spark/askqustion")
                .header("accessKey", accessKey)//头信息，多个头信息多次调用此方法即可
                .header("sign",genSign(SALT,secretKey))
                .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        return result;
    }

    public String getChart(MultipartFile multipartFile, GenChatByAiRequest genChatByAiRequest) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("input", genChartString(multipartFile,genChatByAiRequest));

        String result = HttpRequest.post(GATEWAY_HOST + "/spark/bi")
                .header("accessKey", accessKey)//头信息，多个头信息多次调用此方法即可
                .header("sign",genSign(SALT,secretKey))
                .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();

        return result;
    }

    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        // 一定不能直接发送
//        hashMap.put("secretKey", secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", genSign(body, secretKey));
        return hashMap;
    }


    public String genChartString(MultipartFile multipartFile, GenChatByAiRequest genChatByAiRequest) {
        String chartType = genChatByAiRequest.getChartType();
        String goal = genChatByAiRequest.getGoal();

        //读取用户上传的文件  从excel表中拿取数据  尽可能压缩数据使用csv，接口有输入限制
        StringBuilder userInput = new StringBuilder();
        //用户输入
        userInput.append("分析目标:") .append(goal).append("生成" + chartType).append("\n");

        //压缩成csv的数据
        InputStream is = null;
        try {
            is = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String csvData = ExcelUtils.excelToCsv(is);

        userInput.append("数据如下:").append(csvData).append("\n");


        return  userInput.toString();
    }

}
