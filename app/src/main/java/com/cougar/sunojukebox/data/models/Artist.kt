package com.cougar.sunojukebox.data.models

data class Artist(
    val id: String,
    val name: String,
    val tracks: List<Track>
) 