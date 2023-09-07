package com.connor.composeui.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.connor.composeui.models.event.ContactEvent
import com.connor.composeui.models.event.HomeEvent
import com.connor.composeui.models.state.ContactState
import com.connor.composeui.ui.Screen
import com.connor.composeui.viewmodels.ContactViewModel

@Composable
fun Contact(
    vm: ContactViewModel = hiltViewModel(),
    onHomeEvent: (HomeEvent) -> Unit
) {
    val state by vm.state.collectAsState()
    Column(
        Modifier.fillMaxSize()
    ) {
        ContactList(state, onEvent = vm::onEvent, onHomeEvent = onHomeEvent)
        ModalSheet(state = state, onEvent = vm::onEvent)
    }
}

@Composable
fun ContactList(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    onHomeEvent: (HomeEvent) -> Unit) {
    Scaffold(
        floatingActionButton = { FabAddContact(onEvent = onHomeEvent) }
    ) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val lazyListState = rememberLazyListState()
            LazyColumn(
                state = lazyListState
            ) {
                items(state.contacts) { contact ->
                    Text(text = "${contact.id} ${contact.firstName} ${contact.lastName}",
                        Modifier
                            .padding(25.dp)
                            .clickable {
                                onEvent(ContactEvent.OnSheetShow)
                            })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalSheet(state: ContactState, onEvent: (ContactEvent) -> Unit) {
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    var edgeToEdgeEnabled by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    if (state.isSheetOpen) {
        val windowInsets = if (edgeToEdgeEnabled)
            WindowInsets(0) else BottomSheetDefaults.windowInsets
        ModalBottomSheet(
            onDismissRequest = { onEvent(ContactEvent.OnSheetDismiss) },
            sheetState = bottomSheetState,
            windowInsets = windowInsets,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = { onEvent(ContactEvent.OnSheetDismiss) }) {
                Text(text = "Dismiss")
            }
        }
    }
}

@Composable
private fun FabAddContact(onEvent: (HomeEvent) -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onEvent(HomeEvent.NavigateTo(Screen.AddContact.route)) },
        icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
        text = { Text(text = "Extended FAB") },
    )
}