# Hospital API 🏥

### Descrição
Este projeto foi desenvolvido como parte de um teste técnico para a vaga de Desenvolvedor Júnior na Anlix. Foi pedido que criassemos um software que contenha uma base de dados consutável através de uma API REST para a consulta de caracterísitcas de pacientes.

---

## 🚀 Tecnologias Utilizadas

- **Backend**:
  - Java
  - Spring Boot
  - Hibernate / JPA
  - H2
  - Docker e Docker Compose

- **Frontend**:
  - Vite
  - React
  - TailwindCSS

---

## Requisitos API

- Consultar, para cada paciente, cada uma das características individualmente e cada uma delas sendo a mais recente disponível.
- Consultar em uma única chamada, todas as características de um paciente, com os valores mais recentes de cada uma.
- Consultar para uma determinada data (dia, mês e ano), todas as características existentes de todos os pacientes da base de dados.
- Consultar uma característica qualquer de um paciente para um intervalo de datas a ser especificado na chamada da API.
- Consultar o valor mais recente de uma característica de um paciente que esteja entre um intervalo de valores a ser especificado na chamada da API.
- Consultar pacientes que contenham um nome ou parte de um nome a ser especificado na chamada da API.

## Requisitos Cliente

- Buscar um paciente por nome e exibir o valor mais recente de cada uma de suas características.
- Ser possível exportar as características de um ou mais pacientes de todas as datas disponíveis para um arquivo CSV.
- Exibir um gráfico temporal para um determinado paciente e uma determinada característica a ser inserida através da interface.

## Uso

- Instale o docker antes de iniciar se não tiver instalado.

- Clone o repositório e entre nele

```
git clone https://github.com/carloseduardo22-rjce/desafio-anlix..git
cd desafio-anlix.
```

- Rode

```
docker-compose up --build
```

- Acesse

```
Frontend: http://localhost:3000
```

- Este aqui é o repositório do backend separado com histórico de commits
```
https://github.com/carloseduardo22-rjce/desafio-anlix
```
