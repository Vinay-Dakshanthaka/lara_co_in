package com.lara.c2c.service;


import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lara.c2c.model.Order;
import com.lara.c2c.model.PaymentDetail;
import com.lara.c2c.repository.CartRepository;
import com.lara.c2c.repository.CoursePackageRepository;
import com.lara.c2c.repository.LearnerCourseRepository;
import com.lara.c2c.repository.OrderRepository;
import com.lara.c2c.repository.PaymentDetailRepository;

import com.lara.c2c.model.Cart;
import com.lara.c2c.model.LearnerCourse;

@Service
public class OrderProcessingService {
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private PaymentDetailRepository paymentDetailRepo;
	
	@Autowired
	private LearnerCourseRepository learnerCourseRepo;
	
	@Autowired 
	private CoursePackageRepository coursePackageRepo;

	@Autowired
	private CartRepository cartRepo;
	
	Logger log = LoggerFactory.getLogger(OrderProcessingService.class);
	
	public void insertIntoCart(Cart cartRequest) {
		try {
			log.trace("Your log - {}", "trace");
			log.debug("debug - {}", "debug");
			log.info("info- {}", "info");          
			log.warn("warn - {}", "warn");          
			log.error("error - {}", "error");
			cartRepo.save(cartRequest);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*public TreeMap<String, String> prepareOrderForm(int coursePackageId) throws Exception{
		String merchantMid = "lNLRQO65223782190457";
    	// Key in your staging and production MID available in your dashboard
    	String merchantKey = "JythD3wgTm0R_2fu";
    	// Key in your staging and production merchant key available in your dashboard
    	Random randomGenerator = new Random();
    	String orderId = "OD"+ randomGenerator.nextInt(1000000);
		String channelId = "WEB";
		String custId = "cust123";
		String mobileNo = "7899815050";
		String email = "ritesh";
		String txnAmount = "100.12";
		String website = "WEBSTAGING";
		// This is the staging value. Production value is available in your dashboard
		String industryTypeId = "Retail";
		// This is the staging value. Production value is available in your dashboard
		String callbackUrl = "https://<Merchant_Response_URL>";
		TreeMap<String, String> paytmParams = new TreeMap<String, String>();
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
		paytmParams.put("CHECKSUMHASH", paytmChecksum);
		String transactionURL="https://securegw-stage.paytm.in/theia/processTransaction"; 
		paytmParams.put("transactionURL", transactionURL);
		return paytmParams;
	}*/
	
	public String createOrder(Order order) {
		int coursePackageId = Integer.parseInt(order.getOrderItems());
		double totalAmount = coursePackageRepo.findCpackPrice(coursePackageId);
		order.setTotalAmount(totalAmount);
		String orderId = orderRepo.save(order).getOrderId();		
		return orderId;
	}

	public void addPaymentDetails(TreeMap<String, String> parameters){		
		PaymentDetail paymentDetailObj = new PaymentDetail();
		paymentDetailObj.setBankName(parameters.get("BANKNAME"));
		
		paymentDetailObj.setCurrency(parameters.get("CURRENCY"));
		
		//paymentDetailObj.setMerchantId(parameters.get("MID"));
		paymentDetailObj.setOrderId(parameters.get("ORDERID"));
		paymentDetailObj.setPaymentMode(parameters.get("PAYMENTMODE"));
		paymentDetailObj.setResponseCode(Integer.parseInt(parameters.get("RESPCODE")));

		paymentDetailRepo.save(paymentDetailObj);
		
		
	}

	public void updateOrderStatus(String orderId, String status, String userId){
		orderRepo.updateOrderStatus(orderId, status, userId);	
	}

	public void updateLrCoursesByOrderId(String orderId, String userId){
		Order order = orderRepo.findByOrderId(orderId);
		LearnerCourse lrc = new LearnerCourse();
		lrc.setCoursePackageId(Integer.parseInt(order.getOrderItems()));
		lrc.setUserId(userId);
		learnerCourseRepo.save(lrc);
	}
	
	/*  newly adding for free access */
	
	
	public void updateLearnerWithFreeCoursePackages(String userId){
		//college to corporate
		LearnerCourse lrc = new LearnerCourse();
		lrc.setCoursePackageId(1);
		lrc.setUserId(userId);
		learnerCourseRepo.save(lrc);
		
		
		//J2EE
		LearnerCourse lrc1 = new LearnerCourse();
		lrc1.setCoursePackageId(3);
		lrc1.setUserId(userId);
		learnerCourseRepo.save(lrc1);		
	}
	
	
	
	
	public void updateLearnerWithFreeDemoPackage(String userId){
		//demo package
		LearnerCourse lrc = new LearnerCourse();
		lrc.setCoursePackageId(21);
		lrc.setUserId(userId);
		learnerCourseRepo.save(lrc);		
	
	}
	
	

	public String getOrderIdBycurrOrderSessTimeId(String userCurrOrderSessTimeId) {
		return orderRepo.findByUserCurrOrderSessTimeId(userCurrOrderSessTimeId).getOrderId();
	}

	public void updateAbortedOrder(String orderId, String orderStatus, String orderStatusMessage, String userId) {
		orderRepo.updateOrderAbortedStatus(orderId, orderStatus, orderStatusMessage, userId);		
	}

	public Order _getOrderDetailsByOrderIdAndUserId(String orderId, String userId) {
		return orderRepo.findByOrderIdAndUserId(orderId, userId);
	}			
	
}
