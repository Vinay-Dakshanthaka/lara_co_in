package com.lara.c2c.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.lara.c2c.dto.ParamsRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class PhonePeUtils {

	private final String SALT_KEY = "358f38c8-9019-4623-88e1-037723c25449";
//	private final String SALT_KEY = "4fe201c8-32e9-4143-821b-00296f0f19d7";
	private final String SALT_INDEX = "1";
	private final String API_ENDPOINT = "/pg/v1/pay";
	private final String MERCHANT_ID = "M223I77VDWEEK";
//	private final String MERCHANT_ID = "PGTESTPAYUAT84";
	private final String FRONTEND_URL = "https://lara.co.in/";
//	private final String FRONTEND_URL = "https://919e-49-37-176-94.ngrok-free.app/";
	private final String BACKEND_URL = "https://lara.co.in/";
//	private final String BACKEND_URL = "https://d657-49-37-176-94.ngrok-free.app/";

	public String base64(String payload) {
		return Base64.getEncoder().encodeToString(payload.getBytes());
	}

	public String checksum(String base64) throws NoSuchAlgorithmException {
		return calculateSha256(base64) + "###" + SALT_INDEX;
	}

	public String checksumShort(String base64) throws NoSuchAlgorithmException {
		return calculateSha256Short(base64) + "###" + SALT_INDEX;
	}
	
	public String checksumCheckStatus(String merchantTransactionId) throws NoSuchAlgorithmException {
		return calculateSha256CheckStatus(merchantTransactionId) + "###" + SALT_INDEX;
	}

	public String calculateSha256(String base64) throws NoSuchAlgorithmException {
		// String to convert
		String input = base64 + API_ENDPOINT + SALT_KEY;

		// get sha256 instance
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(input.getBytes());
		byte[] bytes = md.digest();

		StringBuilder hashHex = new StringBuilder();
		for (byte b : bytes) {
			hashHex.append(String.format("%02x", b));
		}
		return hashHex.toString();
	}

	public String calculateSha256Short(String base64) throws NoSuchAlgorithmException {
		// String to convert
		String input = base64 + SALT_KEY;

		// get sha256 instance and the byte array
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(input.getBytes());
		byte[] bytes = md.digest();

		// build the string from byte array
		StringBuilder hashHex = new StringBuilder();
		for (byte b : bytes) {
			hashHex.append(String.format("%02x", b));
		}
		return hashHex.toString();
	}
	
	public String calculateSha256CheckStatus(String merchantTransactionId) throws NoSuchAlgorithmException {
		// String to convert
		String input = "/pg/v1/status/" + MERCHANT_ID + "/" + merchantTransactionId + SALT_KEY;

		// get sha256 instance
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(input.getBytes());
		byte[] bytes = md.digest();

		StringBuilder hashHex = new StringBuilder();
		for (byte b : bytes) {
			hashHex.append(String.format("%02x", b));	
		}
		return hashHex.toString();
	}

	public Map<String, Object> createPhonePeObjectMap(ParamsRequest paramsRequest) throws JsonProcessingException {
		Map<String, Object> params = new HashMap<>();
		params.put("merchantId", MERCHANT_ID);
		params.put("merchantTransactionId", paramsRequest.getOrderId());
		params.put("amount", (paramsRequest.getAmount()*100) + "");
		params.put("merchantUserId", paramsRequest.getUserId());
		params.put("redirectUrl", FRONTEND_URL + "#/cartDetails?orderId=" + paramsRequest.getOrderId());
		params.put("redirectMode", "REDIRECT");
//		params.put("callbackUrl",
//				BACKEND_URL + "c2c/api/orders/v2/confirmOrder/" + paramsRequest.getOrderId() + "/" + MERCHANT_ID);
		params.put("paymentInstrument", getSingleJsonNode("type", "PAY_PAGE"));
		return params;
	}

	public JsonNode createPhonePeRequestJsonNode(Map<String, Object> map, ObjectMapper om) {
		ObjectNode objectNode = om.createObjectNode();
		map.entrySet().stream().forEach(entry -> {
			try {
				JsonNode jsonNode = (JsonNode) entry.getValue();
				objectNode.set(entry.getKey(), jsonNode);
			} catch (Exception e) {
				objectNode.putPOJO(entry.getKey(), om.valueToTree(new TextNode((String) entry.getValue())));
			}
		});
		return objectNode;
	}

	public JsonNode getSingleJsonNode(String key, String value) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		return om.createObjectNode().putPOJO(key, om.valueToTree(new TextNode(value)));
	}
}
