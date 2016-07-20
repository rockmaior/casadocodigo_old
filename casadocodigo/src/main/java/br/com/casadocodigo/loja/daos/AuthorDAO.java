package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.casadocodigo.loja.models.Author;

@Dependent
public class AuthorDAO {
	@PersistenceContext
	private EntityManager manager;
/**
	public List<Author> list() {
		CriteriaQuery<Author> query = manager.getCriteriaBuilder().createQuery(Author.class);
		query.select(query.from(Author.class));
		List<Author> lista = manager.createQuery(query).getResultList();
		return lista;
	}
	*/
	
	public List<Author> list() {
		TypedQuery<Author> tq = manager.createQuery("SELECT a FROM Author a",Author.class);
		return tq.getResultList();
	}
}
