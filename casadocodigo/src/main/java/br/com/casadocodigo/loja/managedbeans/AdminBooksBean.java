package br.com.casadocodigo.loja.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.daos.AuthorDAO;
import br.com.casadocodigo.loja.daos.BookDAO;
import br.com.casadocodigo.loja.infra.MessagesHelper;
import br.com.casadocodigo.loja.models.Author;
import br.com.casadocodigo.loja.models.Book;

@Model
public class AdminBooksBean {
	private Book product = new Book();
	private List<Integer> selectedAuthorsIds = new ArrayList<>();
	private List<Author> authors = new ArrayList<>();

	@Inject
	private BookDAO bookDAO;
	
	@Inject
	private AuthorDAO authorDAO;
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private MessagesHelper messagesHelper;

	@PostConstruct
	public void loadObjects() {
		this.authors = authorDAO.list();
	}

	@Transactional
	public String save() {
		populateBookAuthor();
		if (product.getTitle() == null || product.getTitle().trim().isEmpty()) {
			messagesHelper.addFlash(new FacesMessage("titulo obrigatorio"));
		}
		
		if (product.getDescription()==null || product.getDescription().trim().isEmpty()) {
			messagesHelper.addFlash(new FacesMessage("descrição obrigatoria"));
		}
		
		bookDAO.save(product);
		
		/**
		 * clearObjects();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		 * Quando add mensagens elas ficam disponiveis somente durante o request
		 * atual, como estamos fazendo um redirect do lado do cliente a
		 * instrução abaixo não tem efeito senão utilizarmos o escopo getFlash.
		 * facesContext.addMessage(null, new FacesMessage("Livro gravado com sucesso"));
		 *
		 *Trecho isolado na classe MessagesHelper
		 *facesContext.getExternalContext().getFlash().setKeepMessages(true);
		 *facesContext.addMessage(null, new FacesMessage("Livro gravado com sucesso"));
		 */
				
		messagesHelper.addFlash(new FacesMessage("Livro gravado com sucesso"));
		return "/livros/lista?faces-redirect=true";
	}

	private void clearObjects() {
		// Limpa o objeto product de Book
		this.product = new Book();
		// Limpa a lista de Ids de autores. Poderiamos da um new ArrayList;
		this.selectedAuthorsIds.clear();
	}

	private void populateBookAuthor() {
		// Usa lambda???
		selectedAuthorsIds.stream().map((id) -> {
			return new Author(id);
		}).forEach(product::add);
	}

	public void setSelectedAuthorsIds(List<Integer> selectedAuthorsIds) {
		this.selectedAuthorsIds = selectedAuthorsIds;
	}

	public List<Integer> getSelectedAuthorsIds() {
		return selectedAuthorsIds;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public Book getProduct() {
		return product;
	}
}
