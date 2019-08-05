package br.com.smartcarweb.api.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.smartcarweb.api.excecoes.BaseApi;
import br.com.smartcarweb.api.excecoes.BaseDTO;
import br.com.smartcarweb.api.excecoes.BaseValidacao;
import br.com.smartcarweb.api.excecoes.Detalhe;
import br.com.smartcarweb.api.excecoes.ErroBase;
import br.com.smartcarweb.api.excecoes.InjecaoCdiUtil;
import br.com.smartcarweb.api.excecoes.ValidacoesResult;
import br.com.smartcarweb.api.jwt.filter.LoggedIn;
import br.com.smartcarweb.api.util.persistence.ejb3.BaseEntity;
import br.com.smartcarweb.model.Usuario;

public class ConversorClasse {

	public static final String PACOTE = "br.com.smartcarweb";
	
	private final static Logger LOGGER = Logger.getLogger(ConversorClasse.class.getName()); 	
	/**
     * Método que converte uma lista de objetos de uma classe para outra lista de objetos de outra classe.
     * 
     * @param origem - Lista com os objetos a serem convertidos.
     * @param classeDestino - Classe em que os objetos devem ser convertidos.
     * @param nivelLimite - Nivel de profundidade que o conversor irá descer nas propriedades cujo classe é do pacote br.com.agrotis (nível 0 significa sem limites).
     */
	public static <Origem, Destino> List<Destino> ConverterListaClasses(List<Origem> origem, Class<Destino> classeDestino, Integer nivelLimite) throws ErroBase {
		if (origem == null) {
			return null;
		} else {
			List<Destino> listaRetorno = new ArrayList<Destino>();
			for (Origem itemOrigem : origem) {
				listaRetorno.add(ConverterClasse(itemOrigem, classeDestino, nivelLimite));
			}
			return listaRetorno;
		}		
	}
	
	/**
     * Método que converte um objeto de uma classe para outra classe.
     * 
     * @param origem - Objeto a ser convertido.
     * @param classeDestino - Classe em que os objeto deve ser convertido.
     * @param nivelLimite - Nivel de profundidade que o conversor irá descer nas propriedades cujo classe é do pacote br.com.agrotis (nível 0 significa sem limites).
     */
	public static <Origem, Destino> Destino ConverterClasse(Origem origem, Class<Destino> classeDestino, Integer nivelLimite) throws ErroBase {
		return ConverterClasse(origem, classeDestino, nivelLimite, 0);
	}

	/**
     * Método que transfere os dados de um objeto para outro.
     * 
     * @param origem - Objeto a ser convertido.
     * @param destino - Objeto no qual os dados devem ser convertidos.
     * @param nivelLimite - Nivel de profundidade que o conversor irá descer nas propriedades cujo classe é do pacote br.com.agrotis (nível 0 significa sem limites).
     */
	public static <Origem, Destino> void ConverterClasse(Origem origem, Destino destino, Integer nivelLimite) throws ErroBase {
		ConverterClasse(origem, destino, nivelLimite, 0);
	}

	@SuppressWarnings("unchecked")
	private static <Origem, Destino> Destino ConverterClasse(Origem origem, Class<Destino> classeDestino, Integer nivelLimite, Integer nivel) throws ErroBase { 
		if ((origem != null) && ((nivelLimite.equals(0)) || (nivel <= nivelLimite))) {
			Destino destino;
			try {
				destino = classeDestino.newInstance();
			} catch (InstantiationException e) {
				throw new ErroBase(ErroBase.Codigos.BASE_E_2);
			} catch (IllegalAccessException e) {
				throw new ErroBase(ErroBase.Codigos.BASE_E_2);
			}
			if ((origem instanceof BaseDTO) && (destino instanceof BaseEntity)) {					
				try {
					Method metodoEspecifico = origem.getClass().getDeclaredMethod("converterParaEntidade", destino.getClass(), Integer.class);
					try {
						Object objetoRetornoGetXSD = metodoEspecifico.invoke(origem.getClass(), destino, 0);
						//executa o codigo implementadao chamado atraves do invoke e retorna
						return (Destino) objetoRetornoGetXSD;
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new ErroBase(ErroBase.Codigos.BASE_E_2);
					}
				} catch (NoSuchMethodException e) {
					//Segue implementação padrão
				}

			} else if ((origem instanceof BaseEntity) && (destino instanceof BaseDTO)) {
				try {
					Method metodoEspecifico = destino.getClass().getDeclaredMethod("converterParaDto", origem.getClass(), Integer.class);
					try {
						Object objetoRetornoGetXSD = metodoEspecifico.invoke(origem.getClass(), destino, 0);
						//executa o codigo implementadao chamado atraves do invoke e retorna
						return (Destino) objetoRetornoGetXSD;
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new ErroBase(ErroBase.Codigos.BASE_E_2);
					}
				} catch (NoSuchMethodException e) {
					//Segue implementação padrão
				}
			} else if ((origem instanceof BaseApi) && (destino instanceof BaseDTO)) {
				try {
					Method metodoEspecifico = origem.getClass().getDeclaredMethod("converterParaDto", origem.getClass(), Integer.class);
					try {
						Object objetoRetornoGetXSD = metodoEspecifico.invoke(origem.getClass(), origem, 0);
						//executa o codigo implementadao chamado atraves do invoke e retorna
						return (Destino) objetoRetornoGetXSD;
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} catch (NoSuchMethodException e) {
					//Segue implementação padrão						
				} 
			} else if ((origem instanceof BaseDTO) && (destino instanceof BaseApi)) {
				try {
					Method metodoEspecifico = destino.getClass().getDeclaredMethod("converterParaApi", origem.getClass(), Integer.class);
					try {
						Object objetoRetornoGetXSD = metodoEspecifico.invoke(destino.getClass(), origem, 0);
						//executa o codigo implementadao chamado atraves do invoke e retorna
						return (Destino) objetoRetornoGetXSD;
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} catch (NoSuchMethodException e) {
					//Segue implementação padrão
				}
			}
			ConverterClasse(origem, destino, nivelLimite, nivel);
			if ((origem instanceof BaseApi) && (destino instanceof BaseDTO)) {
				validar(destino);
			} else if ((origem instanceof BaseDTO) && (destino instanceof BaseEntity)) {
				validar(destino);
			}				
			return destino;
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <Origem, Destino> void ConverterClasse(Origem origem, Destino destino, Integer nivelLimite, Integer nivel) throws ErroBase {		
		SimpleDateFormat sdfUtc = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
		if ((origem != null) && (destino != null) && ((nivelLimite.equals(0)) || (nivel <= nivelLimite))) {
			try {
				Method[] methodsBEAN = origem.getClass().getMethods();
				for (Method mBEANGet : methodsBEAN) {
					if ((mBEANGet.getDeclaringClass().getPackage().getName().startsWith("br.com.smartcarweb")) && (mBEANGet.getName().startsWith("get"))) {
						Object retornoGetBEAN = mBEANGet.invoke(origem, new Object[]{});
						if ((retornoGetBEAN != null)) {
							Method mXSDSet = buscarMetodoSet(origem, destino, mBEANGet.getName());
							if (mXSDSet != null) {
								if (retornoGetBEAN instanceof Collection) {
									ParameterizedType listType = (ParameterizedType) mXSDSet.getParameters()[0].getParameterizedType();
							        Class<?> objectListClass = (Class<?>) listType.getActualTypeArguments()[0];
							        Collection objetoRetornoGetXSD;
							        if (mXSDSet.getParameterTypes()[0] == Set.class) {
							        	objetoRetornoGetXSD = createSetOfType(objectListClass);
							        } else {
							        	objetoRetornoGetXSD = createListOfType(objectListClass);
							        }
									for (Object item : (Collection)retornoGetBEAN) {
										if ((item.getClass().isEnum()) && (objectListClass == String.class)) {
											objetoRetornoGetXSD.add(item.toString());
										} else if ((item.getClass() == String.class) && (objectListClass.isEnum())) {
											objetoRetornoGetXSD.add(objectListClass.getField(item.toString()).get(null));
										} else if (objectListClass.getPackage().getName().contains(PACOTE)) {
											Object itemLista = ConverterClasse(item, objectListClass, nivelLimite, nivel + 1);
											objetoRetornoGetXSD.add(itemLista);
										} else if ((item.getClass() == objectListClass)) {
											objetoRetornoGetXSD.add(item);
										} else {
											LOGGER.info("Logging an Tipo de conversão inválida. Tipo de origem: "+item.getClass().getName()
													+" para Tipo de destino:  "+objectListClass.getName());									
										}
									}
									mXSDSet.invoke(destino, objetoRetornoGetXSD);
								} else if ((retornoGetBEAN instanceof Date) && (mXSDSet.getParameterTypes()[0] == String.class)) {	
									mXSDSet.invoke(destino, sdfUtc.format(retornoGetBEAN));
								} else if ((retornoGetBEAN instanceof String) && (mXSDSet.getParameterTypes()[0] == Date.class)) {	
									mXSDSet.invoke(destino, sdfUtc.parse(retornoGetBEAN.toString()));
								} else if (mBEANGet.getReturnType() == mXSDSet.getParameterTypes()[0]) {
									mXSDSet.invoke(destino, retornoGetBEAN);
								} else if (mXSDSet.getParameterTypes()[0].isEnum()) {	
									if (!retornoGetBEAN.equals("")) {
										mXSDSet.invoke(destino, mXSDSet.getParameterTypes()[0].getField(retornoGetBEAN.toString()).get(null));
									}
								} else if ((retornoGetBEAN.getClass().isEnum()) && (mXSDSet.getParameterTypes()[0] == String.class)) {	
									mXSDSet.invoke(destino, retornoGetBEAN.toString());
								} else if (mBEANGet.getReturnType().getPackage().getName().contains(PACOTE)) {
									Object objetoRetornoGetXSD = null;
									try {
										if ((origem instanceof BaseDTO) && (destino instanceof BaseEntity)) {
											Method metodoEspecifico = mBEANGet.getReturnType().getDeclaredMethod("converterParaEntidade", mBEANGet.getReturnType(), Integer.class);
											objetoRetornoGetXSD = metodoEspecifico.invoke(retornoGetBEAN.getClass(), retornoGetBEAN);
										} else if ((origem instanceof BaseEntity) && (destino instanceof BaseDTO)) {
											Method metodoEspecifico = mXSDSet.getParameterTypes()[0].getDeclaredMethod("converterParaDto", retornoGetBEAN.getClass(), Integer.class);
											objetoRetornoGetXSD = metodoEspecifico.invoke(retornoGetBEAN.getClass(), retornoGetBEAN);
										} else if ((origem instanceof BaseDTO) && (destino instanceof BaseApi)) {
											Method metodoEspecifico = mXSDSet.getParameterTypes()[0].getDeclaredMethod("converterParaApi", retornoGetBEAN.getClass(), Integer.class);
											objetoRetornoGetXSD = metodoEspecifico.invoke(retornoGetBEAN.getClass(), retornoGetBEAN);
										} else if ((origem instanceof BaseApi) && (destino instanceof BaseDTO)) {
											Method metodoEspecifico = mBEANGet.getReturnType().getDeclaredMethod("converterParaDto", mBEANGet.getReturnType(), Integer.class);
											objetoRetornoGetXSD = metodoEspecifico.invoke(retornoGetBEAN.getClass(), retornoGetBEAN);
										} else {
											objetoRetornoGetXSD = null;
										}
									} catch (NoSuchMethodException erro) {
										objetoRetornoGetXSD = null;
									}
									if (objetoRetornoGetXSD == null) {										
										objetoRetornoGetXSD = ConverterClasse(retornoGetBEAN, mXSDSet.getParameterTypes()[0], nivelLimite, nivel + 1);
									}
									mXSDSet.invoke(destino, objetoRetornoGetXSD);
								} else if (mXSDSet.getParameterTypes()[0] == String.class) {
									mXSDSet.invoke(destino, retornoGetBEAN.toString());
								} else {
									LOGGER.info("Logging an Tipo de conversão inválida. Tipo de origem: "+retornoGetBEAN.getClass().getName()
											+" para Tipo de destino:  "+mXSDSet.getParameterTypes()[0].getName());									
								}
							}
						}
					}
				}
				if ((origem instanceof BaseEntity) && (destino instanceof BaseDTO)) {
					BaseDTO dtoDestino = (BaseDTO) destino;
					BaseEntity entidadeOrigem = (BaseEntity) origem;
					dtoDestino.setDetalhe(new Detalhe());
					if (entidadeOrigem.getDataHoraAlteracao() != null) {
						dtoDestino.getDetalhe().setDataHoraAlteracao(sdfUtc.format(entidadeOrigem.getDataHoraAlteracao()));
					}
					if (entidadeOrigem.getDataHoraInclusao() != null) {
						dtoDestino.getDetalhe().setDataHoraInclusao(sdfUtc.format(entidadeOrigem.getDataHoraInclusao()));
					}
					dtoDestino.getDetalhe().setId(entidadeOrigem.getId());
					dtoDestino.getDetalhe().setVersao(entidadeOrigem.getVersao());
					dtoDestino.getDetalhe().setUsuarioAlteracao(entidadeOrigem.getUsuarioAlteracao());
					dtoDestino.getDetalhe().setUsuarioInclusao(entidadeOrigem.getUsuarioInclusao());
					dtoDestino.getDetalhe().setUuid(entidadeOrigem.getUuid());
				} else if ((destino instanceof BaseEntity) && (origem instanceof BaseDTO)) {
					BaseDTO dtoOrigem = (BaseDTO) origem;
					BaseEntity entidadeDestino = (BaseEntity) destino;
					if (dtoOrigem.getDetalhe() != null) {
						try {
							if (dtoOrigem.getDetalhe().getDataHoraAlteracao() != null) {
								entidadeDestino.setDataHoraAlteracao(sdfUtc.parse(dtoOrigem.getDetalhe().getDataHoraAlteracao()));
							}
							if (dtoOrigem.getDetalhe().getDataHoraInclusao() != null) {
								entidadeDestino.setDataHoraInclusao(sdfUtc.parse(dtoOrigem.getDetalhe().getDataHoraAlteracao()));
							}
						} catch (ParseException e) {
							throw new ErroBase(ErroBase.Codigos.BASE_E_2);
						}
						entidadeDestino.setId(dtoOrigem.getDetalhe().getId());
						entidadeDestino.setVersao(dtoOrigem.getDetalhe().getVersao());
						entidadeDestino.setUsuarioAlteracao(dtoOrigem.getDetalhe().getUsuarioAlteracao());
						entidadeDestino.setUsuarioInclusao(dtoOrigem.getDetalhe().getUsuarioInclusao());
						entidadeDestino.setUuid(dtoOrigem.getDetalhe().getUuid());
					}
				} else if ((origem instanceof BaseApi) && (destino instanceof BaseDTO)) {
					BaseDTO dtoDestino = (BaseDTO) destino;
					BaseApi apiOrigem = (BaseApi) origem;
					dtoDestino.setDetalhe(apiOrigem.getDetalhe());
				} else if ((destino instanceof BaseApi) && (origem instanceof BaseDTO)) {
					BaseDTO dtoOrigem = (BaseDTO) origem;
					BaseApi apiDestino = (BaseApi) destino;
					apiDestino.setDetalhe(dtoOrigem.getDetalhe());
				}
			} catch (ErroBase e) {
				throw e;
			} catch (Exception e) {
				throw new ErroBase(ErroBase.Codigos.BASE_E_2);
			}
		}
	}
	
	private static Method buscarMetodoLista(Method[] lista, String nomeGet) {
		for (Method metodoSet : lista) {
			if (metodoSet.getName().equals(resolveNomeBean(nomeGet, "set"))) {
				if (metodoSet.getParameterCount() == 1) {
					return metodoSet;
				}
			}
		}
		return null;
	}

	private static Method buscarMetodoSet(Object origem, Object destino, String methodGetName) {
		Method[] mXSDSetLista = destino.getClass().getMethods();
		Method mXSDSet = buscarMetodoLista(mXSDSetLista, methodGetName);
		if (mXSDSet == null) {
			if ((origem instanceof BaseDTO) && (destino instanceof BaseEntity)) {
				mXSDSet = buscarMetodoLista(mXSDSetLista, methodGetName.replace("Dto", ""));
			} else if ((origem instanceof BaseEntity) && (destino instanceof BaseDTO)) {
				mXSDSet = buscarMetodoLista(mXSDSetLista, methodGetName+"Dto");
			} else if ((origem instanceof BaseApi) && (destino instanceof BaseDTO)) {
				mXSDSet = buscarMetodoLista(mXSDSetLista, methodGetName.replace("Api", "")+"Dto");
			} else if ((origem instanceof BaseDTO) && (destino instanceof BaseApi)) {
				mXSDSet = buscarMetodoLista(mXSDSetLista, methodGetName.replace("Dto", "")+"Api");
			}
		}		
		return mXSDSet;
	}

	private static String resolveNomeBean(String nomeXsd, String prefixRetorno) {
		String nomeXsdSemPrefix = nomeXsd.substring(3);
		return prefixRetorno+nomeXsdSemPrefix;
 	}
	
	private static <T> List<T> createListOfType(Class<T> type) {
	    return new ArrayList<T>();
	}
	
	private static <T> HashSet<T> createSetOfType(Class<T> type) {
	    return new HashSet<T>();
	}

	private static <T> void validar(T objetoValidar) {

		ValidacoesResult validacoesResult = new ValidacoesResult();

		// injecao da classe de validacoes
		validacoesResult = InjecaoCdiUtil.getFacade(BaseValidacao.class).validarObjeto(objetoValidar);

		// caso validacoes size > 0 entao lancar exceção ErroBase
		if (validacoesResult.getValidacoes() != null && validacoesResult.getValidacoes().size() > 0)
			throw new ErroBase(validacoesResult).converterParaRest();

	}
	
}
