package br.com.zitrus.dao;

import br.com.zitrus.model.Solicitacao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class RegraDAO {

    private EntityManager entityManager;
    public RegraDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional
    public boolean validaSolicitacao(Solicitacao solicitacao) {
        boolean existeRegra = false;

        try {
            String jpql = "SELECT COUNT(r) FROM Regra r " +
                    "WHERE r.procedimento.id = :procedimentoId " +
                    "AND r.idade = :idade " +
                    "AND r.sexo = :sexo";

            Query query = entityManager.createQuery(jpql)
                    .setParameter("procedimentoId", solicitacao.getProcedimento().getId())
                    .setParameter("idade", solicitacao.getIdade())
                    .setParameter("sexo", solicitacao.getSexo());

            Long count = (Long) query.getSingleResult();

            existeRegra = count > 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return existeRegra;
    }
}
