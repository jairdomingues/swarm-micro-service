package br.com.smartcarweb.api.excecoes;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Named
public class BaseValidacao {

	@Inject
	private Validator validator;

	@Inject
	ValidacoesResult validacoesResult;

	/**
	 * <p>
	 * Validar o objetos da API REST.
	 * </p>
	 * 
	 * @param <T>
	 * 
	 * @param object
	 *            objeto para ser validado
	 * @throws ErroBase
	 *             caso o objeto contenha erros de validação
	 */
	public <T> ValidacoesResult validarObjeto(T object) {

		ValidacoesResult validacoesResult = new ValidacoesResult();

		//lista de atributos do objeto
		Field[] fieldsObject = object.getClass().getDeclaredFields();

		//processar atributos para verificar presenca anotacao @AgObrigatorio
		for (Field atributo : fieldsObject) {
			if (atributo.isAnnotationPresent(AgObrigatorio.class)) {
				AgObrigatorio agObrigatorio = atributo.getAnnotation(AgObrigatorio.class);
				int[] estagios = agObrigatorio.estagio();
				String nomeAtributoEstagio = agObrigatorio.nomeAtributoEstagio();
				String mensagemErro = agObrigatorio.mensagemErro();
				for (int i = 0; i < estagios.length; i++) {
					try {
						Field atributoObrigatorio = object.getClass().getDeclaredField(nomeAtributoEstagio);
						Object value = null;
						atributoObrigatorio.setAccessible(true);
						value = atributoObrigatorio.get(object);
						if (value == null) {
							validacoesResult.addValidacoes(ErroBase.class, "BASE_E_2", atributo.getName(), null);
							break;
						}
						if (value.equals(estagios[i])) {
							atributo = object.getClass().getDeclaredField(atributo.getName());
							value = null;
							atributo.setAccessible(true);
							value = atributo.get(object);
							if (value == null) {
								validacoesResult.addValidacoes(ErroBase.class, mensagemErro, atributo.getName(), null);
							}
						}
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
						throw new ErroBase(ErroBase.Codigos.BASE_E_2); 
					}
				}
			}
		}

		//validacao anotacoes hibernate do objeto 
		Set<ConstraintViolation<T>> validacoes = validator.validate(object);
		if (!validacoes.isEmpty()) {
			for (ConstraintViolation<?> cv : validacoes) {
				String mensagem = cv.getMessageTemplate();
				String propriedade = cv.getPropertyPath().toString();

				Map<String, String> parametros = new HashMap<String, String>();

				for (Entry<String, Object> prop : cv.getConstraintDescriptor().getAttributes().entrySet()) {
					parametros.put(prop.getKey(), prop.getValue().toString());
				}

				validacoesResult.addValidacoes(ErroBase.class, mensagem, propriedade, parametros);
			}
		}
		return validacoesResult;
	}

}
