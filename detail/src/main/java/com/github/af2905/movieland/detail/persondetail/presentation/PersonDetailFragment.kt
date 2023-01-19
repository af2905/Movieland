package com.github.af2905.movieland.detail.persondetail.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.IntentFilterKey
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.decorator.VerticalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.PersonMovieCreditsCastItem
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.detail.R
import com.github.af2905.movieland.detail.databinding.FragmentPersonDetailBinding
import com.github.af2905.movieland.detail.persondetail.PersonDetailNavigator
import com.github.af2905.movieland.detail.persondetail.di.DaggerPersonDetailComponent

class PersonDetailFragment :
    BaseFragment<PersonDetailNavigator, FragmentPersonDetailBinding, PersonDetailViewModel>() {

    override fun layoutRes(): Int = R.layout.fragment_person_detail
    override fun getNavigator(navController: NavController) = PersonDetailNavigator(navController)
    override fun viewModelClass() = PersonDetailViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            viewType = PersonMovieCreditsCastItem.VIEW_TYPE,
            listener = PersonMovieCreditsCastItem.Listener { item ->
                viewModel.navigateToMovieDetail(item.id)
            }
        )
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val detailComponent = DaggerPersonDetailComponent.factory().create(
            coreComponent = appComponent,
            personId = requireNotNull(arguments?.getInt(PERSON_ID_ARG))
        )
        detailComponent.injectPersonDetailFragment(this)
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

        initToolbar()

        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect { effect ->
                when (effect) {
                    is PersonDetailContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                    is PersonDetailContract.Effect.OpenPreviousScreen -> handleEffect(effect.navigator)
                    is PersonDetailContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                    is PersonDetailContract.Effect.LikeClicked -> {
                        context?.sendBroadcast(Intent(IntentFilterKey.LIKED_PERSON))
                    }
                }
            }
        }
    }

    private fun initToolbar() {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(binding.personDetailToolbar.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }

    companion object {
        const val PERSON_ID_ARG =
            "com.github.af2905.movieland.detail.persondetail.presentation.PERSON_ID_ARG"
    }
}