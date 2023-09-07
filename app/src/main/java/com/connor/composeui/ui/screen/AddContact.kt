package com.connor.composeui.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.connor.ContactEnitity
import com.connor.composeui.models.event.AddContactEvent
import com.connor.composeui.models.event.HomeEvent
import com.connor.composeui.viewmodels.AddContactViewModel

@Composable
fun AddContact(
    vm: AddContactViewModel = hiltViewModel(),
    onHomeEvent: (HomeEvent) -> Unit
) {
    val state by vm.state.collectAsState()
    val onEvent: (AddContactEvent) -> Unit = vm::onEvent
    if (state.addedContact) onHomeEvent(HomeEvent.Back)
    val contact = ContactEnitity(
        id = 0,
        firstName = "Connor",
        lastName = "Wu",
        phoneNumber = "123456789",
        email = "connor@gmail.com",
        createdAt = 0,
        imagePath = null
    )
    Column(Modifier.fillMaxSize()) {
        Button(onClick = { onEvent(AddContactEvent.Save(contact)) }) {
            Text(text = "AddContact")
        }
        Text(text = state.addedContact.toString())
    }

}