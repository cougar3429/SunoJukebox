package com.cougar.sunojukebox.data.models

import java.util.Date

data class Track(
    val id: String,
    val title: String,
    val artistId: String,
    val artistName: String = "",
    val name: String = "",
    val url: String? = null,
    val date: Date? = null,
    val comments: String? = null,
    val genre: String? = null,
    val category: String? = null,
    val duration: Long? = null
) 