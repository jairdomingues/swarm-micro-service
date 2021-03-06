package br.com.smartcarweb.api.jwt.filter;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;
import javax.ws.rs.NameBinding;

@InterceptorBinding 
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface JWTToken {
}