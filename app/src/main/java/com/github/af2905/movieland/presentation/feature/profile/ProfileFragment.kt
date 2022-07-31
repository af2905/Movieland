package com.github.af2905.movieland.presentation.feature.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.af2905.movieland.CoreComponentProvider
import com.github.af2905.movieland.R
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.item.ProfileMenuItem
import com.github.af2905.movieland.databinding.FragmentProfileBinding
import com.github.af2905.movieland.presentation.feature.profile.di.DaggerProfileComponent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val detailComponent = DaggerProfileComponent.factory().create(appComponent)
        detailComponent.injectProfileFragment(this)
    }

    @OptIn(InternalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = baseAdapter
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect(FlowCollector { effect ->
                when (effect) {
                    is ProfileContract.Effect.OpenMenuItemDetail -> handleEffect(effect.navigator)
                }
            })
        }
    }
}