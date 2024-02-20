package com.fzdkx.schedule.service;

import com.fzdkx.model.common.enums.AppHttpCodeEnum;
import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.schedule.bean.Task;
import com.fzdkx.schedule.mapper.TaskMapper;
import com.fzdkx.schedule.service.impl.ScheduleService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author 发着呆看星
 * @create 2024/2/20
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public Result<String> addTask(Task task) {
        if (task == null || task.getId() == null) {
            return Result.error(AppHttpCodeEnum.PARAM_INVALID);
        }
        Date publishTime = task.getPublishTime();
        String id = task.getId().toString();
        String queueName;
        Message message;
        // 代表不延迟发布，直接发送消息至MQ标准队列
        if (publishTime == null) {
            queueName = "normal.queue";
            message = MessageBuilder
                    .withBody(id.getBytes(StandardCharsets.UTF_8))
                    .build();
        }
        // 发送延迟消息
        else {
            // 计算出延迟时间
            long timeout = publishTime.getTime() - System.currentTimeMillis();
            if (timeout < 0) {
                return Result.error(AppHttpCodeEnum.PARAM_INVALID);
            }
            // 准备消息，设置延迟时间
            message = MessageBuilder
                    .withBody(id.getBytes(StandardCharsets.UTF_8))
                    .setExpiration(String.valueOf(timeout))
                    .build();
            // 普通消息对象名，该队列绑定了死信队列
            queueName = "simple.queue";
        }
        // 向MQ发送消息 新闻id + UUID
        rabbitTemplate.convertAndSend(queueName, message);
        return Result.success();
    }

    @Override
    public Result<String> cancelTask(Long id) {
        // 取消新闻的定时发布
        if (id == null) {
            return Result.error(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 将该新闻保存至数据库
        taskMapper.insert(id);
        return Result.success();
    }

    @Override
    public Result<Boolean> isCancel(Long newsId) {
        Long id = taskMapper.select(newsId);
        if (id != null) {
            return Result.success(true);
        }
        return Result.success(false);
    }

    @Override
    public Result<String> removeData(Long id) {
        if (id == null) {
            return Result.error(AppHttpCodeEnum.PARAM_INVALID);
        }
        taskMapper.delete(id);
        return Result.success();
    }
}
