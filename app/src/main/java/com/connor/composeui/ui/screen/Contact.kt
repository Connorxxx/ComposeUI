package com.connor.composeui.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.connor.composeui.R
import com.connor.composeui.models.data.ChildData
import com.connor.composeui.models.data.ContactData
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
    val state by vm.state.collectAsStateWithLifecycle()
    ContactList(state, onEvent = vm::onEvent, onHomeEvent = onHomeEvent)
    ModalSheet(state = state, onEvent = vm::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ContactList(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    onHomeEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = { FabAddContact(onEvent = onHomeEvent) }
    ) {
        val lazyListState = rememberLazyListState()
        LazyColumn(
            Modifier.padding(it),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.contacts) { contact ->
                Card(modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(ContactEvent.OnSheetShow(contact)) }
                ) {
                    Text(
                        text = "${contact.firstName} ${contact.lastName}",
                        Modifier.padding(top = 12.dp, bottom = 12.dp)
                    )
                    contact.child.forEach { child ->
                        Text(
                            text = "${child.phoneNumber} ${child.email}"
                        )
                    }
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
            Column {
                Spacer(modifier = Modifier.height(32.dp))
                Image(
                    painter = painterResource(id = R.drawable.icon_person),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(120.dp)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = state.selectContact.firstName,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(28f, type = TextUnitType.Sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                state.selectContact.child.forEach {
                    Text(
                        text = it.phoneNumber,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(28f, type = TextUnitType.Sp),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Text(text = "${state.selectContact.id}")
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