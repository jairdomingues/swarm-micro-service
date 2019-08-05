package br.com.smartcarweb.api.excecoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 * Classe para lançar erros de regra de negócio.
 * (Deve ser reimplementada em cada módulo)
 *
 */
@ApplicationException(rollback = true)
public abstract class ErroException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String codigo;
    ValidacoesResult validacoesResult;

    public ErroException(ValidacoesResult validacoesResult) {
        super("Erro de Validacao");
        this.validacoesResult = validacoesResult;
    }

    public ValidacoesResult getValidacoesResult() {
        return validacoesResult;
    }
    
    public void setValidacoesResult(ValidacoesResult validacoesResult) {
        this.validacoesResult = validacoesResult;
    }
    
    public ErroException(String message) {
        super(message);
    }

    public ErroException(Class<? extends ErroException> classeErro, String codigo, String... parametros) {
        super(converterMensagemExcecao(classeErro, codigo, parametros));
        this.codigo = codigo;
    }

    public ErroException(Class<? extends ErroException> classeErro, String codigo, Throwable causa, String... parametros) {
        super(converterMensagemExcecaoComCausa(classeErro, codigo, causa, parametros), causa);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    private static String converterMensagemExcecao(Class<? extends ErroException> classe, String codigo, String... parametros) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = null;
        try {
            bundle = ResourceBundle.getBundle("dicionario.excecoes." + classe.getSimpleName(), locale, cl);
        } catch (MissingResourceException erro) {
            bundle = ResourceBundle.getBundle("dicionario.excecoes." + classe.getSimpleName(), 
                    Locale.forLanguageTag("pt-BR"), cl);
        }
        String mensagem  = bundle.getString(codigo);
        if (parametros != null) {
            for (int i = 0; i < parametros.length; i++) {
                mensagem = mensagem.replaceFirst("[?]", parametros[i]);
            }
        }
        return mensagem;
    }

    private static String converterMensagemExcecaoComCausa(
            Class<? extends ErroException> classeErro, String codigo, Throwable causa, String... parametros) {
        String mensagem = converterMensagemExcecao(classeErro, codigo, parametros);
        if (causa instanceof ErroException) {
            mensagem += "\n" + ((ErroException)causa).getMessage();
        }
        return mensagem;
    }

    /**
     * Conerte a exceção em padrão json para retornar via REST.
     */
    public WebApplicationException converterParaRest() {
        List<MensagemErro> responseObj = new ArrayList<MensagemErro>();
        if (this.getValidacoesResult() != null) {
            responseObj = this.getValidacoesResult().getValidacoes();
        } else {
            responseObj.add(new MensagemErro(getCodigo(), getMessage(), getCause() != null ? getCause().getMessage() : null));
        }
        GenericEntity<List<MensagemErro>> list = new GenericEntity<List<MensagemErro>>(responseObj) {};
        if (getCodigo() != null && getCodigo().equals(ErroBase.Codigos.BASE_E_26.toString())) {
            return new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity(list).build());
        } else if (getCodigo() != null && getCodigo().equals(ErroBase.Codigos.BASE_E_2.toString())) {
            return new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(list).build());
        } else if (getCodigo() != null && getCodigo().equals(ErroBase.Codigos.BASE_E_1.toString())) {
            return new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).entity(list).build());
        } else if (getCodigo() != null && (getCodigo().equals(ErroBase.Codigos.BASE_E_23.toString())
        											|| getCodigo().equals(ErroBase.Codigos.BASE_E_21.toString()))) {
            return new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).entity(list).build());
        } else if (getCodigo() != null && getCodigo().equals(ErroBase.Codigos.BASE_E_5.toString())) {
            return new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED).entity(list).build());
        } else {
            return new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(list).build());
        }
    }
}
