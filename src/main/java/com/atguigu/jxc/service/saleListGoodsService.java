package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.SaleList;

import java.util.Map;

public interface saleListGoodsService {
    ServiceVO save(SaleList saleList, String saleListGoodsStr);

    Map<String, Object> list(String returnNumber, Integer customerId, Integer state, String sTime, String eTime);

    Map<String, Object> productInformation(Integer saleListId);

    void deleteSales(Integer saleListId);
}
