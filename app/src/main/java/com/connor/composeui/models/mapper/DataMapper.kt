package com.connor.composeui.models.mapper

import com.connor.GetContactsWithChildren
import com.connor.composeui.models.data.ChildData
import com.connor.composeui.models.data.ContactData

fun List<GetContactsWithChildren>.mapToContactData() =
    groupBy {
        ContactData(
            firstName = it.firstName,
            lastName = it.lastName,
            imagePath = it.imagePath
        )
    }.map { (data, selectRows) ->
        data.copy(
            child = selectRows.map {
                ChildData(it.phoneNumber ?: "", it.email ?: "")
            }
        )
    }
