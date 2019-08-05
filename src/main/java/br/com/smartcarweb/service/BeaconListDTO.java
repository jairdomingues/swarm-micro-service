package br.com.smartcarweb.service;

import java.io.Serializable;
import java.util.List;

public class BeaconListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<BeaconDTO> beacons;

	public List<BeaconDTO> getBeacons() {
		return beacons;
	}

	public void setBeacons(List<BeaconDTO> beacons) {
		this.beacons = beacons;
	}

}
