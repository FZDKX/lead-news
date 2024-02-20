package com.fzdkx.media.schedule;

import com.fzdkx.apis.article.IScheduleClient;
import com.fzdkx.media.service.MediaNewsAutoScanService;
import com.fzdkx.model.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author 发着呆看星
 * @create 2024/2/20
 */
@Component
@Slf4j
public class TaskConsumer {

    @Autowired
    private IScheduleClient scheduleClient;
    @Autowired
    private MediaNewsAutoScanService mediaNewsAutoScanService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("schedule.queue"),
            exchange = @Exchange(name = "schedule.direct"),
            key = {"schedule"}
    ))
    public void listenerSimpleQueue(String id) {
        if (!StringUtils.hasLength(id)) {
            return;
        }
        log.info("正在定时发布任务：newsId：{}", id);
        Long newsId = Long.valueOf(id);
        // 检查该新闻是否取消
        Result<Boolean> result = scheduleClient.isCancel(newsId);
        if (result.getData()) {
            // 如果取消，清除数据库记录，直接消费完成
            scheduleClient.removeData(newsId);
        } else {
            // 如果未取消，进行正常发布
            mediaNewsAutoScanService.autoScanWmNews(newsId);
        }
    }


    @RabbitListener(queuesToDeclare = @Queue("normal.queue"))
    public void listenerNormalQueue(String id) {
        if (!StringUtils.hasLength(id)) {
            return;
        }
        log.info("正在发送发布新闻消息：newsId：{}", id);
        // 正常发布
        mediaNewsAutoScanService.autoScanWmNews(Long.valueOf(id));
    }
}
