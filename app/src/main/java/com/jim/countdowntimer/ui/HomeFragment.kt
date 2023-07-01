package com.jim.countdowntimer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jim.countdowntimer.HomeViewModel
import com.jim.countdowntimer.databinding.HomeFragmentBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {


    internal var view: View? = null
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!


    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.view = view
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.btnRefresh.setOnClickListener {
            viewModel.refreshToken()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentTimeString.collect {
                    binding.textViewTime.text = it
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentTime.collect {
                    binding.progressBarCircle.progress = (it / 1000).toInt()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tokenString.collect {
                    // setProgressBarValues()
                    binding.textViewToken.text = it
                    viewModel.startStop()
                }
            }
        }
        setProgressBarValues()
    }

    /**
     * method to set circular progress bar values
     */
    private fun setProgressBarValues() {
        binding.progressBarCircle.max = (viewModel.tokenMaxTimer / 1000).toInt()
        binding.progressBarCircle.progress = (viewModel.tokenMaxTimer / 1000).toInt()
    }
}