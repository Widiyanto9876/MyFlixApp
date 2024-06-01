package id.aej.myflix.home.api

/**
 * Created by dinopriyano on 07/12/23.
 */
sealed class HomeRoute(val route: String) {
  object Home: HomeRoute("home")
  object MovieDetail: HomeRoute("movie/{movieId}")
}