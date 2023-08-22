package com.learner.funzo.util

import android.widget.ListAdapter
import android.widget.ListView

/**
 * A utility class for working with ListViews.
 */
object ListHelper {
    /**
     * Calculates and adjusts the height of a ListView to accommodate its content.
     *
     * @param myListView The ListView whose height needs to be adjusted.
     */
    fun adjustListViewHeight(myListView: ListView) {
        val myListAdapter : ListAdapter = myListView.adapter ?: return
        var totalHeight = 0
        for(size in 0 until myListAdapter.count) {
            val listItem = myListAdapter.getView(size, null, myListView)
            listItem.measure(0,0)
            totalHeight += listItem.measuredHeight
        }

        val params = myListView.layoutParams
        params.height = totalHeight + myListView.dividerHeight * (myListAdapter.count - 1 )
        myListView.layoutParams = params
    }
}
