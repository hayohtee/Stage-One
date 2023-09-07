package dev.hayohtee.stageone

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import dev.hayohtee.stageone.ui.theme.StageOneTheme


@Composable
fun StageOneApp() {
    var isMainScreen by rememberSaveable { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isMainScreen) {
            MainScreen(
                onGithubClick = { isMainScreen = false },
                modifier = Modifier
                    .matchParentSize()
                    .padding(top = dimensionResource(id = R.dimen.large_padding))
            )
        } else {
            ComposeWebView(
                url = "https://github.com/hayohtee",
                onNavigateBack = { isMainScreen = true },
                modifier = Modifier.matchParentSize()
            )
        }
    }

}

@Composable
fun MainScreen(onGithubClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_padding))
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = stringResource(id = R.string.profile_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.image_profile_size))
                .clip(CircleShape)
                .border(
                    width = dimensionResource(id = R.dimen.image_profile_border_width),
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )
        Text(
            text = "Olamilekan Akintilebo",
            style = MaterialTheme.typography.titleLarge
        )
        Button(onClick = onGithubClick, modifier = Modifier.fillMaxSize(0.5f)) {
            Text(text = stringResource(id = R.string.open_github))
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ComposeWebView(url: String, onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {
    BackHandler {
        onNavigateBack()
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true

                loadUrl(url)
            }
        },
        modifier = modifier,
        update = { it.loadUrl(url) }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StageOneTheme {
        StageOneApp()
    }
}