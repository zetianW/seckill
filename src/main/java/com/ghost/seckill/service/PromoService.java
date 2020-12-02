package com.ghost.seckill.service;

import com.ghost.seckill.service.model.PromoModel;

/**
 * Description
 *
 * @author Zetian
 * @date 03, 12 2020
 */
public interface PromoService {
    /**
     * 根据itemId获取即将进行的或者正在进行的秒杀活动
     * @param itemId
     * @return
     */
    PromoModel getPromoByItemId(Integer itemId);
}
