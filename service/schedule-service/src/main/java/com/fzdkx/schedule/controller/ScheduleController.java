package com.fzdkx.schedule.controller;

import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.schedule.bean.Task;
import com.fzdkx.schedule.service.impl.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 发着呆看星
 * @create 2024/2/20
 */
@RestController
@RequestMapping("/api/v1/task")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    /**
     * 添加定时任务
     */
    @PostMapping("/add")
    public Result<String> addTask(@RequestBody Task task){
        return scheduleService.addTask(task);
    }

    /**
     * 取消定时任务
     * @param id 新闻ID
     */
    @GetMapping("/cancel/{id}")
    public Result<String> cancelTask(@PathVariable("id") Long id){
        return scheduleService.cancelTask(id);
    }

    /**
     * 定时任务是否取消
     */
    @GetMapping("/isCancel")
    public Result<Boolean> isCancel(@RequestParam("id") Long id){
        return scheduleService.isCancel(id);
    }

    /**
     * 清除数据库中指定的 取消的定时任务记录
     */
    @GetMapping("/remove")
    public Result<String> removeData(@RequestParam("id") Long id){
        return scheduleService.removeData(id);
    }
}
