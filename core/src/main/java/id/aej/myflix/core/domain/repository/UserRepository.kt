package id.aej.myflix.core.domain.repository

import id.aej.myflix.core.data.source.remote.dto.response.UserResponse
import id.aej.myflix.core.data.source.remote.dto.response.WebResponse

/**
 * Created by dinopriyano on 31/03/24.
 */
interface UserRepository {

    suspend fun getProfile(): WebResponse<UserResponse>

}