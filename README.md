# Sistema de Empréstimo de Livros para Biblioteca

Este é um sistema de empréstimo de livros desenvolvido em Java para gerenciar as operações de empréstimo de livros em uma biblioteca. O sistema permite que os usuários realizem empréstimos, devoluções e consultas sobre os livros disponíveis na biblioteca.
Utiliza autenticação JWT.

## Funcionalidades

- Cadastro de usuários
- Cadastro de livros
- Empréstimo de livros
- Devolução de livros
- Consulta de livros disponíveis
- Consulta de empréstimos por usuário
- Gerenciamento de prazos de empréstimo

## Tecnologias Utilizadas

- Linguagem de Programação: Java
- Framework de Desenvolvimento: Spring Boot
- Banco de Dados: MySQL
- Sistema de Controle de Versão: Git

## Pré-requisitos

- JDK (Java Development Kit) instalado
- Maven para gerenciamento de dependências
- MySQL Server instalado e configurado
- Git para clonar o repositório

## Configuração do Banco de Dados

1. Crie um banco de dados MySQL chamado `biblioteca`.
2. Execute o script `schema.sql` para criar as tabelas necessárias.

## Configuração do Projeto

1. Clone este repositório para o seu ambiente de desenvolvimento.
2. Configure as credenciais do banco de dados no arquivo `application.properties`.
3. Execute o comando `mvn clean install` para baixar as dependências e compilar o projeto.

## Executando a Aplicação

1. Execute o comando `mvn spring-boot:run` para iniciar a aplicação.
2. Acesse `http://localhost:8080` em seu navegador para utilizar o sistema.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue ou enviar um pull request com melhorias para o sistema.

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).
