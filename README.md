# 💈 Barber Shop Management

Barber Shop Management é uma aplicação focada em otimizar o gerenciamento de agendas para barbearias, permitindo que clientes agendem serviços de forma prática e que os funcionários mantenham uma organização eficiente. A plataforma facilita o controle de horários e melhora a experiência tanto para os clientes quanto para os profissionais.


## 🎯 Funcionalidades
- **Cadastro e gerenciamento de barbearias, clientes e agendamentos** 🆕
- **Controle de status (Confirmado, Cancelado, Concluído)** 🔄❌✅
- **Associação de funcionários a uma barbearia** 🤝
- **Validação de horários para evitar conflitos** ⏰
- **Listagem e pesquisa de barbearias** 🔍

## 🧑‍💻 Tecnlogias Utilizadas

- **[Java](https://www.java.com/pt-BR/)**
- **[Spring Boot](https://spring.io/projects/spring-boot)**
- **[Spring Security](https://spring.io/projects/spring-security)**
- **[JSON Web Token (JWT)](https://jwt.io/)**
- **[JUnit](https://junit.org/junit5/)**
- **[Mockito](https://site.mockito.org/)**
- **[Docker](https://www.docker.com/)**
- **[Docker Compose](https://docs.docker.com/compose/)**
- **[Swagger](https://swagger.io/)**

## 📃 Pré Requisitos

- Ter o **[Docker](https://www.docker.com/)** instalado na máquina que rodará a aplicação e estar com ele Running.

## 🛠️ Instalação e Configuração
Há duas formas de realizar a instalação e rodar a aplicação: clonando o projeto no github ou rodando um docker compose chamando a aplicação via DockerHub.

## 1° Modo
Clonando o repositório e rodando a inicialização do sistema.

### ⚙️ Passo 1: Clonar o repositório
O primeiro passo é clonar o repositorio:

```bash
git clone https://github.com/oLucasNascimento/barbershop.git
```

### 💻 Passo 2: Rodar a inicialização do projeto
O segundo passo é rodar o arquivo de inicialização do projeto:

```bash
cd barbershop
./initializer-docker.sh
```
Feito isso, o projeto já estará rodando conteinerizado e poderá ser visto via Docker.

## 2° Modo
Rodando o projeto via DockerHub, com Docker Compose.

### 🔗 Passo 1
Realizar o download da configuração do Docker Compose [aqui](https://drive.google.com/file/d/1F-lTkYqpxO6IvqcnaZC-1KX53d82XAkM/view?usp=drive_link).

### 💻 Passo 2
Após o download, executar o arquivo para que suba a aplicação:
```bash
docker compose up -d
```
Pronto, o projeto já estará rodando via DockerHub e também poderá ser visto via Docker.

## Documentação Swagger
Para acessar as requisições e endpoints para testes, o Swagger estará disponível em:
```
localhost:8080/swagger-ui/index.html
```

Para a enviar requisições dos endpoints, deve-se ter um token de autenticação no campo **Authorize**. O token é gerado e fornecido via o endpoint de **Login**, que pode ser feito após a criação de **Barbearia** ou **Cliente**, utilizando o **email** e a **senha** para fazer login e gerar o token para autenticação.

## 🧪 Testes

O projeto inclui testes automatizados para validar as funcionalidades implementadas até o momento.

Para rodar os testes, utilize o comando:

```bash
cd barbershopmanagement/barbershopmanagement
./mvnw test    
```

## 🫡 Colaboradores
<a href="https://www.linkedin.com/in/lucas-nferreira"><img src="https://media.licdn.com/dms/image/v2/D4D03AQG1eTD3sWXV8Q/profile-displayphoto-shrink_800_800/B4DZWwQclNHAAg-/0/1742418856771?e=1747872000&v=beta&t=qKhkSastbbbrRXd0YXAZMJaj5kCQXkCGYCwfOHHdknQ" width="85px" style="border-radius:50px">

*Lucas Ferreira*
</a>