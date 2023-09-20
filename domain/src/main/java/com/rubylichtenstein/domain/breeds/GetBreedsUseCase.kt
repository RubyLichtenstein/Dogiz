package com.rubylichtenstein.domain.breeds

import com.rubylichtenstein.domain.breeds.data.BreedInfo
import com.rubylichtenstein.domain.breeds.data.BreedsRepository
import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.common.capitalizeWords
import com.rubylichtenstein.domain.common.mapSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(
    private val breedsRepository: BreedsRepository
) {

    operator fun invoke(): Flow<AsyncResult<List<BreedEntity>>> {
        return breedsRepository.breedsFlow.map {
            it.mapSuccess { data -> mapInfoToItems(data) }
        }
    }

    private fun mapInfoToItems(
        breedInfoList: List<BreedInfo>
    ): List<BreedEntity> {
        return breedInfoList.flatMap { breed ->
            val subBreedItems = breed.subBreedsNames.map { subBreedName ->
                BreedEntity(
                    buildDisplayName(breed.name, subBreedName),
                    "${breed.name}_${subBreedName}"
                )
            }

            listOf(
                BreedEntity(
                    breed.name,
                    breed.name
                )
            ) + subBreedItems
        }
    }
}

fun buildDisplayName(breedName: String, subBreedName: String?): String {
    val capitalizedBreed = breedName.capitalizeWords()
    val capitalizedSubBreed = subBreedName?.capitalizeWords()

    return if (capitalizedSubBreed != null) {
        "$capitalizedBreed ($capitalizedSubBreed)"
    } else {
        capitalizedBreed
    }
}

fun buildDisplayNameFromKey(breedName: String): String {
    val parts = breedName.split('/')
    val breed = parts[0].capitalizeWords()
    val subBreed = parts.getOrNull(1)?.capitalizeWords()

    return buildDisplayName(breed, subBreed)
}
