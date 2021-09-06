package com.github.af2905.movieland.helper.navigator

import androidx.navigation.navOptions
import com.github.af2905.movieland.R

object NavOptions {

    fun optionsAnimSlide() = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }
}