package com.ljy.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljy.eduservice.entity.EduSubject;
import com.ljy.eduservice.entity.excel.SubjectData;
import com.ljy.eduservice.service.EduSubjectService;
import com.ljy.servicebase.exceptionhandler.GuliException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    public EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }
    public SubjectExcelListener() {

    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new GuliException(20001,"文件数据为空");
        }

        System.out.println("**************");

        EduSubject oneSubject = existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(oneSubject==null){
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(oneSubject);
        }
        String pid=oneSubject.getId();
        EduSubject twoSubject = existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(twoSubject==null){
            twoSubject= new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(twoSubject);
        }

    }

    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",name).eq("parent_id",0);

        EduSubject oneSubject = subjectService.getOne(queryWrapper);
        return oneSubject;
    }


    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pId){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",name).eq("parent_id",pId);

        EduSubject twoSubject = subjectService.getOne(queryWrapper);
        return twoSubject;
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
