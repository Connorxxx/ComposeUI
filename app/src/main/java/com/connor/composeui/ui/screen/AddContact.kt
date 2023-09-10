package com.connor.composeui.ui.screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.connor.composeui.models.data.ChildData
import com.connor.composeui.models.data.ContactData
import com.connor.composeui.models.event.AddContactEvent
import com.connor.composeui.models.event.HomeEvent
import com.connor.composeui.ui.theme.md_theme_light_primary
import com.connor.composeui.viewmodels.AddContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContact(
    vm: AddContactViewModel = hiltViewModel(),
    onHomeEvent: (HomeEvent) -> Unit
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val onEvent: (AddContactEvent) -> Unit = vm::onEvent
    if (state.addedContact) onHomeEvent(HomeEvent.Back)
    val childList = arrayListOf(
        ChildData("123456", "connor@qq.com"),
        ChildData("111222", "tian@qq.com")
    )
    val contact = ContactData(
        firstName = "Connor${(0..100).random()}",
        lastName = "Wu",
        imagePath = null,
        child = childList
    )
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    shape = CardDefaults.outlinedShape,
                    colors = CardDefaults.cardColors(),
                    modifier = Modifier
                        .padding(top = 64.dp)
                        .size(120.dp)

                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.CenterHorizontally)
                            .size(64.dp)
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth()
            )
            Row(Modifier.padding(horizontal = 12.dp)) {
                OutlinedTextField(
                    value = state.firstName,
                    onValueChange = { onEvent(AddContactEvent.ChangeFirstName(it)) },
                    label = { Text(text = "First name") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(0.1f))
                OutlinedTextField(
                    value = state.lastName,
                    onValueChange = { onEvent(AddContactEvent.ChangeLastName(it)) },
                    label = { Text(text = "last Name") },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        itemsIndexed(state.phones) { position, text ->
            OutlinedTextField(
                value = text,
                onValueChange = { onEvent(AddContactEvent.PhoneTextChange(position, it)) },//state.phones[position] = it
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.AccountCircle, contentDescription = null) },
                label = { Text(text = "Phone number") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 12.dp)
            )
        }
        item {
            Column(Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = { onEvent(AddContactEvent.AddPhone) }, //if (state.phones.size < 5) state.phones.add("")
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 24.dp)
                ) {
                    Text(text = "Add")
                }
            }
        }
        itemsIndexed(state.emails) { position, text ->
            OutlinedTextField(
                value = text,
                onValueChange = { onEvent(AddContactEvent.EmailTextChange(position, it)) }, //state.emails[position] = it
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                label = { Text(text = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 12.dp)
            )
        }
        item {
            Column(Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = { onEvent(AddContactEvent.AddEmail) }, //if (state.emails.size < 5) state.emails.add("")
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 24.dp)
                ) {
                    Text(text = "Add")
                }
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    onClick = { onEvent(AddContactEvent.Save(contact)) },
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary),
                    elevation = CardDefaults.cardElevation(0.dp),
                    enabled = state.enable,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = "AddContact",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                    )
                }
                Text(text = state.addedContact.toString())
            }
        }
    }

}