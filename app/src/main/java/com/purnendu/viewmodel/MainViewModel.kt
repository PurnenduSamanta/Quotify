package com.purnendu.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import java.lang.ref.WeakReference


class MainViewModel(private val context: WeakReference<Context>) : ViewModel() {

    private var quotesList = emptyArray<QuotesModel>()
    private var index = 0

    init {
        quotesList = loadQuotesFromJson()
    }

    private fun loadQuotesFromJson(): Array<QuotesModel> {

        val inputStream = context.get()!!.assets.open("quotes.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json, Array<QuotesModel>::class.java)
    }

    fun getCode() = quotesList[index]

    fun nextQuote():QuotesModel {
        index++
        index %= quotesList.size
        return quotesList[index]
    }

    fun previousQuote():QuotesModel {
        index--
        if(index==-1)
            index=quotesList.size-1
        index %= quotesList.size
        return quotesList[index]
    }

}