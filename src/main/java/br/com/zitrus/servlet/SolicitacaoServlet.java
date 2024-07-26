package br.com.zitrus.servlet;

import br.com.zitrus.dao.ProcedimentoDAO;
import br.com.zitrus.dao.RegraDAO;
import br.com.zitrus.dao.SolicitacaoDAO;
import br.com.zitrus.enums.PermissaoEnum;
import br.com.zitrus.enums.SexoEnum;
import br.com.zitrus.model.Procedimento;
import br.com.zitrus.model.Solicitacao;
import br.com.zitrus.util.ConnectionFactory;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/solicitacao")
public class SolicitacaoServlet extends HttpServlet {

    EntityManager entityManager = ConnectionFactory.getEntityManager();
    SolicitacaoDAO solicitacaoDAO = new SolicitacaoDAO(entityManager);
    ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO(entityManager);
    RegraDAO regraDAO = new RegraDAO(entityManager);


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Procedimento procedimento = procedimentoDAO.buscarPorId(Long.parseLong(request.getParameter("procedimento")));
            String nome = request.getParameter("nome");
            int idade = Integer.parseInt(request.getParameter("idade"));
            SexoEnum sexo = SexoEnum.fromValue(request.getParameter("sexo"));

            Solicitacao solicitacao = new Solicitacao(procedimento, nome, idade, sexo, PermissaoEnum.NAO_PERMITIDO);
            boolean autorizacaoDaSolicitacao = regraDAO.validaSolicitacao(solicitacao);

            if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
                solicitacao.setId(Long.parseLong(request.getParameter("id")));
                solicitacaoDAO.alterarSolicitacao(solicitacao, autorizacaoDaSolicitacao);
            } else {
                solicitacaoDAO.criaSolicitacao(solicitacao, autorizacaoDaSolicitacao);
            }

            populaIndex(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Procedimento inválido. Solicitação negada.");
        }
    }

    private void populaIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("procedimentos", procedimentoDAO.buscarTodos());
        request.setAttribute("sexos", SexoEnum.values());
        request.setAttribute("solicitacoes", solicitacaoDAO.buscarTodos());

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }


}
