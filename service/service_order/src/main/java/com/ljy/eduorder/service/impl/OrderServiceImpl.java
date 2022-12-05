package com.ljy.eduorder.service.impl;

import com.ljy.commonutils.CourseWebVoOrder;
import com.ljy.commonutils.UcenterVo;
import com.ljy.eduorder.client.EduClient;
import com.ljy.eduorder.client.UcentClient;
import com.ljy.eduorder.entity.Order;
import com.ljy.eduorder.mapper.OrderMapper;
import com.ljy.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljy.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-30
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcentClient ucentClient;
    @Override
    public String createOrders(String courseId, String memberId) {
        UcenterVo userInfo = ucentClient.getUserInfo(memberId);
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        Order order = new Order();
        order.setOrderNo(order.getOrderNo());
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setEmail(userInfo.getEmail());
        order.setNickname(userInfo.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();


    }
}
