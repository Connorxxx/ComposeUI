package com.connor.composeui.models.event

import com.connor.ContactEnitity
import com.connor.composeui.models.data.ContactData

sealed interface ContactEvent {
    data object OnSheetShow : ContactEvent
    data object OnSheetDismiss : ContactEvent
    data class PhotoPick(val path: String) : ContactEvent
    data object OnAddPhoto : ContactEvent
    data class Select(val contact: ContactEnitity) : ContactEvent
    data class Edit(val contact: ContactEnitity) : ContactEvent
    data object Delete : ContactEvent
}

sealed interface AddContactEvent {
    data class Save(val contact: ContactData) : AddContactEvent
    data class ChangeFirstName(val name: String) : AddContactEvent
    data class ChangeLastName(val name: String) : AddContactEvent
    data object AddPhone : AddContactEvent
    data object AddEmail : AddContactEvent
    data class PhoneTextChange(val position: Int, val phone: String) : AddContactEvent
    data class EmailTextChange(val position: Int, val email: String) : AddContactEvent
}