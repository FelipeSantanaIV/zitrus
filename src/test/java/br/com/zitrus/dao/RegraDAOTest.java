package br.com.zitrus.dao;

import br.com.zitrus.enums.SexoEnum;
import br.com.zitrus.model.Procedimento;
import br.com.zitrus.model.Solicitacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegraDAOTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private RegraDAO regraDAO;

    private Solicitacao solicitacao;
    private Query query;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        solicitacao = new Solicitacao();
        Procedimento procedimento = new Procedimento();
        procedimento.setId(1L);
        solicitacao.setProcedimento(procedimento);
        solicitacao.setIdade(30);
        solicitacao.setSexo(SexoEnum.MASCULINO);

        query = mock(Query.class);
    }

    @Test
    public void testValidaSolicitacao_ExisteRegra() {
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);

        boolean resultado = regraDAO.validaSolicitacao(solicitacao);

        assertTrue(resultado);
    }

    @Test
    public void testValidaSolicitacao_NaoExisteRegra() {
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        boolean resultado = regraDAO.validaSolicitacao(solicitacao);

        assertFalse(resultado);
    }

    @Test
    public void testValidaSolicitacao_Exception() {
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            regraDAO.validaSolicitacao(solicitacao);
        });
    }
}
