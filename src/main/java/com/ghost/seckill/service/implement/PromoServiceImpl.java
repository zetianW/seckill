package com.ghost.seckill.service.implement;

import com.ghost.seckill.dao.PromoDOMapper;
import com.ghost.seckill.dataobject.PromoDO;
import com.ghost.seckill.service.PromoService;
import com.ghost.seckill.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Description
 *
 * @author Zetian
 * @date 03, 12 2020
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {

        PromoDO promoDO = null;

        try{
            promoDO = promoDOMapper.selectByItemId(itemId);
        }catch (Exception e){
            e.printStackTrace();
        }


        //dataObject->model
        PromoModel promoModel = convertFromDataObject(promoDO);
        if(promoModel == null){
            return null;
        }

        //判断当前时间是否比秒杀时间早或者晚

        //比当前时间晚
        if(promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1);
            //比当前时间还要提前
        }else if(promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);
        }else{
            promoModel.setStatus(2);
        }


        return promoModel;
    }

    private PromoModel convertFromDataObject(PromoDO promoDO) {
        if (promoDO == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        promoModel.setId(promoDO.getId());
        promoModel.setItemId(promoDO.getItemId());
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setPromoName(promoDO.getPromoName());
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));

        return promoModel;
    }
}
