package com.ljy.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljy.commonutils.R;
import com.ljy.eduservice.entity.EduCourse;
import com.ljy.eduservice.entity.EduTeacher;
import com.ljy.eduservice.entity.vo.CourseInfoVo;
import com.ljy.eduservice.entity.vo.CoursePublishVo;
import com.ljy.eduservice.entity.vo.CourseQuery;
import com.ljy.eduservice.entity.vo.TeacherQuery;
import com.ljy.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-21
 */
@RestController
@RequestMapping("/eduservice/course")
 
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;


    @GetMapping
    public R getList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id=courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo=courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
       CoursePublishVo coursePublishVo= courseService.publishCourseInfo(id);
        return R.ok().data("pubishCourse",coursePublishVo);
    }

    @PostMapping("publishCourse/{id}")
    @CacheEvict(value="banner",key = "'index'",allEntries = true)
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse=new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current, @PathVariable long limit
            , @RequestBody(required = false)CourseQuery courseQuery){
        Page<EduCourse> page = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        wrapper.orderByDesc("gmt_create");


        courseService.page(page,wrapper);

        long total = page.getTotal();
        List<EduCourse> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

