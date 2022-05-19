package com.github.af2905.movieland.presentation.feature.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentProfileBinding
import com.github.af2905.movieland.presentation.base.BaseFragment
import com.github.af2905.movieland.presentation.common.BaseAdapter
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.model.item.ProfileMenuItem

class ProfileFragment : BaseFragment<ProfileNavigator, FragmentProfileBinding, ProfileViewModel>() {
    override fun getNavigator(navController: NavController) = ProfileNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_profile
    override fun viewModelClass(): Class<ProfileViewModel> = ProfileViewModel::class.java

    private val baseAdapter: BaseAdapter =
        BaseAdapter(
            ItemDelegate(
                ProfileMenuItem.VIEW_TYPE,
                listener = ProfileMenuItem.Listener { item ->

                }
            ))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = baseAdapter
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect { effect ->
                when (effect) {
                    is ProfileContract.Effect.OpenMenuItemDetail -> handleEffect(effect.navigator)
                }
            }
        }
    }
}