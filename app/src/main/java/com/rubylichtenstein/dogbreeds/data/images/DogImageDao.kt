package com.rubylichtenstein.dogbreeds.data.images

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface DogImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dogImage: DogImageDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dogImages: List<DogImageDataEntity>)

    @Update
    fun update(dogImage: DogImageDataEntity)

    @Delete
    fun delete(dogImage: DogImageDataEntity)

    @Query("SELECT * FROM dog_images WHERE url = :url")
    fun getDogImageByUrl(url: String): DogImageDataEntity?

    @Query("SELECT * FROM dog_images WHERE breed_name = :breedName")
    fun getDogImagesByBreed(breedName: String): Flow<List<DogImageDataEntity>>

    @Query("SELECT * FROM dog_images")
    fun getAllDogImages(): Flow<List<DogImageDataEntity>>

    @Query("SELECT * FROM dog_images WHERE is_favorite = 1")
    fun getFavoriteDogImages(): Flow<List<DogImageDataEntity>>

    @Query("UPDATE dog_images SET is_favorite = :isFavorite WHERE url = :url")
    fun updateFavoriteStatus(url: String, isFavorite: Boolean): Int
}