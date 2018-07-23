package fr.ribesg.tweetsearch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.*
import org.jetbrains.anko.*
import java.util.*

class ResultsActivity : AppCompatActivity() {

    lateinit var adapter: TweetTimelineRecyclerViewAdapter
        private set

    private lateinit var ui: ResultsActivityUI

    private lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query = intent.getCharSequenceExtra("query").toString()
        createTweetsAdapter()
        createUI()
    }

    fun refresh() {
        adapter.refresh(object : Callback<TimelineResult<Tweet>>() {

            override fun success(result: Result<TimelineResult<Tweet>>?) {
                ui.endRefresh()
            }

            override fun failure(exception: TwitterException?) {
                longToast(R.string.activity_results_error)
                ui.endRefresh()
            }

        })
    }

    private fun createTimeline() =
        SearchTimeline.Builder()
            .query(query)
            .languageCode(Locale.getDefault().language)
            .build()

    private fun createTweetsAdapter() {
        Twitter.initialize(this)
        adapter = TweetTimelineRecyclerViewAdapter.Builder(this)
            .setTimeline(createTimeline())
            .setViewStyle(R.style.tw__TweetLightStyle)
            .build()
    }

    private fun createUI() {
        ui = ResultsActivityUI().apply {
            setContentView(this@ResultsActivity)
        }
        val title = ctx.getString(R.string.activity_results_title, query)
        supportActionBar!!.title = title
    }

}
