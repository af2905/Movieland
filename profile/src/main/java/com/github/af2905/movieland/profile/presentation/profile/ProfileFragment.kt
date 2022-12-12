package com.github.af2905.movieland.profile.presentation.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.item.ProfileMenuItem
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.profile.R
import com.github.af2905.movieland.profile.databinding.FragmentProfileBinding
import com.github.af2905.movieland.profile.di.DaggerProfileComponent

class ProfileFragment : BaseFragment<ProfileNavigator, FragmentProfileBinding, ProfileViewModel>() {
    override fun getNavigator(navController: NavController) = ProfileNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_profile
    override fun viewModelClass(): Class<ProfileViewModel> = ProfileViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            viewType = ProfileMenuItem.VIEW_TYPE,
            listener = ProfileMenuItem.Listener { item ->
                viewModel.navigateToOption(type = item.type)
            }
        ))

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val profileComponent = DaggerProfileComponent.factory().create(appComponent)
        profileComponent.injectProfileFragment(this)
    }

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
                    is ProfileContract.Effect.OpenOption -> handleEffect(effect.navigator)
                }
            }
        }
    }
}