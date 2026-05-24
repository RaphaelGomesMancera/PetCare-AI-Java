# 🐾 PetCare AI+ - Backend Java

API REST desenvolvida em Java com Spring Boot para o projeto **PetCare AI+**, criado para o **Challenge FIAP 2026** em parceria com a **CLYVO VET**.

## 📌 Sobre o Projeto

O **PetCare AI+** e uma plataforma inteligente para acompanhamento continuo da saude de pets.

Este backend centraliza o gerenciamento do ciclo de cuidados do animal, incluindo:

- cadastro de tutores e pets
- historico de vacinas, consultas e medicamentos
- monitoramento IoT simulado (atividade e sinais vitais)
- recomendacoes preventivas com IA simples baseada em regras
- persistencia de dados e validacoes automatizadas

O objetivo e auxiliar tutores e clinicas veterinarias no cuidado preventivo dos animais.

## 🚀 Funcionalidades

### ✅ CRUD e Gestao de Dados

- cadastro, consulta, atualizacao e remocao de tutores
- cadastro, consulta, atualizacao e remocao de pets
- controle de vacinas por pet
- controle de consultas por pet
- controle de medicamentos por pet
- registro de atividades IoT por pet

### ✅ Regras Inteligentes Simuladas

O sistema possui regras de IA simples para gerar recomendacoes, como:

- frequencia de consultas por faixa etaria
- alertas de vacinas vencidas ou proximas
- alertas de medicamentos ativos proximos do fim
- orientacoes preventivas conforme idade e historico

### ✅ Validacoes

Utilizacao de Bean Validation para garantir integridade dos dados:

- campos obrigatorios
- e-mail valido
- data de nascimento valida
- faixa de peso valida
- validacoes automaticas da API

### ✅ Tratamento de Excecoes

- respostas HTTP padronizadas
- mensagens de erro claras
- tratamento global de excecoes com `@RestControllerAdvice`

### ✅ Paginacao, Ordenacao e Busca

Listagem de recursos com suporte a:

- `Pageable`
- `Page`
- ordenacao por campos
- filtros por parametros de consulta

### ✅ Documentacao Swagger

Documentacao automatica com OpenAPI/Swagger para exploracao e testes dos endpoints.

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 4
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Cache
- Lombok
- H2 Database
- Swagger/OpenAPI
- Maven

## 📂 Estrutura do Projeto

```bash
src
 ┣ main
 ┃ ┣ java
 ┃ ┃ ┗ br.com.fiap.petcareai
 ┃ ┃    ┣ controller
 ┃ ┃    ┣ dto
 ┃ ┃    ┣ domain
 ┃ ┃    ┣ repository
 ┃ ┃    ┣ service
 ┃ ┃    ┣ exception
 ┃ ┃    ┗ config
 ┃ ┗ resources
```

## ▶️ Como Executar o Projeto

### 1) Clonar repositorio

```bash
git clone https://github.com/RaphaelGomesMancera/PetCare-AI-Java.git
cd PetCare-AI-Java/petcareai
```

### 2) Executar aplicacao

No Windows:

```bash
mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

## 📖 Swagger

Apos iniciar a aplicacao, acesse:

```bash
http://localhost:8080/swagger-ui
```

Alternativa:

```bash
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```bash
http://localhost:8080/api-docs
```

## 🗄️ Banco de Dados H2

Console do banco:

```bash
http://localhost:8080/h2-console
```

Configuracao padrao:

- JDBC URL: `jdbc:h2:mem:petcaredb`
- User: `sa`
- Password: *(vazio)*

## 🎯 Objetivo Academico

Projeto desenvolvido para o Challenge FIAP 2026 com foco em:

- arquitetura REST
- boas praticas em Java e Spring Boot
- modelagem de dominio com JPA
- validacoes e tratamento de erros
- documentacao de APIs modernas
- preparacao para integracao com app mobile

## 👨‍💻 Integrantes

| Nome | RM |
|------|------|
| Bruno Vinicius Barbosa | RM566366 |
| Guilherme De Andrade Martini | RM566087 |
| Raphael Gomes Mancera | RM562279 |

## 🏆 Challenge FIAP 2026

Projeto academico desenvolvido para o Challenge FIAP 2026 em parceria com a **CLYVO VET**.
