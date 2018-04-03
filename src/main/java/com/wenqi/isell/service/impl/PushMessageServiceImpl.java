package com.wenqi.isell.service.impl;

import com.wenqi.isell.config.WeChatAccountConfig;
import com.wenqi.isell.dto.OrderDTO;
import com.wenqi.isell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/4/3 下午1:51
 * @Modified By:
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WeChatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {


        try {
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder().build();
            templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
            templateMessage.setToUser(orderDTO.getBuyerOpenid());

            templateMessage.addWxMpTemplateData(new WxMpTemplateData("first", "亲，请记得收货。"));
            templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword1", "微信点餐"));
            templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword2", "18888888888"));
            templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword3", orderDTO.getOrderId()));
            templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnum().getMessage()));
            templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword5", "￥" + orderDTO.getOrderAmount()));
            templateMessage.addWxMpTemplateData(new WxMpTemplateData("remark", "欢迎再次光临！"));

            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("【消息模板消息】 发送失败,{}", e);
        }

    }
}
