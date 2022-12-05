package com.ljy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljy.eduservice.entity.EduChapter;
import com.ljy.eduservice.entity.EduVideo;
import com.ljy.eduservice.entity.vo.chapter.ChapterVo;
import com.ljy.eduservice.entity.vo.chapter.VideoVo;
import com.ljy.eduservice.mapper.EduChapterMapper;
import com.ljy.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljy.eduservice.service.EduVideoService;
import com.ljy.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-21
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapperChapter=new QueryWrapper<>();
        wrapperChapter.eq("course_Id",courseId);
        List<EduChapter> eduChapterList = this.list(wrapperChapter);

        QueryWrapper<EduVideo> wrapperVideo=new QueryWrapper<>();
        wrapperVideo.eq("course_Id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        List<ChapterVo> finalList=new ArrayList<>();
        for (EduChapter eduChapter : eduChapterList) {

            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);

            List<VideoVo> videoList=new ArrayList<>();
            for (EduVideo eduVideo : eduVideoList) {
                if (eduChapter.getId().equals(eduVideo.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoList);

            finalList.add(chapterVo);
        }
        return finalList;

    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper=new QueryWrapper<>();

        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if(count>0){
            throw new GuliException(20001,"不能删除");
        }else{
            boolean b = this.removeById(chapterId);
            return b;
        }

    }

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        remove(queryWrapper);
    }
}
