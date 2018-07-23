package fr.ribesg.tweetsearch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import org.jetbrains.anko.*
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity() {

    private lateinit var ui: SearchActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createUI()
        bindTextChanges()
        bindSearchTriggers()
    }

    private fun createUI() {
        ui = SearchActivityUI().apply {
            setContentView(this@SearchActivity)
        }
    }

    private fun bindTextChanges() {
        RxTextView
            .textChanges(ui.searchField)
            .subscribe(::onSearchFieldTextChanged)
    }

    private fun bindSearchTriggers() {
        RxTextView
            .editorActionEvents(ui.searchField)
            .filter { event ->
                event.actionId() == EditorInfo.IME_NULL
                && event.keyEvent() != null
                && event.keyEvent()!!.action == KeyEvent.ACTION_DOWN
            }
            .throttleFirst(2, TimeUnit.SECONDS)
            .subscribe { onSearchTriggered() }
        RxView
            .clicks(ui.searchButton)
            .throttleFirst(2, TimeUnit.SECONDS)
            .subscribe { onSearchTriggered() }
    }

    private fun onSearchFieldTextChanged(text: CharSequence) {
        ui.searchButton.isEnabled = text.isNotBlank()
    }

    private fun onSearchTriggered() {
        if (ui.searchButton.isEnabled) {
            startActivity<ResultsActivity>("query" to ui.searchField.text)
        }
    }

}
