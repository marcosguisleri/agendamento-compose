package br.dev.guisleri.agendamento

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.dev.guisleri.agendamento.ui.theme.AgendamentoTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgendamentoTheme {
                AgendamentoTela()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendamentoTela(
    viewModel: AgendamentoViewModel = viewModel()
) {

    val context = LocalContext.current

    var nome by rememberSaveable { mutableStateOf("") }
    var opcaoSelecionada by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(23.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Agendamento", style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Nome: ${viewModel.nomeConfirmado}")

                Text(
                    "Deseja receber lembrete? ${
                        if (viewModel.opcaoConfirmada) "Sim" else "Não"
                    }"
                )

                Text("Data: ${viewModel.data}")
                Text("Hora: ${viewModel.hora}")
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    viewModel.mostrarDialog = true
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Informar dados")
            }

            Button(
                onClick = {
                    viewModel.mostrarDatePicker = true
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Selecionar data")
            }

            Button(
                onClick = {
                    viewModel.mostrarTimePicker = true
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Selecionar horário")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = {
                    if (
                        viewModel.nomeConfirmado.isBlank() ||
                        viewModel.data == "Nenhuma data" ||
                        viewModel.hora == "Nenhum horário"
                    ) {
                        Toast.makeText(
                            context,
                            "Preencha todos os dados do agendamento!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Agendamento realizado com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()

                        viewModel.limparAgendamento()
                        nome = ""
                        opcaoSelecionada = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ),
                modifier = Modifier.fillMaxWidth()
            ) { Text("CONFIRMAR AGENDAMENTO") }
        }

        if (viewModel.mostrarDialog) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.mostrarDialog = false
                },

                title = {
                    Text("Dados do agendamento")
                },

                text = {
                    Column {
                        Text("Informe os dados do agendamento")

                        Spacer(modifier = Modifier.padding(8.dp))

                        OutlinedTextField(value = nome, onValueChange = { nome = it }, label = {
                            Text("Nome")
                        })

                        Spacer(modifier = Modifier.padding(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Switch(
                                checked = opcaoSelecionada,
                                onCheckedChange = { opcaoSelecionada = it },
                                Modifier.padding(8.dp)
                            )
                            Text("Receber lembrete")
                        }
                    }
                },

                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.nomeConfirmado = nome
                            viewModel.opcaoConfirmada = opcaoSelecionada
                            viewModel.mostrarDialog = false
                        }
                    ) {
                        Text("Confirmar")
                    }
                },

                dismissButton = {
                    Button(
                        onClick = {
                            viewModel.mostrarDialog = false
                        }
                    ) { Text("Cancelar") }
                }
            )
        }

        val datePickerState = rememberDatePickerState()

        if (viewModel.mostrarDatePicker) {
            DatePickerDialog(
                onDismissRequest = {
                    viewModel.mostrarDatePicker = false
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val dataMillis = datePickerState.selectedDateMillis

                            if (dataMillis != null) {
                                val formato = SimpleDateFormat(
                                    "dd/MM/yyyy",
                                    Locale.getDefault()
                                )

                                formato.timeZone = TimeZone.getTimeZone("UTC")

                                viewModel.data = formato.format(Date(dataMillis))
                            }

                            viewModel.mostrarDatePicker = false
                        }
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            viewModel.mostrarDatePicker = false
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false
                )
            }
        }

        val timePickerState = rememberTimePickerState()

        if (viewModel.mostrarTimePicker) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.mostrarTimePicker = false
                },

                title = {
                    Text("Selecionar horário")
                },

                text = {
                    TimePicker(
                        state = timePickerState
                    )
                },

                confirmButton = {
                    Button(
                        onClick = {
                            val hora = timePickerState.hour
                            val minuto = timePickerState.minute

                            viewModel.hora = String.format(
                                "%02d:%02d",
                                hora,
                                minuto
                            )

                            viewModel.mostrarTimePicker = false
                        }
                    ) {
                        Text("Confirmar")
                    }
                },

                dismissButton = {
                    Button(
                        onClick = {
                            viewModel.mostrarTimePicker = false
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }

    }
}

