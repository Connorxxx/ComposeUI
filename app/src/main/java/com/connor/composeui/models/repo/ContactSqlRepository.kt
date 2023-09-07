package com.connor.composeui.models.repo

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.connor.ContactEnitity
import com.connor.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import javax.inject.Inject

class ContactSqlRepository @Inject constructor(private val database: Database) {

    val getContacts = database.billsQueries.getContacts()
        .asFlow().mapToList(Dispatchers.IO)

    suspend fun insertContact(contact: ContactEnitity) = withContext(Dispatchers.IO) {
        database.billsQueries.insertContact(
            id = null,
            firstName = contact.firstName,
            lastName = contact.lastName,
            phoneNumber = contact.phoneNumber,
            email = contact.email,
            createdAt = Clock.System.now().toEpochMilliseconds(),
            imagePath = null
        )
    }




}