package br.com.smartcarweb.api.util.domain;

import java.io.Serializable;

public interface DomainObject extends Serializable {

    String getUuid();

    void setUuid(String uuid);

}
