package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.CustomerReturnList;
import com.atguigu.jxc.entity.CustomerReturnListGoods;

import java.util.List;
import java.util.Map;

public interface CustomerReturnListDao {
    void savecustomerReturnListGoodsStr(CustomerReturnListGoods customerReturnListGood);

    void savecustomerReturnList(CustomerReturnList customerReturnList);

    List<Map<String, Object>> getReturnList(String returnNumber, Integer customerId, Integer state, String sTime, String eTime);

    List<Map<String, Object>> returnPolicy(Integer customerReturnListId);


    void deletecustomer(Integer customerReturnListId);
    void deletecustomerReturnList(Integer customerReturnListId);
}
