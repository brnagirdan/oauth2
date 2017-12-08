package com.ecquaria.oauth2.config;

import com.ecquaria.oauth2.feign.TokenFeign;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class MyTokenServices implements ResourceServerTokenServices {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private TokenFeign tokenFeign;
    private String clientId;
    private String clientSecret;
    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    public void setTokenFeign(TokenFeign tokenFeign) {
        this.tokenFeign = tokenFeign;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
        this.tokenConverter = accessTokenConverter;
    }

    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", this.getAuthorizationHeader(this.clientId, this.clientSecret));
        Map map = this.postForMap(accessToken, headers);
        if(map.containsKey("error")) {
            this.logger.debug("check_token returned error: " + map.get("error"));
            throw new InvalidTokenException(accessToken);
        } else {
            Assert.state(map.containsKey("client_id"), "Client id must be present in response from auth server");
            return this.tokenConverter.extractAuthentication(map);
        }
    }

    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    private String getAuthorizationHeader(String clientId, String clientSecret) {
        if(clientId == null || clientSecret == null) {
            this.logger.warn("Null Client ID or Client Secret detected. Endpoint that requires authentication will reject request with 401 error.");
        }

        String creds = String.format("%s:%s", new Object[]{clientId, clientSecret});

        try {
            return "Basic " + new String(Base64.encode(creds.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException var5) {
            throw new IllegalStateException("Could not convert String");
        }
    }

    private Map<String, Object> postForMap(String token, HttpHeaders headers) {
        if(headers.getContentType() == null) {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }
        return tokenFeign.getTokenInfo(headers,token);
    }
}
