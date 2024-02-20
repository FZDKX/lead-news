package com.fzdkx.apis.article;

import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.schedule.bean.Task;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 发着呆看星
 * @create 2024/2/20
 */
@FeignClient(value = "schedule-service")
public interface IScheduleClient {
    /**
     * 添加定时任务
     */
    @PostMapping("/api/v1/task/add")
    Result<String> addTask(@RequestBody Task task);

    /**
     * 取消定时任务
     * @param id 新闻ID
     */
    @GetMapping("/api/v1/task/cancel/{id}")
    Result<String> cancelTask(@PathVariable("id") Long id);

    /**
     * 定时任务是否取消
     */
    @GetMapping("/api/v1/task/isCancel")
    Result<Boolean> isCancel(@RequestParam("id") Long id);

    /**
     * 清除数据库中指定的 取消的定时任务记录
     */
    @GetMapping("/api/v1/task/remove")
    Result<String> removeData(@RequestParam("id") Long id);
}
