package com.connor.composeui.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.connor.composeui.models.data.ContactData

class SqlWork(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val data = inputData.keyValueMap["contact"] as? ContactData ?: return Result.failure()
        return Result.success()
    }
}