package id.aej.myflix.core.data.source.remote.service

import id.aej.myflix.core.data.source.remote.dto.response.UserResponse
import id.aej.myflix.core.data.source.remote.dto.response.WebResponse

/**
 * Created by dinopriyano on 31/03/24.
 */
interface UserService {

    suspend fun getProfile(): WebResponse<UserResponse>

}