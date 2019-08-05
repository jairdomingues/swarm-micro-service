package br.com.smartcarweb.api.excecoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

@InterceptorBinding
@Target({ java.lang.annotation.ElementType.METHOD,java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AgObrigatorio {

	@Nonbinding
	int[] estagio() default {0};
	
	@Nonbinding
	String nomeAtributoEstagio() default "";

	@Nonbinding
	String mensagemErro() default "BASE_E_6";
	
}