package com.ljy.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.ljy.eduservice.entity.EduSubject;
import com.ljy.eduservice.entity.excel.SubjectData;
import com.ljy.eduservice.entity.subject.OneSubject;
import com.ljy.eduservice.entity.subject.TwoSubject;
import com.ljy.eduservice.listener.SubjectExcelListener;
import com.ljy.eduservice.mapper.EduSubjectMapper;
import com.ljy.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-20
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {

        try {
            InputStream in =file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id",0);
        List<EduSubject> oneList = list(wrapperOne);

        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id",0);
        List<EduSubject> twoList = list(wrapperTwo);

        ArrayList<OneSubject> finalSubjectList = new ArrayList<>();

        for (EduSubject oneEduSubject : oneList) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(oneEduSubject,oneSubject);



            List<TwoSubject> twoFinalSubjectList=new ArrayList<>();
            for (EduSubject twoEduSubject : twoList) {
                if (twoEduSubject.getParentId().equals(oneEduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoEduSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }

            oneSubject.setChildren(twoFinalSubjectList);

            finalSubjectList.add(oneSubject);
        }
        return finalSubjectList;
    }
}
