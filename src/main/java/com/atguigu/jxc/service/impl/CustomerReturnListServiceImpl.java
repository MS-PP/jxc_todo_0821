package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.CustomerReturnListDao;
import com.atguigu.jxc.dao.GoodsDao;
import com.atguigu.jxc.dao.UserDao;
import com.atguigu.jxc.dao.saleListGoodsDao;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.*;
import com.atguigu.jxc.service.CustomerReturnListService;
import com.atguigu.jxc.service.LogService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerReturnListServiceImpl implements CustomerReturnListService {
    @Autowired
    UserDao userDao;
    @Autowired
    CustomerReturnListDao customerReturnListDao;
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    LogService logService;

    @Override
    public void save(CustomerReturnList customerReturnList, String customerReturnListGoodsStr) {
        Gson gson = new Gson();
        List<CustomerReturnListGoods> customerReturnListGoods= gson.fromJson(customerReturnListGoodsStr,new TypeToken<List<CustomerReturnListGoods>>(){
        }.getType());
        //设置当前操作用户
        User userByName = userDao.findUserByName((String) SecurityUtils.getSubject().getPrincipal());
        customerReturnList.setUserId(userByName.getUserId());
        customerReturnListDao.savecustomerReturnList(customerReturnList);
        for (CustomerReturnListGoods customerReturnListGood : customerReturnListGoods) {
            customerReturnListGood.setCustomerReturnListId(customerReturnList.getCustomerReturnListId());
            customerReturnListDao.savecustomerReturnListGoodsStr(customerReturnListGood);
            Goods goodsId = goodsDao.findByGoodsId(customerReturnListGood.getGoodsId());
            goodsId.setInventoryQuantity(goodsId.getInventoryQuantity()-customerReturnListGood.getGoodsNum());
            goodsId.setState(1);
            goodsDao.updateGoods(goodsId);
        }
        logService.save(new Log(Log.INSERT_ACTION, "新增商品单：" + customerReturnList.getReturnNumber()));

    }

    @Override
    public Map<String, Object> returnsSingle(String returnNumber, Integer customerId, Integer state, String sTime, String eTime) {
        Map<String,Object> result = new HashMap<>();
        List<Map<String,Object>> returnList = customerReturnListDao.getReturnList(returnNumber, customerId, state, sTime, eTime);
        logService.save(new Log(Log.SELECT_ACTION, "退货单查询"));
        result.put("rows",returnList);
        return result;

    }

    @Override
    public Map<String, Object> returnPolicy(Integer customerReturnListId) {
        HashMap<String, Object> map = new HashMap<>();
    List<Map<String,Object>> customerReturnList= customerReturnListDao.returnPolicy(customerReturnListId);
    logService.save(new Log(Log.SELECT_ACTION,"退货单商品信息"));
    map.put("rows",customerReturnList);
        return map;
    }

    @Override
    public void materialRejectBill(Integer customerReturnListId) {
        customerReturnListDao.deletecustomer(customerReturnListId);
        logService.save(new Log(Log.DELETE_ACTION,"删除退货单详情表"));
        customerReturnListDao.deletecustomerReturnList(customerReturnListId);
        logService.save(new Log(Log.DELETE_ACTION,"删除退货单"));
    }
}
