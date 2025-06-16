package com.psycicproductions.contractioncounter.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contractions")
data class Contraction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val contractionStartDt: Long,
    val contractionEndDt: Long? = null
)