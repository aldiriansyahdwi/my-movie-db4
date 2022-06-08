package com.example.mymoviedb.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.fragment.navArgs
import coil.compose.rememberAsyncImagePainter
import com.example.mymoviedb.R
import com.example.mymoviedb.data.detailmovie.Genre
import com.example.mymoviedb.data.detailmovie.GetDetailMovieResponse
import com.example.mymoviedb.data.detailmovie.ProductionCompany
import com.example.mymoviedb.data.userdatabase.UserFavorite
import com.example.mymoviedb.ui.theme.Gray
import com.example.mymoviedb.ui.theme.MyFirstComposeTheme
import com.example.mymoviedb.ui.theme.OrangeMain
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent {
                MyFirstComposeTheme {
                    Column{
                        val movieId = args.movieId
                        val ownerEmail = args.emailUser

                        viewModel.fetchAllData(movieId)
                        val movie = viewModel.detailMovie.observeAsState().value

                        if (movie != null) {
                            DetailHeader(detailMovie = movie)
                        }

                        val userFavorite =
                            movie?.title?.let {
                                movie.posterPath?.let { it1 ->
                                    UserFavorite(null, ownerEmail, movieId,
                                        it, it1
                                    )
                                }
                            }

                        if (movie != null) {
                            DetailSubHeader(detailMovie = movie) {
                                if (userFavorite != null) {
                                    favoriteClicked(userFavorite)
                                }
                            }
                        }

                        val genre = movie?.genres?.let { movieGenre(it) }
                        val production = movie?.productionCompanies?.let { movieProduction(it) }
                        val description = movie?.overview

                        if (production != null) {
                            if (genre != null) {
                                if (description != null) {
                                    DetailBody(description = description, genre = genre, production = production)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun favoriteClicked(favorite: UserFavorite) {
        viewModel.saveFavorite(favorite)
        viewModel.saved.observe(viewLifecycleOwner) {
            if (it != 0.toLong()) {
                Toast.makeText(requireContext(), "added to favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun movieGenre(listGenre: List<Genre>): String {
        var movieGenre = ""
        listGenre.forEach {
            if (movieGenre.isEmpty()) {
                movieGenre = it.name.toString()
            } else movieGenre += ", ${it.name}"
        }
        return movieGenre
    }

    private fun movieProduction(listProduction: List<ProductionCompany>): String {
        var movieProduction = ""
        listProduction.forEach {
            if (movieProduction.isEmpty()) {
                movieProduction = it.name.toString()
            } else movieProduction += ", ${it.name}"
        }
        return movieProduction
    }
}

@Composable
fun DetailHeader(detailMovie : GetDetailMovieResponse){
    Row (modifier = Modifier
        .padding(
            start = 16.dp,
            end = 16.dp,
            top = 32.dp
        )
        .fillMaxWidth()) {
        Image(
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${detailMovie.posterPath}"),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(128.dp, 192.dp)
                .fillMaxWidth(0.3f)
        )
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        Column(modifier = Modifier.align(alignment = Alignment.Bottom)){
            detailMovie.title?.let { Text(text = it, style = TextStyle(color = Color.Black, fontSize = 24.sp)) }
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            detailMovie.tagline?.let { Text(text = it, style = TextStyle(color = Gray)) }
        }
    }
}

@Composable
fun DetailSubHeader(detailMovie: GetDetailMovieResponse, onClickFavorite:() -> Unit){
    Row (modifier = Modifier
        .padding(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp
        )
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_release),
            contentDescription = "",
            modifier = Modifier.size(20.dp),
            tint = Gray
        )
        detailMovie.releaseDate?.let {
            Text(text = it,
                style = TextStyle(fontSize = 14.sp, color = Gray),
                modifier = Modifier.padding(start = 2.dp, end = 4.dp)
            )
        }
        Icon(painter = painterResource(id = R.drawable.ic_favorite),
            contentDescription = "",
            modifier = Modifier.size(20.dp),
            tint = Gray
        )
        Text(text = detailMovie.popularity.toString(),
            style = TextStyle(fontSize = 14.sp, color = Gray),
            modifier = Modifier.padding(start = 2.dp, end = 16.dp)
        )
        Button(onClick = onClickFavorite,
            colors = ButtonDefaults.buttonColors(backgroundColor = OrangeMain,
                contentColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) { Text(text = "Add to Favorite")}
    }
}

@Composable
fun DetailBody(description: String, genre: String, production: String){
    Column(modifier = Modifier
        .padding(vertical = 8.dp, horizontal = 16.dp)
        .fillMaxWidth()){
        Text(text = genre,
            style = TextStyle(color = Gray,
            fontSize = 14.sp)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Text(text = description, style = TextStyle(color = Color.Black, fontSize = 16.sp))
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Text(text = production,
            style = TextStyle(color = Gray,
                fontSize = 14.sp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailPreview() {
    MyFirstComposeTheme {
        Column {

        }
    }
}