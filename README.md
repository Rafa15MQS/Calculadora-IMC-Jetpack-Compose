# Calculadora de IMC — Jetpack Compose

Projeto acadêmico desenvolvido para a disciplina de Desenvolvimento de Dispositivos Móveis da Fatec, com o objetivo de criar uma calculadora de Índice de Massa Corporal (IMC), utilizando conceitos de interface declarativa do Jetpack Compose.

## Objetivo

Desenvolver uma calculadora que permita ao usuário informar seu peso e sua altura, calcular o IMC e visualizar o resultado com duas casas decimais.

## Funcionalidades

- Entrada de peso em quilogramas (kg).
- Entrada de altura em metros (m).
- Cálculo automático do IMC.
- Exibição do resultado com duas casas decimais.
- Classificação do resultado.
- Validação de campos vazios e valores inválidos.
- Mensagens de erro para impedir cálculos incorretos.
- Interface personalizada com cabeçalho roxo e fundo branco.

## Tecnologias e ferramentas

- Kotlin
- Jetpack Compose
- Material 3
- Google AI Studio (Gemini) — apoio à geração e revisão do código.

## Fórmula utilizada

O cálculo do IMC é realizado pela fórmula:

**IMC = Peso / (Altura × Altura)**

## Estrutura do projeto

```text
Calculadora-IMC-Jetpack-Compose/
│
├── MainActivity.kt
└── README.md
```

## Observação

O código-fonte Kotlin foi elaborado com apoio do Google AI Studio. Os testes visuais e as capturas de tela foram realizados no simulador visual criado na ferramenta. O projeto contém o código-fonte da atividade, não um aplicativo Android nativo compilado.

## Autora

Rafaela dos Santos Marques

Análise e Desenvolvimento de Sistemas — Fatec Ferraz de Vasconcelos.

---

Projeto desenvolvido para fins acadêmicos e de aprendizagem.

## 📸 Capturas de tela

### 1. Tela inicial — campos vazios
![Tela inicial](screenshots/calculadora-imc-vazia.png)

### 2. Calculadora com valores preenchidos
![Cálculo do IMC](screenshots/calculadora-imc-valores.png)

### 3. Validação de erro
![Mensagem de erro](screenshots/calculadora-imc-mgs-erro.png)
