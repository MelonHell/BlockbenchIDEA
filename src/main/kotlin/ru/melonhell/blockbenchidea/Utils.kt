package ru.melonhell.blockbenchidea

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications

object Utils {
    fun notify(text: String, type: NotificationType = NotificationType.INFORMATION) {
        Notifications.Bus.notify(
            Notification(
                "BlockbenchIDEA",
                "BlockbenchIDEA",
                text,
                type
            )
        )
    }
}