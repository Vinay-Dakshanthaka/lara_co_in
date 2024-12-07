//package com.lara.c2c.utility;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.lara.c2c.dto.ParamsRequest;
//import com.phonepe.sdk.pg.Env;
//import com.phonepe.sdk.pg.common.http.PhonePeResponse;
//import com.phonepe.sdk.pg.payments.v1.PhonePePaymentClient;
//import com.phonepe.sdk.pg.payments.v1.models.request.PgPayRequest;
//import com.phonepe.sdk.pg.payments.v1.models.response.PayPageInstrumentResponse;
//import com.phonepe.sdk.pg.payments.v1.models.response.PgPayResponse;
//
//
//public class PhonePeStandardCheckout {
//
//    @Autowired
//    private PhonePeUtils phonePeUtils;
//
//    Env env = Env.PROD;
//    boolean shouldPublishEvents = true;
//
//    public String getPaymentUrl(ParamsRequest paramsRequest) {
//	PhonePePaymentClient phonepeClient = new PhonePePaymentClient(phonePeUtils.getMERCHANT_ID(),
//		phonePeUtils.getSALT_KEY(), Integer.valueOf(phonePeUtils.getSALT_INDEX()), env, shouldPublishEvents);
//
//	PgPayRequest pgPayRequest = PgPayRequest.PayPagePayRequestBuilder()
//						.amount(paramsRequest.getAmount())
//						.merchantId(phonePeUtils.getMERCHANT_ID())
//						.merchantTransactionId(paramsRequest.getOrderId())
//						.merchantUserId(paramsRequest.getUserId())
//						.redirectMode("REDIRECT")
//						.redirectUrl(phonePeUtils.getFRONTEND_URL() + "#/cartDetails?orderId=" + paramsRequest.getOrderId())
//						.callbackUrl(phonePeUtils.getBACKEND_URL() + "c2c/api/orders/v2/confirmOrder/" + paramsRequest.getOrderId() + "/" + phonePeUtils.getMERCHANT_ID())
//						.build();
//	
//	PhonePeResponse<PgPayResponse> payResponse = phonepeClient.pay(pgPayRequest);  
//	PayPageInstrumentResponse payPageInstrumentResponse = (PayPageInstrumentResponse) payResponse.getData().getInstrumentResponse();  
//	String url = payPageInstrumentResponse.getRedirectInfo().getUrl();
//	
//	System.out.println(url);
//	return url;
//    }
//
//}
