package com.connor.composeui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connor.composeui.models.data.ChildData
import com.connor.composeui.models.data.ContactData
import com.connor.composeui.models.event.ContactEvent
import com.connor.composeui.models.mapper.mapToContactData
import com.connor.composeui.models.repo.ContactSqlRepository
import com.connor.composeui.models.state.ContactState
import com.connor.composeui.utils.logCat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactSqlRepository: ContactSqlRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ContactState())
    val state = _state.asStateFlow()

    init {
        contactSqlRepository.getContacts.onEach { list ->
            _state.update {
                it.copy(contacts = list.mapToContactData())
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ContactEvent) {
        when (event) {
            is ContactEvent.OnSheetShow -> {
                _state.update {
                    it.copy(isSheetOpen = true)
                }
            }

            is ContactEvent.OnSheetDismiss -> {
                _state.update {
                    it.copy(isSheetOpen = false)
                }
            }

            else -> {}
        }
    }
}