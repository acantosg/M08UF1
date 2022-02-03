package cat.copernic.acantosg.practicacoroutines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.ViewModelProvider
import cat.copernic.acantosg.practicacoroutines.ProgressBarViewModel.State.*
import cat.copernic.acantosg.practicacoroutines.databinding.ProgressBarFragmentBinding

class ProgressBarFragment : Fragment() {

    private lateinit var viewModel: ProgressBarViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = ProgressBarFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(this).get(ProgressBarViewModel::class.java)
        binding.viewModel = viewModel

        binding.startButton.setOnClickListener() {
            viewModel.onStartClick()
        }

        binding.restartButton.setOnClickListener() {
            viewModel.onRestartClick()
        }

        binding.stopButton.setOnClickListener() {
            viewModel.onStopClick()
        }

        viewModel.startClick.observe(viewLifecycleOwner) {
            if (it) {
                action()
                viewModel.onStartClickEnd()
            }
        }

        viewModel.restartClick.observe(viewLifecycleOwner) {
            if (it) {
                restartBar()
                viewModel.onRestartClickEnd()
            }
        }

        viewModel.stopClick.observe(viewLifecycleOwner) {
            if (it) {
                stopBar()
                viewModel.onStopClickEnd()
            }
        }

        return binding.root
    }

    fun action() {
        when (viewModel.state.value) {
            NOT_STARTED -> {
                viewModel.start()
            }
            RUNNING -> {
                viewModel.pause()
            }
            PAUSED -> {
                viewModel.resume()
            }
            else -> {}
        }
    }

    fun stopBar() {
        viewModel.stop()
    }

    fun restartBar() {
        viewModel.restart()
    }

}