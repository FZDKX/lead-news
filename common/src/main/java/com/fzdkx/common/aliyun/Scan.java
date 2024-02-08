package com.fzdkx.common.aliyun;

import com.aliyun.imageaudit20191230.Client;
import com.aliyun.imageaudit20191230.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.fzdkx.common.exception.CustomException;
import com.fzdkx.model.common.enums.AppHttpCodeEnum;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Data
public class Scan {

    @Autowired
    private AliYunClient aliYunClient;

    public ScanTextResponse scanText(String content) throws Exception {
        if (!StringUtils.hasLength(content)){
            return null;
        }
        // 获取client
        Client client = aliYunClient.getClient();
        // 设置文本
        ScanTextRequest.ScanTextRequestTasks tasks = new ScanTextRequest.ScanTextRequestTasks()
                .setContent(content);
        // 设置标签
        ScanTextRequest.ScanTextRequestLabels labels = new ScanTextRequest.ScanTextRequestLabels()
                .setLabel("spam");
        // 创建扫描文本请求
        ScanTextRequest scanTextRequest = new ScanTextRequest()
                .setLabels(Arrays.asList(labels))
                .setTasks(Arrays.asList(tasks));
        // 创建运行时的选项
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印API的返回值
            ScanTextResponse response = client.scanTextWithOptions(scanTextRequest, runtime);
            return response;
        } catch (TeaException error) {
            // 直接抛出异常
            throw new CustomException(AppHttpCodeEnum.ALIYUN_ERROR);
        }
    }


    public ScanImageResponse scanImage(List<String> images) throws Exception {
        if (images == null || images.size() == 0) {
            return null;
        }
        // 创建连接客户端
        Client client = aliYunClient.getClient();
        // 创建Task集合
        List<ScanImageAdvanceRequest.ScanImageAdvanceRequestTask> taskList = new ArrayList<>();
        // 设置图片地址
        for (String image : images) {
            // 下载远程图片地址
            URL url = new URL(image);
            InputStream in = url.openConnection().getInputStream();
            // 封装Task数据
            ScanImageAdvanceRequest.ScanImageAdvanceRequestTask task
                    = new ScanImageAdvanceRequest.ScanImageAdvanceRequestTask();
            task.setDataId(image);
            task.setImageTimeMillisecond(1L);
            task.setInterval(1);
            task.setMaxFrames(1);
            task.setImageURLObject(in);
            // 间下载好的图片存入
            taskList.add(task);
        }


        // 设置鉴别场景
        // porn: 色情低俗
        // terrorism：血腥，暴力，敏感事件
        // ad: 垃圾广告
        // live: 赌，毒，吸烟
        // logo: 商标水印
        List<String> sceneList = new ArrayList<>();
        // 如果设置多个场景，那么会进行多场景计费
        sceneList.add("porn");
        // 设置数据，准备审查
        ScanImageAdvanceRequest scanImageAdvanceRequest
                = new ScanImageAdvanceRequest().setTask(taskList).setScene(sceneList);

        RuntimeOptions runtime = new RuntimeOptions();
        try {
            ScanImageResponse response = client.scanImageAdvance(scanImageAdvanceRequest, runtime);
            return response;
        } catch (com.aliyun.tea.TeaException teaException) {
            // 直接抛出异常
            throw new CustomException(AppHttpCodeEnum.ALIYUN_ERROR);
        }
    }

    // 解析返回结果
    public String analyzeTextResponse(ScanTextResponse response) {
        if (response == null) {
            // 直接抛出异常
            throw new CustomException(AppHttpCodeEnum.ALIYUN_ERROR);
        }
        Integer code = response.getStatusCode();
        // 如果不是 200
        if (code == null || !code.equals(200)) {
            // 直接抛出异常
            throw new CustomException(AppHttpCodeEnum.ALIYUN_ERROR);
        }
        List<ScanTextResponseBody.ScanTextResponseBodyDataElementsResults> results = response.getBody().getData().getElements().get(0).getResults();
        if (results == null || results.isEmpty()) {
            return "review";
        }
        ScanTextResponseBody.ScanTextResponseBodyDataElementsResults endResult = results.get(0);
        return endResult.suggestion;
    }

    // 解析返回结果
    public String analyzeImageResponse(ScanImageResponse response) {
        if (response == null) {
            // 直接抛出异常
            throw new CustomException(AppHttpCodeEnum.ALIYUN_ERROR);
        }
        Integer code = response.statusCode;
        // 如果不是 200
        if (code == null || !code.equals(200)) {
            // 直接抛出异常
            throw new CustomException(AppHttpCodeEnum.ALIYUN_ERROR);
        }
        HashMap<String, String> map = new HashMap<>();
        List<ScanImageResponseBody.ScanImageResponseBodyDataResults> results = response.getBody().getData().getResults();
        String flag = "pass";
        for (ScanImageResponseBody.ScanImageResponseBodyDataResults endResult : results) {
            List<ScanImageResponseBody.ScanImageResponseBodyDataResultsSubResults> subResults = endResult.subResults;
            ScanImageResponseBody.ScanImageResponseBodyDataResultsSubResults scanResult = subResults.get(0);
            if (scanResult.suggestion.equals("review")) {
                flag = "review";
            }else if (scanResult.suggestion.equals("block")){
                return "block";
            }
        }
        return flag;
    }


}