package com.connor.composeui.models.state

import com.connor.ContactEnitity

data class ContactState(
    val contacts: List<ContactEnitity> = emptyList(),
    val contact: ContactEnitity? = null,
    val isSheetOpen: Boolean = false,
    val isContactSheetOpen: Boolean = false,
    val error: String = "",

)

data class AddContactState(
    val addedContact: Boolean = false
)
