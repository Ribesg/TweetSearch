package fr.ribesg.tweetsearch

import android.text.InputType
import android.view.Gravity
import android.widget.*
import org.jetbrains.anko.*

class SearchActivityUI : AnkoComponent<SearchActivity> {

    lateinit var searchField: EditText
        private set

    lateinit var searchButton: Button
        private set

    override fun createView(ui: AnkoContext<SearchActivity>) = with(ui) {

        verticalLayout {

            padding = dip(24)

            searchField = editText {
                id = R.id.activity_search_field
                hintResource = R.string.activity_search_hint
                textSize = 18f
                maxLines = 1
                inputType = InputType.TYPE_CLASS_TEXT
            }.lparams {
                width = LinearLayout.LayoutParams.MATCH_PARENT
            }

            searchButton = button {
                id = R.id.activity_search_button
                textResource = R.string.activity_search_button
                isEnabled = searchField.text.isNotBlank()
            }.lparams {
                gravity = Gravity.END
            }

        }

    }

}
