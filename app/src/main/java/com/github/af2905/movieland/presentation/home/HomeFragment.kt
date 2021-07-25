package com.github.af2905.movieland.presentation.home

import android.os.Bundle
import android.view.View
import com.github.af2905.movieland.R
import com.github.af2905.movieland.base.BaseFragment
import com.github.af2905.movieland.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun layoutRes(): Int = R.layout.fragment_home
    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonNext.setOnClickListener {
            //viewModel.openMovieDetail()

        }
    }
}