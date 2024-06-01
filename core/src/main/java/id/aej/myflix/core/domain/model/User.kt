package id.aej.myflix.core.domain.model

/**
 * Created by dinopriyano on 31/03/24.
 */

data class User(
    val id: String? = null,
    val detail: Detail? = null
)

data class Detail(
    val gender: String? = null,
    val watchList: List<String>? = null,
    val birthDate: String? = null,
    val email: String? = null,
    val username: String? = null,
    val profilePictureUrl: String? = null
)
