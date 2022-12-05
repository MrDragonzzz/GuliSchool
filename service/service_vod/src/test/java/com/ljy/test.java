package com.ljy;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class test {
    public static void main(String[] args) throws ClientException {
        getPlayAuth();


    }

    public static void getPlayAuth() throws ClientException {
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tGLtQsh77o5H1KUh8wu", "5FpCuz5qRJR3WKHwkO3GJoPu2sXCC6");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("09d031e87dbf4d759e5b3b83817e4b4f");
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        System.out.println(response.getPlayAuth());
    }

    public static void getPlayUrl() throws Exception{
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tGLtQsh77o5H1KUh8wu", "5FpCuz5qRJR3WKHwkO3GJoPu2sXCC6");
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response=new GetPlayInfoResponse();
        request.setVideoId("09d031e87dbf4d759e5b3b83817e4b4f");
        response=client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println("url:"+playInfo.getPlayURL());
        }
        System.out.println(response.getVideoBase().getTitle());
    }
}
