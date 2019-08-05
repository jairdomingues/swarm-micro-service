package br.com.smartcarweb.api.excecoes;

public final class ErroAcc extends ErroException {

	private static final long serialVersionUID = 1L;

	public enum Codigos {
		/** {@literal "Erro desconhecido. Verificar Servidor de Banco de Dados" } */
		ERRO_SERVIDOR("ERRO_SERVIDOR"),
		/** {@literal "Conexão inválida! Verifique o preenchimento correto das informações." } */
		ACC_E_1("ACC-E-1"),
		/** {@literal "Usuário não está ativo." } */
		ACC_E_2("ACC-E-2"),
		/** {@literal "A senha está bloqueada! Cadastre uma nova senha ou contacte o supervisor de software." } */
		ACC_E_3("ACC-E-3"),
		/** {@literal "Este email já existe." } */
		ACC_E_4("ACC-E-4"),
		/** {@literal "Venda não pode mais ser excluida." } */
		ACC_E_5("ACC_E_5"),
		/** {@literal "CPF já existe. Verifique a lista de clientes." } */
		ACC_E_6("ACC_E_6"),
		/** {@literal "O email ou a senha são inválidos." } */
		ACC_E_7("ACC_E_7"),
		/** {@literal "CPF inválido." } */
		ACC_E_11("ACC_E_11"),
		/** {@literal "Usuário não foi validado via email." } */
		ACC_E_75("ACC-E-75");

		private String value;

		Codigos(String value) {
			this.value = value;
		}

		public String value() {
			return value;
		}
	}

	public ErroAcc(Codigos codigo, String... parametros) {
		super(ErroAcc.class, codigo.value(), parametros);
	}

	public ErroAcc(String message) {
		super(message);
	}
}
