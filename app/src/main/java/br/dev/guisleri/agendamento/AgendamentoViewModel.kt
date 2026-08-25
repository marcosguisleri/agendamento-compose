package br.dev.guisleri.agendamento

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AgendamentoViewModel : ViewModel() {

    var data by mutableStateOf("Nenhuma data")
    var hora by mutableStateOf("Nenhum horário")

    var nomeConfirmado by mutableStateOf("")
    var opcaoConfirmada by mutableStateOf(false)

    var mostrarDialog by mutableStateOf(false)
    var mostrarDatePicker by mutableStateOf(false)
    var mostrarTimePicker by mutableStateOf(false)

    fun limparAgendamento() {
        data = "Nenhuma data"
        hora = "Nenhum horário"
        nomeConfirmado = ""
        opcaoConfirmada = false
    }
}