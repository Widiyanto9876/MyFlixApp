package id.aej.myflix.core.domain.model

/**
 * Created by dinopriyano on 13/02/24.
 */
data class Auth(
  val user: User? = null,
  val token: String? = null
)
