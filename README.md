# Exercício 3: Integração Spark

Este repositório contém a implementação do sistema **Music (not)PlayList**, desenvolvido como parte do **Exercício 3: Integração Spark** da disciplina de TI II da PUC Minas.  
O sistema é um cadastro de músicas em **Java**, utilizando o **Eclipse** e o **SGBDR PostgreSQL**, para implementar o back-end de uma aplicação Web com o framework **Spark**, além de uma interface HTML para interação com o usuário.

## Objetivo

A tarefa consiste em implementar um **CRUD** utilizando o Spark Framework. Além disso, é necessário criar um formulário em HTML para leitura e escrita de dados, conforme o exemplo [**WS03 Produto Service**](https://github.com/icei-pucminas/ti2cc/tree/master/WS03ProdutoService) presente no [repositório da disciplina de TI II](https://github.com/icei-pucminas/ti2cc). Esta atividade visa aplicar os conceitos aprendidos no [**Exercício 2**](https://github.com/cestpassion/ti2-exercicio02), com o diferencial de integrar formulários HTML.  
A aplicação gerencia uma lista de músicas, permitindo que o usuário adicione, atualize, visualize e remova músicas.

## Tecnologias Utilizadas

- **Java** (versão utilizada na IDE Eclipse)
- **Eclipse IDE** (para desenvolvimento)
- **Maven** (para gerenciamento de dependências)
- **PostgreSQL** (para persistência de dados)
- **Spark Framework** (para construção do back-end)
- **HTML/CSS** (para o front-end)

## Estrutura do Projeto

- **/src**: Contém os arquivos de código-fonte.
  - **/main**: Código principal da aplicação.
    - **/java**: Implementação do back-end com Spark e integração com PostgreSQL.
      - **/app**: Classe principal da aplicação, incluindo as rotas do Spark.
      - **/dao**: Classe de acesso a dados (Data Access Objects), responsável pela interação com o banco de dados PostgreSQL.
      - **/model**: Modelos de dados, como a classe `Music`, que representa as músicas cadastradas.
      - **/service**: Classe que contém a lógica de negócio do sistema, manipulando dados entre o DAO e o front-end.
    - **/resources**: Arquivos de recursos, incluindo:
      - **html**: Formulários e páginas HTML utilizadas para a interação com o usuário.
      - **sql**: Scripts SQL necessários para a criação das tabelas no banco de dados.
  - **/test**: Testes unitários e de integração.

- **pom.xml**: Arquivo de configuração do Maven, responsável por gerenciar as dependências do projeto, incluindo o Spark Framework e o driver do PostgreSQL.
