package id.aej.myflix.home.impl.presentation.screen.home

import id.aej.myflix.core.domain.model.MovieItem
import id.aej.myflix.core.domain.model.User

/**
 * Created by dinopriyano on 31/03/24.
 */
data class HomeScreenUiState(
    val isProfileLoading: Boolean = true,
    val isMoviesLoading: Boolean = true,
    val isProfileError: Boolean = false,
    val isMoviesError: Boolean = false,
    val profileErrorMessage: String? = null,
    val moviesErrorMessage: String? = null,
    val movies: List<MovieItem> = emptyList(),
    val profile: User? = null
)
