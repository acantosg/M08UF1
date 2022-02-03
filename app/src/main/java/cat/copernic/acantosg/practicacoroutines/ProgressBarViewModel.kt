package cat.copernic.acantosg.practicacoroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressBarViewModel() : ViewModel() {

    enum class State {
        NOT_STARTED,
        RUNNING,
        PAUSED,
        STOPPED,
        DONE
    }

    private val _state = MutableLiveData<State>(State.NOT_STARTED)
    val state: LiveData<State> = _state

    private val _max = MutableLiveData<Int>(100)
    val max: LiveData<Int> = _max

    private val _progress = MutableLiveData<Int>(0)
    val progress: LiveData<Int> = _progress

    private val _startClick = MutableLiveData<Boolean>(false)
    val startClick: LiveData<Boolean> = _startClick

    private val _restartClick = MutableLiveData<Boolean>(false)
    val restartClick: LiveData<Boolean> = _restartClick

    private val _stopClick = MutableLiveData<Boolean>(false)
    val stopClick: LiveData<Boolean> = _stopClick

    private var job: Job? = null

    private fun jobToggle() {
        if (job == null) {
            job = viewModelScope.launch(Dispatchers.IO) {
                while (_progress.value?.compareTo(_max.value ?: 0) ?: 0 < 0) {
                    delay(100L)
                    _progress.postValue(_progress.value?.inc())
                }
                done()
            }
        } else {
            job?.cancel()
            job = null
        }
    }

    private fun stopJob() {
        if (job != null) {
            job?.cancel()
            job = null
        }
        job = viewModelScope.launch(Dispatchers.IO) {
            while (_progress.value?.compareTo(0) ?: 0 > 0) {
                delay(10L)
                _progress.postValue(_progress.value?.dec())
            }
            doneStop()
        }
    }

    private fun jobCancel() {
        job?.cancel()
        job = null
    }

    fun onStartClick() {
        _startClick.value = true
    }

    fun onStartClickEnd() {
        _startClick.value = false
    }

    fun onRestartClick() {
        _restartClick.value = true
    }

    fun onRestartClickEnd() {
        _restartClick.value = false
    }

    fun onStopClick() {
        _stopClick.value = true
    }

    fun onStopClickEnd() {
        _stopClick.value = false
    }

    fun start() {
        jobToggle()
        _state.value = State.RUNNING
    }

    fun pause() {
        jobToggle()
        _state.value = State.PAUSED
    }

    fun resume() {
        jobToggle()
        _state.value = State.RUNNING
    }

    fun stop() {
        stopJob()
        _state.value = State.STOPPED
    }

    private fun done() {
        jobCancel()
        _state.postValue(State.DONE)
    }

    private fun doneStop() {
        jobCancel()
        _state.postValue(State.NOT_STARTED)
    }

    fun restart() {
        jobCancel()
        _progress.value = 0
        _state.postValue(State.NOT_STARTED)
    }

}