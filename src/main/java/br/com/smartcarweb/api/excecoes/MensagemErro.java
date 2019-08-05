package br.com.smartcarweb.api.excecoes;

public class MensagemErro {

    protected String codigo;
    protected String mensagem;
    protected String detalhe;

    public MensagemErro() {

    }

    /**
     * Construtor com parâmetros.
     */
    public MensagemErro(String codigo, String mensagem, String detalhe) {
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.detalhe = detalhe;
    }

    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public String getDetalhe() {
        return detalhe;
    }
    
    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

}
