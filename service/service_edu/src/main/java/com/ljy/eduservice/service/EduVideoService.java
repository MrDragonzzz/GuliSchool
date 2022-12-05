package com.ljy.eduservice.service;

import com.ljy.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-21
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);
}
