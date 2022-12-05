package com.ljy.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljy.commonutils.CourseWebVoOrder;
import com.ljy.commonutils.JWTUtils;
import com.ljy.commonutils.R;
import com.ljy.eduservice.client.OrdersClient;
import com.ljy.eduservice.entity.EduCourse;
import com.ljy.eduservice.entity.vo.chapter.ChapterVo;
import com.ljy.eduservice.entity.frontvo.CourseFrontVo;
import com.ljy.eduservice.entity.frontvo.CourseWebVo;
import com.ljy.eduservice.service.EduChapterService;
import com.ljy.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
 
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrdersClient ordersClient;

    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false)CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse=new Page<>(page,limit);
        Map<String,Object> map=courseService.getCourseFrontList(pageCourse,courseFrontVo);
        return R.ok().data(map);
    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
       CourseWebVo courseWebVo= courseService.getBaseCourseInfo(courseId);

        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        String memberId = JWTUtils.getMemberIdByJwtToken(request);
        boolean isBuyCourse=false;
        if(!StringUtils.isEmpty(memberId)){
            isBuyCourse = ordersClient.isBuyCourse(courseId, memberId);
        }


        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuyCourse",isBuyCourse);
    }

    @PostMapping("getCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("courseId") String courseId){
        CourseWebVo courseWebVo= courseService.getBaseCourseInfo(courseId);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseWebVo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
