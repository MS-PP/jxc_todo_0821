package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.SaleList;
import com.atguigu.jxc.entity.SaleListGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface saleListGoodsDao {
   Integer saveSaleList(SaleList saleList);


    Integer savesaleListGoodsStr(SaleListGoods saleListGood);

    List<Map<String, Object>> getReturnList(String returnNumber, Integer customerId, Integer state, String sTime, String eTime);

    List<SaleListGoods> productInformation(Integer saleListId);

    Integer deleteSales(@Param("saleListId")Integer saleListId);

    Integer deleteListSales(@Param("saleListId")Integer saleListId);
}
