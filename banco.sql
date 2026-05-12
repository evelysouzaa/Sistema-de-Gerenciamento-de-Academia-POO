

-- ── Tabela: aluno 
CREATE TABLE IF NOT EXISTS aluno (
    id               SERIAL PRIMARY KEY,
    nome             VARCHAR(100) NOT NULL,
    cpf              CHAR(11)     NOT NULL UNIQUE,
    idade            INTEGER      NOT NULL CHECK (idade BETWEEN 14 AND 100),
    telefone         VARCHAR(20)  NOT NULL,
    peso             NUMERIC(5,2) NOT NULL CHECK (peso > 0),
    altura           NUMERIC(4,2) NOT NULL CHECK (altura > 0 AND altura <= 3),
    plano            VARCHAR(20)  NOT NULL,
    meses_cadastrado INTEGER      NOT NULL DEFAULT 0 CHECK (meses_cadastrado >= 0)
);

-- ── Tabela: instrutor
CREATE TABLE IF NOT EXISTS instrutor (
    id                SERIAL PRIMARY KEY,
    nome              VARCHAR(100) NOT NULL,
    cpf               CHAR(11)     NOT NULL UNIQUE,
    idade             INTEGER      NOT NULL CHECK (idade BETWEEN 14 AND 100),
    telefone          VARCHAR(20)  NOT NULL,
    cref              VARCHAR(30)  NOT NULL UNIQUE,
    especialidade     VARCHAR(80)  NOT NULL,
    salario           NUMERIC(10,2) NOT NULL CHECK (salario >= 0),
    alunos_orientados INTEGER       NOT NULL DEFAULT 0 CHECK (alunos_orientados >= 0)
);

-- ── Tabela: funcionario
CREATE TABLE IF NOT EXISTS funcionario (
    id             SERIAL PRIMARY KEY,
    nome           VARCHAR(100)  NOT NULL,
    cpf            CHAR(11)      NOT NULL UNIQUE,
    idade          INTEGER       NOT NULL CHECK (idade BETWEEN 14 AND 100),
    telefone       VARCHAR(20)   NOT NULL,
    cargo          VARCHAR(60)   NOT NULL,
    setor          VARCHAR(60)   NOT NULL,
    salario        NUMERIC(10,2) NOT NULL CHECK (salario >= 0),
    horas_semanais INTEGER       NOT NULL CHECK (horas_semanais BETWEEN 1 AND 44)
);

-- ── Tabela: pagamento
CREATE TABLE IF NOT EXISTS pagamento (
    id                SERIAL PRIMARY KEY,
    aluno_id          INTEGER        NOT NULL REFERENCES aluno(id) ON DELETE CASCADE,
    valor_mensalidade NUMERIC(10,2)  NOT NULL CHECK (valor_mensalidade > 0),
    desconto          NUMERIC(5,2)   NOT NULL DEFAULT 0 CHECK (desconto BETWEEN 0 AND 100),
    data_vencimento   VARCHAR(10)    NOT NULL,
    data_pagamento    VARCHAR(10),
    status            VARCHAR(20)    NOT NULL DEFAULT 'Pendente'
);

-- ── Tabela: avaliacao_fisica
CREATE TABLE IF NOT EXISTS avaliacao_fisica (
    id                 SERIAL PRIMARY KEY,
    aluno_id           INTEGER       NOT NULL REFERENCES aluno(id) ON DELETE CASCADE,
    peso_atual         NUMERIC(5,2)  NOT NULL CHECK (peso_atual > 0),
    altura_atual       NUMERIC(4,2)  NOT NULL CHECK (altura_atual > 0 AND altura_atual <= 3),
    percentual_gordura NUMERIC(5,2)  NOT NULL CHECK (percentual_gordura BETWEEN 0 AND 100),
    data_avaliacao     VARCHAR(10)   NOT NULL
);

-- ── Tabela: ficha_treino 
CREATE TABLE IF NOT EXISTS ficha_treino (
    id           SERIAL PRIMARY KEY,
    aluno_id     INTEGER      NOT NULL REFERENCES aluno(id)    ON DELETE CASCADE,
    instrutor_id INTEGER      NOT NULL REFERENCES instrutor(id) ON DELETE RESTRICT,
    objetivo     VARCHAR(120) NOT NULL,
    exercicios   TEXT,         -- lista separada por ";" ou JSON
    data_inicio  VARCHAR(10)  NOT NULL
);

-- ── Dados de exemplo (opcional)
INSERT INTO aluno (nome, cpf, idade, telefone, peso, altura, plano, meses_cadastrado)
VALUES ('Ana Paula Silva', '12345678901', 25, '(13) 99001-1111', 62.0, 1.65, 'Mensal', 3);

INSERT INTO instrutor (nome, cpf, idade, telefone, cref, especialidade, salario, alunos_orientados)
VALUES ('Carlos Eduardo', '98765432100', 32, '(13) 99002-2222', 'CREF-12345-SP', 'Musculação', 3500.00, 0);

INSERT INTO funcionario (nome, cpf, idade, telefone, cargo, setor, salario, horas_semanais)
VALUES ('Fernanda Lima', '11122233344', 28, '(13) 99003-3333', 'Recepcionista', 'Atendimento', 2000.00, 40);
