package br.com.smartcarweb.service.dto;

import java.io.Serializable;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import br.com.smartcarweb.model.Responsavel;

public class ResponsavelCreateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String cpf;
	private String email;

	public static Responsavel createEntity(ResponsavelCreateDTO responsavelCreateDTO) {
		Responsavel responsavel = new Responsavel();
		responsavel.setCpf(responsavelCreateDTO.getCpf());
		responsavel.setEmail(responsavelCreateDTO.getEmail());
		responsavel.setNome(responsavelCreateDTO.getNome());
		
		 ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		 Validator validator = factory.getValidator();
		 Set<ConstraintViolation<Responsavel>> constraintViolations = validator.validate( responsavel );
		 System.out.println(constraintViolations.size());
		return responsavel;
	}
	
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
