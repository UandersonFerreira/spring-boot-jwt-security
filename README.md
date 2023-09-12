# Projeto Spring Boot com Docker, MySQL, Lombok, Spring Security, JWT e Spring Data JPA
## Tabela de Conteúdos

- [Descrição](#descrição-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação-e-uso)
- [Instalando o curl no Ubuntu](#instalando-o-curl-no-ubuntu)
- [Endpoints para teste](#endpoints-para-teste)


## Descrição do Projeto

Este projeto foi desenvolvido com base em um tutorial criado por [Kalusen](https://www.youtube.com//watch?v=cbasQcUZ3cl) no YouTube e 
este é um projeto de exemplo que demonstra a criação de um aplicativo Spring Boot que utiliza várias tecnologias e recursos para criar um sistema de autenticação e registro seguro. O projeto inclui as seguintes tecnologias:


- **Spring Boot 3**: Um framework Java para criação de aplicativos web e RESTful.
- **Docker e MySQL**: Utilizados para criar um ambiente de contêiner Docker com um banco de dados MySQL para armazenar dados do aplicativo.
- **Lombok**: Uma biblioteca Java que ajuda a reduzir a verbosidade do código através da geração automática de métodos getter, setter, construtores, etc.
- **Spring Boot DevTools**: Ferramentas de desenvolvimento para agilizar o processo de desenvolvimento do Spring Boot.
- **Spring Security 6+**: Framework de segurança utilizado para proteger endpoints e controlar a autenticação e autorização.
- **Spring Web**: Módulo do Spring que facilita o desenvolvimento de aplicativos web.
- **Token JWT para Autenticação e Registro**: Implementação de autenticação baseada em tokens JSON Web Tokens (JWT) para fornecer autenticação segura e registro de usuários.
- **Spring Data JPA**: Parte do Spring Data que facilita a interação com bancos de dados relacionais usando a Java Persistence API (JPA).


## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas antes de executar o projeto:

- Docker
- Java (compatível com Spring Boot 3)
- IDE (opcional)
- Git (opcional)
- Curl (opcional)

# Instalação e Uso

1. Clone este repositório:

```bash
   git clone https://github.com/seu-usuario/seu-projeto.git
```
2. Abra em um IDE de preferência
3. Abra o terminal na pasta do projeto clonado:

```bash
   cd /caminho-do-seu-projeto-clonado
```
4. Rode o comando:
```bash
    docker-compose up
```
5.  Inicialize a aplicação spring pela IDE

## Instalando o `curl` no Ubuntu

Este guia fornece instruções passo a passo para instalar a ferramenta `curl` no sistema operacional Ubuntu, para a realização dos teste.

#### Passo 1: Abrir o Terminal

Abra um terminal no Ubuntu. Você pode fazer isso pressionando `Ctrl + Alt + T` ou pesquisando "Terminal" no menu de aplicativos.

#### Passo 2: Atualizar a Lista de Pacotes

Execute o seguinte comando no terminal para atualizar a lista de pacotes disponíveis:

```bash
sudo apt update
```
#### Passo 3: Instalar o curl
```bash
sudo apt install -y curl
```
#### Passo 4 verificar a versão
```bash
curl --version
```


## Endpoints para Teste

O projeto fornece os seguintes endpoints para autenticação e registro:

- **Autenticação (Login)**
    - URL: `http://localhost:8080/api/v1/auth/login`
    - Método: POST
    - Descrição: Este endpoint permite que os usuários façam login no sistema. Ele espera receber as credenciais de usuário, ou seja, o "email" e a "password" e retornará um token JWT válido em caso de sucesso.

- **Registro de Usuário (Register)**
    - URL: `http://localhost:8080/api/v1/auth/register`
    - Método: POST
    - Descrição: Este endpoint permite que novos usuários se registrem no sistema. É necessário fornecer informações obrigatórias, incluindo "username", "email", "password" e "mobileNumber". Após o registro bem-sucedido, um novo usuário será criado no banco de dados.

### Exemplo de Uso

Aqui está um exemplo de como usar esses endpoints com a ferramenta `curl`:

#### Autenticação (Login)

```bash
curl -X POST \
  http://localhost:8080/api/v1/auth/login \
  -H 'Content-Type: application/json' \
  -d '{
    "email": "seu-email@example.com",
    "password": "sua-senha"
  }'
```

#### Registro de Usuário (Register)
```bash
curl -X POST \
  http://localhost:8080/api/v1/auth/register \
  -H 'Content-Type: application/json' \
  -d '{
    "username": "Seu Nome de Usuário",
    "email": "seu-novo-email@example.com",
    "password": "sua-nova-senha",
    "mobileNumber": "seu-numero-de-telefone"
  }'

```
#### Endpoint Protegido do Administrador (Necessita de Autorização Adequada)
```bash
curl -X GET \
  http://localhost:8080/demo/admin \
  -H 'Authorization: Bearer SEU_TOKEN_JWT_VALIDO_DO_USUARIO'
```

#### Endpoint Protegido do Usuário (Necessita de Autorização Adequada)
```bash
curl -X GET \
  http://localhost:8080/demo/user \
  -H 'Authorization: Bearer SEU_TOKEN_JWT_VALIDO_DO_USUARIO'
```

