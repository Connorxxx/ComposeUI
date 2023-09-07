package com.connor.composeui.models.event

import com.connor.ContactEnitity

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
    data class Save(val contact: ContactEnitity) : AddContactEvent
}