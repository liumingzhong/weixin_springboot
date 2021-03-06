package com.beyondlmz.service;

import com.beyondlmz.alipay.core.Alipay;
import com.beyondlmz.alipay.core.AlipayBuilder;
import com.beyondlmz.alipay.model.pay.WapPayDetail;
import com.beyondlmz.alipay.model.pay.WebPayDetail;
import com.beyondlmz.alipay.model.refund.RefundDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Author: haolin
 * Email: haolin.h0@gmail.com
 * Date: 23/11/15
 */
@Service
public class AlipayService {

    @Value("${merchantId}")
    private String merchantId;

    @Value("${secret}")
    private String secret;

    @Value("${payNotifyUrl}")
    private String payNotifyUrl;

    @Value("${refundNotifyUrl}")
    private String refundNotifyUrl;

    @Value("${webReturnUrl}")
    private String webReturnUrl;

    @Value("${wapReturnUrl}")
    private String wapReturnUrl;

    private Alipay alipay;

    @PostConstruct
    public void initAlipay(){
        alipay = AlipayBuilder
                    .newBuilder(merchantId, secret)
                    .build();

        System.err.println(alipay);
    }

    /**
     * web支付
     */
    public String webPay(WebPayDetail detail){
        detail.setNotifyUrl(payNotifyUrl);
        detail.setReturnUrl(webReturnUrl);
        return alipay.pay().webPay(detail);
    }

    /**
     * wap支付
     */
    public String wapPay(WapPayDetail detail) {
        detail.setNotifyUrl(payNotifyUrl);
        detail.setReturnUrl(wapReturnUrl);
        return alipay.pay().wapPay(detail);
    }

    /**
     * MD5验证
     */
    public Boolean notifyVerifyMd5(Map<String, String> params){
        return alipay.verify().md5(params);
    }

    /**
     * 退款申请
     */
    public Boolean refund(RefundDetail detail){
        detail.setNotifyUrl(refundNotifyUrl);
        return alipay.refund().refund(detail);
    }
}
