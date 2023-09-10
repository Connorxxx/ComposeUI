package com.connor.composeui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connor.composeui.models.data.ChildData
import com.connor.composeui.models.data.ContactData
import com.connor.composeui.models.event.AddContactEvent
import com.connor.composeui.models.repo.ContactSqlRepository
import com.connor.composeui.models.state.AddContactState
import com.connor.composeui.utils.logCat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val contactSqlRepository: ContactSqlRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AddContactState())
    val state = _state.asStateFlow()

    fun onEvent(event: AddContactEvent) {
        when (event) {
            is AddContactEvent.Save -> {
                viewModelScope.launch {
                    val child = maxOf(_state.value.phones.size,_state.value.emails.size).let {
                        (0..<it).asIterable().map {i ->
                            ChildData(
                                _state.value.phones.getOrElse(i) { "" },
                                _state.value.emails.getOrElse(i) { "" },
                                )
                        }
                    }
                    val contact = ContactData(
                        firstName = _state.value.firstName,
                        lastName = _state.value.lastName,
                        imagePath = null,
                        child = child
                    )
                    contactSqlRepository.insertContact(contact).logCat()
                    _state.update {
                        it.copy(addedContact = true)
                    }
                }
            }

            is AddContactEvent.ChangeFirstName -> {
                _state.update {
                    it.copy(firstName = event.name,
                        enable = (event.name.isNotEmpty() && it.phones.any {s -> s.isNotEmpty() }))
                }
            }

            is AddContactEvent.ChangeLastName -> {
                _state.update {
                    it.copy(lastName = event.name)
                }
            }

            AddContactEvent.AddPhone -> {
                _state.update {
                    it.copy(phones = it.phones.add(""))
                }
            }

            AddContactEvent.AddEmail -> {
                _state.update {
                    it.copy(emails = it.emails.add(""))
                }
            }

            is AddContactEvent.PhoneTextChange -> {
                _state.update {
                    it.copy(
                        phones = it.phones.set(event.position, event.phone),
                        enable = (it.firstName.isNotEmpty() && event.phone.isNotEmpty())
                    )
                }
            }

            is AddContactEvent.EmailTextChange -> {
                _state.update {
                    it.copy(emails = it.emails.set(event.position, event.email))
                }
            }
        }
    }
}