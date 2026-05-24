# 📋 PLANO DE DESENVOLVIMENTO - PetCare AI+ (1º SPRINT)

## 🎯 OBJETIVO GERAL
Desenvolver uma API REST inteligente com Spring Boot para gerenciar saúde e cuidados contínuos de pets.

---

## 📊 ESTRUTURA DO PLANO (5 FASES COM COMMITS INCREMENTAIS)

### **FASE 1: SETUP & ARQUITETURA (Commits: 1-3)**
**Status:** ⏳ Não iniciado  
**Duração estimada:** 2 horas  
**Pontos:** Até 5 pontos (cronograma)

#### Commit 1.1: Configuração Maven e Dependências
- [ ] Adicionar dependências ao `pom.xml`:
  - Spring Boot Web, Data JPA, H2 Database
  - Bean Validation (Jakarta Validation)
  - Lombok
  - Springdoc OpenAPI (Swagger)
  - Spring Cache

#### Commit 1.2: Diagrama de Classes e DER
- [ ] Criar arquivo `docs/ARCHITECTURE.md` com:
  - Diagrama de Classes de Entidades
  - Diagrama de Relacionamento (DER)
  - Explicação dos relacionamentos

#### Commit 1.3: Estrutura de Pacotes
- [ ] Criar estrutura de packages:
  ```
  br.com.fiap.petcareai
  ├── domain         (Entidades JPA)
  ├── dto            (Data Transfer Objects)
  ├── repository     (Spring Data JPA)
  ├── service        (Lógica de negócio)
  ├── controller     (Endpoints REST)
  ├── exception      (Tratamento de erros)
  ├── util           (Utilitários)
  └── config         (Configurações)
  ```

---

### **FASE 2: MODELAGEM DE ENTIDADES (Commits: 4-8)**
**Status:** ⏳ Não iniciado  
**Duração estimada:** 3 horas  
**Pontos:** Até 40 pontos (implementação)

#### Commit 2.1: Entidade `Owner` (Tutor)
- [ ] Criar classe `Owner` com validações:
  - id (UUID), name, email, phone, cpf
  - Relacionamento 1:N com `Pet`

#### Commit 2.2: Entidade `Pet` (Animal)
- [ ] Criar classe `Pet` com validações:
  - id, name, species, breed, birthDate, weight
  - Relacionamento N:1 com `Owner`
  - Relacionamento 1:N com `Appointment`

#### Commit 2.3: Entidade `VaccinationSchedule`
- [ ] Criar tabela de vacinação por espécie/idade
- [ ] Relacionamento N:1 com `Pet`

#### Commit 2.4: Entidade `Appointment` (Consulta)
- [ ] Criar classe `Appointment`:
  - id, date, type, veterinarian, notes
  - Relacionamento N:1 com `Pet`

#### Commit 2.5: Entidade `HealthRecommendation` (IA)
- [ ] Criar classe para recomendações automáticas:
  - id, petId, type, description, priority, createdAt
  - Regras baseadas em idade, espécie, raça

#### Commit 2.6: Entidade `MedicationSchedule`
- [ ] Criar classe para medicamentos:
  - id, name, frequency, startDate, endDate
  - Relacionamento N:1 com `Pet`

#### Commit 2.7: Entidade `IoTSensor` (Simulação)
- [ ] Criar classe para dados de sensores:
  - id, petId, activityLevel, heartRate, temperature, timestamp

#### Commit 2.8: Testes unitários das entidades
- [ ] Testes de validação e relacionamentos

---

### **FASE 3: REPOSITORIES & QUERIES (Commits: 9-12)**
**Status:** ⏳ Não iniciado  
**Duração estimada:** 2 horas  
**Pontos:** Até 10 pontos (queries)

#### Commit 3.1: OwnerRepository
- [ ] Criar Spring Data JPA repository
- [ ] Queries: `findByEmail()`, `findByCpf()`

#### Commit 3.2: PetRepository
- [ ] Queries: `findByOwnerId()`, `findBySpecies()`, `findByBreed()`
- [ ] Pagination & Sorting support

#### Commit 3.3: AppointmentRepository
- [ ] Queries: `findByPetId()`, `findByDateBetween()`, `findUpcoming()`
- [ ] JPQL customizada

#### Commit 3.4: RecommendationRepository & VaccinationRepository
- [ ] Queries para buscas de recomendações e vacinações
- [ ] Filtros por tipo, prioridade, status

---

### **FASE 4: LÓGICA DE NEGÓCIO & IA (Commits: 13-18)**
**Status:** ⏳ Não iniciado  
**Duração estimada:** 4 horas  
**Pontos:** Até 40 pontos (lógica + IA)

#### Commit 4.1: RecommendationService - Regras de IA
- [ ] Implementar engine de recomendações:
  - **Regra 1:** Pet com idade > 7 anos = Consulta anual
  - **Regra 2:** Filhote < 1 ano = 4 consultas/ano
  - **Regra 3:** Gato adulto = 2 consultas/ano
  - **Regra 4:** Verificar vacinas atrasadas
  - **Regra 5:** Monitorar peso (alerta se +/- 20%)

#### Commit 4.2: OwnerService
- [ ] CRUD completo
- [ ] Validações com Bean Validation
- [ ] Tratamento de exceções

#### Commit 4.3: PetService
- [ ] CRUD com paginação e ordenação
- [ ] Método para calcular idade
- [ ] Trigger automático de recomendações ao criar pet

#### Commit 4.4: AppointmentService
- [ ] Agendamento de consultas
- [ ] Busca com filtros (data, tipo)
- [ ] Validação de conflitos

#### Commit 4.5: HealthRecommendationService
- [ ] Geração automática de recomendações
- [ ] Atualização de status (new, read, completed)
- [ ] Alertas críticos

#### Commit 4.6: MedicationScheduleService
- [ ] Gestão de medicamentos
- [ ] Alertas de final de medicação
- [ ] Histórico

---

### **FASE 5: API REST & ENDPOINTS (Commits: 19-28)**
**Status:** ⏳ Não iniciado  
**Duração estimada:** 4 horas  
**Pontos:** Até 15 pontos (RESTful)

#### Commit 5.1: OwnerController
```
POST   /api/v1/owners                      - Criar tutor
GET    /api/v1/owners/{id}                 - Buscar por ID
GET    /api/v1/owners                      - Listar com paginação
PUT    /api/v1/owners/{id}                 - Atualizar
DELETE /api/v1/owners/{id}                 - Deletar
```

#### Commit 5.2: PetController
```
POST   /api/v1/pets                        - Criar pet
GET    /api/v1/pets/{id}                   - Buscar por ID
GET    /api/v1/pets                        - Listar (filtro, paginação, sort)
PUT    /api/v1/pets/{id}                   - Atualizar
DELETE /api/v1/pets/{id}                   - Deletar
GET    /api/v1/pets/{id}/recommendations   - Recomendações do pet
```

#### Commit 5.3: AppointmentController
```
POST   /api/v1/appointments                - Agendar consulta
GET    /api/v1/appointments/{id}           - Buscar consulta
GET    /api/v1/appointments                - Listar (filtro por pet/data)
PUT    /api/v1/appointments/{id}           - Atualizar
DELETE /api/v1/appointments/{id}           - Cancelar
```

#### Commit 5.4: HealthRecommendationController
```
GET    /api/v1/recommendations             - Listar recomendações
GET    /api/v1/recommendations/{id}        - Detalhes
PUT    /api/v1/recommendations/{id}/read   - Marcar como lido
```

#### Commit 5.5: MedicationScheduleController
```
POST   /api/v1/medications                 - Criar medicamento
GET    /api/v1/medications/{petId}         - Medicamentos do pet
PUT    /api/v1/medications/{id}            - Atualizar
DELETE /api/v1/medications/{id}            - Remover
```

#### Commit 5.6: DTOs - Request/Response
- [ ] OwnerDTO, PetDTO, AppointmentDTO, etc.
- [ ] Validação com `@Valid`

#### Commit 5.7: Global Exception Handler
- [ ] `@ControllerAdvice`
- [ ] Tratamento de:
  - ResourceNotFoundException
  - ValidationException
  - BadRequestException

#### Commit 5.8: Swagger/OpenAPI Documentation
- [ ] Configurar Springdoc OpenAPI
- [ ] Documentar todos os endpoints
- [ ] Exemplo de responses

---

### **FASE 6: CACHE & OTIMIZAÇÕES (Commits: 29-30)**
**Status:** ⏳ Não iniciado  
**Duração estimada:** 1 hora  
**Pontos:** Até 5 pontos (otimização)

#### Commit 6.1: Spring Cache Configuration
- [ ] Configurar `@Cacheable`, `@CacheEvict`
- [ ] Cache para:
  - Recomendações de vacinação
  - Tabelas de referência

#### Commit 6.2: Performance Tuning
- [ ] Lazy loading JPA
- [ ] Índices no banco H2

---

### **FASE 7: TESTES & DOCUMENTAÇÃO (Commits: 31-35)**
**Status:** ⏳ Não iniciado  
**Duração estimada:** 3 horas  
**Pontos:** Até 10 pontos (testes)

#### Commit 7.1: Testes Unitários Services
- [ ] Testes para RecommendationService
- [ ] Testes para cálculos de idade/recomendações

#### Commit 7.2: Testes de Integração Controllers
- [ ] Testes dos endpoints com `@SpringBootTest`
- [ ] MockMvc para validação

#### Commit 7.3: Arquivo de Postman/Insomnia
- [ ] Exportar collection de requisições
- [ ] Incluir exemplos de:
  - Criar owner + pet
  - Gerar recomendações
  - Listar com filtros e paginação

#### Commit 7.4: README.md
- [ ] Instruções de setup
- [ ] Como rodar a aplicação
- [ ] Exemplos de uso

#### Commit 7.5: Documentação Final
- [ ] Arquitetura geral
- [ ] Fluxos principais
- [ ] Regras de IA explicadas

---

## 📅 CRONOGRAMA

| Fase | Descrição | Commits | Duração | Data Planejada |
|------|-----------|---------|---------|----------------|
| 1 | Setup & Arquitetura | 1.1-1.3 | 2h | 24/05/2026 |
| 2 | Modelagem de Entidades | 2.1-2.8 | 3h | 24/05 - 25/05 |
| 3 | Repositories & Queries | 3.1-3.4 | 2h | 25/05 |
| 4 | Lógica & IA | 4.1-4.6 | 4h | 25/05 - 26/05 |
| 5 | API REST | 5.1-5.8 | 4h | 26/05 - 27/05 |
| 6 | Cache & Otimizações | 6.1-6.2 | 1h | 27/05 |
| 7 | Testes & Docs | 7.1-7.5 | 3h | 27/05 - 28/05 |

**Total estimado:** 19 horas  
**Total de commits:** ~35 commits

---

## 📋 CHECKLIST DE ENTREGAS

- [ ] Repositório público no GitHub (link compartilhado)
- [ ] Commits incrementais com mensagens claras
- [ ] Diagramas (DER + Classes)
- [ ] API documentada no Swagger
- [ ] Collection Postman/Insomnia com testes
- [ ] Testes passando
- [ ] README com instruções
- [ ] Todos os requisitos técnicos implementados

---

## 🚀 PRÓXIMOS PASSOS

1. ✅ Configurar repositório Git
2. ➡️ **Iniciar Commit 1.1** (pom.xml e dependências)
3. Prosseguir sequencialmente

