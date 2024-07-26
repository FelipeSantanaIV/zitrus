package br.com.zitrus.dao;

import br.com.zitrus.model.Procedimento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProcedimentoDAOTest {

    private EntityManager entityManager;
    private ProcedimentoDAO procedimentoDAO;

    @BeforeEach
    public void setUp() {
        entityManager = mock(EntityManager.class);
        procedimentoDAO = new ProcedimentoDAO(entityManager);
    }

    @Test
    public void testCadastrar() {
        // Arrange
        Procedimento procedimento = new Procedimento();

        // Act
        procedimentoDAO.cadastrar(procedimento);

        // Assert
        verify(entityManager).persist(procedimento);
    }

    @Test
    public void testBuscarPorId() {
        // Arrange
        Long id = 1L;
        Procedimento procedimento = new Procedimento();
        when(entityManager.find(Procedimento.class, id)).thenReturn(procedimento);

        // Act
        Procedimento result = procedimentoDAO.buscarPorId(id);

        // Assert
        assertEquals(procedimento, result);
        verify(entityManager).find(Procedimento.class, id);
    }

    @Test
    public void testBuscarTodos() {
        // Arrange
        Procedimento procedimento1 = new Procedimento();
        Procedimento procedimento2 = new Procedimento();
        List<Procedimento> procedimentos = Arrays.asList(procedimento1, procedimento2);
        TypedQuery<Procedimento> query = mock(TypedQuery.class);
        when(query.getResultList()).thenReturn(procedimentos);
        when(entityManager.createQuery(any(String.class), eq(Procedimento.class))).thenReturn(query);

        // Act
        List<Procedimento> result = procedimentoDAO.buscarTodos();

        // Assert
        assertEquals(procedimentos, result);
        verify(entityManager).createQuery("SELECT p FROM Procedimento p", Procedimento.class);
    }

    @Test
    public void testBuscarPorNome() {
        // Arrange
        String nome = "Teste";
        Procedimento procedimento1 = new Procedimento();
        Procedimento procedimento2 = new Procedimento();
        List<Procedimento> procedimentos = Arrays.asList(procedimento1, procedimento2);
        TypedQuery<Procedimento> query = mock(TypedQuery.class);
        when(query.getResultList()).thenReturn(procedimentos);
        when(entityManager.createQuery(any(String.class), eq(Procedimento.class))).thenReturn(query);
        when(query.setParameter("nome", nome)).thenReturn(query);

        // Act
        List<Procedimento> result = procedimentoDAO.buscarPorNome(nome);

        // Assert
        assertEquals(procedimentos, result);
        verify(entityManager).createQuery("SELECT p FROM Procedimento p WHERE p.nome = :nome", Procedimento.class);
        verify(query).setParameter("nome", nome);
    }
}
