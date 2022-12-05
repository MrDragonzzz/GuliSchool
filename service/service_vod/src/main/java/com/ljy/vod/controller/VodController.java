package com.ljy.vod.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.ljy.commonutils.R;
import com.ljy.servicebase.exceptionhandler.GuliException;
import com.ljy.vod.service.VodService;
import com.ljy.vod.utils.ConstantVodUtils;
import com.ljy.vod.utils.InitVodClient;
import net.bytebuddy.implementation.bytecode.assign.primitive.VoidAwareAssigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
 
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file){

        String VideoId=vodService.uploadVideo(file);
        return R.ok().data("videoId",VideoId);
    }
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
       vodService.removeAlyVideo(id);
       return R.ok();
    }

    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List videoIdList){

        vodService.removeMoreVideo(videoIdList);
        return R.ok();

    }

    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){

        DefaultAcsClient client =
                InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
        GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
        request.setVideoId(id);
        try {
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            throw new GuliException(20001,"获取视频凭证失败");
        }
    }
}
