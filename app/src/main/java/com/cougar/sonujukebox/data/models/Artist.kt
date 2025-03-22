package com.cougar.sonujukebox.data.models

data class Artist(
    val id: String,
    val name: String,
    val tracks: List<Track>
) 