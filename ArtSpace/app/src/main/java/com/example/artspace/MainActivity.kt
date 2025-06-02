package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.artspace.ui.theme.ArtSpaceTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {

    val maxScreenCount = 3

    var screenCount by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .statusBarsPadding()
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ArtWall(
            screenCount,
            maxScreenCount,
            onScreenCountChange = { screenCount = it },
            modifier = Modifier
        )
        ArtworkDescriptor(
            screenCount,
            modifier = Modifier
        )
        DisplayController(
            screenCount,
            maxScreenCount,
            onScreenCountChange = { screenCount = it },
            modifier = Modifier
        )
    }
}

@Composable
fun ArtWall(
    screenCount: Int,
    maxScreenCount: Int,
    onScreenCountChange: (Int) -> Unit,
    modifier: Modifier
) {
    val artImages = listOf(
        R.drawable.art_1,
        R.drawable.art_2,
        R.drawable.art_3
    )
    val imageResId = artImages[screenCount - 1]

    val artImagesContentDescription = listOf(
        R.string.art_1_content_description,
        R.string.art_2_content_description,
        R.string.art_3_content_description
    )
    val contentDescriptionId = artImagesContentDescription[screenCount - 1]

    Box(
        modifier = modifier
            .padding(16.dp)
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
            .shadow(elevation = 4.dp)
            .pointerInput(screenCount) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { change, dragAmount ->
                        if (dragAmount > 50) { // Right swipe
                            val newCount = if (screenCount == 1) maxScreenCount else screenCount - 1
                            onScreenCountChange(newCount)
                        } else if (dragAmount < -50) { // Left swipe
                            val newCount = if (screenCount == maxScreenCount) 1 else screenCount + 1
                            onScreenCountChange(newCount)
                        }
                        change.consume()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = stringResource(contentDescriptionId),
            modifier = Modifier
                .padding(28.dp)
        )
    }
}
@Composable
fun ArtworkDescriptor(
    screenCount: Int,
    modifier: Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    val artTitle = listOf(
        R.string.art_1_art_title,
        R.string.art_2_art_title,
        R.string.art_3_art_title
    )
    val artTitleId = artTitle[screenCount - 1]

    val artArtist = listOf(
        R.string.art_1_art_artist,
        R.string.art_2_art_artist,
        R.string.art_3_art_artist
    )
    val artArtistId = artArtist[screenCount - 1]

    val toolTip = listOf(
        R.string.art_1_tool_tip,
        R.string.art_2_tool_tip,
        R.string.art_3_tool_tip
    )
    val toolTipId = toolTip[screenCount - 1]

    var showTooltip by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(bottom = 4.dp)
            .background(color = colorResource(R.color.greyDescriptorBox))
            .wrapContentSize(Alignment.Center)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        showTooltip = true
                        coroutineScope.launch {
                            delay(3000L)
                            showTooltip = false
                        }
                    }
                )
            }

    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(artTitleId),
                color = Color.Black
            )
            Text(
                text = stringResource(artArtistId),
                color = Color.Black
            )
        }

        if (showTooltip) {
            Popup(
                alignment = Alignment.TopCenter,
                offset = IntOffset(0, -100)
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 250.dp)
                        .background(Color.DarkGray, shape = RoundedCornerShape(6.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(toolTipId),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Composable
fun DisplayController(
    screenCount: Int,
    maxScreenCount: Int,
    onScreenCountChange: (Int) -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    val newCount = if (screenCount == 1) maxScreenCount else screenCount - 1
                    onScreenCountChange(newCount)
                }, // Handle Previous click
                shape = RoundedCornerShape(50),
                modifier = Modifier.width(140.dp)
            ) {
                Text(text = stringResource(R.string.previous_button))
            }

            Button(
                onClick = {
                    val newCount = if (screenCount == maxScreenCount) 1 else screenCount + 1
                    onScreenCountChange(newCount)
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier.width(140.dp)
            ) {
                Text(text = stringResource(R.string.next_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}