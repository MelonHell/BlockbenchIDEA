package ru.melonhell.blockbenchidea

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.readText
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.handler.CefLoadHandler
import org.cef.network.CefRequest

class BlockbenchLoadHandler(
    private val file: VirtualFile,
) : CefLoadHandler {
    override fun onLoadEnd(
        browser: CefBrowser,
        frame: CefFrame,
        httpStatusCode: Int,
    ) {
        val jsonObject = JsonObject(
            mapOf(
                "path" to JsonPrimitive(file.path),
                "content" to JsonPrimitive(file.readText())
            )
        )
        browser.executeJavaScript(
            """
            loadModelFile(${jsonObject});
            document.getElementById('tab_bar').remove();
            document.getElementById('web_download_button').remove();
            Prop.show_left_bar = false;
            Prop.show_right_bar = false;
            resizeWindow();
            """.trimIndent(), browser.url, 0
        )
    }

    override fun onLoadingStateChange(
        browser: CefBrowser,
        isLoading: Boolean,
        canGoBack: Boolean,
        canGoForward: Boolean,
    ) = Unit

    override fun onLoadStart(
        browser: CefBrowser,
        frame: CefFrame,
        transitionType: CefRequest.TransitionType,
    ) = Unit

    override fun onLoadError(
        browser: CefBrowser,
        frame: CefFrame,
        errorCode: CefLoadHandler.ErrorCode,
        errorText: String,
        failedUrl: String,
    ) = Unit
}
