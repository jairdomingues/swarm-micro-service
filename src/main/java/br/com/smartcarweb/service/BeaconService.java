package br.com.smartcarweb.service;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.smartcarweb.dao.BeaconDAO;
import br.com.smartcarweb.dao.UsuarioDAO;
import br.com.smartcarweb.model.Beacon;
import br.com.smartcarweb.model.Usuario;
import br.com.smartcarweb.service.dto.BeaconDTO;
import br.com.smartcarweb.service.dto.BeaconListDTO;
import br.com.smartcarweb.service.dto.FCMRequestDTO;
import br.com.smartcarweb.service.dto.NotificationDTO;

@Stateless
public class BeaconService {

	@Inject
	BeaconDAO beaconDAO;

	@Inject
	UsuarioDAO usuarioDAO;

	public void getBeacon(String uuid) {

		Beacon beacon = beaconDAO.consultarPorUuidBeacon(uuid);
		if (beacon != null) {
			// envia mensagem de acordo com o local
			System.out.println("foi: " + uuid);

		} else {
			System.out.println("não foi: " + uuid);
		}

	}

	public void postBeacons(Long idUsuario, BeaconListDTO beaconList) {
		
		Usuario usuario = usuarioDAO.consultarPorId(idUsuario);
		
		//independente de quantos beacons estiverem na lista, vou pegar apenas o primeiro que é o mais aproximado
		BeaconDTO beaconDTO = beaconList.getBeacons().stream()
			     .sorted(Comparator.comparingDouble(x -> new Double(((BeaconDTO) x).getDistance()))).findFirst().get();

		usuario.setDeviceId(beaconDTO.getTokenDevice());
		Beacon beacon = beaconDAO.consultarPorUuidBeacon(beaconDTO.getUuidBeacon());
		
		Double distancia = new Double(beaconDTO.getDistance());
		if(distancia <= 1.0 ) {
			//esta muito proximo
			enviaNotificacaoFCM(usuario.getDeviceId(),new Date(), "Local Muito próximo: "+beaconDTO.getDistance(), beacon.getPlace().getName(), "www.indcash.com.br", "");
		} else if (distancia > 1.0 && distancia <= 3.0) {
			//esta apenas proximo registrar proximidade com alguma ação
			enviaNotificacaoFCM(usuario.getDeviceId(),new Date(), "Local Próximo: "+beaconDTO.getDistance(), beacon.getPlace().getName(), "www.indcash.com.br", "");
		} else if (distancia > 3) {
			//esta longe
			enviaNotificacaoFCM(usuario.getDeviceId(),new Date(), "Local Longe: "+beaconDTO.getDistance(), beacon.getPlace().getName(), "www.indcash.com.br", "");
		}
	}

	public void enviaNotificacaoFCM(String deviceId, Date data, String title, String body, String action, String icon) {

		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();

		Locale brasil = new Locale("pt", "BR"); // Retorna do país e a língua
		DateFormat f2 = DateFormat.getDateInstance(DateFormat.FULL, brasil);

		icon = "https://cdn2.iconfinder.com/data/icons/plump-by-zerode_/256/Folder-URL-History-icon.png";
		Client client = ClientBuilder.newClient();
		FCMRequestDTO fcmRequest = new FCMRequestDTO();
		fcmRequest.setTo(deviceId);
		NotificationDTO notification = new NotificationDTO();
		notification.setBody(body + " em " + f2.format(date) + ".");
		notification.setTitle(title);
		notification.setClick_action(action);
		notification.setIcon(icon);
		fcmRequest.setNotification(notification);

		WebTarget target = client.target("http://fcm.googleapis.com/fcm/send");
		Response response = target.request().header("Content-Type", "application/json").header("Authorization",
				"key=AAAA4ZNJqRg:APA91bGHwgCRh7bMNbfaxU2J2HyA_OFwIBxS6aIj7IOjX_leJ5KxMTMv8Cx3BQwUctXCjmJ-kE_eviQvyf7WYTsBffPWp1JIo8bw2E4oCBSMD5IJwJG1MZBvfwP_ImmkN8b0QlgJxnRe")
				.post(Entity.entity(fcmRequest, MediaType.APPLICATION_JSON), Response.class);

		String erro = response.readEntity(String.class);

		if (response.getStatus() == 200) {
			System.out.println("ok");
		}

	}

}