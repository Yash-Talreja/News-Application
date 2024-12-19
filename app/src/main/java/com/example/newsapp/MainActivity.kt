package com.example.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapp.ui.theme.HomePage
import com.example.newsapp.ui.theme.HomepageScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.NewsArticlePage
import com.example.newsapp.ui.theme.NewsArticleScreen
import com.example.newsapp.ui.theme.NewsViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val newsViewModel=ViewModelProvider(this)[NewsViewModel::class.java]
        setContent {

            val navController=rememberNavController()

            NewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {

                    Column(modifier = Modifier.padding(10.dp).fillMaxSize()) {

                        Spacer(modifier = Modifier.padding(18.dp))
                        Text(text = "NEWS NOW",
                            modifier = Modifier.align(androidx.compose.ui.Alignment.CenterHorizontally),
                            color = Color.Red,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )

                        NavHost(navController=navController, startDestination =HomepageScreen ) {
                            composable<HomepageScreen>{
                                HomePage(newsViewModel,navController)
                            }
                            composable<NewsArticleScreen>{

                                val args=it.toRoute<NewsArticleScreen>()

                                NewsArticlePage(args.url)
                            }
                        }


                    }

                }
            }
        }
    }
}

