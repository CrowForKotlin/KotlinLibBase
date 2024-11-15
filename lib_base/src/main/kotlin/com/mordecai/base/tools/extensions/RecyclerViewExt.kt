package com.mordecai.base.tools.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.findCenterViewPosition(): Int {
    return getChildAdapterPosition(findChildViewUnder(width / 2f, height / 2f) ?: return RecyclerView.NO_POSITION)
}

fun RecyclerView.findViewPosition(x: Float, y: Float): Int {
     return getChildAdapterPosition(findChildViewUnder(x, y) ?: return RecyclerView.NO_POSITION)
}

fun RecyclerView.findFisrtVisibleViewPosition(): Int {
    var pos = -1
    if (layoutManager is LinearLayoutManager) {
        pos = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    }
    return pos
}

fun RecyclerView.findFirstCompletelyVisibleItemPosition(): Int {
    var pos = -1
    if (layoutManager is LinearLayoutManager) {
        pos = (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
    }
    return pos
}

fun RecyclerView.findLastCompletelyVisibleItemPosition(): Int {
    var pos = -1
    if (layoutManager is LinearLayoutManager) {
        pos = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
    }
    return pos
}

fun RecyclerView.findLastVisibleViewPosition(): Int {
    var pos = -1
    if ( layoutManager is LinearLayoutManager) {
        pos = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
    }
    return pos
}