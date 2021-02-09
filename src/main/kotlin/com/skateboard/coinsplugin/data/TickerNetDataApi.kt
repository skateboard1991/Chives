package com.skateboard.coinsplugin.data

import com.google.gson.reflect.TypeToken
import com.skateboard.coinsplugin.util.GsonUtil
import org.apache.http.HttpHeaders
import org.apache.http.NameValuePair
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import java.io.IOException
import java.lang.Exception
import java.net.URISyntaxException
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


object TickerNetDataApi {

    private var isStart = false

    private var lastTickerDataList: List<TickerData>? = null

    fun scheduleGetTickerData() {
        if (!isStart) {
            val scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
            scheduledExecutorService.scheduleAtFixedRate(Runnable {
                getTickerData()
            }, 0, 30, TimeUnit.SECONDS)
            isStart = true
        }
    }

    fun getTickerData() {
        val uri = "https://fxhapi.feixiaohao.com/public/v1/ticker"
        val paratmers: MutableList<NameValuePair> =
            ArrayList()
        paratmers.add(BasicNameValuePair("start", "0"))
        paratmers.add(BasicNameValuePair("limit", "100"))
        try {
            val tickerDataList = makeAPICall(uri, paratmers)
            tickerDataList?.let {
                val lastData = lastTickerDataList
                if (lastData == null || lastData[0].last_updated > it[0].last_updated) {
                    org.greenrobot.eventbus.EventBus.getDefault().post(TickerDataEvent(tickerDataList))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Throws(URISyntaxException::class, IOException::class)
    fun makeAPICall(uri: String, parameters: List<NameValuePair?>?): List<TickerData>? {
        var responseContent: String? = ""
        val query = URIBuilder(uri)
        query.addParameters(parameters)
        val client = HttpClients.createDefault()
        val request = HttpGet(query.build())
        request.setHeader(HttpHeaders.ACCEPT, "application/json")
        val response = client.execute(request)
        response.use { response ->
            println(response.statusLine)
            val entity = response.entity
            responseContent = EntityUtils.toString(entity)
            EntityUtils.consume(entity)
        }
        return responseContent?.let {
            GsonUtil.fromJson(it, object : TypeToken<List<TickerData>>() {
            }.type)
        }
    }


}