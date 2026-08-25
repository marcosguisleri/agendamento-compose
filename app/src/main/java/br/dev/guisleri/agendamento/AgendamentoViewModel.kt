package br.dev.guisleri.agendamento

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AgendamentoViewModel : ViewModel() {

    var data by mutableStateOf("")
    var hora by mutableStateOf("")

    var nomeConfirmado by mutableStateOf("")
    var opcaoConfirmada by mutableStateOf(false)

    var mostrarDialog by mutableStateOf(false)
    var mostrarDatePicker by mutableStateOf(false)
    var mostrarTimePicker by mutableStateOf(false)

}