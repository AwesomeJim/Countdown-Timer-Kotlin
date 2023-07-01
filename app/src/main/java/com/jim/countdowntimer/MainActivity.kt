package com.jim.countdowntimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jim.countdowntimer.ui.TokenScreen
import com.jim.countdowntimer.ui.theme.CountDownTimerTheme

class MainActivity : ComponentActivity() {
    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountDownTimerTheme {
                TokenScreen()
            }
        }
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