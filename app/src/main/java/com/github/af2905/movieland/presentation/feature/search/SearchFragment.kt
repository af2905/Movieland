package com.github.af2905.movieland.presentation.feature.search

import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentSearchBinding
import com.github.af2905.movieland.presentation.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_search
    override fun viewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java

}