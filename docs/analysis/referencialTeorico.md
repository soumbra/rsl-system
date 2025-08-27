# Processo de Revisão Sistemática da Literatura - Workflow Base

## Referência Principal

**Kitchenham, B. (2007). Guidelines for performing Systematic Literature Reviews in Software Engineering. EBSE Technical Report EBSE-2007-01, Keele University and University of Durham.**

## Definição

Uma **Revisão Sistemática da Literatura (RSL)** é um meio de identificar, avaliar e interpretar toda a evidência disponível relevante a uma questão de pesquisa particular, área de tópico ou fenômeno de interesse. Estudos individuais que contribuem para uma revisão sistemática são chamados de estudos primários; uma revisão sistemática é uma forma de estudo secundário.

## Objetivos Principais

- Sumarizar a evidência existente sobre um tratamento ou tecnologia
- Identificar lacunas na pesquisa atual para sugerir áreas para investigação futura
- Fornecer um framework/background para posicionar adequadamente novas atividades de pesquisa
- Examinar até que ponto a evidência empírica suporta/contradiz hipóteses teóricas

## Processo de 3 Fases

### 1️⃣ **FASE 1: PLANEJAMENTO**

#### 1.1 Identificação da Necessidade da Revisão

- Confirmar que uma RSL é necessária
- Verificar se já existem RSLs sobre o tópico
- Avaliar RSLs existentes usando critérios DARE

#### 1.2 Especificação da(s) Questão(ões) de Pesquisa

**Estrutura PICOC recomendada:**

- **P**opulation (População): desenvolvedores, empresas, projetos
- **I**ntervention (Intervenção): metodologia/ferramenta/tecnologia
- **C**omparison (Comparação): metodologia alternativa ou controle
- **O**utcome (Resultado): fatores de importância para practitioners
- **C**ontext (Contexto): ambiente acadêmico/industrial, participantes, tarefas

#### 1.3 Desenvolvimento do Protocolo de Revisão

**Componentes obrigatórios:**

- Background e justificativa
- Questões de pesquisa
- Estratégia de busca (termos, bases, recursos)
- Critérios de seleção de estudos
- Procedimentos de seleção de estudos
- Checklists e procedimentos de avaliação de qualidade
- Estratégia de extração de dados
- Síntese dos dados extraídos
- Cronograma do projeto

#### 1.4 Avaliação do Protocolo

- Revisão por especialistas independentes
- Verificação da consistência interna
- Pilotagem dos procedimentos

---

### 2️⃣ **FASE 2: CONDUÇÃO**

#### 2.1 Identificação da Pesquisa

**Estratégia de busca:**

- Quebrar a questão em facetas individuais (PICOC)
- Criar lista de sinônimos, abreviações, grafias alternativas
- Construir strings de busca usando operadores booleanos (AND/OR)
- Buscar em múltiplas fontes:
  - Bibliotecas digitais (IEEE, ACM, ScienceDirect, etc.)
  - Journals específicos
  - Anais de conferências
  - Literatura cinzenta
  - Registros de pesquisa
  - Internet e contato direto com pesquisadores

**Documentação obrigatória:**

- Nome da base de dados
- Estratégia de busca para cada base
- Data da busca
- Anos cobertos pela busca

#### 2.2 Seleção de Estudos

**Processo multi-estágio:**

1. **Seleção inicial** (título/abstract)

   - Aplicar critérios de inclusão/exclusão liberalmente
   - Quando em dúvida, incluir para próxima fase

2. **Seleção detalhada** (texto completo)

   - Aplicar critérios práticos (idioma, disponibilidade)
   - Aplicar critérios específicos da pesquisa

3. **Confiabilidade das decisões**
   - Múltiplos avaliadores independentes
   - Medição do acordo (Cohen's Kappa)
   - Discussão e resolução de discordâncias

#### 2.3 Avaliação de Qualidade

**Objetivos:**

- Critérios detalhados de inclusão/exclusão
- Investigar se diferenças de qualidade explicam diferenças nos resultados
- Ponderar a importância dos estudos individuais
- Guiar interpretação dos achados
- Orientar recomendações para pesquisas futuras

**Tipos de viés a considerar:**

- **Viés de seleção**: diferenças sistemáticas entre grupos de comparação
- **Viés de performance**: diferenças sistemáticas na condução além do tratamento
- **Viés de medição**: diferenças sistemáticas na avaliação de resultados
- **Viés de atrito**: diferenças sistemáticas em retiradas/exclusões

#### 2.4 Extração de Dados

**Componentes do formulário:**

- Nome do revisor e data da extração
- Detalhes da publicação (título, autores, journal)
- Informações para responder questões de pesquisa
- Critérios de qualidade
- Espaço para notas adicionais

**Procedimentos:**

- Extração independente por múltiplos pesquisadores
- Comparação e resolução de discordâncias
- Contato com autores para dados faltantes/ambíguos

#### 2.5 Síntese de Dados

**Tipos de síntese:**

1. **Síntese Descritiva (Narrativa)**

   - Tabular informações extraídas
   - Destacar similaridades e diferenças
   - Identificar homogeneidade vs. heterogeneidade

2. **Síntese Quantitativa (Meta-análise)**

   - Quando estudos são suficientemente homogêneos
   - Usar medidas de efeito apropriadas
   - Forest plots para apresentação visual

3. **Síntese Qualitativa**
   - Translação recíproca
   - Síntese refutacional
   - Síntese de linha de argumento

**Análises complementares:**

- Análise de sensibilidade
- Investigação de viés de publicação (funnel plots)
- Análise de subgrupos

---

### 3️⃣ **FASE 3: RELATÓRIO**

#### 3.1 Estratégia de Disseminação

**Audiências-alvo:**

- Comunidade acadêmica (journals, conferências)
- Practitioners (revistas especializadas, press releases)
- Tomadores de decisão (relatórios executivos, comunicação direta)

#### 3.2 Estrutura do Relatório Principal

1. **Resumo estruturado**
2. **Introdução e background**
3. **Questões de revisão**
4. **Métodos** (baseado no protocolo)
5. **Resultados** (estudos incluídos/excluídos, achados, meta-análises)
6. **Discussão** (achados principais, forças/fraquezas, significado)
7. **Conclusões** (recomendações, questões não respondidas)
8. **Referências e apêndices**

#### 3.3 Avaliação do Relatório

- Revisão por pares (journals/conferências)
- Avaliação por painel de especialistas
- Uso de checklists de qualidade para RSLs

---

## Mapeamentos Sistemáticos da Literatura

### Diferenças principais da RSL tradicional:

- **Questões mais amplas** e múltiplas questões de pesquisa
- **Termos de busca menos focados** para cobertura ampla
- **Processo de extração mais superficial** (classificação/categorização)
- **Análise descritiva** com totais, sumários e distribuições gráficas
- **Objetivo**: mapear evidências existentes, identificar clusters e desertos de evidência

### Quando usar Mapeamento vs RSL:

- **Mapeamento**: quando pouca evidência existe ou tópico muito amplo
- **RSL**: quando evidência suficiente existe para responder questões específicas

---

## Ferramentas de Apoio Recomendadas

- Gerenciadores bibliográficos (Reference Manager, EndNote)
- Sistemas de logging para documentar buscas
- Formulários eletrônicos para extração de dados
- Software estatístico para meta-análises (quando aplicável)

---

## Critérios de Qualidade para RSL

### Critérios DARE (mínimos):

1. Critérios de inclusão/exclusão descritos e apropriados?
2. Busca cobriu todos os estudos relevantes?
3. Qualidade/validade dos estudos incluídos foi avaliada?
4. Dados básicos/estudos foram adequadamente descritos?

---

_Este documento serve como guia para o desenvolvimento do RSL System, garantindo que todas as fases e atividades essenciais de uma Revisão Sistemática da Literatura sejam adequadamente suportadas pela ferramenta._
