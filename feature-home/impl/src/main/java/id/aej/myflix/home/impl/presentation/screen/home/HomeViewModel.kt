package id.aej.myflix.home.impl.presentation.screen.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.aej.myflix.core.domain.model.Resource
import id.aej.myflix.core.domain.use_case.MovieUseCase
import id.aej.myflix.core.domain.use_case.UserUseCase
import id.aej.myflix.core.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by dinopriyano on 15/02/24.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val movieUseCase: MovieUseCase,
  private val userUseCase: UserUseCase
): BaseViewModel() {

  private var _uiState = MutableStateFlow(HomeScreenUiState())
  val uiState = _uiState.asStateFlow()

  fun getMovies(genre: String) {
    viewModelScope.launch {
      movieUseCase.getMovies(genre).runFlow { result ->
        when (result) {
          is Resource.Loading -> {
            _uiState.update {
              it.copy(
                isMoviesLoading = true
              )
            }
          }
          is Resource.Success -> {
            _uiState.update {
              it.copy(
                isMoviesLoading = false,
                isMoviesError = false,
                moviesErrorMessage = null,
                movies = result.data.data.orEmpty()
              )
            }
          }
          is Resource.Error -> {
            _uiState.update {
              it.copy(
                isMoviesLoading = false,
                isMoviesError = true,
                moviesErrorMessage = result.message
              )
            }
          }
          else -> Unit
        }
      }
    }
  }

  fun getProfile() {
    viewModelScope.launch {
      userUseCase.getProfile().collect { result ->
        when (result) {
          is Resource.Loading -> {
            _uiState.update {
              it.copy(
                isProfileLoading = true
              )
            }
          }
          is Resource.Success -> {
            _uiState.update {
              it.copy(
                isProfileLoading = false,
                isProfileError = false,
                profileErrorMessage = null,
                profile = result.data.data
              )
            }
          }
          is Resource.Error -> {
            _uiState.update {
              it.copy(
                isProfileLoading = false,
                isProfileError = true,
                profileErrorMessage = result.message
              )
            }
          }
          else -> Unit
        }
      }
    }
  }
}