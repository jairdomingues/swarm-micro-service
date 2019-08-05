package br.com.smartcarweb.api.util.persistence.ejb3;

import br.com.smartcarweb.api.util.domain.DomainObject;

public interface PersistentObject extends DomainObject {

    Long getId();

    Long getVersao();

    boolean isPersistent();

}
