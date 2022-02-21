package sainero.dani.intermodular.Views.SplashScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import sainero.dani.intermodular.Navigation.Destinations
import sainero.dani.intermodular.Utils.GlobalVariables




@Composable
fun SplashScreenMain() {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        GlobalVariables.navController.popBackStack()
        GlobalVariables.navController.navigate(Destinations.Login.route)
    }
    Splash(alpha = alphaAnim.value)

}


@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = sainero.dani.intermodular.R.drawable.logo_eros),
            contentDescription = "Logo",
            modifier = Modifier
                .height(300.dp)
                .width(500.dp)
                .padding(20.dp)
                .alpha(alpha = alpha)
        )
/*
        Icon(
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha = alpha),
            imageVector = Icons.Default.Email,
            contentDescription = "Logo Icon",
            tint = Color.Blue
        )*/
    }
}
