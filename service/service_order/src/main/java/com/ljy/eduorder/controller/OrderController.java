package com.ljy.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljy.commonutils.JWTUtils;
import com.ljy.commonutils.R;
import com.ljy.eduorder.client.EduClient;
import com.ljy.eduorder.client.UcentClient;
import com.ljy.eduorder.entity.Order;
import com.ljy.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-30
 */
@RestController
@RequestMapping("/eduorder/order")
 
public class OrderController {
    @Autowired
    private OrderService orderService;

    
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        String memberId = JWTUtils.getMemberIdByJwtToken(request);
        String orderNo=orderService.createOrders(courseId,memberId);
        return R.ok().data("orderId",orderNo);
    }

    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

    @PostMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){

        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if(count==0){
            return  false;
        }
        return  true;

    }

}

