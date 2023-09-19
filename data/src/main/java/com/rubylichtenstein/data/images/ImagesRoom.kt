package com.rubylichtenstein.data.images

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rubylichtenstein.domain.images.DogImageEntity

@Entity(tableName = "dog_images")
data class DogImageDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "breed_name") val breedName: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean
) {
    companion object {
        fun fromDogImageEntity(dogImageEntity: DogImageEntity): DogImageDataEntity {
            return DogImageDataEntity(
                url = dogImageEntity.url,
                breedName = dogImageEntity.breedName,
                isFavorite = dogImageEntity.isFavorite
            )
        }

        fun DogImageDataEntity.toDogImageEntity(): DogImageEntity {
            return DogImageEntity(
                url = url,
                breedName = breedName,
                isFavorite = isFavorite
            )
        }
    }
}


@Database(entities = [DogImageDataEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dogImageDao(): DogImageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}