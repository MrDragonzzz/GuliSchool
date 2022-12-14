package com.ljy.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljy.commonutils.R;
import com.ljy.eduservice.entity.EduTeacher;
import com.ljy.eduservice.entity.vo.TeacherQuery;
import com.ljy.eduservice.service.EduTeacherService;
import com.ljy.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-13
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
 
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher(){

        List<EduTeacher> list = eduTeacherService.list(null);

        return R.ok().data("items",list);

    }
    @ApiOperation(value = "根据讲师ID删除")
    @DeleteMapping("{id}")
    public R deleteById( @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit){
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);


        eduTeacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,@PathVariable long limit
                               ,@RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> page = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        wrapper.orderByDesc("gmt_create");


        eduTeacherService.page(page,wrapper);

        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }

    }

    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable long id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    @PutMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher){
        boolean save = eduTeacherService.updateById(teacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }



}

