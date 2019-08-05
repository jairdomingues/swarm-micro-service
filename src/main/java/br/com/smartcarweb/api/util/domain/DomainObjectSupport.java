package br.com.smartcarweb.api.util.domain;

import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DomainObjectSupport implements DomainObject {

    private static final long serialVersionUID = 1L;

    @Basic
    @Column(nullable = false, length = 40 , updatable = false, insertable = true, unique = true)
    protected String uuid;

    public DomainObjectSupport() {
        uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (!getClass().equals(obj.getClass()))) { 
            return false; 
        }
        DomainObjectSupport domainObject = (DomainObjectSupport) obj;
        return uuid.equals(domainObject.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    public int compareTo(DomainObjectSupport object) {
        return uuid.compareTo(object.uuid);
    }

}
