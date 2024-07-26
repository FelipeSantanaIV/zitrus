<h1>Zitrus - Desafio</h1>

Você é o desenvolvedor responsável por um sistema de controle de autorizações de
procedimentos médicos para um plano de saúde. Os critérios para permitir a execução de um
procedimento são idade e sexo, de acordo com a tabela a seguir:

| PROCEDIMENTO | IDADE | SEXO | PERMITIDO |
|--------------|-------|------|:---------:|
| 1234         | 10    | M    | NÃO       |
| 4567         | 20    | M    | SIM       |
| 6789         | 10    | F    | NÃO       |
| 6789         | 10    | M    | SIM       |
| 1234         | 20    | M    | SIM       |
| 4567         | 30    | F    | SIM       |

Procedimentos não listados na tabela devem ser negados no cadastro, com mensagens de
retorno da requisição, justificados.


<h2>Tecnologias Utilizadas:</h2>



* Intellij

* Java 8

* Maven

* MySQL

* JPA + JPQL

* Liquibase

* JBoss/WildFly 24.0.1 FINAL

* Test Unitário com JUNIT/Mockito

<h3>Estrutura da Aplicação:</h3>


**Banco de Dados** - Temos 3 tabelas em um banco MySQL, solicitações, procedimentos existentes e uma com a regra de negócio, com os perfis aprovados.

**Código Java** - Usando JPA / EntityManager e JPQL para tratar a camada de persistência e consultas, respectivamente. Temos uma classe Servlet para intermediar as requisições.

**View** - A interface de usuário nessa aplicação é uma página simples em HTML + CSS (bootstrap/bootswatch) que visa receber as novas solicitações e exibir em tela as anteriores.


<h2>Como executar a aplicação:</h2>

Para executar a aplicação verique antes se você possui instalado o, [Maven](https://www.apache.org/), [Intellij](https://www.jetbrains.com/pt-br/idea/), [JBoss Wildfly](https://download.jboss.org/wildfly/24.0.1.Final/wildfly-24.0.1.Final.zip) e [MySQL 8.0.33](https://downloads.mysql.com/archives/workbench/).

+ No MySQL, criar o schema "zitrus". (porta 3306, user: root, password:(sua senha))

+ Na pasta raiz do projeto, executar no terminal o comando "mvn clean install -U" para baixar as dependências, criar e inserir dados nas tabelas.

+ Caso o Banco de dados não seja populado, rode o comando "liquibase update" do liquibase para fazer o update no banco, com o schama "zitrus" já cirado

+ No Intellij, Acesse "Run >> Edit Configurations". Selecione WildFly 24.0.1 FINAL.
+ O projeto, em desenvolvimento, rodou com as seguintes configurações:

 Server: WildFly 24.0.1 FINAL
 
 URL: http://localhost:8080/zitrus-solicitacao/
 
 JRE: jdk-8
 
 Deployment: zitrus:war

 

+ Após essas configurações, é só rodar a aplicação.
