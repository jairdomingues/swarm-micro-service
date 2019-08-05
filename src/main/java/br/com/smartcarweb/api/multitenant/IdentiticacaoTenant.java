package br.com.smartcarweb.api.multitenant;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class IdentiticacaoTenant {
    
    private String tenant;

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

}
