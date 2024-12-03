package com.example.openweatherapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.openweatherapp.data.source.local.entity.UserEntity

@Dao
interface AuthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT EXISTS (SELECT 1 FROM user_table WHERE email = :email)")
    suspend fun checkUserExist(email: String): Boolean

}