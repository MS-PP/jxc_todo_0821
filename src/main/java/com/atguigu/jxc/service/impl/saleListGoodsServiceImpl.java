package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.GoodsDao;
import com.atguigu.jxc.dao.UserDao;
import com.atguigu.jxc.dao.saleListGoodsDao;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.*;
import com.atguigu.jxc.service.LogService;
import com.atguigu.jxc.service.saleListGoodsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class saleListGoodsServiceImpl implements saleListGoodsService {

    @Autowired
    UserDao userDao;
    @Autowired
    saleListGoodsDao saleListGoodsDao;
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    LogService logService;
    @Override
    public ServiceVO save(SaleList saleList, String saleListGoodsStr) {
        Gson gson = new Gson();
       List<SaleListGoods>   saleListGoods= gson.fromJson(saleListGoodsStr, new TypeToken<List<SaleListGoods>>() {
        }.getType());
        User userByName = userDao.findUserByName((String) SecurityUtils.getSubject().getPrincipal());
        saleList.setUserId(userByName.getUserId());
        System.out.println(saleList.getSaleListId()+"-----------");
        saleListGoodsDao.saveSaleList(saleList);
        System.out.println(saleList.getSaleListId()+"==============");
        for (SaleListGoods saleListGood : saleListGoods) {
            System.out.println(saleList.getSaleListId());
            saleListGood.setSaleListId(saleList.getSaleListId());
            saleListGoodsDao.savesaleListGoodsStr(saleListGood);
            Goods goodsId = goodsDao.findByGoodsId(saleListGood.getGoodsId());
            goodsId.setInventoryQuantity(goodsId.getInventoryQuantity()+saleListGood.getGoodsNum());
            goodsId.setState(2);
            goodsDao.updateGoods(goodsId);

        }
        logService.save(new Log(Log.INSERT_ACTION,"新增销售单"+saleList.getSaleNumber()));
        return new ServiceVO<>(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);
    }

    @Override
    public Map<String, Object> list(String returnNumber, Integer customerId, Integer state, String sTime, String eTime) {
        Map<String,Object> result = new HashMap<>();
        List<Map<String,Object>> returnList = saleListGoodsDao.getReturnList(returnNumber, customerId, state, sTime, eTime);
        logService.save(new Log(Log.SELECT_ACTION, "销售单据查询"));
        result.put("rows",returnList);
        return result;
    }

    @Override
    public Map<String, Object> productInformation(Integer saleListId) {
        HashMap<String, Object> map = new HashMap<>();

        System.out.println("saleListId = " + saleListId);
        List<SaleListGoods>  saleListGoods=saleListGoodsDao.productInformation(saleListId);
        saleListGoods.forEach(saleListGood->{
            logService.save(new Log(Log.SELECT_ACTION,"销售单商品信息"));
        });
           map.put("rows",saleListGoods);
        System.out.println(map+"map");
       return map;
    }

    @Override
    public void deleteSales(Integer saleListId) {
        saleListGoodsDao.deleteSales(saleListId);
        logService.save(new Log(Log.DELETE_ACTION,"销售 表单的子表删除"));
        saleListGoodsDao.deleteListSales(saleListId);
        logService.save(new Log(Log.DELETE_ACTION,"删除销售表单"));

    }
}
