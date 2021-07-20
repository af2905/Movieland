package com.github.af2905.movieland.helper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.af2905.movieland.R
import com.github.af2905.movieland.base.BaseNavigator
import javax.inject.Inject

class MainNavigator @Inject constructor(
    private val fragmentManager: FragmentManager
) : BaseNavigator() {

    override fun forward(fragment: Fragment, tag: String) {
        fragmentManager.beginTransaction()
            .replace(R.id.baseContainer, fragment, tag)
            .addToBackStack(tag)
            .setReorderingAllowed(true)
            .commit()
    }

    public override fun back() = fragmentManager.popBackStack()

    override fun backTo(tag: String) = fragmentManager.popBackStack(tag, 0)
}