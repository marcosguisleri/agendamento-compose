# 📅 Agendamento

Tela de agendamento construída **100% em Jetpack Compose**, com gerenciamento de estado via `ViewModel` e persistência de dados durante mudanças de configuração (rotação de tela).

> Projeto acadêmico da disciplina **Desenvolvimento para Dispositivos Móveis**, da Especialização em Desenvolvimento de Sistemas Web e Aplicativos Móveis — **IFSP Capivari**.

---

## ✨ Sobre o projeto

O app simula o fluxo de criação de um agendamento: o usuário informa nome e preferência de lembrete em um `AlertDialog`, seleciona data e horário em pickers nativos do Material 3, acompanha tudo em tempo real na tela e confirma o agendamento com um toast de feedback.

O foco do projeto é demonstrar o gerenciamento correto de estado em Compose — o que deve viver no `ViewModel`, o que pode ficar em `rememberSaveable`, e por que essa distinção evita perda de dados ao girar o dispositivo.

## ✅ Funcionalidades

- **Diálogo de dados** (`AlertDialog`) com `OutlinedTextField` para o nome e `Switch` para "Receber lembrete"
- **Seleção de data** via `DatePickerDialog` + `DatePicker`, exibida em `Text` na tela
- **Seleção de horário** via `TimePicker` dentro de um `AlertDialog`, exibida em `Text` na tela
- **Card de resumo** exibindo nome, preferência de lembrete, data e horário selecionados
- **Botão "Confirmar agendamento"** que valida o preenchimento, exibe um `Toast` de sucesso (ou de aviso, se algo estiver faltando) e limpa os campos para um novo agendamento

## 🧠 Requisitos avançados

| Requisito | Onde está implementado |
|---|---|
| `ViewModel` guardando data, hora e estado dos diálogos | `AgendamentoViewModel`: `data`, `hora`, `mostrarDialog`, `mostrarDatePicker`, `mostrarTimePicker`, além de `nomeConfirmado` e `opcaoConfirmada` |
| Uso de `rememberSaveable` | Os campos "rascunho" do diálogo (`nome` e `opcaoSelecionada`, em `AgendamentoTela`) usam `rememberSaveable`, garantindo que o que está sendo digitado não se perca numa rotação mesmo antes de confirmar |
| Dados não se perdem ao girar a tela | Dados confirmados sobrevivem por estarem no `ViewModel` (retido entre recomposições/mudanças de configuração); dados em edição sobrevivem por estarem em `rememberSaveable` |

## 🛠️ Tecnologias

- **Kotlin** 2.2.10
- **Jetpack Compose** (BOM 2026.02.01) + **Material 3**
- **Android ViewModel** (`lifecycle-viewmodel-compose` 2.11.0)
- **AGP** 9.3.2 · **Gradle** 9.5
- `compileSdk`/`targetSdk` 37 · `minSdk` 24

## 🏗️ Arquitetura

```
app/src/main/java/br/dev/guisleri/agendamento/
├── MainActivity.kt          # Activity + Composable AgendamentoTela (UI, diálogos, pickers)
├── AgendamentoViewModel.kt  # Estado persistente: data, hora, visibilidade dos diálogos, dados confirmados
└── ui/theme/                # Tema Material 3 (Color.kt, Type.kt, Theme.kt)
```

O padrão adotado é próximo de um MVVM simplificado:

- O **ViewModel** concentra o estado que representa o agendamento em si (o que já foi confirmado) e o estado de UI que precisa sobreviver a recomposições, como qual diálogo está aberto.
- Os **campos de rascunho do formulário** (o que o usuário está digitando/alternando antes de clicar em "Confirmar" no diálogo) ficam no Composable com `rememberSaveable`, já que são efêmeros e específicos daquela tela.

## ▶️ Como executar

1. Abra a pasta do projeto no Android Studio
2. Aguarde a sincronização do Gradle
3. Rode em um emulador ou dispositivo físico com Android 7.0 (API 24) ou superior

## 👤 Autor

Marcos — projeto desenvolvido para fins acadêmicos (IFSP Capivari).
