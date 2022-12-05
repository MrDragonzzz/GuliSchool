package com.ljy.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljy.commonutils.JWTUtils;
import com.ljy.commonutils.R;
import com.ljy.commonutils.UcenterVo;
import com.ljy.eduservice.client.UcenterClient;
import com.ljy.eduservice.entity.EduComment;
import com.ljy.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-30
 */
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;
    @Autowired
    private UcenterClient ucenterClient;

    @GetMapping("{page}/{limit}")
    public R pageSelect(@PathVariable("page") long page,@PathVariable("limit")long limit ,String courseId){
        Page<EduComment> pageParam=new Page<>(page,limit);
        QueryWrapper<EduComment> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.eq("course_id",courseId);
        commentService.page(pageParam,wrapper);
        List<EduComment> commentList = pageParam.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return R.ok().data(map);

    }

    @PostMapping("auth/save")
    public R saveComment(@RequestBody EduComment comment, HttpServletRequest request){
        String memberId = JWTUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().message("未登录，请登录后再评论");

        }

        UcenterVo ucenterInfo = ucenterClient.getUserInfo(memberId);
        comment.setNickname(ucenterInfo.getNickname());
        comment.setAvatar(ucenterInfo.getAvatar());
        comment.setMemberId(memberId);
        commentService.save(comment);
        return R.ok();

    }

}

