package br.dev.guisleri.agendamento

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.dev.guisleri.agendamento.ui.theme.AgendamentoTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.AlertDialog

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

@Composable
fun AgendamentoTela(
    viewModel: AgendamentoViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(23.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Agendamento",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Nome: -")
        Text("Opção: -")

        Text("Data: ${viewModel.data}")
        Text("Hora: ${viewModel.hora}")

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    viewModel.mostrarDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Informar dados")
            }

            Button(
                onClick = {
                    viewModel.mostrarDatePicker = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Selecionar data")
            }

            Button(
                onClick = {
                    viewModel.mostrarTimePicker = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Selecionar horário")
            }
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
                    Text("Teste")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.mostrarDialog = false
                        }
                    ) {
                        Text("Confirmar")
                    }
                }
            )
        }
    }
}

