package br.com.zitrus.dao;

import br.com.zitrus.model.Procedimento;

import javax.persistence.EntityManager;

import java.util.List;

public class ProcedimentoDAO {

    private EntityManager entityManager;

    public ProcedimentoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar (Procedimento pedido) {
        this.entityManager.persist(pedido);

    }
    public Procedimento buscarPorId(Long id) {
        return entityManager.find(Procedimento.class, id);

    }
    public List<Procedimento> buscarTodos(){
        String jpql = "SELECT p FROM Procedimento p";
        return entityManager.createQuery(jpql, Procedimento.class).getResultList();

    }
    public List<Procedimento> buscarPorNome(String nome){
        String jpql = "SELECT p FROM Procedimento p WHERE p.nome = :nome";
        return entityManager.createQuery(jpql, Procedimento.class).setParameter("nome", nome).getResultList();

    }
}
