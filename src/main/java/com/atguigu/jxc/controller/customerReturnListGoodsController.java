package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.CustomerReturnList;
import com.atguigu.jxc.service.CustomerReturnListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/customerReturnListGoods")
public class customerReturnListGoodsController {

    @Autowired
    CustomerReturnListService customerReturnListService;
        @PostMapping("/save")
    public ServiceVO   creditOrder(CustomerReturnList customerReturnList, String customerReturnListGoodsStr){
        customerReturnListService.save(customerReturnList,customerReturnListGoodsStr);
        return  new ServiceVO<>(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);
    }
    @PostMapping("/list")
    public Map<String,Object>   returnsSingle(String returnNumber, Integer customerId, Integer state, String sTime,String eTime){
     return    customerReturnListService.returnsSingle( returnNumber, customerId, state,sTime,eTime);
    }

    @PostMapping("goodsList")
    public Map<String,Object>   returnPolicy(Integer customerReturnListId){
          return   customerReturnListService.returnPolicy(customerReturnListId);
    }

    @PostMapping("/delete")
    public ServiceVO materialRejectBill(Integer customerReturnListId){
            customerReturnListService.materialRejectBill(customerReturnListId);
            return new ServiceVO<>(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);
    }

}
