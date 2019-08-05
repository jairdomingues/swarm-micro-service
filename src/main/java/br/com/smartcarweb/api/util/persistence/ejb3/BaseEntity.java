package br.com.smartcarweb.api.util.persistence.ejb3;

import java.util.Date;
import java.util.UUID;

import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import br.com.smartcarweb.api.util.domain.DomainObjectSupport;

@Named("baseEntity")
@MappedSuperclass
public abstract class BaseEntity extends DomainObjectSupport implements PersistentObject {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	@Column(nullable = false)
	private Long versao;

	@Column(nullable = false, updatable = false)
	protected String usuarioInclusao;

	@Column(nullable = false)
	private String usuarioAlteracao;

	@Column(nullable = false, updatable = false)
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataHoraInclusao;

	@Column(nullable = false)
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraAlteracao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

	public String getUsuarioInclusao() {
		return usuarioInclusao;
	}

	public void setUsuarioInclusao(String usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

	public String getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(String usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

	public Date getDataHoraInclusao() {
		return dataHoraInclusao;
	}

	public void setDataHoraInclusao(Date dataHoraInclusao) {
		this.dataHoraInclusao = dataHoraInclusao;
	}

	public Date getDataHoraAlteracao() {
		return dataHoraAlteracao;
	}

	public void setDataHoraAlteracao(Date dataHoraAlteracao) {
		this.dataHoraAlteracao = dataHoraAlteracao;
	}

	@PrePersist
	private void initTimeStamps() {
		if (dataHoraInclusao == null) {
			dataHoraInclusao = new Date();
		}
		if (uuid == null) {
			uuid = UUID.randomUUID().toString();
		}
		dataHoraAlteracao = dataHoraInclusao;
		usuarioAlteracao = this.getUsuarioInclusao();
	}

	@PreUpdate
	private void updateTimeStamp() {
		dataHoraAlteracao = new Date();
	}

	public boolean isPersistent() {
		return (id != null);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof BaseEntity)) {
			return false;
		}
		BaseEntity other = (BaseEntity) object;

		return (((getId() != null) || (other.getId() == null)))
				&& (((getId() == null) || (getId().equals(other.getId()))));
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.id != null) ? this.id.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return "Instance of " + getClass().getName() + " [id: " + id + "; uuid: " + uuid + "]";
	}

}