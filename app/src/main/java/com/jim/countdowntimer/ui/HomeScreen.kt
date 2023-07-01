package com.jim.countdowntimer.ui


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jim.countdowntimer.HomeViewModel
import com.jim.countdowntimer.ui.theme.CountDownTimerTheme
import com.jim.countdowntimer.ui.theme.colorRed
import com.jim.countdowntimer.ui.theme.colorWhite
import com.jim.countdowntimer.ui.theme.greenish


@Composable
private fun TokenView(
    currentToken: String = "",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = currentToken,
            modifier = modifier.padding(16.dp),
            style = MaterialTheme.typography.h1
        )
    }
}

@Preview(name = "Token Light", showBackground = true, heightDp = 200)
@Preview(name = "Token dark", showBackground = true, heightDp = 200, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TokenViewPreview() {
    CountDownTimerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            TokenView("16838")
        }
    }
}


@Composable
fun CircularProgressbar(
    viewModel:HomeViewModel = viewModel(),
    number: Float = 60f,
    numberStyle: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    size: Dp = 200.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
    foregroundIndicatorColor: Color = colorRed,
    backgroundIndicatorColor: Color = greenish,
) {
    val progressValue = (viewModel.tokenMaxTimer / 1000).toFloat()
    val maxProgressValue =  (viewModel.tokenMaxTimer / 1000).toInt()
    val currentTime = viewModel.currentTime.asFlow().collectAsState(initial = "").toString()
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val progressAnimationValue by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = progressValue,
        animationSpec = infiniteRepeatable(animation = tween(900)),
        label = "")

        // It remembers the number value
    var numberR by remember {
        mutableStateOf(-1f)
    }

    // Number Animation
    val animateNumber = animateFloatAsState(
        targetValue = progressValue,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        ), label = ""
    )

    // This is to start the animation when the activity is opened
    LaunchedEffect(Unit) {
        numberR = number
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size = size)
    ) {

        CircularProgressIndicator(
            modifier = Modifier.size(size),
            color = foregroundIndicatorColor,
            backgroundColor = backgroundIndicatorColor,
            strokeWidth = 10.dp,
            progress = progressAnimationValue
        )
        // Text that shows number inside the circle
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = currentTime,
                style = numberStyle
            )
            Text(
                text = "Count Down",
                style = numberStyle
            )
        }
    }
}

@Composable
private fun ButtonProgressbar(
    modifier: Modifier = Modifier,
    onClickButton: () -> Unit,
    color: Color
) {
    OutlinedButton(
        modifier = Modifier.size(width = 160.dp, height = 60.dp),
        border = BorderStroke(1.dp, color),
        shape = RoundedCornerShape(percent = 25),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorWhite,
            contentColor = color
        ),
        onClick = {
            onClickButton()
        }) {
        Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = "Refresh Icon",
            tint = color,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            text = "Refresh",
            color = color,
            fontSize = 16.sp
        )
    }
}


@Preview(name = "Token Light",
    showBackground = true)
@Preview(
    name = "Token dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun ButtonProgressbarPreview() {
    CountDownTimerTheme {
        ButtonProgressbar(onClickButton = {},color = colorRed)
    }
}

@Preview(name = "Token Light", showBackground = true, widthDp = 300, heightDp = 300)
@Preview(
    name = "Token dark",
    showBackground = true,
    widthDp = 300,
    heightDp = 300,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun CustomLinearProgressIndicatorPreview() {
    CountDownTimerTheme {
        CircularProgressbar()
    }
}