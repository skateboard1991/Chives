package com.skateboard.coinsplugin.ui

import com.skateboard.coinsplugin.data.TickerDataEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.table.DefaultTableModel
import javax.swing.table.TableColumn

class BitCoinWindow {

    private lateinit var contentPanel: JPanel

    private lateinit var contentScrollPane: JScrollPane

    private lateinit var coinsTable: JTable

    private val columnList =
        listOf("排名", "名称", "最新价格", "24h的成交额", "流通市值", "流通数量", "总发行量", "最大发行量", "1小时涨跌幅", "24小时涨跌幅", "7天涨跌幅", "更新时间")

    init {
        EventBus.getDefault().register(this)
        val tableModel = coinsTable.model as DefaultTableModel
        for (index in columnList.indices) {
            val column = columnList[index]
            tableModel.addColumn(column)
        }
        coinsTable.autoResizeMode = JTable.AUTO_RESIZE_OFF
    }

    fun getContent(): JPanel = contentPanel

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTickerDataEvent(event: TickerDataEvent) {
        if (coinsTable.rowCount <= 0) {
            for (tickerData in event.tickerDataList) {
                val tableModel = coinsTable.model as DefaultTableModel
                tableModel.addRow(tickerData.toArray())
            }
        } else {
            val tableModel = coinsTable.model as DefaultTableModel
            val rowVector = Vector<Vector<String>>()
            for (tickerData in event.tickerDataList) {
                rowVector.add(tickerData.toVector())
            }
            tableModel.setDataVector(rowVector, Vector(columnList))
            coinsTable.repaint()
        }
        fitTableColumns(coinsTable)
    }

    private fun fitTableColumns(myTable: JTable) {
        val header = myTable.tableHeader
        val rowCount = myTable.rowCount
        val columns = myTable.columnModel.columns
        while (columns.hasMoreElements()) {
            val column = columns.nextElement() as TableColumn
            val col = header.columnModel.getColumnIndex(column.identifier);
            var width = (myTable.tableHeader.defaultRenderer.getTableCellRendererComponent(
                myTable, column.identifier
                , false, false, -1, col
            ).preferredSize.getWidth()).toInt()
            for (row in 0 until rowCount) {
                val preferedWidth = (myTable.getCellRenderer(row, col).getTableCellRendererComponent(
                    myTable,
                    myTable.getValueAt(row, col), false, false, row, col
                ).preferredSize.getWidth()).toInt()
                width = width.coerceAtLeast(preferedWidth)
            }
            header.resizingColumn = column
            column.width = width + myTable.intercellSpacing.width;
        }
    }
}
