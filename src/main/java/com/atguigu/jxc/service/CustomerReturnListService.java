package com.atguigu.jxc.service;

import com.atguigu.jxc.entity.CustomerReturnList;

import java.util.Map;

public interface CustomerReturnListService {
    void save(CustomerReturnList customerReturnList, String customerReturnListGoodsStr);

    Map<String, Object> returnsSingle(String returnNumber, Integer customerId, Integer state, String sTime, String eTime);

    Map<String, Object> returnPolicy(Integer customerReturnListId);

    void materialRejectBill(Integer customerReturnListId);
}
