package com.example.audioflix

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LeftSpacingItemDecoration(private val padding: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = padding
        var position = parent.getChildAdapterPosition(view)
        if(position == state.itemCount - 1) {
            outRect.right = padding
        }
    }

}