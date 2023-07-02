package com.jim.countdowntimer

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.util.Random
import java.util.concurrent.TimeUnit

class HomeViewModel : ViewModel() {
    private var timeCountInMilliSeconds = 1 * 60000.toLong()
    val tokenMaxTimer = 1 * 60 * 1000.toLong()

    //  private var email: String
    private enum class TimerStatus {
        STARTED, STOPPED
    }

    private val _currentTime = MutableStateFlow<Long>(0)
    val currentTime: StateFlow<Long>
        get() = _currentTime.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    private val _currentTimeString = MutableStateFlow<String>("00:00:00")
    val currentTimeString: StateFlow<String>
        get() = _currentTimeString.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )


    private val _tokenString = MutableStateFlow<String>("Test Token")
    val tokenString: StateFlow<String>
        get() = _tokenString.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )


    private var timerStatus = TimerStatus.STOPPED

    private var countDownTimer: CountDownTimer? = null


    /**
     * method to start count down timer
     */
    private fun startCountDownTimer() {
        countDownTimer = object : CountDownTimer(timeCountInMilliSeconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTimeString.update { hmsTimeFormatter(millisUntilFinished) }
                _currentTime.update {millisUntilFinished}
            }

            override fun onFinish() {
                // changing the timer status to stopped
                timerStatus = TimerStatus.STOPPED
                // doGetToken()
                refreshToken()
            }
        }.start()
    }


    fun startStop() {
        if (timerStatus === TimerStatus.STOPPED) {
            // call to initialize the timer values
            setTimerValues()
            // call to initialize the progress bar values
            // showing the reset icon
            // changing the timer status to started
            timerStatus = TimerStatus.STARTED
            // call to start the count down timer
            startCountDownTimer()
        } else {
            // changing the timer status to stopped
            timerStatus = TimerStatus.STOPPED
            stopCountDownTimer()
        }
    }

    /**
     * method to initialize the values for count down timer
     */
    private fun setTimerValues() {
        val time = 1   //Time time in minutes
        // assigning values after converting to milliseconds
        timeCountInMilliSeconds = time * 60 * 1000.toLong()
    }

    /**
     * method to stop count down timer
     */
    private fun stopCountDownTimer() {
        countDownTimer?.cancel()
    }


    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return HH:mm:ss time formatted string
     */
    private fun hmsTimeFormatter(milliSeconds: Long): String {
        return String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(milliSeconds),
            TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(
                    milliSeconds
                )
            ),
            TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(
                    milliSeconds
                )
            )
        )
    }

    private fun generateRand() {
        val min = 100000
        val max = 900000
        val rnd = Random(System.nanoTime())
        _tokenString.update {  (min + rnd.nextInt(max)).toString()}
        startStop()
        Timber.e("TAG generateRand:  %s", _tokenString.value)
    }

    fun refreshToken() {
        // changing the timer status to stopped
        timerStatus = TimerStatus.STOPPED
        stopCountDownTimer()
        //doGetToken()
        generateRand()
    }
    init {
        refreshToken()
    }
}