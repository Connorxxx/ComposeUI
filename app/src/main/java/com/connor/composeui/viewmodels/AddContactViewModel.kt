package com.connor.composeui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connor.composeui.models.event.AddContactEvent
import com.connor.composeui.models.event.ContactEvent
import com.connor.composeui.models.repo.ContactSqlRepository
import com.connor.composeui.models.state.AddContactState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val contactSqlRepository: ContactSqlRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddContactState())
    val state = _state.asStateFlow()

    fun onEvent(event: AddContactEvent) {
        when (event) {
            is AddContactEvent.Save -> {
                viewModelScope.launch {
                    contactSqlRepository.insertContact(event.contact)
                }
                _state.update {
                    it.copy(addedContact = true)
                }
            }
        }
    }
}