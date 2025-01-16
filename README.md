# Gerenciamento de Clientes

Este projeto é uma API de Gerenciamento de Clientes desenvolvido em Java utilizando o framework Spring Boot. A aplicação permite o cadastro de usuários, para que eles armazenenm transações como receitas e despesas, e possam gerar relatórios simples com esses dados

## Sumário

- [Recursos](#recursos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Uso](#uso)
- [Endpoints](#endpoints)
- [Contribuição](#contribuição)
- [Licença](#licença)

## Recursos

- Cadastro de Clientes
- Gerenciamento de Contas e histórico
- Transações financeiras (receitas e despesas)
- ~Documentação da API com Swagger~
- ~Autenticação e autorização com JWT~

## Instalação

1. Clone o repositório:
   git clone https://github.com/Telkynha/gerenciamento-clientes.git
   cd gerenciamento-clientes

2. Certifique-se de ter o Maven instalado e configurado.

3. Compile o projeto:
   mvn clean install

## Configuração

1. Crie o banco de dados MySQL:
   CREATE DATABASE gerenciamento_clientes;

2. Configure as credenciais do banco de dados no arquivo `src/main/resources/application.properties`:
   spring.datasource.url=jdbc:mysql://<HOST>:<PORT>/gerenciamento_clientes
   spring.datasource.username=<USERNAME>
   spring.datasource.password=<PASSWORD>
   spring.jpa.hibernate.ddl-auto=update

3. Configure o serviço de aplicativo da Azure no `application.properties`, se necessário.

## Uso

1. Inicie a aplicação:
   mvn spring-boot:run

2. Acesse a documentação da API Swagger em:
   http://localhost:8080/swagger-ui/

## Endpoints

Aqui estão alguns dos principais endpoints disponíveis na API:

### Clientes
- GET /api/cliente/{id}: Busca cliente por ID
- POST /api/cliente: Cria um novo cliente
- DELETE /api/cliente/{id}: Deleta um cliente por ID

### Contas
- GET /api/conta/{id}: Busca conta por ID
- PATCH /api/conta/{id}/saldo: Atualiza saldo da conta
- GET /api/conta/{id}/saldo: Imprime saldo da conta

### Transações
- POST /api/transacao: Cria uma nova transação
- PATCH /api/transacao/{id}: Edita uma transação existente
- DELETE /api/transacao/{id}: Deleta uma transação por ID
- GET /api/transacao/{id}: Busca transação por ID
- GET /api/transacao: Lista transações por período
