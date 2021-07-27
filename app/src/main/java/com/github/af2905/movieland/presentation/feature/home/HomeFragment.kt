package com.github.af2905.movieland.presentation.feature.home

import android.os.Bundle
import android.view.View
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentHomeBinding
import com.github.af2905.movieland.presentation.base.BaseFragment
import com.github.af2905.movieland.presentation.common.ItemAdapter
import com.github.af2905.movieland.presentation.common.ListAdapter
import com.github.af2905.movieland.presentation.feature.home.item.MovieItem

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun layoutRes(): Int = R.layout.fragment_home
    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonNext.setOnClickListener {
            //viewModel.openMovieDetail()
        }

        binding.homeRecyclerView.apply {
            adapter = ListAdapter(
                ItemAdapter(MovieItem.VIEW_TYPE)
            )
        }
    }
}