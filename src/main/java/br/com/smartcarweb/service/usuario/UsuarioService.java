package br.com.smartcarweb.service.usuario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.smartcarweb.api.excecoes.ErroAcc;
import br.com.smartcarweb.api.jwt.filter.LoggedIn;
import br.com.smartcarweb.api.util.ConversorClasse;
import br.com.smartcarweb.dao.ClienteDAO;
import br.com.smartcarweb.dao.PapelDAO;
import br.com.smartcarweb.dao.UsuarioDAO;
import br.com.smartcarweb.dao.WalletDAO;
import br.com.smartcarweb.model.Address;
import br.com.smartcarweb.model.Cliente;
import br.com.smartcarweb.model.Papel;
import br.com.smartcarweb.model.Usuario;
import br.com.smartcarweb.model.Wallet;

@Stateless
public class UsuarioService {

	@Inject
	UsuarioDAO usuarioDAO;

	@Inject
	PapelDAO papelDAO;

	@Inject
	ClienteDAO clienteDAO;

	@Inject
	WalletDAO walletDAO;

	@Inject
	@LoggedIn
	Usuario currentUser;

	public List<UsuarioDTO> listaUsuarios() {
		return ConversorClasse.ConverterListaClasses(usuarioDAO.listarTodos(), UsuarioDTO.class, 0);
	}

	public UsuarioDTO consultarUsuario(Long idUsuario) {
		Usuario usuario = usuarioDAO.consultarPorId(idUsuario);
		return ConversorClasse.ConverterClasse(usuario, UsuarioDTO.class, 0);
	}

	public Autenticado adicionarUsuario(UsuarioDTO usuarioDTO) {
		Usuario usuario = this.consultarPorEmail(usuarioDTO.getEmail());
		if (usuario==null) {
			usuario = ConversorClasse.ConverterClasse(usuarioDTO, Usuario.class, 0);
			usuario.setAdmin(false);
			usuario.setAtivo(true);
			usuario.setBloqueado(false);
			usuario.setValidado(false);
			List<Papel> papeis = new ArrayList<Papel>();
			Papel papel = papelDAO.consultarPorPapel(usuarioDTO.getPapeis().stream().findFirst().get().getNomeDoPapel());
			papeis.add(papel);
			usuario.setPapeis(papeis);
			usuario.setUsuarioInclusao("admin@email.com.br");
			usuarioDAO.inserir(usuario);
		}
		Autenticado autenticado = new Autenticado();
		autenticado.setEmail(usuario.getEmail());
		autenticado.setDeviceId(usuario.getDeviceId());
		autenticado.setId(usuario.getId());
		autenticado.setNome(usuario.getNome());
		autenticado.setNomeDoPapel(Papel.NomeDoPapel.Usuario.name());
		return autenticado;
	}

	public UsuarioDTO atualizarUsuario(Long idUsuario, UsuarioDTO usuarioDTO) {
		Usuario usuarioEdit = usuarioDAO.consultarPorId(idUsuario);
		if (usuarioEdit == null) {
			throw new ErroAcc(ErroAcc.Codigos.ACC_E_11);
		}
		Usuario usuarioNew = ConversorClasse.ConverterClasse(usuarioDTO, Usuario.class, 0);
		usuarioNew.setPapeis(usuarioEdit.getPapeis());
		usuarioNew.setUsuarioAlteracao(currentUser.getEmail());
		usuarioDAO.atualizar(usuarioNew);
		return ConversorClasse.ConverterClasse(usuarioNew, UsuarioDTO.class, 0);
	}

	public void removerUsuario(Long idUsuario) {
		Usuario usuario = usuarioDAO.consultarPorId(idUsuario);
		if (usuario == null) {
			throw new ErroAcc(ErroAcc.Codigos.ACC_E_11);
		}
		usuarioDAO.remover(usuario);
	}

	public Usuario consultarPorEmail(String email) {
		return usuarioDAO.consultarPorEmail(email);
	}

	public Autenticado login(String username, String password, String fusoHorario) {

		Usuario usuario = usuarioDAO.consultarPorEmail(username);

		if (usuario == null) {
			throw new ErroAcc(ErroAcc.Codigos.ACC_E_11);
		}

		if (!usuario.getPassword().equals(password) || !usuario.getEmail().equals(username)) {
			throw new ErroAcc(ErroAcc.Codigos.ACC_E_11);
		}

		if (!usuario.getAtivo()) {
			throw new ErroAcc(ErroAcc.Codigos.ACC_E_2);
		}

		if (usuario.getBloqueado()) {
			throw new ErroAcc(ErroAcc.Codigos.ACC_E_3);
		}

		usuario.setDataUltimoLogin(new Date());

		usuarioDAO.atualizar(usuario);

		Autenticado autenticado = ConversorClasse.ConverterClasse(usuario, Autenticado.class, 0);
		autenticado.setNomeDoPapel(
				ConversorClasse.ConverterClasse(usuario.getPapeis().stream().findFirst().get(), PapelDTO.class, 0)
						.getNomeDoPapel().name());
		autenticado.setId(usuario.getId());
		return autenticado;

	}

	public Boolean consultarPorEmailExiste(String email) {
		boolean existe = false;
		Usuario usuario = usuarioDAO.consultarPorEmail(email);
		if (usuario==null) {
			existe = false;
		} else {
			existe = true;
		}
		return existe;
	}

	public void adicionarCliente(Long idUsuario, ClienteDTO clienteDTO) {

		UsuarioDTO usuarioDTO = ConversorClasse.ConverterClasse(usuarioDAO.consultarPorId(idUsuario), UsuarioDTO.class, 0);
		clienteDTO.setUsuario(usuarioDTO);
		Cliente cliente = ConversorClasse.ConverterClasse(clienteDTO, Cliente.class, 0);
		cliente.setUsuarioInclusao(currentUser.getEmail());
		//remove papel Usuario - somente Usuario pode se tornar Cliente
		List<Papel> a = cliente.getUsuario().getPapeis().stream().filter(x -> x.getNomeDoPapel().equals(Papel.NomeDoPapel.Usuario)).collect(Collectors.toList());
		cliente.getUsuario().getPapeis().remove(a.get(0));
		//adiciona novo papel Cliente
		List<Papel> papeis = new ArrayList<Papel>();
		Papel papel = papelDAO.consultarPorPapel(Papel.NomeDoPapel.Cliente);
		papeis.add(papel);
		cliente.getUsuario().setPapeis(papeis);
		usuarioDAO.atualizar(cliente.getUsuario());
		clienteDAO.inserir(cliente);
		
		this.carteira(cliente);
		
		
	}

	private void carteira(Cliente cliente) {

		Client client = ClientBuilder.newClient();
		Response response = client.target("https://api.blockcypher.com/v1/bcy/test/addrs")
				.request(MediaType.APPLICATION_JSON)
				.post(null, Response.class);
     
		System.out.println("response: " + response.getEntity() + ",  status " + response.getStatus());
		
		if(response.getStatus()!=201) {
			Erro erro = response.readEntity(Erro.class);
			throw new ErroAcc(erro.getError());
		}
		AddressDTO address = response.readEntity(AddressDTO.class);
		
		System.out.println(address);
		
        WalletDTO wallet = new WalletDTO();
        wallet.setName(cliente.getCarteira());
        List<String> addresses = new ArrayList<String>();
        addresses.add(address.getAddress());
        wallet.setAddresses(addresses);
		
		client = ClientBuilder.newClient();
		response = client.target("https://api.blockcypher.com/v1/bcy/test/wallets?token=a190878092c04837987e81bde533e96e")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(wallet, MediaType.APPLICATION_JSON), Response.class);
     
		System.out.println("response: " + response.getEntity() + ",  status " + response.getStatus());
		
		if(response.getStatus()!=201) {
			Erro erro = response.readEntity(Erro.class);
			throw new ErroAcc(erro.getError());
		}
		
		TokenInfoDTO tokenInfo = response.readEntity(TokenInfoDTO.class);
		System.out.println(tokenInfo);
		
		Address addressNew = new Address();
		addressNew.setAddress(address.getAddress());
		addressNew.setPrivateAddress(address.getPrivateAddress());
		addressNew.setPublicAddress(address.getPublicAddress());
		addressNew.setWif(address.getWif());
		addressNew.setUsuarioInclusao(currentUser.getEmail());
		
		Wallet walletNew = new Wallet();
		walletNew.setName(wallet.getName());
		walletNew.setAddresses(new ArrayList<Address>());
		walletNew.getAddresses().add(addressNew);
		walletNew.setUsuarioInclusao(currentUser.getEmail());
		walletNew.setCliente(cliente);
		walletDAO.inserir(walletNew);
		
	}
	
	public Balance consultarCarteira(Long idUsuario) {
		
		Wallet wallet = walletDAO.consultarPorCliente(idUsuario);
		String abcd = wallet.getName();
		BigDecimal saldo = new BigDecimal("0");
		Client client = ClientBuilder.newClient();
		Response response = client.target("https://api.blockcypher.com/v1/bcy/test/wallets/"+abcd+"?token=a190878092c04837987e81bde533e96e")
				.request(MediaType.APPLICATION_JSON)
				.get();
		

		if(response.getStatus()!=200) {
			Erro erro = response.readEntity(Erro.class);
			throw new ErroAcc(erro.getError());
		}
		
		TokenInfoDTO tokenInfo = response.readEntity(TokenInfoDTO.class);
		System.out.println(tokenInfo);
		Balance balance = null;
		for (String addr : tokenInfo.getAddresses()) {
			client = ClientBuilder.newClient();
			response = client.target("https://api.blockcypher.com/v1/bcy/test/addrs/"+addr+"/balance")
					.request(MediaType.APPLICATION_JSON)
					.get();

			if(response.getStatus()!=200) {
				Erro erro = response.readEntity(Erro.class);
				throw new ErroAcc(erro.getError());
			}
			
			balance = response.readEntity(Balance.class);
			
		}
		return balance;
	}
	
	public ClienteDTO consultarCliente(Long idUsuario) {
		Cliente cliente = clienteDAO.consultarPorUsuario(idUsuario);
		return ConversorClasse.ConverterClasse(cliente, ClienteDTO.class, 0);
	}	
	
}