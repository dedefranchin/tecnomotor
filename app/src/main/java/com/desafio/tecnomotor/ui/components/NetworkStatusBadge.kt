package com.desafio.tecnomotor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.desafio.tecnomotor.util.NetworkStatus
import kotlinx.coroutines.delay

@Composable
fun NetworkStatusBadge(networkStatus: NetworkStatus, modifier: Modifier = Modifier) {
    var showTooltip by remember { mutableStateOf(false) }

    val color = when (networkStatus) {
        is NetworkStatus.Available -> Color.Green
        is NetworkStatus.Unavailable -> Color.Red
    }

    val tooltipMessage = when (networkStatus) {
        is NetworkStatus.Available -> "Conectado à internet"
        is NetworkStatus.Unavailable -> "Sem conexão com a internet"
    }

    Box(
        modifier = modifier
            .wrapContentSize()
    ) {
    
        if (showTooltip) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    
                    .background(
                        color = MaterialTheme.colorScheme.inverseSurface,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .zIndex(1f)
            ) {
                Text(
                    text = tooltipMessage,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    fontSize = 12.sp
                )
            }

          
            LaunchedEffect(Unit) {
                delay(2000)
                showTooltip = false
            }
        }

     
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color)
                .clickable { showTooltip = true }
                .align(Alignment.Center)
        )
    }
}
