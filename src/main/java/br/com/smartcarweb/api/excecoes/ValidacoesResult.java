package br.com.smartcarweb.api.excecoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("validacoesResult")
@RequestScoped
public class ValidacoesResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<MensagemErro> validacoes = new ArrayList<MensagemErro>();

    public List<MensagemErro> getValidacoes() {
        return validacoes;
    }

    public void addValidacoes(Class<? extends ErroException> classe, String codigo, 
            String propriedade, Map<String, String> parametros) {
        this.validacoes.add(converterMensagemExcecao(classe, codigo, propriedade, parametros));
    }

    private static MensagemErro converterMensagemExcecao(Class<? extends ErroException> classe, 
            String codigo, String propriedade, Map<String, String> parametros) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = null;
        System.out.println("dicionario.excecoes." + classe.getSimpleName());
        try {
            bundle = ResourceBundle.getBundle("dicionario.excecoes." + classe.getSimpleName(), locale, cl);
        } catch (MissingResourceException erro) {
            bundle = ResourceBundle.getBundle("dicionario.excecoes." + classe.getSimpleName(), Locale.forLanguageTag("pt-BR"), cl);
        }
        MensagemErro mensagemErro = new MensagemErro();
        String mensagem  = bundle.getString(codigo);
        mensagem = mensagem.replaceFirst("\\?", propriedade);
        if (parametros != null) {
            for (Entry<String, String> param : parametros.entrySet()) {
                mensagem = mensagem.replaceFirst("\\@" + param.getKey(), param.getValue());
            }
        }
        mensagemErro.codigo = codigo;
        mensagemErro.mensagem = mensagem;
        if (propriedade.equals("senha")) { 
            mensagemErro.detalhe = parametros.toString();
        }
        return mensagemErro;
    }

}



