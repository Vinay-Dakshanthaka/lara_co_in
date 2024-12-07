 package com.lara.c2c.controller;


import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

//import com.ccavenue.security.AesCryptUtil;
import com.lara.c2c.constants.ApplicationConfig;
import com.lara.c2c.dto.user.LoggedInUserResponse;
import com.lara.c2c.dto.user.UserDetailDto;
import com.lara.c2c.model.Cart;
import com.lara.c2c.model.Coupon;
import com.lara.c2c.model.Order;
import com.lara.c2c.model.PaymentDetail;
import com.lara.c2c.service.CouponCodeService;
import com.lara.c2c.service.CoursePackageService;
import com.lara.c2c.service.OrderProcessingService;
import com.lara.c2c.service.PaymentDetailService;
import com.lara.c2c.service.UserService;
import com.lara.c2c.utility.ServiceUtils;
import com.lara.c2c.utility.UserInfo;
//import com.paytm.merchant.CheckSumServiceHelper;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orderp")
public class OrderProcessingController {
	
	@Autowired
	private OrderProcessingService orderProcessingService;
	
	@Autowired
	HttpServletRequest hsRequest;
	
	@Autowired
	private CoursePackageService coursePackageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentDetailService paymentDetailService;
	
	@Value("${tax.gst.percentage}")
	private Integer gstPercentage;
	
	@Autowired
	private CouponCodeService couponCodeService;
	
	@PostMapping("/addCart")
	public ResponseEntity<Void> insertCartRecord( @RequestBody Cart cartRequest){					
		orderProcessingService.insertIntoCart(cartRequest);
		return ResponseEntity.ok().build();	
		
	}	
		
	/*@GetMapping("/processOrder_paytm/{coursePackageId}")
	public String processCourseOrder_paytm(@PathVariable int coursePackageId) throws Exception {
		String merchantMid = ApplicationConfig.MERCHANT_ID;		
		String merchantKey = ApplicationConfig.MERCHANT_KEY;		
		Random randomGenerator = new Random();
    	String orderId = ApplicationConfig.ORDER_ID_PREFIX+ randomGenerator.nextInt(1000000);
		String channelId = ApplicationConfig.CHANNEL_ID;
		LoggedInUserResponse response = UserInfo.getUserDetails((HttpServletRequest) hsRequest);
		String custId = response.getUserId().toUpperCase();
		UserDetailDto userDetailDto = userService._fetchUserDetails(custId);
		String mobileNo = userDetailDto.getMobileNo();
		String email = userDetailDto.getEmail();
		
		double cpackPrice = coursePackageService.getCpackPriceByCpackId(coursePackageId);
		String txnAmount = ""+cpackPrice;
		//String txnAmount = "1";
		String website = ApplicationConfig.WEBSITE;
		// This is the staging value. Production value is available in your dashboard
		String industryTypeId = ApplicationConfig.INDUSTRY_TYPEI_D;
		// This is the staging value. Production value is available in your dashboard
		String callbackUrl = ApplicationConfig.CALLBACK_URL;
		TreeMap<String, String> paytmParams = new TreeMap<String, String>();
		
		Order orderRequest = new Order();
		orderRequest.setOrderId(orderId);
		orderRequest.setUserId(UserInfo.getUserId(hsRequest));
		orderRequest.setChannelId(channelId);
		orderRequest.setOrderStatus("CREATED");
		orderRequest.setDate(ServiceUtils.getCurrentDateTime());
		String cPackId = ""+coursePackageId;
		orderRequest.setOrderItems(cPackId);
		orderProcessingService.createOrder(orderRequest);
		
		
		paytmParams.put("MID",merchantMid);
		paytmParams.put("ORDER_ID",orderId);
		paytmParams.put("CHANNEL_ID",channelId);
		paytmParams.put("CUST_ID",custId);
		paytmParams.put("MOBILE_NO",mobileNo);
		paytmParams.put("EMAIL",email);
		paytmParams.put("TXN_AMOUNT",txnAmount);
		paytmParams.put("WEBSITE",website);
		paytmParams.put("INDUSTRY_TYPE_ID",industryTypeId);
		paytmParams.put("CALLBACK_URL", callbackUrl);
		String paytmChecksum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(merchantKey, paytmParams);
		StringBuilder outputHtml = new StringBuilder();
		outputHtml.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
		outputHtml.append("<html>");
		outputHtml.append("<head>");
		outputHtml.append("<title>Merchant Checkout Page</title>");
		outputHtml.append("</head>");
		outputHtml.append("<body>");
		outputHtml.append("<center><h1>Please do not refresh this page...</h1></center>");
		String transactionURL="https://securegw-stage.paytm.in/theia/processTransaction";  	// for staging
		// String transactionURL="https://securegw.paytm.in/theia/processTransaction";  // for production
		outputHtml.append("<form method='post' action='"+transactionURL+"' name='f1'>");
		for(Map.Entry<String,String> entry : paytmParams.entrySet()) {
		    outputHtml.append("<input type='hidden' name='"+entry.getKey()+"' value='"+entry.getValue()+"'>");
		}
		outputHtml.append("<input type='hidden' name='CHECKSUMHASH' value='"+paytmChecksum+"'>");
		//outputHtml.append("<input type='submit' value='Submit'>");
		outputHtml.append("</form>");
		outputHtml.append("<script type='text/javascript'>");
		outputHtml.append("document.f1.submit();");
		outputHtml.append("</script>");
		outputHtml.append("</body>");
		outputHtml.append("</html>");
		return outputHtml.toString();
	}*/
	
	/*
	public static final String HDFC_PROD_URL =  "https://secure.ccavenue.com/transaction/transaction.do?command=initiateTransaction";
	public static final String HDFC_MERCHANT_ID = "239349";
	public static final String HDFC_ACCESS_CODE = "AVRF88GK94BS35FRSB";
	public static final String HDFC_WORKING_KEY = "ACF34F1EBF756542D597E92F08C0BAE0";
	public static final String HDFC_REDIRECT_URL = APPURL + "/api/orderp/pgresponse";
	public static final String HDFC_SUCCESS_URL = APPURL + "/#/order/complete";
	public static final String HDFC_CANCEL_URL = APPURL + "/#/order/cancelled";
	public static final String HDFC_CANCEl_URL = APPURL + "/api/orderp/pgresponseCan";
	public static final String HDFC_PAYMENT_LANGUAGE = "EN";
	 */
	
//	@GetMapping("/processOrder/{coursePackageId}/{couponCode}")
//	public String processCourseOrder(@PathVariable int coursePackageId, @PathVariable String couponCode) throws Exception {
//		System.out.println("I am from processCourseOrder with coursePackageId:" + coursePackageId);
//		String merchantMid = ApplicationConfig.HDFC_MERCHANT_ID;		
//		String accessCode = ApplicationConfig.HDFC_ACCESS_CODE;		
//		String workingKey = ApplicationConfig.HDFC_WORKING_KEY;		
//		Random randomGenerator = new Random();
//    	String orderId = ApplicationConfig.ORDER_ID_PREFIX+ randomGenerator.nextInt(1000000000);
//		String channelId = ApplicationConfig.CHANNEL_ID;
//		LoggedInUserResponse response = UserInfo.getUserDetails((HttpServletRequest) hsRequest);
//		String custId = response.getUserId().toUpperCase();
//		UserDetailDto userDetailDto = userService._fetchUserDetails(custId);
//		String mobileNo = userDetailDto.getMobileNo();
//		String email = userDetailDto.getEmail();
//		Order orderRequest = new Order();
//		double cpackPrice = coursePackageService.getCpackPriceByCpackId(coursePackageId);
//		
//		if(!couponCode.equals("none") && coursePackageId == 26) {
//			Coupon coupon = couponCodeService.getCoupon(couponCode);
//			cpackPrice = cpackPrice - (cpackPrice* coupon.getReductionPercentage()  / 100);
//			orderRequest.setCouponCode(couponCode);
//		}
//		
//		cpackPrice = cpackPrice + ((cpackPrice * gstPercentage) / 100);
//		
//		String txnAmount = ""+cpackPrice;
//		
//		String currentSessionId = hsRequest.getSession().getId();
//		String website = ApplicationConfig.WEBSITE;		
//		String industryTypeId = ApplicationConfig.INDUSTRY_TYPEI_D;
//		String redirectUrl = ApplicationConfig.HDFC_REDIRECT_URL + ";jsessionid=" + currentSessionId;
//		String cancelUrl = ApplicationConfig.HDFC_CANCEl_URL + ";jsessionid=" + currentSessionId;
//		
//		
//		String currentSessionTimeId = currentSessionId + orderId;
//		
//		TreeMap<String, String> paymentParams = new TreeMap<String, String>();
//		
//		
//		orderRequest.setOrderId(orderId);
//		orderRequest.setUserId(UserInfo.getUserId(hsRequest));
//		orderRequest.setChannelId(channelId);
//		orderRequest.setUserCurrOrderSessTimeId(currentSessionTimeId);
//		orderRequest.setOrderStatus("Created");
//		orderRequest.setDate(ServiceUtils.getCurrentDateTime());
//		String cPackId = ""+coursePackageId;
//		orderRequest.setOrderItems(cPackId);
//		orderProcessingService.createOrder(orderRequest);
//		
//		String tid = ""+randomGenerator.nextInt(1000000000) + randomGenerator.nextInt(100);
//		paymentParams.put("tid",tid);
//		paymentParams.put("merchant_id",merchantMid);
//		paymentParams.put("order_id",orderId);
//		paymentParams.put("currency","INR");
//		paymentParams.put("amount",txnAmount);
//		/*paymentParams.put("MOBILE_NO",mobileNo);
//		paymentParams.put("EMAIL",email);
//		paymentParams.put("TXN_AMOUNT",txnAmount);*/
//		paymentParams.put("redirect_url", redirectUrl);
//		paymentParams.put("merchant_param1", custId);
//		
//		paymentParams.put("merchant_param2", currentSessionId);
//		paymentParams.put("merchant_param3",mobileNo);
//		paymentParams.put("merchant_param4",currentSessionTimeId);
//		paymentParams.put("cancel_url", cancelUrl);
//		paymentParams.put("language", ApplicationConfig.HDFC_PAYMENT_LANGUAGE);
//
//		String ccaRequest="", pname="", pvalue="";
//		 for(Map.Entry<String,String> entry : paymentParams.entrySet()) {
//			 pname = ""+entry.getKey();
//			 pvalue = ""+entry.getValue();
//			 ccaRequest = ccaRequest + pname + "=" + pvalue + "&";
//		 }
//		 System.out.println("ccaRequest:" + ccaRequest); 
//		 AesCryptUtil aesUtil=new AesCryptUtil(workingKey);
//		 String encRequest = aesUtil.encrypt(ccaRequest);
//		
//		StringBuilder formHtml = new StringBuilder();
//		formHtml.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
//		formHtml.append("<html>");
//		formHtml.append("<head>");
//		formHtml.append("<title>Merchant Checkout Page</title>");
//		formHtml.append("</head>");
//		formHtml.append("<body>");
//		formHtml.append("<center><h1>Please do not refresh this page...</h1></center>");
//		//String transactionUrl= ApplicationConfig.HDFC_TEST_URL;
//		String transactionUrl= ApplicationConfig.HDFC_PROD_URL;
//		formHtml.append("<form id='nonseamless' method='post' action='"+transactionUrl+"' name='redirect'>");
//		formHtml.append("<input type='hidden' id='encRequest' name='encRequest' value='"+encRequest+"'>");
//		formHtml.append("<input type='hidden' id='access_code' name='access_code' value='"+accessCode+"'>");
//		//formHtml.append("<input type='submit' value='Submit' />");
//		formHtml.append("</form>");
//		formHtml.append("<script type='text/javascript'>");
//		formHtml.append("document.redirect.submit();");
//		formHtml.append("</script>");
//		formHtml.append("</body>");
//		formHtml.append("</html>");
//		//System.out.println("processCourseOrder response string: " + formHtml.toString());
//		return formHtml.toString();
//	}
	
	/*@RequestMapping(value="/paytmpgresponse", consumes = MediaType.ALL_VALUE)
	public String getResponseRedirectpaytm(HttpServletRequest request) {
		String userId = UserInfo.getUserId(hsRequest);
		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String,String> parameters = new TreeMap<String,String>();
		mapData.forEach((key,val)-> parameters.put(key, val[0]));
		String paytmChecksum =  "";
		if(mapData.containsKey("CHECKSUMHASH"))
		{
			paytmChecksum = mapData.get("CHECKSUMHASH")[0];
		}
		String result;
		
		boolean isValideChecksum = false;
		try{
			isValideChecksum = validateCheckSum(parameters, paytmChecksum);
			if(isValideChecksum && parameters.containsKey("RESPCODE")){
				orderProcessingService.addPaymentDetails(parameters);
				String orderStatus = "CREATED";
				if(parameters.get("RESPCODE").equals("01")){	
					orderStatus = "SUCCESS";
					result = parameters.toString();
				}else{					
					orderStatus = "FAILED";
					result="<b>Payment Failed.</b>";
				}
				orderProcessingService.updateOrderStatus(parameters.get("ORDERID"), orderStatus, userId);
				if(orderStatus.equals("SUCCESS")) {
					orderProcessingService.updateLrCoursesByOrderId(parameters.get("ORDERID"), userId);
				}
			}else{
				result="<b>Checksum mismatched.</b>";
			}
		}catch(Exception e){
			result=e.toString();
		}
		StringBuilder outputHtml = new StringBuilder();
		outputHtml.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
		outputHtml.append("<html>");
		outputHtml.append("<head>");
		outputHtml.append("<title>Merchant Checkout Page</title>");
		outputHtml.append("</head>");
		outputHtml.append("<body>");
		outputHtml.append("<center><h1>Please do not refresh this page...</h1></center>");
		String redirectUrl = ApplicationConfig.SUCCESS_URL;
		//String redirectUrl = "http://localhost:4200/order/complete";  	// for staging
		// String transactionURL="https://securegw.paytm.in/theia/processTransaction";  // for production
		outputHtml.append("<a id='f2' href='"+redirectUrl+"' name = 'f2'>Return</a>");
		outputHtml.append("<script type='text/javascript'>");
		outputHtml.append("document.getElementById('f2').click();");
		outputHtml.append("</script>");
		outputHtml.append("</body>");
		outputHtml.append("</html>");
		return outputHtml.toString();
	}*/

//	@RequestMapping(value="/pgresponse", consumes = MediaType.ALL_VALUE)
//	public String getResponseRedirect(HttpServletRequest request) {
//		//System.out.println("i am from pgresponse strted:" + request.getParameterMap());
//		String workingKey = ApplicationConfig.HDFC_WORKING_KEY;		//32 Bit Alphanumeric Working Key should be entered here so that data can be decrypted.
//		String encResp= request.getParameter("encResp");
//		AesCryptUtil aesUtil=new AesCryptUtil(workingKey);
//		String decResp = aesUtil.decrypt(encResp);
//		StringTokenizer tokenizer = new StringTokenizer(decResp, "&");
//		Hashtable hs=new Hashtable();
//		String pair=null, pname=null, pvalue=null;
//		while (tokenizer.hasMoreTokens()) {
//			pair = (String)tokenizer.nextToken();
//			if(pair!=null) {
//				StringTokenizer strTok=new StringTokenizer(pair, "=");
//				pname=""; pvalue="";
//				if(strTok.hasMoreTokens()) {
//					pname=(String)strTok.nextToken();
//					if(strTok.hasMoreTokens())
//						pvalue=(String)strTok.nextToken();
//					hs.put(pname, pvalue);
//				}
//			}
//		}
//		String pgReturnedSessionId = hs.get("merchant_param2").toString();		
//		String pgReturnedCurrSessionId = hs.get("merchant_param4").toString();
//		String pgOrderStatus = hs.get("order_status").toString();
//		String pgOrderId = hs.get("order_id").toString();
//		String failureMessage = "";
//		String orderId = "";
//		String orderStatus = "Failed";
//		if(pgReturnedSessionId.equals(hsRequest.getSession().getId())) {
//			orderId = orderProcessingService.getOrderIdBycurrOrderSessTimeId(pgReturnedCurrSessionId);
//			if(orderId.equals(pgOrderId)) {
//				orderStatus = pgOrderStatus;
//			}
//		}else{
//			failureMessage = "Invalid session Id returned from payment gateway";
//		}		
//		
//		String userId = hs.get("merchant_param1").toString();
//		double amount = Double.parseDouble(hs.get("amount").toString());
//		int responseCode = Integer.parseInt(hs.get("response_code").toString());
//		String retry = hs.get("retry").toString();
//		String customerId = userId;
//		orderProcessingService.updateOrderStatus(pgOrderId, orderStatus, userId);
//		
//		if(orderStatus.equals("Success")) {			
//			orderProcessingService.updateLrCoursesByOrderId(orderId, userId);
//		}
//		System.out.println("i am from pgresponse middle");
//
//		PaymentDetail paymentDetail = new PaymentDetail();
//		paymentDetail.setCustomerId(userId);
//		paymentDetail.setCardName(hs.get("card_name").toString());
//		paymentDetail.setBankRefNo(hs.get("bank_ref_no").toString());
//		paymentDetail.setCurrency(hs.get("currency").toString());
//		paymentDetail.setOrderId(orderId);
//		paymentDetail.setPaymentMode(hs.get("payment_mode").toString());
//		
//		paymentDetail.setResponseCode(responseCode);
//		paymentDetail.setAmount(amount);		
//		paymentDetail.setFailureMessage(failureMessage);
//		paymentDetail.setTransDate(hs.get("trans_date").toString());
//		paymentDetail.setTrackingId(hs.get("tracking_id").toString());
//		paymentDetail.setMerAmount(amount);	
//		paymentDetail.setOfferType(hs.get("offer_type").toString());
//		paymentDetail.setResponseCode(responseCode);
//		paymentDetail.setOfferCode(hs.get("offer_code").toString());
//		paymentDetail.setStatusCode(hs.get("status_code").toString());
//		paymentDetail.setVault(hs.get("vault").toString());
//		paymentDetail.setFailureMessage(hs.get("failure_message").toString());
//		paymentDetail.setRetry(hs.get("retry").toString());
//		paymentDetail.setEciValue(hs.get("eci_value").toString());
//		paymentDetail.setStatusMessage(hs.get("status_message").toString());
//		paymentDetail.setStatusCode(hs.get("status_code").toString());
//		paymentDetail.setOrderStatus(orderStatus);
//		paymentDetail.setDiscountValue(Double.parseDouble(hs.get("discount_value").toString()));
//		paymentDetailService.savePaymentDetail(paymentDetail);
//		
//		
//		/*Enumeration enumeration = hs.keys();
//		StringBuilder outputHtml = new StringBuilder();
//		outputHtml.append("<table>");
//		while(enumeration.hasMoreElements()) {
//			pname=""+enumeration.nextElement();
//			pvalue=""+ hs.get(pname);
//			outputHtml.append("<tr>");
//			outputHtml.append("<td>"+pname+"</td>");
//			outputHtml.append("<td>"+pvalue+"</td>");
//			outputHtml.append("</tr>");
//		}			
//		outputHtml.append("</table>");
//		RedirectAttributes attributes = null;
//		attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
//        attributes.addAttribute("attribute", "redirectWithRedirectView");
//        String redirectedUrl = ApplicationConfig.HDFC_SUCCESS_URL;*/
//		System.out.println("i am from pgresponse before html");
//
//		
//		StringBuilder outputHtml = new StringBuilder();
//		outputHtml.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
//		outputHtml.append("<html>");
//		outputHtml.append("<head>");
//		outputHtml.append("<title>Merchant Checkout Page</title>");
//		outputHtml.append("</head>");
//		outputHtml.append("<body>");
//		outputHtml.append("<center><h1>Please do not refresh this page...</h1></center>");
//		String redirectUrl = ApplicationConfig.HDFC_SUCCESS_URL+"/"+orderId;
//		//String redirectUrl = "http://localhost:4200/order/complete";  	// for staging
//		// String transactionURL="https://securegw.paytm.in/theia/processTransaction";  // for production
//		outputHtml.append("<a id='f2' href='"+redirectUrl+"' name = 'f2'>Return</a>");
//		outputHtml.append("<script type='text/javascript'>");
//		outputHtml.append("document.getElementById('f2').click();");
//		outputHtml.append("</script>");
//		outputHtml.append("</body>");
//		outputHtml.append("</html>");
//		//System.out.println("i am from pgresponse end");
//
//		return outputHtml.toString();
//		
//	}
	

//	@RequestMapping(value="/pgresponse", consumes = MediaType.ALL_VALUE, method=RequestMethod.GET)
//	public String getResponseRedirect(HttpServletRequest request) {
//		System.out.println("i am from pgresponse strted");
//		return "i am from pgresponse strted";
//		
//	}

	
	
//	@RequestMapping(value="/pgresponseCan", consumes = MediaType.ALL_VALUE)
//	public String getCanResponseRedirect(HttpServletRequest request) {
//		System.out.println("cancel started");
//		String workingKey = ApplicationConfig.HDFC_WORKING_KEY;		//32 Bit Alphanumeric Working Key should be entered here so that data can be decrypted.
//		String encResp= request.getParameter("encResp");
//		AesCryptUtil aesUtil=new AesCryptUtil(workingKey);
//		String decResp = aesUtil.decrypt(encResp);
//		StringTokenizer tokenizer = new StringTokenizer(decResp, "&");
//		Hashtable hs=new Hashtable();
//		String pair=null, pname=null, pvalue=null;
//		while (tokenizer.hasMoreTokens()) {
//			pair = (String)tokenizer.nextToken();
//			if(pair!=null) {
//				StringTokenizer strTok=new StringTokenizer(pair, "=");
//				pname=""; pvalue="";
//				if(strTok.hasMoreTokens()) {
//					pname=(String)strTok.nextToken();
//					if(strTok.hasMoreTokens())
//						pvalue=(String)strTok.nextToken();
//					hs.put(pname, pvalue);
//				}
//			}
//		}
//		String pgReturnedSessionId = hs.get("merchant_param2").toString();		
//		String pgReturnedCurrSessionId = hs.get("merchant_param4").toString();
//		String pgOrderStatus = hs.get("order_status").toString();
//		String pgOrderId = hs.get("order_id").toString();
//		System.out.println("order cancelked id:" + pgOrderId);
//		String failureMessage = "";
//		String orderId = "";
//		String orderStatus = "Failed";
//		if(pgReturnedSessionId.equals(hsRequest.getSession().getId())) {
//			orderId = orderProcessingService.getOrderIdBycurrOrderSessTimeId(pgReturnedCurrSessionId);
//			if(orderId.equals(pgOrderId)) {
//				orderStatus = pgOrderStatus;
//			}
//		}else{
//			failureMessage = "Invalid session Id returned from payment gateway";
//		}		
//		
//		String retry = hs.get("retry").toString();
//		String userId = UserInfo.getUserId(hsRequest);
//		
//		
//		if(orderStatus.equals("Aborted")) {
//			String orderStatusMessage = hs.get("status_message").toString();
//			orderProcessingService.updateAbortedOrder(pgOrderId, orderStatus, orderStatusMessage, userId);
//		}
//		StringBuilder outputHtml = new StringBuilder();
//		outputHtml.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
//		outputHtml.append("<html>");
//		outputHtml.append("<head>");
//		outputHtml.append("<title>Merchant Checkout Page</title>");
//		outputHtml.append("</head>");
//		outputHtml.append("<body>");
//		outputHtml.append("<center><h1>Please do not refresh this page...</h1></center>");
//		String redirectUrl = ApplicationConfig.HDFC_CANCEL_URL+"/"+orderId;
//		//String redirectUrl = "http://localhost:4200/order/complete";  	// for staging
//		// String transactionURL="https://securegw.paytm.in/theia/processTransaction";  // for production
//		outputHtml.append("<a id='f2' href='"+redirectUrl+"' name = 'f2'>Return</a>");
//		outputHtml.append("<script type='text/javascript'>");
//		outputHtml.append("document.getElementById('f2').click();");
//		outputHtml.append("</script>");
//		outputHtml.append("</body>");
//		outputHtml.append("</html>");
//		System.out.println("cancel end");
//		return outputHtml.toString();
//		
//	}
	
//	private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
//		return CheckSumServiceHelper.
//				getCheckSumServiceHelper().
//				verifycheckSum(ApplicationConfig.MERCHANT_KEY, parameters,paytmChecksum);
//	}
	
	@RequestMapping(value="/pgresponse1", consumes = MediaType.ALL_VALUE)
	public RedirectView redirectWithUsingRedirectView(HttpServletRequest request) {
		RedirectAttributes attributes = null;;
		attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
        attributes.addAttribute("attribute", "redirectWithRedirectView");
        String redirectedUrl = "http://localhost:4200/order";
        return new RedirectView(redirectedUrl);
    }
	
	@RequestMapping(value="/pgresponse2", consumes = MediaType.ALL_VALUE)
	public String getResponseRedirect123(HttpServletRequest request) {
		
		StringBuilder outputHtml = new StringBuilder();
		outputHtml.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
		outputHtml.append("<html>");
		outputHtml.append("<head>");
		outputHtml.append("<title>Merchant Checkout Page</title>");
		outputHtml.append("</head>");
		outputHtml.append("<body>");
		outputHtml.append("<center><h1>Please do not refresh this page...</h1></center>");
		String redirectUrl = "http://localhost:4200/order/complete";  	// for staging
		// String transactionURL="https://securegw.paytm.in/theia/processTransaction";  // for production
		outputHtml.append("<a id='f2' href='"+redirectUrl+"' name = 'f2'>Return</a>");
		outputHtml.append("<script type='text/javascript'>");
		outputHtml.append("document.getElementById('f2').click();");
		outputHtml.append("</script>");
		outputHtml.append("</body>");
		outputHtml.append("</html>");
		return outputHtml.toString();
	}
	
	@GetMapping("/fetchOrderDetails/{orderId}")
	public ResponseEntity<Order> getOrderDetailsByOrderId(@PathVariable String orderId){
		String userId = UserInfo.getUserId(hsRequest);
		Order orderResponse = orderProcessingService._getOrderDetailsByOrderIdAndUserId(orderId, userId);
		return ResponseEntity.ok().body(orderResponse);
	}
	
//	@GetMapping("/check1")	
//	public ResponseEntity<String> checkOrde1(){
//		System.out.println("I am from checkOrder");
//		return ResponseEntity.ok().body("success1");
//	}
//	@GetMapping("/check2")	
//	public ResponseEntity<String> checkOrde2(HttpServletRequest request){
//		System.out.println("I am from checkOrder");
//		return ResponseEntity.ok().body("success2");
//	}
}









