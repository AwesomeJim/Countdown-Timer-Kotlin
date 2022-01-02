package com.jim.countdowntimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jim.countdowntimer.databinding.HomeFragmentBinding

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
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.btnRefresh.setOnClickListener {
            viewModel.refreshToken()
        }

        viewModel.currentTimeString.observe(viewLifecycleOwner) {
            binding.textViewTime.text = it
        }
        viewModel.currentTime.observe(viewLifecycleOwner) {
            binding.progressBarCircle.progress = (it / 1000).toInt()
        }
        viewModel.tokenString.observe(viewLifecycleOwner) {
            binding.textViewToken.text = it
        }
        viewModel.tokenString.observe(viewLifecycleOwner) {
            // setProgressBarValues()
            binding.textViewToken.text = it
            viewModel.startStop()
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