package ru.melonhell.blockbenchidea

import com.intellij.openapi.util.io.toNioPath
import com.intellij.openapi.vfs.VirtualFile
import org.cef.browser.CefBrowser
import org.cef.callback.CefBeforeDownloadCallback
import org.cef.callback.CefDownloadItem
import org.cef.callback.CefDownloadItemCallback
import org.cef.handler.CefDownloadHandler
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class BlockbenchDownloadHandler(
    private val file: VirtualFile
) : CefDownloadHandler {
    override fun onBeforeDownload(
        browser: CefBrowser,
        downloadItem: CefDownloadItem,
        suggestedName: String,
        callback: CefBeforeDownloadCallback,
    ) {
        val showDialog = suggestedName != file.name
        callback.Continue("", showDialog)
    }

    override fun onDownloadUpdated(
        browser: CefBrowser,
        downloadItem: CefDownloadItem,
        callback: CefDownloadItemCallback,
    ) {
        if (!downloadItem.isComplete) return

        if (downloadItem.suggestedFileName == file.name) {
            Files.copy(
                downloadItem.fullPath.toNioPath(),
                file.toNioPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
            Utils.notify("${file.name} successfully saved.")
        }
    }
}