package com.lara.c2c.constants;

public class ApplicationConfig {
	
	/*@Value("${application.appurl}")
	public static String APPURL;*/
	
	//public static final String APPURL = "http://localhost:8080/c2c";
	public static final String APPURL = "https://lara.co.in";
	//public static final String LOCALAPPURL = "http://localhost:4200";
	
	//kaleyra sms
	public static final String OTPAPIURL = "https://api-alerts.kaleyra.com/v4/?";
	public static final String OTPAPIKEY = "A776ee59ee5f35743d9d1b1a2e620f474";
	public static final String OTPMETHOD = "SMS";
	public static final String OTPMSGTEMPLATE = "Your One Time Password (OTP) is ";
	public static final String OTPSENDERID = "mylara";
	
	//paytm payment gateway
	public static final String MERCHANT_ID = "OIKTtV03367933913546";
	public static final String MERCHANT_KEY = "3R#fXgtrNMa4f8%W";
	public static final String CHANNEL_ID = "WEB";
	public static final String WEBSITE = "WEBSTAGING";
	public static final String INDUSTRY_TYPEI_D = "Retail";
	public static final String CALLBACK_URL = APPURL + "/api/orderp/pgresponse";
	public static final String SUCCESS_URL = APPURL + "/#/order/complete";
	//public static final String SUCCESS_URL_DEV = "http://localhost:4200/c2c/#/order/complete";
	
	//hdfc payment gateway
	//public static final String HDFC_TEST_URL = "https://test.ccavenue.com/transaction.do?command=initiateTransaction";
	public static final String HDFC_PROD_URL =  "https://secure.ccavenue.com/transaction/transaction.do?command=initiateTransaction";
	public static final String HDFC_MERCHANT_ID = "239349";
	public static final String HDFC_ACCESS_CODE = "AVRF88GK94BS35FRSB";
	public static final String HDFC_WORKING_KEY = "ACF34F1EBF756542D597E92F08C0BAE0";
	public static final String HDFC_REDIRECT_URL = APPURL + "/api/orderp/pgresponse";
	public static final String HDFC_SUCCESS_URL = APPURL + "/#/order/complete";
	public static final String HDFC_CANCEL_URL = APPURL + "/#/order/cancelled";
	public static final String HDFC_CANCEl_URL = APPURL + "/api/orderp/pgresponseCan";
	public static final String HDFC_PAYMENT_LANGUAGE = "EN";

	
	
	//order
	public static final String ORDER_ID_PREFIX = "OD";
	
	//unsubscribe
	public static final String REDIRECT_UNSUBSCRIBE_URL = APPURL + "/#/unsubscribe";
	public static final String REDIRECT_SUBSCRIBE_URL = APPURL + "/#/subscribe";
}
