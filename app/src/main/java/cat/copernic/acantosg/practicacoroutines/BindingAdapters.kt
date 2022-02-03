package cat.copernic.acantosg.practicacoroutines

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.Button
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("state")
fun bindState(button: Button, state: ProgressBarViewModel.State) {
    when (state) {
        ProgressBarViewModel.State.NOT_STARTED -> button.setText(R.string.start)
        ProgressBarViewModel.State.RUNNING -> button.setText(R.string.pause)
        ProgressBarViewModel.State.PAUSED -> button.setText(R.string.resume)
        else -> {}
    }
}

@BindingAdapter("stop")
fun bindStop(button: Button, state: ProgressBarViewModel.State) {
    when (state) {
        ProgressBarViewModel.State.STOPPED -> button.visibility = Button.INVISIBLE
        ProgressBarViewModel.State.DONE -> button.visibility = Button.INVISIBLE
        else -> {
            button.visibility = Button.VISIBLE
        }
    }
}

@BindingAdapter("restart")
fun bindRestart(button: Button, state: ProgressBarViewModel.State) {
    when (state) {
        ProgressBarViewModel.State.DONE -> button.visibility = Button.VISIBLE
        else -> {
            button.visibility = Button.INVISIBLE
        }
    }
}

@BindingAdapter("color")
fun bindColor(bar: ProgressBar, state: ProgressBarViewModel.State) {
    when (state) {
        ProgressBarViewModel.State.RUNNING -> bar.progressTintList = ColorStateList.valueOf(Color.GREEN)
        ProgressBarViewModel.State.STOPPED -> bar.progressTintList = ColorStateList.valueOf(Color.RED)
        ProgressBarViewModel.State.DONE -> bar.progressTintList = ColorStateList.valueOf(Color.GREEN)
        else -> {
            bar.progressTintList = ColorStateList.valueOf(Color.GRAY)
        }
    }
}
