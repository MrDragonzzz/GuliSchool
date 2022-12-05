package com.ljy.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljy.commonutils.R;
import com.ljy.eduservice.entity.EduCourse;
import com.ljy.eduservice.entity.EduTeacher;
import com.ljy.eduservice.service.EduCourseService;
import com.ljy.eduservice.service.EduTeacherService;
import javafx.scene.AmbientLight;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
 
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(pageTeacher);
        return R.ok().data(map);

    }

    @GetMapping("getInfoTeacherInfo/{teacherId}")
    public R getInfoTeacherInfo(@PathVariable String teacherId){
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);

        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }
}
