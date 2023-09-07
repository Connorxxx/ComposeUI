package com.connor.composeui.models.event

sealed interface HomeEvent {
    data class NavigateTo(val route: String) : HomeEvent
    data object Back : HomeEvent
}