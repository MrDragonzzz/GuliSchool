package com.ljy.eduservice.controller;


import com.ljy.commonutils.R;
import com.ljy.eduservice.entity.subject.OneSubject;
import com.ljy.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-20
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
 
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list= subjectService.getAllOneTwoSubject();

        return R.ok().data("list",list);
    }
}

