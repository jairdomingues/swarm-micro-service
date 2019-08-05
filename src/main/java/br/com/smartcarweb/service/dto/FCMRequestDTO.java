package br.com.smartcarweb.service.dto;

public class FCMRequestDTO {

	private NotificationDTO notification;
	private String to;

	public NotificationDTO getNotification() {
		return notification;
	}

	public void setNotification(NotificationDTO notification) {
		this.notification = notification;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}