package ru.melonhell.blockbenchidea

import com.intellij.ide.actions.SaveAllAction
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ex.AnActionListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.jcef.JBCefBrowser
import com.intellij.ui.jcef.JBCefClient
import java.beans.PropertyChangeListener
import javax.swing.JComponent

class BlockbenchEditor(
    project: Project,
    private val file: VirtualFile,
) : UserDataHolderBase(), FileEditor {
    private val jbBrowser: JBCefBrowser = JBCefBrowser()
    private val client: JBCefClient = jbBrowser.jbCefClient
    private val browser = jbBrowser.cefBrowser

    init {
        client.addLoadHandler(BlockbenchLoadHandler(file), browser)
        client.addDownloadHandler(BlockbenchDownloadHandler(file), browser)
        jbBrowser.loadURL("https://web.blockbench.net/")
    }

    override fun getComponent(): JComponent = jbBrowser.component
    override fun getPreferredFocusedComponent(): JComponent? = jbBrowser.component
    override fun getName(): String = "Blockbench"
    override fun setState(state: FileEditorState) = Unit
    override fun isModified(): Boolean = false
    override fun isValid(): Boolean = true
    override fun addPropertyChangeListener(listener: PropertyChangeListener) = Unit
    override fun removePropertyChangeListener(listener: PropertyChangeListener) = Unit
    override fun dispose() = jbBrowser.dispose()
    override fun getFile(): VirtualFile = file
}
