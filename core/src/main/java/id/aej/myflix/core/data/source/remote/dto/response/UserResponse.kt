package id.aej.myflix.core.data.source.remote.dto.response

import id.aej.myflix.core.domain.model.Detail
import id.aej.myflix.core.domain.model.User

/**
 * Created by dinopriyano on 31/03/24.
 */

data class UserResponse(
    val id: String? = null,
    val detail: DetailResponse? = null
)

data class DetailResponse(
    val gender: String? = null,
    val watchList: List<String>? = null,
    val birthDate: String? = null,
    val email: String? = null,
    val username: String? = null
)

fun UserResponse.toDomain() = User(
    id,
    Detail(
        detail?.gender,
        detail?.watchList,
        detail?.birthDate,
        detail?.email,
        detail?.username,
        "https://avatar.iran.liara.run/public?username=${detail?.username}"
    )
)