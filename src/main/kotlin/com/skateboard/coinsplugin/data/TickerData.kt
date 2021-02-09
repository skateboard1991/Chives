package com.skateboard.coinsplugin.data

import com.skateboard.coinsplugin.util.DateUtil
import java.util.*

data class TickerData(
    var rank: String,
    var name: String,
    var price_usd: String,
    var volume_24h_usd: String,
    var market_cap_usd: String,
    var available_supply: String,
    var total_supply: String,
    var max_supply: String,
    var percent_change_1h: String,
    var percent_change_24h: String,
    var percent_change_7d: String,
    var last_updated: String
) {
    fun toArray(): Array<String> = arrayOf(
        rank,
        name,
        price_usd,
        volume_24h_usd,
        market_cap_usd,
        available_supply,
        total_supply,
        max_supply,
        percent_change_1h,
        percent_change_24h,
        percent_change_7d,
        DateUtil.formatDate(last_updated)
    )

    fun toVector(): Vector<String> {
        val vector = Vector<String>()
        vector.also {
            it.add(rank)
            it.add(name)
            it.add(price_usd)
            it.add(volume_24h_usd)
            it.add(market_cap_usd)
            it.add(available_supply)
            it.add(total_supply)
            it.add(max_supply)
            it.add(percent_change_1h)
            it.add(percent_change_24h)
            it.add(percent_change_7d)
            it.add(DateUtil.formatDate(last_updated))
        }
        return vector
    }
}


data class TickerDataEvent(val tickerDataList: List<TickerData>)