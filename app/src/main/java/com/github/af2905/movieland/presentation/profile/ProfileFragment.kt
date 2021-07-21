package com.github.af2905.movieland.presentation.profile

import com.github.af2905.movieland.R
import com.github.af2905.movieland.base.BaseFragment
import com.github.af2905.movieland.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_profile
    override fun viewModelClass(): Class<ProfileViewModel> = ProfileViewModel::class.java

}