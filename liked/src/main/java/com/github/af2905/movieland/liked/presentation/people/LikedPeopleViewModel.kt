package com.github.af2905.movieland.liked.presentation.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.model.item.PersonV2Item
import com.github.af2905.movieland.core.common.model.item.SimpleTextItem
import com.github.af2905.movieland.liked.R
import com.github.af2905.movieland.liked.domain.GetAllSavedPeople


import javax.inject.Inject

class LikedPeopleViewModel @Inject constructor(
    private val getAllSavedPeople: GetAllSavedPeople
) : ViewModel() {

    val container: Container<LikedPeopleContract.State, LikedPeopleContract.Effect> =
        Container(viewModelScope, LikedPeopleContract.State.Loading)

    init {
        loadData()
    }

    fun loadData() {
        container.intent {
            val savedPeople = getAllSavedPeople.invoke(Unit).getOrNull().orEmpty()

            val list = if (savedPeople.isNotEmpty()) {
                savedPeople.map { PersonV2Item(it) }
            } else {
                listOf(SimpleTextItem(res = R.string.liked_people_empty_list_text))
            }

            container.reduce {
                LikedPeopleContract.State.Content(list = list)
            }
        }
    }

    fun openDetail(itemId: Int) {
        container.intent {
            container.postEffect(LikedPeopleContract.Effect.OpenPersonDetail(Navigate { navigator ->
                (navigator as LikedPeopleNavigator).forwardPersonDetail(itemId)
            }))
        }
    }
}