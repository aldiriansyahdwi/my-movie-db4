package com.example.mymoviedb.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import androidx.navigation.fragment.findNavController
import coil.compose.rememberAsyncImagePainter
import com.example.mymoviedb.R
import com.example.mymoviedb.data.listmovie.Results
import com.example.mymoviedb.ui.theme.MyFirstComposeTheme
import com.example.mymoviedb.ui.theme.OrangeMain
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

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
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(OrangeMain))
                    Column {
                        val email = viewModel.getEmail().observeAsState().value
                        HomeHeader(viewModel, onClickFavorite = {
                            val emailFavorite = email?.let { HomeFragmentDirections.actionHomeFragmentToFavoriteFragment(it)
                            }
                            if (emailFavorite != null) {
                                findNavController().navigate(emailFavorite)
                            }
                        }, onClickProfile = {
                            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
                        })
                        val movie = viewModel.listMovie.value
                        MovieList(data = movie, onClickItemList = {
                            val movieId = email?.let { it1 -> HomeFragmentDirections.actionHomeFragmentToDetailFragment(it, it1)
                            }
                            if (movieId != null) { findNavController().navigate(movieId) }
                        })
                    }
                }
            }
        }
    }
}


@Composable
fun HomeHeader(viewModel: HomeViewModel, onClickFavorite:() -> Unit, onClickProfile:() -> Unit) {
    val username = viewModel.getUsername().observeAsState().value

    Spacer(modifier = Modifier.height(24.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
    Row (modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth(0.7f)){
        if (username != null) {
            Text(text = username,
                style = TextStyle(color = Color.White,
                    fontSize = 16.sp),
            )
        }
    }
    Row(horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()) {
        Image(painter = painterResource(id = R.drawable.ic_favorite),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(32.dp)
                .clickable(onClick = onClickFavorite)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(32.dp)
                .clickable(onClick = onClickProfile)
        )
    }
    }
}

@Composable
fun ItemView(item: Results, onClickItem:(Int) -> Unit){
    Card (modifier = Modifier
        .padding(16.dp, 8.dp)
        .fillMaxWidth()
        .clickable(onClick = { item.id?.let { onClickItem(it) } })
    ){
        Row (modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${item.posterPath}"),
                contentDescription = "",
                modifier = Modifier
                    .height(108.dp)
                    .width(72.dp),
                contentScale = ContentScale.FillHeight
            )
            Text(text = item.title.toString(), modifier = Modifier.fillMaxWidth(0.7f))
        }
    }
}

@Composable
fun MovieList(data: List<Results>, onClickItemList:(Int) -> Unit){
    Column{
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(items = data) { results ->
                ItemView(item = results, onClickItem = onClickItemList)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    MyFirstComposeTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(OrangeMain))
        Column {
        }
    }
}