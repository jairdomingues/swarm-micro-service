package br.com.smartcarweb.api.jwt.filter;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.com.smartcarweb.api.excecoes.ErroBase;

@Interceptor 
@AgValidacao
public class AgValidacaoInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;

    @AroundInvoke
    private Object interceptar(InvocationContext ctx) {

        final Method method = ctx.getMethod();

        //verifica se anotacao de validacao esta presente  
        if (method.isAnnotationPresent(AgValidacao.class)) {
            AgValidacao agValidacao = ctx.getMethod().getAnnotation(AgValidacao.class);
        }

        System.out.println(ctx.getParameters());
        
        Object result = null;
        try {
            //executar metodo de negocio
            result = ctx.proceed();
        } catch (ErroBase e) {
            throw e;
        } catch (Exception e) {
            throw new ErroBase(ErroBase.Codigos.BASE_E_2); 
        }

        return result;

    }

}