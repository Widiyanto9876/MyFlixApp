package id.aej.myflix.core.data.source.remote.service.impl

import id.aej.myflix.core.data.source.remote.HttpRoutes
import id.aej.myflix.core.data.source.remote.dto.response.UserResponse
import id.aej.myflix.core.data.source.remote.dto.response.WebResponse
import id.aej.myflix.core.data.source.remote.service.UserService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

/**
 * Created by dinopriyano on 31/03/24.
 */
class UserServiceImpl constructor(
    private val httpClient: HttpClient
): UserService {
    override suspend fun getProfile(): WebResponse<UserResponse> {
        return httpClient.get {
            url(HttpRoutes.USER)
        }.body()
    }
}