package com.fzdkx.freemarker.controller;

import com.fzdkx.freemarker.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/4
 */
@Controller
public class FreemarkerController {

    @GetMapping("/basic")
    public String test(Model model){
        model.addAttribute("name","freemarker");
        Student student = new Student();
        student.setAge(22);
        student.setName("fzdkx");
        student.setBirthday(new Date());
        student.setMoney(1000000.0f);
        model.addAttribute("student",student);
        return "01-basic";
    }

    @GetMapping("/list")
    public String list(Model model){

        Student stu1 = new Student();
        stu1.setName("小蓝");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());

        //小红对象模型数据
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19);

        //小红对象模型数据
        Student stu3 = new Student();
//        stu3.setName("fzdkx");
//        stu3.setMoney(2000000.1f);
//        stu3.setAge(22);

        //将两个对象模型数据存放到List集合中
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
        stus.add(stu3);

        //向model中存放List集合数据
        model.addAttribute("stus",stus);

        //------------------------------------

        //创建Map，存放数据
        HashMap<String,Student> stuMap = new HashMap<>();
        stuMap.put("stu1",stu1);
        stuMap.put("stu2",stu2);
        // 3.1 向model中存放Map数据
        model.addAttribute("stuMap", stuMap);

        Date date = new Date();
        model.addAttribute("date1",date);
        model.addAttribute("date2",date);

        return "02-list";
    }
}
