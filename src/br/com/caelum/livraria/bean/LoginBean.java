package br.com.caelum.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import br.com.caelum.livraria.util.ForwardView;
import br.com.caelum.livraria.util.RedirectView;
import br.com.caelum.livraria.dao.UsuarioDAO;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public ForwardView efetuaLogin(){
		
		boolean existe = new UsuarioDAO().existe(this.usuario);
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(existe){
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return new ForwardView(new RedirectView("livro").toString());
		}
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));
		
		return new ForwardView(new RedirectView("login").toString());
		
	}
	
	public ForwardView deslogar(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		
		return new ForwardView(new RedirectView("login").toString());
	}
	
}