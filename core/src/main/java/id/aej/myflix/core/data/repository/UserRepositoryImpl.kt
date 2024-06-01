package id.aej.myflix.core.data.repository

import id.aej.myflix.core.data.source.remote.dto.response.UserResponse
import id.aej.myflix.core.data.source.remote.dto.response.WebResponse
import id.aej.myflix.core.data.source.remote.service.UserService
import id.aej.myflix.core.domain.repository.UserRepository

/**
 * Created by dinopriyano on 31/03/24.
 */
class UserRepositoryImpl constructor(
    private val userService: UserService
): UserRepository {
    override suspend fun getProfile(): WebResponse<UserResponse> {
        return userService.getProfile()
    }
}