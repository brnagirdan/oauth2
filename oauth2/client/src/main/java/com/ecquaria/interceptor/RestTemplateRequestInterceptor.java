package com.ecquaria.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestTemplateRequestInterceptor implements ClientHttpRequestInterceptor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Header:").append(httpRequest.getHeaders()).append(" ")
                .append("Path:").append(httpRequest.getURI().getPath()).append(" ");

        if (bytes != null && bytes.length > 0) {
            stringBuilder.append("Body:").append(new String(bytes));
        }
        logger.info(stringBuilder.toString());
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }

}
