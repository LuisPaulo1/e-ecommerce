# E-ecommerce

## Sobre o projeto

É uma API desenvolvida com Java e Spring Boot para gerenciar produtos de um E-ecommerce.

## Collection do Postman
Importar link no Postman: https://api.postman.com/collections/6556841-b3598b6a-061b-4370-acbd-6f850b20d9ea?access_key=PMAT-01HGRT5YWF56K755YTBJSJNQEP

## Documentação swagger da API
http://localhost:8080/swagger-ui/index.html

## Arquitetura da aplicação
![Arquitetura](https://github.com/LuisPaulo1/assets/blob/master/eecommerce/arquitetura.png).

## Tecnologias utilizadas
- Java 11
- Spring (boot, web, data, validation)
- Lombok
- ModelMapper
- Maven
- Mysql
- H2
- Swagger OpenAPI 3.0
- Docker

## Ferramentas utilizadas
- IntelliJ
- Postman
- MySQL Workbench
- Docker Desktop
- Git Bash

## Para executar o projeto
### Utilizando docker

### Pré-requisitos
- Docker em execução

```bash
# Clonar repositório
git clone https://github.com/LuisPaulo1/e-ecommerce.git

# Entrar na pasta do projeto e-ecommerce
cd e-ecommerce

# Executar o projeto
docker-compose up -d (Aguardar 25s para iniciar a API e o banco)

# Encerrar o projeto
 docker-compose down
```
### Utilizando IDE ou linha de comando
### Pré-requisitos
- Java 11
- Mysql

```bash
# Clonar o projeto
git clone https://github.com/LuisPaulo1/e-ecommerce.git

# Entrar na pasta do projeto e-ecommerce
cd e-ecommerce

# Executar o projeto
./mvnw spring-boot:run ou abrir na IDE
```

# Autor

Luis Paulo

https://www.linkedin.com/in/luis-paulo-souza-a54358134/
