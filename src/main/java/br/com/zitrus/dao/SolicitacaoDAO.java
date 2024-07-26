package br.com.zitrus.dao;

import br.com.zitrus.enums.PermissaoEnum;
import br.com.zitrus.model.Solicitacao;

import javax.persistence.EntityManager;
import java.util.List;

public class SolicitacaoDAO {

    private EntityManager entityManager;

    public SolicitacaoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void criaSolicitacao(Solicitacao solicitacao, boolean autorizacaoDaSolicitacao) {
        try {
            entityManager.getTransaction().begin();

            Solicitacao novaSolicitacao = new Solicitacao();
            novaSolicitacao.setProcedimento(solicitacao.getProcedimento());
            novaSolicitacao.setNome(solicitacao.getNome());
            novaSolicitacao.setIdade(solicitacao.getIdade());
            novaSolicitacao.setSexo(solicitacao.getSexo());
            novaSolicitacao.setPermissao(autorizacaoDaSolicitacao ? PermissaoEnum.PERMITIDO : PermissaoEnum.NAO_PERMITIDO);

            entityManager.persist(novaSolicitacao);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        }
    }

    public void alterarSolicitacao(Solicitacao solicitacao, boolean autorizacaoDaSolicitacao) {
        try {
            entityManager.getTransaction().begin();

            Solicitacao solicitacaoExistente = entityManager.find(Solicitacao.class, solicitacao.getId());

            solicitacaoExistente.setProcedimento(solicitacao.getProcedimento());
            solicitacaoExistente.setNome(solicitacao.getNome());
            solicitacaoExistente.setIdade(solicitacao.getIdade());
            solicitacaoExistente.setSexo(solicitacao.getSexo());
            solicitacaoExistente.setPermissao(autorizacaoDaSolicitacao ? PermissaoEnum.PERMITIDO : PermissaoEnum.NAO_PERMITIDO);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        }
    }
    public Solicitacao buscarPorId(Long id) {
        return entityManager.find(Solicitacao.class, id);

    }
    public List<Solicitacao> buscarTodos(){
        String jpql = "SELECT p FROM Solicitacao p";
        return entityManager.createQuery(jpql, Solicitacao.class).getResultList();

    }
    public List<Solicitacao> buscarPorNome(String nome){
        String jpql = "SELECT p FROM Solicitacao p WHERE p.nome = :nome";
        return entityManager.createQuery(jpql, Solicitacao.class).setParameter("nome", nome).getResultList();
    }

    public void deletarSolicitacao(Long id) {
        try {
            entityManager.getTransaction().begin();

            Solicitacao solicitacao = entityManager.find(Solicitacao.class, id);
            if (solicitacao != null) {
                entityManager.remove(solicitacao);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
