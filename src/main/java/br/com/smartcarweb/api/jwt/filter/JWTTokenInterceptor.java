package br.com.smartcarweb.api.jwt.filter;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@JWTToken
@Interceptor
public class JWTTokenInterceptor {

	@AroundInvoke
	public Object audit(InvocationContext ictx) throws Exception{
		return ictx;
	}
	
}
