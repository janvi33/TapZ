import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.ImageLoader
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.memory.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    // Navigate after delay (same logic)
    LaunchedEffect(Unit) {
        delay(1500)
        val user = FirebaseAuth.getInstance().currentUser
        navController.navigate(if (user != null) "main" else "login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // Display GIF
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(GifDecoder.Factory())
        }
        .build()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = R.drawable.dragonball, // your GIF file in drawable
            contentDescription = "Splash Animation",
            imageLoader = imageLoader
        )
    }



}

