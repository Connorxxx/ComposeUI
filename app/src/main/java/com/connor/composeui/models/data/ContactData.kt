package com.connor.composeui.models.data

data class ContactData(
    val id: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val imagePath: String? = null,
    val child: List<ChildData> = emptyList()
)

data class ChildData(
    val phoneNumber: String = "",
    val email: String = "",
)
