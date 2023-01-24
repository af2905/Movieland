package com.github.af2905.movieland.core.base

import android.content.Context
import android.widget.Toast
import com.github.af2905.movieland.core.base.navigator.Navigator

import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.effect.ToastMessage

interface Base<N : Navigator> {

    val navigator: N

    fun handleEffect(effect: UiEffect)
}

class BaseImpl<N : Navigator>(navigatorFactory: () -> N, private val requireContext: () -> Context) :
    Base<N> {

    override val navigator: N by lazy(navigatorFactory)

    override fun handleEffect(effect: UiEffect) {
        when (effect) {
            is ToastMessage -> Toast.makeText(
                requireContext(), effect.text.asCharSequence(requireContext()), Toast.LENGTH_SHORT
            ).show()

            is Navigate -> with(effect) { navigate(navigator) }

            else -> throw IllegalStateException("Unhandled effect ${effect::class.java.simpleName}")
        }
    }
}