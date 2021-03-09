package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.PurchaseList;

import java.util.Map;

public interface purchaseListService {


    ServiceVO save( PurchaseList purchaseList, String purchaseListGoodsStr);


    Map<String, Object> purchaseSingleTable(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime);
}
