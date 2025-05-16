package com.example.myapplication.ui.dashboard
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun GraphScreen() {
    val gastos: List<Pair<String, Int>> = listOf(
        "Alimentos" to 150,
        "Transporte" to 120,
        "Entretenimiento" to 80
    )
    val dineroDisponible = 300f
    val totalGastado = gastos.sumOf { it.second }

    val colores = listOf(Color(0xFF60A5FA), Color(0xFF34D399), Color(0xFFF87171))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF6FB))
            .padding(16.dp)
    ) {
        Text(
            text = "Gráfica",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Visualización de Gastos", fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.height(40.dp))

                DonutChart(
                    values = gastos.map { it.second.toFloat() },
                    colors = colores,
                    labels = gastos.map { it.first }, // nombres de los gastos
                    thickness = 200f,
                    gapAngleDegrees = 2f,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(42.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total gastado", fontWeight = FontWeight.Bold)
                    Text("$${totalGastado.toInt()}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Dinero disponible", fontWeight = FontWeight.Medium, color = Color.Gray)
                    Text("$${dineroDisponible.toInt()}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            gastos.forEach { (categoria, monto) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(categoria)
                    Text("$${monto.toInt()}", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun DonutChart(
    values: List<Float>,
    colors: List<Color>,
    labels: List<String>,
    modifier: Modifier = Modifier,
    thickness: Float = 40f,
    gapAngleDegrees: Float = 0f
) {
    val total = values.sum()
    val totalGap = gapAngleDegrees * values.size
    val angleScale = (360f - totalGap) / total
    val angles = values.map { it * angleScale }

    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2
        val center = this.center
        var startAngle = -90f

        for (i in values.indices) {
            val sweepAngle = angles[i]

            // Dibuja el segmento
            drawArc(
                color = colors[i % colors.size],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = thickness, cap = StrokeCap.Round)
            )

            // Calcula el ángulo medio del segmento
            val midAngle = startAngle + sweepAngle / 2
            val angleRad = Math.toRadians(midAngle.toDouble())

            // Posición del texto (en el borde del círculo)
            val labelX = center.x + (radius + 10f) * cos(angleRad).toFloat()
            val labelY = center.y + (radius + 10f) * sin(angleRad).toFloat()

            // Dibuja el nombre
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    labels[i],
                    labelX,
                    labelY,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textAlign = android.graphics.Paint.Align.CENTER
                        FontWeight.ExtraBold
                        textSize = 38f
                        isAntiAlias = true
                    }
                )
            }

            startAngle += sweepAngle + gapAngleDegrees
        }
    }
}
