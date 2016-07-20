package br.com.casadocodigo.loja.infra;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class MessagesHelper {
	@Inject
	private FacesContext facesContext;
	
	public void addFlash(FacesMessage facesMessage) {
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		facesContext.addMessage(null, facesMessage);
	}

	public void addMessage(FacesMessage facesMessage) {
		facesContext.addMessage(null, facesMessage);
	}

	public boolean hasMessage() {
		// TODO Auto-generated method stub
		return false;
	}
}
