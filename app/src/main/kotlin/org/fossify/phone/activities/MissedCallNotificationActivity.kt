package org.fossify.phone.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import org.fossify.commons.extensions.notificationManager
import org.fossify.phone.helpers.MISSED_CALL_BACK
import org.fossify.phone.helpers.MISSED_CALL_MESSAGE

class MissedCallNotificationActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val phoneNumber = intent.extras?.getString("phoneNumber") ?: return
        val notificationId = intent.extras?.getInt("notificationId", -1) ?: return

        when (intent.action) {
            MISSED_CALL_BACK -> phoneNumber.let {
                Intent(Intent.ACTION_CALL).apply {
                    data = Uri.fromParts("tel", it, null)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            }

            MISSED_CALL_MESSAGE -> phoneNumber.let {
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.fromParts("sms", it, null)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            }

            else -> null
        }?.let {
            startActivity(it)
            notificationManager.cancel(notificationId)
        }

        finish()
    }
}