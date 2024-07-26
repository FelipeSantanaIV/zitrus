package br.com.zitrus.servlet;

import br.com.zitrus.dao.ProcedimentoDAO;
import br.com.zitrus.dao.RegraDAO;
import br.com.zitrus.dao.SolicitacaoDAO;
import br.com.zitrus.model.Procedimento;
import br.com.zitrus.model.Solicitacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class SolicitacaoServletTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private SolicitacaoDAO solicitacaoDAO;

    @Mock
    private ProcedimentoDAO procedimentoDAO;

    @Mock
    private RegraDAO regraDAO;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private SolicitacaoServlet solicitacaoServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoPost_CriaSolicitacao() throws ServletException, IOException {
        when(request.getParameter("procedimento")).thenReturn("1");
        when(request.getParameter("nome")).thenReturn("Teste");
        when(request.getParameter("idade")).thenReturn("30");
        when(request.getParameter("sexo")).thenReturn("M");
        when(procedimentoDAO.buscarPorId(1L)).thenReturn(new Procedimento());
        when(regraDAO.validaSolicitacao(any(Solicitacao.class))).thenReturn(true);
        when(request.getRequestDispatcher("index.jsp")).thenReturn(requestDispatcher);

        solicitacaoServlet.doPost(request, response);

        verify(solicitacaoDAO, times(1)).criaSolicitacao(any(Solicitacao.class), eq(true));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_AlteraSolicitacao() throws ServletException, IOException {
        when(request.getParameter("procedimento")).thenReturn("1");
        when(request.getParameter("nome")).thenReturn("Teste");
        when(request.getParameter("idade")).thenReturn("30");
        when(request.getParameter("sexo")).thenReturn("M");
        when(request.getParameter("id")).thenReturn("1");
        when(procedimentoDAO.buscarPorId(1L)).thenReturn(new Procedimento());
        when(regraDAO.validaSolicitacao(any(Solicitacao.class))).thenReturn(true);
        when(request.getRequestDispatcher("index.jsp")).thenReturn(requestDispatcher);

        solicitacaoServlet.doPost(request, response);

        verify(solicitacaoDAO, times(1)).alterarSolicitacao(any(Solicitacao.class), eq(true));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_Exception() throws ServletException, IOException {
        when(request.getParameter("procedimento")).thenReturn("1");
        when(request.getParameter("nome")).thenReturn("Teste");
        when(request.getParameter("idade")).thenReturn("30");
        when(request.getParameter("sexo")).thenReturn("M");
        when(procedimentoDAO.buscarPorId(1L)).thenThrow(new RuntimeException());

        solicitacaoServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Procedimento inválido. Solicitação negada.");
    }

    @Test
    public void testDoDelete() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");

        solicitacaoServlet.doDelete(request, response);

        verify(solicitacaoDAO, times(1)).deletarSolicitacao(1L);
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    public void testDoDelete_Exception() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        doThrow(new RuntimeException()).when(solicitacaoDAO).deletarSolicitacao(1L);

        solicitacaoServlet.doDelete(request, response);

        verify(response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao deletar solicitação.");
    }
}
