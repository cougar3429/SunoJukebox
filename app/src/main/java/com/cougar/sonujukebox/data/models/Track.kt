package com.cougar.sonujukebox.data.models

import java.util.Date

data class Track(
    val id: String,
    val title: String,
    val artistId: String,
    val artistName: String,
    val name: String,
    val url: String?,
    val date: Date?,
    val comments: String?,
    val genre: String?,
    val category: String?,
    val duration: Long?
) 