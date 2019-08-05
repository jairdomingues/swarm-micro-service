package br.com.smartcarweb.dao;

import br.com.smartcarweb.api.util.persistence.ejb3.BaseDao;
import br.com.smartcarweb.model.Beacon;

public interface BeaconDAO extends BaseDao<Beacon> {

	public abstract Beacon consultarPorUuidBeacon(String uuid);

}
