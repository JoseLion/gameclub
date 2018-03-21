package ec.com.levelap.gameclub.module.fine.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return true;
		}
		
		return false;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		String errorMessage = "REST TEMPLATE ERROR!\nSTATUS: " + response.getRawStatusCode() + " " + response.getStatusText() + "\nMESSAGE: " + IOUtils.toString(response.getBody(), StandardCharsets.UTF_8);
		throw new IOException(errorMessage);
	}
	
}
