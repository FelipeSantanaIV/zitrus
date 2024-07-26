<%@ page import="br.com.zitrus.model.Solicitacao" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.zitrus.dao.SolicitacaoDAO" %>
<%@ page import="br.com.zitrus.util.ConnectionFactory" %>
<%@ page import="javax.persistence.EntityManager" %>
<%@ page import="br.com.zitrus.enums.SexoEnum" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
  <meta charset="UTF-8">
  <title>Zitrus - Desafio Java Servlet</title>
  <link rel="stylesheet" href="css/bootstrap.css"></link>
  <script>
    function deleteSolicitacao(id) {
      fetch('solicitacao?id=' + id, {
        method: 'DELETE'
      }).then(response => {
        if (response.ok) {
          window.location.reload();
        } else {
          alert('Erro ao deletar solicitação');
        }
      });
    }
  </script>
</head>
<body>
<jsp:include page="header.jsp"/>



<div class="container">

  <h4>Nova Solicitação</h4>


  <div class="row justify-content-start">
    <div class="col-md-6">
      <form data-bitwarden-watching="1" action="solicitacao" method="POST">

        <div class="form-group">
          <label class="col-form-label mt-4" for="nome">Nome:</label>
          <input type="text" class="form-control" placeholder="Nome" name="nome" id="nome">

        </div>

        <div class="form-group" >
          <label class="col-form-label mt-4" for="idade">Idade:</label>
          <input type="text" class="form-control" placeholder="Idade" name="idade" id="idade">

        </div>

        <div class="form-group">
          <label class="form-label mt-4" for="sexo">Sexo:</label>
          <select class="form-control" name="sexo" id="sexo">
            <% for (SexoEnum sexoEnum : SexoEnum.values()) { %>
            <option value="<%= sexoEnum.getValor() %>"><%= sexoEnum.getDescricao() %></option>
            <% } %>
          </select>
        </div>

        <div class="form-group">
          <label class="col-form-label mt-4" for="procedimento">Procedimento:</label>
          <input type="text" class="form-control" style="margin-bottom: 20px" placeholder="Procedimento" name="procedimento" id="procedimento">

        </div>

        <button type="submit" class="btn btn-success" id="cadastrarSolicitacao">Solicitar</button>

      </form>

    </div>

  </div>
</div>

<table class="mt-3 table table-hover caption-top">
  <caption class="h4">Solicitações</caption>
  <thead class="table-dark">
  <th>Nome</th>
  <th>Idade</th>
  <th>Sexo</th>
  <th>Procedimento</th>
  <th>Status</th>
  <th>Opções</th>
  </thead>
  <tbody>
  <%
    EntityManager entityManager = ConnectionFactory.getEntityManager();
    SolicitacaoDAO solicitacaoDAO = new SolicitacaoDAO(entityManager);
    List<Solicitacao> solicitacoes = solicitacaoDAO.buscarTodos();

    for (Solicitacao solicitacao : solicitacoes){
  %>
  <tr>
    <td><%= solicitacao.getNome() %></td>
    <td><%= solicitacao.getIdade() %></td>
    <td><%= solicitacao.getSexo().getValor() %></td>
    <td><%= solicitacao.getProcedimento().getNome() %></td>
    <td><%= solicitacao.getPermissao().getDescricao() %></td>
    <td>
      <button onclick="deleteSolicitacao(<%= solicitacao.getId() %>)" class="btn btn-danger">Deletar</button>
    </td>
  </tr>
  <%
    }
  %>


  </tbody>
</table>


</body>
</html>