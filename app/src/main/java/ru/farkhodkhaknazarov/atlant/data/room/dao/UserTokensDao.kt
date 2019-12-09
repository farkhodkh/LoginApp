package ru.farkhodkhaknazarov.atlant.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.farkhodkhaknazarov.atlant.data.room.entities.UserTokens

@Dao
interface UserTokensDao {
    @Query("SELECT * FROM usertokens")
    fun allTokens(): List<UserTokens>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userToken: UserTokens)

    @Query("DELETE FROM usertokens")
    fun deleteAllTokens()
}