package com.ljy.eduservice.controller;


import com.ljy.commonutils.R;
import com.ljy.eduservice.client.VodClient;
import com.ljy.eduservice.entity.EduVideo;
import com.ljy.eduservice.service.EduVideoService;
import com.ljy.servicebase.exceptionhandler.GuliException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-21
 */
@RestController
@RequestMapping("/eduservice/video")
 
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);

        return  R.ok();
    }

    @GetMapping("{id}")
    public R getVideo(@PathVariable String  id){
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("video",eduVideo);
    }

    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            R r = vodClient.removeAlyVideo(videoSourceId);
            if(r.getCode()==20001){
                throw new GuliException(20001,"删除视频失败，熔断器。。。");
            }
        }
        videoService.removeById(id);
        return R.ok();
    }

    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

}

