package com.github.af2905.movieland.liked.presentation.people

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.IntentFilterKey
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.decorator.VerticalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.PersonV2Item
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.liked.R
import com.github.af2905.movieland.liked.databinding.FragmentLikedPeopleBinding
import com.github.af2905.movieland.liked.di.DaggerLikedComponent

class LikedPeopleFragment :
    BaseFragment<LikedPeopleNavigator, FragmentLikedPeopleBinding, LikedPeopleViewModel>() {
    override fun getNavigator(navController: NavController) = LikedPeopleNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_liked_people
    override fun viewModelClass(): Class<LikedPeopleViewModel> = LikedPeopleViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            PersonV2Item.VIEW_TYPE,
            listener = PersonV2Item.Listener { item -> viewModel.openDetail(item.id) })
    )

    private val likedPeopleBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.loadData()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val likedComponent = DaggerLikedComponent.factory().create(appComponent)
        likedComponent.injectLikedPeopleFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context?.registerReceiver(
                likedPeopleBroadcastReceiver,
                IntentFilter(IntentFilterKey.LIKED_PERSON),
                Context.RECEIVER_EXPORTED
            )
        } else {
            @Suppress("UnspecifiedRegisterReceiverFlag")
            context?.registerReceiver(
                likedPeopleBroadcastReceiver,
                IntentFilter(IntentFilterKey.LIKED_PERSON)
            )
        }

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