package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.GoodsDao;
import com.atguigu.jxc.dao.UserDao;
import com.atguigu.jxc.dao.purchaseListDao;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.*;
import com.atguigu.jxc.service.LogService;
import com.atguigu.jxc.service.purchaseListService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class purchaseListServiceImpl implements purchaseListService {


    @Resource
    purchaseListDao purchaseListDao;
    @Autowired
    UserDao userDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private LogService logService;


    @Override
    public ServiceVO save(PurchaseList purchaseList, String purchaseListGoodsStr) {
        Gson gson = new Gson();
        List<PurchaseListGoods>    PurchaseListGoodsList=gson.fromJson(purchaseListGoodsStr,new TypeToken<List<PurchaseListGoods>>(){
        }.getType());
        //设置当前操作用户
        User userByName = userDao.findUserByName((String) SecurityUtils.getSubject().getPrincipal());
            purchaseList.setUserId(userByName.getUserId());
            purchaseListDao.savepurchase(purchaseList);
        for (PurchaseListGoods purchaseListGoods : PurchaseListGoodsList) {
            purchaseListGoods.setPurchaseListId(purchaseList.getPurchaseListId());
            purchaseListDao.savepurchaseList(purchaseListGoods);
            //相应商品库存进行减少
            Goods goodsId = goodsDao.findByGoodsId(purchaseListGoods.getGoodsId());
            //库存的数量减去进货的数量
            goodsId.setInventoryQuantity(goodsId.getInventoryQuantity()+purchaseListGoods.getGoodsNum());
            goodsId.setState(2);
            goodsDao.updateGoods(goodsId);
        }
        logService.save(new Log(Log.INSERT_ACTION, "新增商品单：" + purchaseList.getPurchaseNumber()));
        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public Map<String, Object> purchaseSingleTable(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime) {
        HashMap<String, Object> map = new HashMap<>();

     List<Map<String,Object>> returnList = purchaseListDao.purchaseSingleTable(purchaseNumber,supplierId,state,sTime,eTime);
       logService.save(new Log(Log.SELECT_ACTION,"进货单列表展示"));
        map.put("row",returnList);
        return map;
    }
}

