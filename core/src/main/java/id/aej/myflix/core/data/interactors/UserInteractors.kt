package id.aej.myflix.core.data.interactors

import id.aej.myflix.core.data.source.remote.SafeApiCall
import id.aej.myflix.core.data.source.remote.dto.response.WebResponse
import id.aej.myflix.core.data.source.remote.dto.response.toDomain
import id.aej.myflix.core.domain.model.Resource
import id.aej.myflix.core.domain.model.User
import id.aej.myflix.core.domain.repository.UserRepository
import id.aej.myflix.core.domain.use_case.UserUseCase
import id.aej.myflix.core.utils.execute
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

/**
 * Created by dinopriyano on 31/03/24.
 */
class UserInteractors constructor(
    private val userRepository: UserRepository,
    private val coroutineContext: CoroutineContext
): UserUseCase, SafeApiCall {
    override suspend fun getProfile(): Flow<Resource<WebResponse<User>>> {
        return execute(coroutineContext) {
            safeApiCall {
                userRepository.getProfile().run {
                    WebResponse(
                        data?.toDomain(),
                        success,
                        message,
                        statusCode,
                        error
                    )
                }
            }
        }
    }
}