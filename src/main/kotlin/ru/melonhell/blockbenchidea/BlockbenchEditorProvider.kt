package ru.melonhell.blockbenchidea

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.FileEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class BlockbenchEditorProvider : FileEditorProvider {
    override fun accept(project: Project, file: VirtualFile): Boolean {
        return file.extension.equals("bbmodel", ignoreCase = true)
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        return BlockbenchEditor(project, file)
    }

    override fun getEditorTypeId(): String = "blockbench-editor"

    override fun getPolicy(): FileEditorPolicy = FileEditorPolicy.PLACE_BEFORE_DEFAULT_EDITOR
}
