package com.learner.funzo

import android.widget.ListAdapter
import android.widget.ListView

object ListHelper {
    fun getListViewSize(myListView: ListView) {
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
