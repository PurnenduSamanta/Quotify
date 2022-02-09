package com.purnendu.viewmodel


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.lifecycle.ViewModelProvider
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private  lateinit var mainViewModel: MainViewModel
    private  val quoteText:TextView
    get() = findViewById(R.id.quoteText)
    private  val quoteAuthor:TextView
    get() = findViewById(R.id.quoteAuthor)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        mainViewModel= ViewModelProvider(this,MainViewModelFactory(WeakReference<Context>(application)))[MainViewModel::class.java]
        setQuote(mainViewModel.getCode())
    }

    private fun setQuote(quote:QuotesModel)
    {
        quoteText.text=quote.text
        quoteAuthor.text=quote.author
    }

    fun onNext(view: View) {
        setQuote(mainViewModel.nextQuote())
    }
    fun onPrevious(view: View) {
        setQuote(mainViewModel.previousQuote())
    }

    fun onShare(view: View) {
        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"${mainViewModel.getCode().text} by ${mainViewModel.getCode().author}")
        startActivity(intent)
    }
}