package br.com.smartcarweb.service;

public class BeaconDTO {

	private String uuidBeacon;
	private String distance;
	private String tokenDevice;

	public String getUuidBeacon() {
		return uuidBeacon;
	}

	public void setUuidBeacon(String uuidBeacon) {
		this.uuidBeacon = uuidBeacon;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTokenDevice() {
		return tokenDevice;
	}

	public void setTokenDevice(String tokenDevice) {
		this.tokenDevice = tokenDevice;
	}

}
