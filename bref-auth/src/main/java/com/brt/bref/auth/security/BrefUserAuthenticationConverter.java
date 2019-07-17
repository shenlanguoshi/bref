package com.brt.bref.auth.security;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import com.brt.bref.common.security.entity.BrefUser;

public class BrefUserAuthenticationConverter extends DefaultUserAuthenticationConverter{
	
	@Override    
	public Map<String, ?> convertUserAuthentication(Authentication authentication) {        
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Object> response = new LinkedHashMap();
		BrefUser user = (BrefUser) authentication.getPrincipal();
		response.put("principal", user);
		//response.put("dataSchema", user.getDataSchema());
		//response.put("dataScope", user.getDataScope());
		//response.put("userInfo", authentication.getPrincipal());
		return response;
	}

}
