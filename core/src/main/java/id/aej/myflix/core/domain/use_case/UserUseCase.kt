package id.aej.myflix.core.domain.use_case

import id.aej.myflix.core.data.source.remote.dto.response.WebResponse
import id.aej.myflix.core.domain.model.Resource
import id.aej.myflix.core.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by dinopriyano on 31/03/24.
 */
interface UserUseCase {

    suspend fun getProfile(): Flow<Resource<WebResponse<User>>>

}