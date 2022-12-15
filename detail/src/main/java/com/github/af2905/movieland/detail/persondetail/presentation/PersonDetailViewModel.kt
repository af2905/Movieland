package com.github.af2905.movieland.detail.persondetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.ErrorHandler
import com.github.af2905.movieland.core.common.effect.ToastMessage
import com.github.af2905.movieland.detail.usecase.GetPersonDetail
import com.github.af2905.movieland.detail.usecase.params.PersonDetailParams
import javax.inject.Inject

class PersonDetailViewModel @Inject constructor(
    private  val personId: Int,
    private val getPersonDetail: GetPersonDetail
) : ViewModel() {

    val container: Container<PersonDetailContract.State, PersonDetailContract.Effect> =
        Container(viewModelScope, PersonDetailContract.State.Loading)

    val personDetailClickListener = {
        container.intent {
            container.reduce {
                if (this is PersonDetailContract.State.Content) {
                    PersonDetailContract.State.Content(
                        personDetailItem = this.personDetailItem.copy(liked = !this.personDetailItem.liked)
                    )
                } else {
                    this
                }
            }
        }
    }

    val errorClickListener = { loadData() }

    init {
        loadData()
    }

    private fun loadData() {
        container.intent {
            try {
                val result = getPersonDetail(PersonDetailParams(personId)).getOrThrow()
                container.reduce {
                    PersonDetailContract.State.Content(result)
                }
            } catch (e: Exception) {
                container.reduce { PersonDetailContract.State.Error(e) }
                container.intent {
                    container.postEffect(
                        PersonDetailContract.Effect.ShowFailMessage(
                            ToastMessage(ErrorHandler.handleError(e))
                        )
                    )
                }
            }
        }
    }
}