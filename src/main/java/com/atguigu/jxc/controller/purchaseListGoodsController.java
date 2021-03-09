package com.atguigu.jxc.controller;


import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.service.purchaseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("purchaseListGoods")
public class purchaseListGoodsController {
        @Autowired
        purchaseListService purService;

        @PostMapping
        public  Map<String,Object> purchaseSingleTable(String purchaseNumber, Integer supplierId, Integer state, String sTime,String eTime){
                return   purService.purchaseSingleTable(purchaseNumber,supplierId,state,sTime,eTime);

        }

        /**
         * 进货单保存
         * @param purchaseList
         * @param purchaseListGoodsStr
         * @return
         */
        @PostMapping("/save")
        public ServiceVO save( PurchaseList purchaseList, String purchaseListGoodsStr){
                 purService.save(purchaseList,purchaseListGoodsStr);
                return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);

        }

        /**
         * 退货单保存
         * @param returnList
         * @param returnListGoodsStr
         * @return
         */
        @PostMapping("save?returnNumber")
        public ServiceVO  returnNumber(ReturnList returnList, String returnListGoodsStr){
                return null;
        }

        /**
         * 进货单列表展示
         * @param purchaseNumber
         * @param supplierId
         * @param state
         * @param sTime
         * @param eTime
         * @return
         */
        @PostMapping("list")
        public Map<String,Object>  list(String purchaseNumber, Integer supplierId, Integer state, String sTime,String eTime){
                return null;
        }




}
