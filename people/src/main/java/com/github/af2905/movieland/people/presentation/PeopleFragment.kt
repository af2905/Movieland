package com.github.af2905.movieland.people.presentation

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.AppBarStateChangeListener
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.decorator.VerticalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.PersonV2Item
import com.github.af2905.movieland.people.R
import com.github.af2905.movieland.people.databinding.FragmentPeopleBinding
import com.google.android.material.appbar.AppBarLayout

class PeopleFragment :
    BaseFragment<PeopleNavigator, FragmentPeopleBinding, PeopleViewModel>() {

    override fun getNavigator(navController: NavController) = PeopleNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_people
    override fun viewModelClass(): Class<PeopleViewModel> = PeopleViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            PersonV2Item.VIEW_TYPE,
            listener = PersonV2Item.Listener { item -> viewModel.openDetail(item.id) })
    )

    private val appBarStateChangeListener = object : AppBarStateChangeListener() {
        override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
            when (state) {
                State.COLLAPSED -> {
                    val typedValue = TypedValue()
                    requireActivity().theme.resolveAttribute(R.attr.colorSurface, typedValue, true)
                    binding.toolbar.background = ColorDrawable(typedValue.data)
                }
                State.IDLE -> binding.toolbar.background = ColorDrawable(Color.TRANSPARENT)
                else -> Unit
            }
        }

        override fun onScrolled(state: State, dy: Int) {
            //unused
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*val appComponent = CoreComponentProvider.getAppComponent(context)
        val popularPeopleComponent = DaggerPeopleComponent.factory().create(appComponent)
        popularPeopleComponent.injectPeopleFragment(this)*/
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        binding.appBar.addOnOffsetChangedListener { _, verticalOffset ->
            binding.peopleSwipeRefreshLayout.isEnabled = verticalOffset >= 0
        }

        binding.appBar.apply {
            removeOnOffsetChangedListener(appBarStateChangeListener)
            addOnOffsetChangedListener(appBarStateChangeListener)
        }

        binding.peopleSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            finishRefresh()
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
                    is PeopleContract.Effect.OpenPersonDetail -> handleEffect(effect.navigator)
                }
            }
        }
    }

    private fun initToolbar() {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }

    private fun finishRefresh() {
        binding.peopleSwipeRefreshLayout.isRefreshing = false
    }
}
