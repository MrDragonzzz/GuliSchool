package com.ljy.eduservice.mapper;

import com.ljy.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljy.eduservice.entity.frontvo.CourseWebVo;
import com.ljy.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-11-21
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
        public CoursePublishVo getPublishCourseInfo(String CourseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
