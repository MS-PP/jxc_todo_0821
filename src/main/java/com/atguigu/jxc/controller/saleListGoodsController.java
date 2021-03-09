package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.SaleList;
import com.atguigu.jxc.service.saleListGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/saleListGoods")
public class saleListGoodsController {

    @Autowired
    saleListGoodsService saleListGoodsService;

    /**
     * 销售单保存
     * @param saleList
     * @param saleListGoodsStr
     * @return
     */
    @PostMapping("/save")
      public  ServiceVO salesChanBaoCun(SaleList saleList, String saleListGoodsStr){
        saleListGoodsService.save(saleList,saleListGoodsStr);
     return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);

    }
    @PostMapping("/list")
    public Map<String, Object> list(String returnNumber, Integer customerId, Integer state, String sTime, String eTime) {
        return  saleListGoodsService.list(returnNumber,customerId,state,sTime,eTime);
    }

    @PostMapping("/goodsList")
    public Map<String,Object>  productInformation(Integer saleListId){
        return saleListGoodsService.productInformation(saleListId);
    }

    @PostMapping("/delete")
    public ServiceVO  deleteSales(Integer saleListId){
        saleListGoodsService.deleteSales(saleListId);
        return new ServiceVO<>(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);
    }
}
