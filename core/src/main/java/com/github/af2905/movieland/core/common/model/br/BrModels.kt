package com.github.af2905.movieland.core.common.model.br

import androidx.lifecycle.ViewModel
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model

class BrItem(override val id: Int) : Model(id)

class BrViewModel : ViewModel()

class BrListener : ItemDelegate.Listener
