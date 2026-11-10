package com.exemplo.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    CalculadoraImcApp()
                }
            }
        }
    }
}

/**
 * Tela principal da Calculadora de IMC.
 * Desenvolvida em Kotlin nativo com Jetpack Compose e Material 3.
 */
@Composable
fun CalculadoraImcApp() {
    // 1. Estados reativos que guardam os textos digitados
    var pesoInput by remember { mutableStateOf("") }
    var alturaInput by remember { mutableStateOf("") }

    // Estados para o resultado e mensagens
    var resultadoImc by remember { mutableStateOf<String?>(null) }
    var classificacao by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf("") }

    // Definição explícita da cor roxa
    val corRoxa = Color(0xFF6A1B9A)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Requisito 5: Fundo da área principal branco
    ) {
        // Requisito 4: Cabeçalho com largura total, altura exata de 30 dp,
        // fundo roxo explícito e texto branco "Calculadora de IMC"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(corRoxa),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Calculadora de IMC",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        // Formulário com espaçamento lateral de 16 dp
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de entrada de Peso em kg
            OutlinedTextField(
                value = pesoInput,
                onValueChange = { novoPeso ->
                    pesoInput = novoPeso
                    // Limpa resultados e erros anteriores ao digitar novo valor
                    resultadoImc = null
                    classificacao = ""
                    mensagemErro = ""
                },
                label = { Text("Peso (kg)") },
                placeholder = { Text("Ex: 70.5") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            // Campo de entrada de Altura em metros
            OutlinedTextField(
                value = alturaInput,
                onValueChange = { novaAltura ->
                    alturaInput = novaAltura
                    // Limpa resultados e erros anteriores ao digitar novo valor
                    resultadoImc = null
                    classificacao = ""
                    mensagemErro = ""
                },
                label = { Text("Altura (m)") },
                placeholder = { Text("Ex: 1.75") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            // Botão com texto "Calcular IMC"
            Button(
                onClick = {
                    val pesoLimpo = pesoInput.trim().replace(',', '.')
                    val alturaLimpa = alturaInput.trim().replace(',', '.')
                    val peso = pesoLimpo.toDoubleOrNull()
                    val altura = alturaLimpa.toDoubleOrNull()

                    // Validação completa:
                    // 1. Não podem ser nulos ou vazios
                    // 2. Não podem ser NaN ou Infinity (!isFinite())
                    // 3. Devem ser estritamente maiores que zero
                    // 4. Altura segura: mínimo de 0.20 m (20 cm) e quadrado maior que zero
                    if (peso == null || altura == null || 
                        !peso.isFinite() || !altura.isFinite() || 
                        peso <= 0.0 || altura <= 0.0 || 
                        altura < 0.20 || (altura * altura) <= 0.0
                    ) {
                        mensagemErro = "Por favor, insira valores válidos e maiores que zero."
                        resultadoImc = null
                        classificacao = ""
                    } else {
                        val imc = peso / (altura * altura)

                        // Verificação pós-cálculo com !imc.isFinite()
                        // Impede que NaN, Infinity ou divisões anômalas sejam exibidas
                        if (!imc.isFinite() || imc <= 0.0 || imc > 1000.0) {
                            mensagemErro = "Por favor, insira valores válidos e maiores que zero."
                            resultadoImc = null
                            classificacao = ""
                        } else {
                            // Exibição com exatamente duas casas decimais
                            resultadoImc = String.format(Locale.US, "%.2f", imc)
                            mensagemErro = ""

                            // Classificação padrão da OMS
                            classificacao = when {
                                imc < 18.5 -> "Abaixo do peso"
                                imc < 25.0 -> "Peso normal"
                                imc < 30.0 -> "Sobrepeso"
                                imc < 35.0 -> "Obesidade Grau I"
                                imc < 40.0 -> "Obesidade Grau II"
                                else -> "Obesidade Grau III (Mórbida)"
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = corRoxa,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Calcular IMC",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Mensagem de erro de validação
            if (mensagemErro.isNotEmpty()) {
                Text(
                    text = mensagemErro,
                    color = Color(0xFFD32F2F),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Exibição do resultado do IMC formatado com 2 casas decimais
            resultadoImc?.let { valorImc ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF3E5F5)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Seu IMC é:",
                            fontSize = 16.sp,
                            color = Color(0xFF4A148C)
                        )
                        Text(
                            text = valorImc,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = corRoxa
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Classificação: $classificacao",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF333333)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculadoraImc() {
    MaterialTheme {
        CalculadoraImcApp()
    }
}