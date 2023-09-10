package com.connor.composeui.models.repo

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.connor.ChildEnitity
import com.connor.ContactEnitity
import com.connor.Database
import com.connor.composeui.models.data.ChildData
import com.connor.composeui.models.data.ContactData
import com.connor.composeui.utils.logCat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import javax.inject.Inject

class ContactSqlRepository @Inject constructor(private val database: Database) {

    val getContacts2 = database.contactQueries.getContacts()
        .asFlow().mapToList(Dispatchers.IO)

    val getContacts = database.contactQueries.getContactsWithChildren()
        .asFlow().mapToList(Dispatchers.IO)

    suspend fun insertContact(contact: ContactData) = withContext(Dispatchers.IO) {
        with(database.contactQueries) {
            transactionWithResult {
                insertContact(
                    firstName = contact.firstName,
                    lastName = contact.lastName,
                    createdAt = Clock.System.now().toEpochMilliseconds(),
                    imagePath = null
                )
                val id = lastContactId().executeAsList().last()
                contact.child.forEach {
                    insertChild(
                        contactId = id,
                        phoneNumber = it.phoneNumber,
                        email = it.email
                    )
                }
                id
            }
        }
    }
}