package com.brt.bref.common.security.feign;

import feign.RequestTemplate;
import org.springframework.cloud.security.oauth2.client.AccessTokenContextRelay;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;

public class BrefFeignClientInterceptor extends OAuth2FeignRequestInterceptor {
	private final OAuth2ClientContext oAuth2ClientContext;

	public BrefFeignClientInterceptor(OAuth2ClientContext oAuth2ClientContext) {
		super(oAuth2ClientContext, null);
		this.oAuth2ClientContext = oAuth2ClientContext;
	}

	@Override
	public void apply(RequestTemplate template) {
		if (oAuth2ClientContext != null
			&& oAuth2ClientContext.getAccessToken() != null) {
			AccessTokenContextRelay accessTokenContextRelay = new AccessTokenContextRelay(oAuth2ClientContext);
			accessTokenContextRelay.copyToken();
			super.apply(template);
		}else {
			throw new RuntimeException("未授权的访问");
		}
	}
}
