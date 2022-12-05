package com.ljy.eduorder.service;

import com.ljy.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-30
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String memberId);
}
