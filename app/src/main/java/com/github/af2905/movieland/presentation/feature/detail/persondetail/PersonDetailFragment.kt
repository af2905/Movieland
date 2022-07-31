package com.github.af2905.movieland.presentation.feature.detail.persondetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.github.af2905.movieland.CoreComponentProvider
import com.github.af2905.movieland.core.base.navigator.AppNavigator
import com.github.af2905.movieland.core.compose.BaseComposeFragment
import com.github.af2905.movieland.presentation.feature.detail.persondetail.compose.PersonDetailScreen
import com.github.af2905.movieland.presentation.feature.detail.persondetail.di.DaggerPersonDetailComponent

class PersonDetailFragment : BaseComposeFragment<AppNavigator, PersonDetailViewModel>() {

    val args: PersonDetailFragmentArgs by navArgs()

    override fun getNavigator(navController: NavController) = AppNavigator(navController)
    override fun viewModelClass() = PersonDetailViewModel::class.java

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val detailComponent = DaggerPersonDetailComponent.factory().create(appComponent, args)
        detailComponent.injectPersonDetailFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent { PersonDetailScreen(viewModel) }
        }
    }
}