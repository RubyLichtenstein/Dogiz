package com.rubylichtenstein.data.images

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface DogImageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(dogImages: List<DogImageDataEntity>)

    @Query("SELECT * FROM dog_images WHERE breed_key = :breedKey")
    fun getDogImagesByBreedKey(breedKey: String): Flow<List<DogImageDataEntity>>

    @Query("SELECT * FROM dog_images WHERE is_favorite = 1")
    fun getFavoriteDogImages(): Flow<List<DogImageDataEntity>>

    @Query("UPDATE dog_images SET is_favorite = :isFavorite WHERE url = :url")
    fun updateFavoriteStatus(url: String, isFavorite: Boolean): Int
}