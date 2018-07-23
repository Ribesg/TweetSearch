package fr.ribesg.tweetsearch

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

class ResultsActivityUI : AnkoComponent<ResultsActivity> {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun createView(ui: AnkoContext<ResultsActivity>) = with(ui) {

        swipeRefreshLayout = swipeRefreshLayout {
            onRefresh { owner.refresh() }
            recyclerView {
                adapter = owner.adapter
                layoutManager = LinearLayoutManager(ctx)
            }
        }

        swipeRefreshLayout

    }

    fun endRefresh() {
        swipeRefreshLayout.isRefreshing = false
    }

}
