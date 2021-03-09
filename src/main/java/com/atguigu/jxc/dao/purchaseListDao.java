package com.atguigu.jxc.dao;


import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.entity.PurchaseListGoods;

import java.util.List;
import java.util.Map;

public interface purchaseListDao {
    Integer savepurchase(PurchaseList purchaseList);

    Integer savepurchaseList(PurchaseListGoods purchaseListGoods);

    List<Map<String, Object>> purchaseSingleTable(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime);
}
