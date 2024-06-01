package id.aej.myflix.home.impl.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import id.aej.myflix.core.data.source.DataDummy
import id.aej.myflix.core.domain.model.MovieItem
import id.aej.myflix.core.domain.model.User
import id.aej.myflix.design_system.utils.carouselTransition
import id.aej.myflix.design_system.utils.showShimmer
import id.aej.myflix.home.impl.R

/**
 * Created by dinopriyano on 20/11/23.
 */

@Composable fun HomeScreen(
  viewModel: HomeViewModel,
  onMovieClick: (String) -> Unit
) {
  var selectedCategoryIndex by rememberSaveable {
    mutableIntStateOf(0)
  }

  val uiState by viewModel.uiState.collectAsState()
  val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.movie))

  LaunchedEffect(Unit) {
    viewModel.getProfile()
  }

  LaunchedEffect(selectedCategoryIndex) {
    viewModel.getMovies(((selectedCategoryIndex + 1) * 12).toString())
  }

  Column(modifier = Modifier.fillMaxSize()) {
    HeaderSection(
      user = uiState.profile,
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 40.dp)
        .padding(horizontal = 24.dp)
    )
    MovieCategories(
      modifier = Modifier
        .padding(top = 24.dp)
        .fillMaxWidth(),
      selectedIndex = selectedCategoryIndex,
      onItemSelected = { index ->
        selectedCategoryIndex = index
      }
    )
    Box(modifier = Modifier
      .fillMaxSize()
      .padding(bottom = 88.dp),
      contentAlignment = Alignment.Center
    ) {
      MovieSlider(
        modifier = Modifier
          .fillMaxSize(),
        movies = uiState.movies
      ) { movie ->
        movie.id?.let {
          onMovieClick(it.toString())
        }
      }

      if (uiState.isMoviesLoading) {
        CircularProgressIndicator(
          modifier = Modifier
            .size(36.dp),
          color = MaterialTheme.colorScheme.onBackground
        )
      }

      if (!uiState.isMoviesLoading && uiState.movies.isEmpty()) {
        Column (
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          LottieAnimation(
            modifier = Modifier.height(230.dp),
            contentScale = ContentScale.FillHeight,
            composition = composition,
            iterations = LottieConstants.IterateForever
          )
          Text(
            text = stringResource(id = R.string.empty_movie_msg),
            style = MaterialTheme.typography.bodyMedium.copy(
              textAlign = TextAlign.Center
            ),
            color = MaterialTheme.colorScheme.onBackground
          )
        }
      }
    }
  }
}

@Composable fun HeaderSection(
  user: User?,
  modifier: Modifier
) {

  var isShouldShowImageShimmer by remember {
    mutableStateOf(true)
  }

  val titleSpan = buildAnnotatedString {
    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
      append(stringResource(R.string.hello_txt))
    }
    append(" ")
    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
      append(user?.detail?.username.orEmpty())
      append(",")
    }
  }

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        fontSize = 24.sp,
        text = titleSpan,
        color = Color.White,
        style = MaterialTheme.typography.titleSmall
      )
      Text(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp),
        text = stringResource(id = R.string.greeting_message),
        color = Color.White,
        style = MaterialTheme.typography.labelMedium
      )
    }
    AsyncImage(
      model = user?.detail?.profilePictureUrl,
      contentDescription = null,
      modifier = Modifier
        .padding(start = 16.dp)
        .size(56.dp)
        .clip(CircleShape)
        .showShimmer(isShouldShowImageShimmer),
      contentScale = ContentScale.Crop,
      onSuccess = {
        isShouldShowImageShimmer = false
      }
    )
  }
}

@Composable fun MovieCategories(
  modifier: Modifier,
  selectedIndex: Int,
  onItemSelected: (Int) -> Unit
) {
  ScrollableTabRow(
    selectedTabIndex = selectedIndex,
    modifier = modifier,
    containerColor = MaterialTheme.colorScheme.background,
    edgePadding = 24.dp,
    divider = {},
    indicator = { tabPositions ->
      Box(
        Modifier
          .tabIndicatorOffset(tabPositions[selectedIndex])
          .fillMaxSize()
          .clip(RoundedCornerShape(50))
          .background(MaterialTheme.colorScheme.primary)
      )
    }
  ) {
    DataDummy.categories.forEachIndexed { index, category ->
      val isSelected by remember {
        mutableStateOf(selectedIndex == index)
      }
      Tab(
        selected = isSelected,
        onClick = { onItemSelected(index) },
        modifier = Modifier
          .padding(horizontal = 6.dp, vertical = 4.dp)
          .zIndex(1f)
      ) {
        Text(
          text = category,
          style = MaterialTheme.typography.labelLarge,
          color = MaterialTheme.colorScheme.onBackground
        )
      }
    }
  }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class) @Composable fun MovieSlider(
  modifier: Modifier,
  movies: List<MovieItem>,
  onCardClick: (MovieItem) -> Unit
) {

  val pagerState = rememberPagerState {
    movies.size
  }

  LaunchedEffect(movies) {
    pagerState.animateScrollToPage(0)
  }

  HorizontalPager(
    state = pagerState,
    pageSpacing = 8.dp,
    modifier = modifier,
    contentPadding = PaddingValues(24.dp),
  ) { index ->
    movies.getOrNull(index)?.let { movie ->
      Card(
        modifier = Modifier.carouselTransition(index, pagerState),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = {
          onCardClick(movie)
        }
      ) {
        AsyncImage(
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop,
          model = movie.posterUrl,
          contentDescription = null
        )
      }
    }
  }

}