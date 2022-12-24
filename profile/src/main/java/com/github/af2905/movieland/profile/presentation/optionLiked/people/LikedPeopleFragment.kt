package com.github.af2905.movieland.profile.presentation.optionLiked.people

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.decorator.VerticalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.PersonItemV2
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.profile.R
import com.github.af2905.movieland.profile.databinding.FragmentLikedPeopleBinding
import com.github.af2905.movieland.profile.di.DaggerProfileComponent

class LikedPeopleFragment :
    BaseFragment<LikedPeopleNavigator, FragmentLikedPeopleBinding, LikedPeopleViewModel>() {
    override fun getNavigator(navController: NavController) = LikedPeopleNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_liked_people
    override fun viewModelClass(): Class<LikedPeopleViewModel> = LikedPeopleViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            PersonItemV2.VIEW_TYPE,
            listener = PersonItemV2.Listener { item -> viewModel.openDetail(item.id) })
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val profileComponent = DaggerProfileComponent.factory().create(appComponent)
        profileComponent.injectLikedPeopleFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = baseAdapter
            addItemDecoration(
                VerticalListItemDecorator(
                    marginTop = this.context.resources.getDimensionPixelSize(R.dimen.default_margin),
                    marginBottom = this.context.resources.getDimensionPixelSize(R.dimen.default_margin),
                    spacing = this.context.resources.getDimensionPixelSize(R.dimen.default_margin)
                )
            )
        }
        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect { effect ->
                when (effect) {
                    is LikedPeopleContract.Effect.OpenPersonDetail -> handleEffect(effect.navigator)
                }
            }
        }
    }
}