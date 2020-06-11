package com.example.audioflix

import com.example.audioflix.models.SongItem

class DataSource {

    companion object {
        fun createDataSet(): ArrayList<SongItem> {
            val list = ArrayList<SongItem>()
//            list.add(
//                SongItem(
//                    "Song 1",
//                    ""
//                )
//            )
            for(i in 1..10) {
                list.add(
                    SongItem(
                        "Song "+i,
                        ""
                    )
                )
            }

            return list
        }
    }
}