package dk.shape.dogbreeds.data.breeds

import kotlinx.serialization.Serializable

//@Serializable
//data class BreedInfoImpl(
//    override val breed: String,
//    override val subBreeds: List<String>
//) : dk.shape.domain.BreedInfo {
//    companion object {
//        fun fromMap(map: Map<String, List<String>>): List<dk.shape.domain.BreedInfo> {
//            return map.map { (breed, subBreeds) ->
//                BreedInfoImpl(breed, subBreeds)
//            }
//        }
//    }
//}