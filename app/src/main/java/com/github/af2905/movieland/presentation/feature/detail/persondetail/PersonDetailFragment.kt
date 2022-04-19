package com.github.af2905.movieland.presentation.feature.detail.persondetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.github.af2905.movieland.helper.navigator.AppNavigator
import com.github.af2905.movieland.presentation.base.compose.BaseComposeFragment
import com.github.af2905.movieland.presentation.feature.detail.persondetail.compose.PersonDetailScreen

class PersonDetailFragment : BaseComposeFragment<AppNavigator, PersonDetailViewModel>() {

    val args: PersonDetailFragmentArgs by navArgs()

    override fun getNavigator(navController: NavController) = AppNavigator(navController)
    override fun viewModelClass() = PersonDetailViewModel::class.java

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