package com.jim.countdowntimer.ui


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    viewModel: HomeViewModel = viewModel(),
    numberStyle: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    size: Dp = 200.dp,
    foregroundIndicatorColor: Color = colorRed,
    backgroundIndicatorColor: Color = greenish,
) {

    val currentTimeString by viewModel.currentTimeString.collectAsStateWithLifecycle()
    val progress by viewModel.currentTime.collectAsStateWithLifecycle()

    // animation
    val progressAnimate by animateFloatAsState(
        targetValue = progress.toFloat() / 60000, //turn  range into 0.0 - 1.0 by divide the progress value by 60000.
        animationSpec = tween(
            durationMillis = 300,//animation duration
            delayMillis = 50,//delay before animation start
            easing = LinearOutSlowInEasing
        ), label = ""
    )
    
    // This is to start the animation when the activity is opened
    LaunchedEffect(Unit) {
        viewModel.refreshToken()
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
            progress = progressAnimate
        )
        // Text that shows number inside the circle
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = currentTimeString,
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
private fun RefreshTokenButton(
    modifier: Modifier = Modifier,
    onClickButton: () -> Unit,
    color: Color
) {
    OutlinedButton(
        modifier = modifier.size(width = 160.dp, height = 60.dp),
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

@Composable
fun TokenScreen(
    viewModel: HomeViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val tokenString by viewModel.tokenString.collectAsStateWithLifecycle()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TokenView(tokenString)
        Spacer(modifier = Modifier.size(24.dp))
        CircularProgressbar()
        Spacer(modifier = Modifier.size(24.dp))
        RefreshTokenButton(onClickButton = {
            viewModel.refreshToken()
        }, color = colorRed)
    }

}

@Preview(
    name = "Token Screen",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun TokenScreenPreview() {
    CountDownTimerTheme {
        TokenScreen()
    }
}

@Preview(
    name = "Token Light",
    showBackground = true
)
@Preview(
    name = "Token dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun ButtonProgressbarPreview() {
    CountDownTimerTheme {
        RefreshTokenButton(onClickButton = {}, color = colorRed)
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