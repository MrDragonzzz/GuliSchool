package com.ljy.eduservice.client;

import com.ljy.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideoFileDegradeFeignClient implements VodClient{
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}
