package ec.com.levelap.gameclub.module.paymentez.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value="api/paymentez", produces=MediaType.APPLICATION_JSON_VALUE)
public class PaymentezController {
	
	public static final String BASE_URL = "https://ccapi-stg.paymentez.com";
	
	public static final String APP_CODE = "TOMO-EC";
	
	public static final String APP_KEY = "LeqWG1P48IC8Cfj99DPW6rLGnh5wSC";
	
	@RequestMapping(value="addTest/{sessionId}", method=RequestMethod.GET)
	public ResponseEntity<?> addTest(@PathVariable String sessionId) throws ServletException, UnsupportedEncodingException {
		Date today = new Date();
		String addUrl = BASE_URL + "/api/cc/add/?" +
							"application_code={appCode}&" +
							"uid={userId}&" +
							"email={username}&" +
							"session_id={sessionId}&" +
							"auth_timestamp={authTimestamp}&" +
							"auth_token={authToken}&" +
							"buyer_phone={phone}";
		
		UriComponentsBuilder addUrlBuilder = UriComponentsBuilder.fromUriString(addUrl);
		
		String token = DigestUtils.sha256Hex("application_code=" + APP_CODE +
											"&email=" + "joseluis.levelap%40gmail.com" +
											"&session_id=" + sessionId +
											"&uid=" + 1 +
											"&" + today.getTime() +
											"&" + APP_KEY);
		
		Map<String, String> params = new HashMap<>();
		params.put("appCode", APP_CODE);
		params.put("userId", "1");
		params.put("username", "joseluis.levelap%40gmail.com");
		params.put("sessionId", sessionId);
		params.put("authTimestamp", "" + today.getTime());
		params.put("authToken", DigestUtils.sha256Hex(token));
		params.put("phone", "0998591484");
		
		RestTemplate rest = new RestTemplate();
		rest.setErrorHandler(new ResponseErrorHandler() {
			
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}
			
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				System.out.println("ERROR RESPONSE: " + IOUtils.toString(response.getBody()));
			}
		});
		System.out.println("URL: " + addUrlBuilder.buildAndExpand(params).toUriString());
		ResponseEntity<String> response = rest.getForEntity(addUrlBuilder.buildAndExpand(params).toUriString(), String.class);
		System.out.println("RESPONSE BODY: " + response.getBody());
		
		return response;
	}
}
