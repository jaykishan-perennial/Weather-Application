package com.example.openweatherapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val uId: Int,
    val firstName: String,
    val email: String,
    val password: String,
    val createdAt: String,
    val updatedAt: String,
)