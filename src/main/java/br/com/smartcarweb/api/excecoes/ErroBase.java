package br.com.smartcarweb.api.excecoes;

public class ErroBase extends ErroException {

    private static final long serialVersionUID = 1L;

    public enum Codigos {
        /** 
         * {@literal "Sua sessão expirou! Faça login novamente."} */
        BASE_E_1("BASE_E_1"),
        /** 
         * {@literal "Ocorreu um erro interno, contate o administrador do sistema."} */
        BASE_E_2("BASE_E_2"),
        /** 
         * {@literal "Erro ao gravar o registro."} */
        BASE_E_3("BASE_E_3"),        
        /** 
         * {@literal "Usuário de inclusão e/ou alteração não foi informado."} */
        BASE_E_4("BASE_E_4"),
        /** 
         * {@literal 412 Precondition Failed, see {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.13">HTTP/1.1 documentation</a>}.} */
        BASE_E_5("BASE_E_5"),
        /** 
         * {@literal "O campo ? é obrigatório." } */
        BASE_E_6("BASE_E_6"),
        /** 
         * {@literal "O campo ? não pode estar vazio."} */
        BASE_E_7("BASE_E_7"),
        /** 
         * {@literal "O usuário precisa ter pelo menos um papel vinculado."} */
        BASE_E_8("BASE_E_8"),
        /** 
         * {@literal "O usuário precisa ter pelo menos um módulo vinculado."} */
        BASE_E_9("BASE_E_9"),
        /** 
         * {@literal "O email informado já existe."} */
        BASE_E_10("BASE_E_10"),
        /** 
         * {@literal "O campo ? deve possuir de 3 a 60 caracteres."} */
        BASE_E_11("BASE_E_11"),
        /** 
         * {@literal "Campo ? contém caracteres inválidos."} */
        BASE_E_12("BASE_E_12"),
        /** 
         * {@literal "O usuário precisa ter pelo menos um contrato vinculado."} */
        BASE_E_13("BASE_E_13"),
        /** 
         * {@literal "O campo ? deve possuir de @min a @max caracteres."} */
        BASE_E_15("BASE_E_15"),
        /** 
         * {@literal "O campo ? deve possuir no mínimo @min caracteres."} */
        BASE_E_16("BASE_E_16"),
        /** 
         * {@literal "O campo ? deve possuir no máximo @max caracteres."} */
        BASE_E_17("BASE_E_17"),
        /** 
         * {@literal "O(a) ? é inválido(a)."} */
        BASE_E_18("BASE_E_18"),
        /** 
         * {@literal "O sistema está em modo somente leitura."} */
        BASE_E_19("BASE_E_19"),
        /** 
         * {@literal "Este registro já foi alterado por outro usuário."} */
        BASE_E_20("BASE_E_20"),
        /** 
         * {@literal "Acesso não autenticado. Faça o login."} */
        BASE_E_21("BASE_E_21"),
        /** 
         * {@literal "Operação não permitida."} */
        BASE_E_22("BASE_E_22"),
        /** 
         * {@literal "Token de acesso inválido ou expirado. Faça novamente um login." } */
        BASE_E_23("BASE_E_23"),        
        /** 
         * {@literal "Erro ao consultar o registro."} 
         * */
        BASE_E_24("ERRO_AO_CONSULTAR"),        
        /** 
         * {@literal "Mais de um registro não encontrado."} 
         * */
        BASE_E_25("BASE_E_25"),        
        /**
         *  {@literal "Registro não encontrado."} 
         *  */
        BASE_E_26("BASE_E_26"),
        /**
         *  {@literal "Conversor de classes: Tipo de conversão não implementada. Tipo de origem: ?/ Tipo de destino: ?."} 
         *  */
        BASE_E_27("BASE_E_27");

        private String value;

        Codigos(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public ErroBase(Codigos codigo, String... parametros) {
        super(ErroBase.class, codigo.value(), parametros);
    }

    public ErroBase(String message) {
        super(message);
    }

    public ErroBase(ValidacoesResult validacoesResult) {
        super(validacoesResult);
    }


}
