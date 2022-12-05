package com.ljy.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljy.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljy.eduservice.entity.frontvo.CourseFrontVo;
import com.ljy.eduservice.entity.frontvo.CourseWebVo;
import com.ljy.eduservice.entity.vo.CourseInfoVo;
import com.ljy.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-21
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
