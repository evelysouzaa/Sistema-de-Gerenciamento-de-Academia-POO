
# Sistema-de-Gerenciamento-de-Academia-POO-BD
 
## 👥 Integrantes do Grupo
 
Isabelly Vitoria  - 45235791  
Evely Vitoria dos Santos Souza - 42648777  
Milena Batista Rodrigues - 45407185   
Matheus Kaizer Pereira - 45952722
 
## 📋 Tema Escolhido
Academia
 
## 🎯 Objetivo do Sistema
O Sistema de Gerenciamento de Academia é uma aplicação console desenvolvida em Java para facilitar a administração completa de uma academia fitness. O sistema permite gerenciar alunos, instrutores, funcionários, planos de treino, avaliações físicas e pagamentos de mensalidades em um único lugar, substituindo controles manuais e planilhas desorganizadas.
 
Por meio de um menu interativo, funcionários e administradores conseguem cadastrar e consultar pessoas de diferentes tipos (aluno, instrutor, funcionário), montar fichas de treino personalizadas, registrar avaliações físicas com medidas corporais e controlar os pagamentos e planos de cada aluno. O sistema aplica validações em todos os dados inseridos, garantindo a integridade das informações.
 
O projeto foi desenvolvido com foco nos conceitos de Programação Orientada a Objetos, utilizando encapsulamento, construtores, getters/setters com validação e o método `toString()` em todas as classes. No CP2, o sistema foi evoluído com **herança**, **polimorfismo**, **sobrescrita (@Override)**, uso de **super** e **sobrecarga de métodos**, servindo como base para futuras evoluções com banco de dados e interface gráfica.
 
## 📦 Funcionalidades Principais
1. **Cadastro Polimórfico** — Alunos, Instrutores e Funcionários via hierarquia `Pessoa`
2. **Cadastro de Instrutores** — Registro de instrutores com especialidade e CREF
3. **Ficha de Treino** — Criação de fichas de treino vinculando aluno, instrutor e exercícios
4. **Avaliação Física** — Registro de medidas corporais e cálculo automático de IMC
5. **Controle de Pagamento** — Registro de mensalidades com status (pago/pendente) e vencimento
## 🏗️ Estrutura de Classes
 
### CP1 — Classes Independentes
- **Classe 1:** `Aluno` — Representa o aluno da academia com seus dados pessoais e plano
- **Classe 2:** `Instrutor` — Representa o instrutor responsável pelos treinos
- **Classe 3:** `FichaTreino` — Ficha de treino do aluno com lista de exercícios e instrutor responsável
- **Classe 4:** `AvaliacaoFisica` — Registra medidas corporais e calcula o IMC do aluno
- **Classe 5:** `Pagamento` — Controla mensalidades, datas de vencimento e status do pagamento
### CP2 — Hierarquia com Herança
```
Pessoa (superclasse)
├── Aluno       — dados físicos, plano, cálculo de IMC e desconto progressivo
├── Instrutor   — CREF, especialidade, salário, cálculo de bônus
└── Funcionario — cargo, setor, horas semanais, cálculo de hora extra
```
 
## 🔁 Conceitos OOP Aplicados (CP2)
 
| Conceito | Onde é usado |
|---|---|
| **Herança** (`extends`) | `Aluno`, `Instrutor`, `Funcionario` estendem `Pessoa` |
| **`super()` no construtor** | Todas as subclasses chamam `super(id, nome, cpf, idade, tel)` |
| **`@Override`** | `getTipo()`, `exibirInfo()`, `toString()` em cada subclasse |
| **`super.metodo()`** | `exibirInfo(detalhado)` chama `super.exibirInfo(detalhado)` antes de adicionar detalhes específicos |
| **Polimorfismo** | `ArrayList<Pessoa>` armazena Aluno, Instrutor e Funcionario juntos |
| **Sobrecarga** | `calcularDesconto()`, `calcularBonus()`, `calcularHoraExtra()` e `exibirInfo()` com diferentes parâmetros |
 
## 🔄 Regra de Negócio Complexa
**Cálculo de IMC com classificação e desconto progressivo na mensalidade:**
 
Ao registrar uma avaliação física, o sistema calcula automaticamente o IMC do aluno (`peso / altura²`) e o classifica (Abaixo do peso, Normal, Sobrepeso, Obesidade). Com base nessa classificação e no tempo de permanência do aluno na academia, o sistema aplica um desconto progressivo na mensalidade:
 
- Aluno com IMC normal + mais de 12 meses = **15% de desconto**

# 🏋️ Sistema de Gerenciamento de Academia — Checkpoint 3

## O que foi implementado

###  1. Classe Abstrata (`Pessoa`)
- `Pessoa` tornou-se **abstrata**, impedindo instanciação direta
- Atributos comuns mantidos como `protected`: `id`, `nome`, `cpf`, `idade`, `telefone`
- Método concreto: `obterContato()` — comportamento comum a todos
- **Método abstrato 1:** `getTipo()` — cada subclasse retorna seu tipo
- **Método abstrato 2:** `exibirDetalhesEspecificos()` — cada subclasse imprime seus dados próprios
- Subclasses: `Aluno`, `Instrutor`, `Funcionario` (implementam os dois métodos abstratos)

###  2. Padrão DAO com JDBC
| Arquivo | Responsabilidade |
|---|---|
| `dao/ConexaoBD.java` | Gerencia conexão JDBC com PostgreSQL |
| `dao/AlunoDAO.java` | CRUD completo da tabela `aluno` |
| `dao/InstrutorDAO.java` | CRUD completo da tabela `instrutor` |
| `dao/FuncionarioDAO.java` | CRUD completo da tabela `funcionario` |
| `dao/PagamentoDAO.java` | CRUD completo da tabela `pagamento` |

Cada DAO usa:
- `PreparedStatement` (previne SQL Injection)
- `try-with-resources` (garante fechamento de conexões)
- `RETURN_GENERATED_KEYS` para obter ID gerado no INSERT


##  Conceitos aplicados

| Conceito | Onde |
|---|---|
| Classe abstrata | `model/Pessoa.java` |
| Métodos abstratos | `getTipo()` e `exibirDetalhesEspecificos()` |
| Herança | `Aluno`, `Instrutor`, `Funcionario` extends `Pessoa` |
| Polimorfismo | `listarAlunos()` chama `getTipo()` e `exibirInfo()` da subclasse correta |
| Padrão DAO | Todo pacote `dao/` |
| JDBC + PreparedStatement | Todos os DAOs |
| try-with-resources | Todos os blocos de conexão |
| Organização em pacotes | `model`, `dao`, `util` |
