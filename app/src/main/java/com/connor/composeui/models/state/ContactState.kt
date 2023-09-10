package com.connor.composeui.models.state

import com.connor.composeui.models.data.ContactData
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ContactState(
    val contacts: List<ContactData> = emptyList(),
    val contact: ContactData? = null,
    val selectContact: ContactData = ContactData(),
    val isSheetOpen: Boolean = false,
    val isContactSheetOpen: Boolean = false,
    val error: String = "",

)

data class AddContactState(
    val addedContact: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val phones: PersistentList<String> = persistentListOf(""),
    val emails: PersistentList<String> = persistentListOf(""),
    val enable: Boolean = false
)

fun test() {
    val test: PersistentList<String> = persistentListOf("0")
    test.set(0, "")
}
