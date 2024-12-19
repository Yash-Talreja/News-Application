package com.example.newsapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.kwabenaberko.newsapilib.models.Article

@Composable
fun HomePage(newsViewModel: NewsViewModel, navController: NavHostController) {

    val articles = newsViewModel.articles.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {

        CategoriesBar(newsViewModel)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(articles.value) { article ->
                ArticleItem(article,navController)
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article,navController: NavHostController) {
    Card(
        modifier = Modifier.padding(8.dp),colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF0F0FF) // Light grey-white
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
         onClick = {

            navController.navigate(NewsArticleScreen(article.url))
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "Article Image",
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop,

                )

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)) {

                Text(text = article.title, fontWeight = FontWeight.ExtraBold, maxLines = 3, color = Color.Black)

                Text(text = article.source.name, fontSize = 18.sp, maxLines = 1, color = Color.Blue)


            }
        }
    }
}

@Composable
fun CategoriesBar(newsViewModel: NewsViewModel) {
    var searchquery by remember {
        mutableStateOf("")
    }
    var SearchExpanded by remember {
        mutableStateOf(false)
    }

    val categorylist = listOf(
        "General",
        "Business",
        "Entertainment",
        "Health",
        "Science",
        "Sports",
        "Technology"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
    )
    {

        if (SearchExpanded) {

            OutlinedTextField(
                modifier = Modifier
                    .padding(12.dp)
                    .height(55.dp)
                    .border(
                        1.dp, color = Color.Gray,
                        CircleShape
                    )
                    .clip(CircleShape),
                value = searchquery,
                onValueChange = { searchquery = it },
                textStyle = TextStyle(color = Color.Black),
                trailingIcon = {IconButton(onClick = {
                    SearchExpanded = false
                    if (searchquery.isNotEmpty())
                    {
                        newsViewModel.fetchEverything(searchquery)
                    }

                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null,tint = Color.Black )
                }}


                )

        } else {
            IconButton(onClick = {
                SearchExpanded = true

            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Black)
            }
        }


        categorylist.forEach { category ->
            Button(onClick = {
                newsViewModel.fetchNewsTopHeadlines(category)
            }, modifier = Modifier.padding(5.dp)) {
                Text(text = category)
            }
        }
    }
}



