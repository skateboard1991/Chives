package com.skateboard.coinsplugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.skateboard.coinsplugin.data.TickerNetDataApi

class BitCoinToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val bitCoinWindow = BitCoinWindow()
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content =
            contentFactory.createContent(bitCoinWindow.getContent(), "", false)
        toolWindow.contentManager.addContent(content)
        TickerNetDataApi.scheduleGetTickerData()
    }

}