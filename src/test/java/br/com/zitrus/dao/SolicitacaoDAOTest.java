package br.com.zitrus.dao;

import br.com.zitrus.enums.PermissaoEnum;
import br.com.zitrus.enums.SexoEnum;
import br.com.zitrus.model.Procedimento;
import br.com.zitrus.model.Solicitacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SolicitacaoDAOTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private SolicitacaoDAO solicitacaoDAO;

    private Solicitacao solicitacao;
    private Procedimento procedimento;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        procedimento = new Procedimento();
        procedimento.setId(1L);

        solicitacao = new Solicitacao();
        solicitacao.setId(1L);
        solicitacao.setProcedimento(procedimento);
        solicitacao.setNome("Teste");
        solicitacao.setIdade(30);
        solicitacao.setSexo(SexoEnum.MASCULINO);
        solicitacao.setPermissao(PermissaoEnum.NAO_PERMITIDO);

        when(entityManager.getTransaction()).thenReturn(transaction);
    }

    @Test
    public void testCriaSolicitacao() {
        doNothing().when(transaction).begin();
        doNothing().when(transaction).commit();

        solicitacaoDAO.criaSolicitacao(solicitacao, true);

        verify(entityManager).persist(any(Solicitacao.class));
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    public void testAlterarSolicitacao() {
        doNothing().when(transaction).begin();
        doNothing().when(transaction).commit();
        when(entityManager.find(Solicitacao.class, 1L)).thenReturn(solicitacao);

        solicitacaoDAO.alterarSolicitacao(solicitacao, true);

        verify(entityManager).find(Solicitacao.class, 1L);
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    public void testBuscarPorId() {
        when(entityManager.find(Solicitacao.class, 1L)).thenReturn(solicitacao);

        Solicitacao resultado = solicitacaoDAO.buscarPorId(1L);

        assertEquals(solicitacao, resultado);
    }

    @Test
    public void testBuscarTodos() {
        TypedQuery<Solicitacao> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Solicitacao.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(solicitacao));

        List<Solicitacao> resultado = solicitacaoDAO.buscarTodos();

        assertEquals(1, resultado.size());
        assertEquals(solicitacao, resultado.get(0));
    }

    @Test
    public void testBuscarPorNome() {
        TypedQuery<Solicitacao> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Solicitacao.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(solicitacao));

        List<Solicitacao> resultado = solicitacaoDAO.buscarPorNome("Teste");

        assertEquals(1, resultado.size());
        assertEquals(solicitacao, resultado.get(0));
    }

    @Test
    public void testDeletarSolicitacao() {
        doNothing().when(transaction).begin();
        doNothing().when(transaction).commit();
        when(entityManager.find(Solicitacao.class, 1L)).thenReturn(solicitacao);

        solicitacaoDAO.deletarSolicitacao(1L);

        verify(entityManager).remove(solicitacao);
        verify(transaction).begin();
        verify(transaction).commit();
    }
}
