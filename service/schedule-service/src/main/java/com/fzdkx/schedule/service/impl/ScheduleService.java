package com.fzdkx.schedule.service.impl;

import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.schedule.bean.Task;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

/**
 * @author 发着呆看星
 * @create 2024/2/20
 * 定时任务
 */
public interface ScheduleService {
    /**
     * 添加定时任务
     */
    Result<String> addTask(Task task);

    /**
     * 取消定时任务
     * @param id 新闻ID
     */
    Result<String> cancelTask(Long id);

    /**
     * 定时任务是否取消
     */
    @GetMapping("/api/v1/task/isCancel")
    Result<Boolean> isCancel(Long id);

    /**
     * 清除数据库中指定的 取消的定时任务记录
     */
    @GetMapping("/api/v1/task/remove")
    Result<String> removeData(Long id);
}
