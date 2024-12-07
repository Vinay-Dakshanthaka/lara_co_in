//package com.lara.c2c.controller;
//
//import java.util.Map;
//import java.util.Random;
//import java.util.TreeMap;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.lara.c2c.constants.ApplicationConfig;
//import com.lara.c2c.dto.user.LoggedInUserResponse;
//import com.lara.c2c.model.Order;
//import com.lara.c2c.model.PaymentDetail;
//import com.lara.c2c.service.CoursePackageService;
//import com.lara.c2c.service.OrderProcessingService;
//import com.lara.c2c.service.PaymentDetailService;
//import com.lara.c2c.utility.ServiceUtils;
//import com.lara.c2c.utility.UserInfo;
//import com.paytm.merchant.CheckSumServiceHelper;
//
//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("/payment")
//public class PaymentController
//{
//	@Autowired
//	private OrderProcessingService orderProcessingService;
//	
//	@Autowired
//	private PaymentDetailService paymentDetailService;
//	
//	@Autowired
//	HttpServletRequest hsRequest;
//	
//	@Autowired
//	private CoursePackageService coursePackageService;
//	
//	
//	
//	@GetMapping("/startPayment/{coursePackageId}")
//	public String processCourseOrder(Model model, @PathVariable int coursePackageId) throws Exception {
//		String merchantMid = ApplicationConfig.MERCHANT_ID;		
//		String merchantKey = ApplicationConfig.MERCHANT_KEY;		
//		Random randomGenerator = new Random();
//    	String orderId = ApplicationConfig.ORDER_ID_PREFIX+ randomGenerator.nextInt(1000000);
//		String channelId = ApplicationConfig.CHANNEL_ID;
//		LoggedInUserResponse response = UserInfo.getUserDetails((HttpServletRequest) hsRequest);
//		String custId = response.getUserId().toUpperCase();
//		String mobileNo = "7899815050";
//		String email = "username@emailprovider.com";
//		
//		double cpackPrice = coursePackageService.getCpackPriceByCpackId(coursePackageId);
//		
//		//cpackPrice = cpackPrice + ((cpackPrice * gstPercentage) / 100);
//		
//		String txnAmount = ""+cpackPrice;
//		//String txnAmount = "1";
//		String website = ApplicationConfig.WEBSITE;
//		// This is the staging value. Production value is available in your dashboard
//		String industryTypeId = ApplicationConfig.INDUSTRY_TYPEI_D;
//		// This is the staging value. Production value is available in your dashboard
//		String callbackUrl = ApplicationConfig.CALLBACK_URL;
//		TreeMap<String, String> paytmParams = new TreeMap<String, String>();
//		
//		
//		
//		
//		paytmParams.put("MID",merchantMid);
//		paytmParams.put("ORDER_ID",orderId);
//		paytmParams.put("CHANNEL_ID",channelId);
//		paytmParams.put("CUST_ID",custId);
//		paytmParams.put("MOBILE_NO",mobileNo);
//		paytmParams.put("EMAIL",email);
//		paytmParams.put("TXN_AMOUNT",txnAmount);
//		paytmParams.put("WEBSITE",website);
//		paytmParams.put("INDUSTRY_TYPE_ID",industryTypeId);
//		paytmParams.put("CALLBACK_URL", callbackUrl);
//		String paytmChecksum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(merchantKey, paytmParams);
//		StringBuilder outputHtml = new StringBuilder();
//		outputHtml.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
//		outputHtml.append("<html>");
//		outputHtml.append("<head>");
//		outputHtml.append("<title>Merchant Checkout Page</title>");
//		outputHtml.append("</head>");
//		outputHtml.append("<body>");
//		outputHtml.append("<center><h1>Please do not refresh this page...</h1></center>");
//		String transactionURL="https://securegw-stage.paytm.in/theia/processTransaction";  	// for staging
//	    //String transactionURL="https://securegw.paytm.in/theia/processTransaction";  // for production
//		outputHtml.append("<form method='post' action='"+transactionURL+"' name='f1'>");
//		for(Map.Entry<String,String> entry : paytmParams.entrySet()) {
//		    outputHtml.append("<input type='hidden' name='"+entry.getKey()+"' value='"+entry.getValue()+"'>");
//		}
//		outputHtml.append("<input type='hidden' name='CHECKSUMHASH' value='"+paytmChecksum+"'>");
//		//outputHtml.append("<input type='submit' value='Submit'>");
//		outputHtml.append("</form>");
//		outputHtml.append("<script type='text/javascript'>");
//		outputHtml.append("document.f1.submit();");
//		outputHtml.append("</script>");
//		outputHtml.append("</body>");
//		outputHtml.append("</html>");
//		model.addAttribute("paymentForm",outputHtml);
//		return "payment.html";
//	}
//	
//	@GetMapping("/fetchPaymentDetails/{orderId}")
//	public ResponseEntity<PaymentDetail> getPaymentDetails(@PathVariable String orderId){
//		String userId = UserInfo.getUserId(hsRequest);
//		PaymentDetail paymentDetailResponse = paymentDetailService._getPaymentDetails(userId, orderId);
//		return ResponseEntity.ok().body(paymentDetailResponse);
//	}
//}
