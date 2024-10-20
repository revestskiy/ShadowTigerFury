package com.shadows.tigers.fury

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun MenuScreen(
    onPlay: () -> Unit = {},
    onSettings: () -> Unit = {},
    onStats: () -> Unit = {},
    onExit: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Image(
                painter = painterResource(id = R.drawable.nadpis),
                contentDescription = "Title",
                modifier = Modifier
                    .padding(32.dp)
                    .size(240.dp, 180.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.playbutton),
                contentDescription = "Play Button",
                modifier = Modifier
                    .size(width = 250.dp, height = 80.dp)
                    .clickable {
                        onPlay()
                    }
            )
            Spacer(modifier = Modifier.height(22.dp))

            Image(
                painter = painterResource(id = R.drawable.statsbutton),
                contentDescription = "Settings Button",
                modifier = Modifier
                    .size(width = 250.dp, height = 80.dp)
                    .clickable {
                        onStats()
                    }
            )

            Spacer(modifier = Modifier.height(22.dp))

            Image(
                painter = painterResource(id = R.drawable.settingsbutton),
                contentDescription = "Settings Button",
                modifier = Modifier
                    .size(width = 250.dp, height = 80.dp)
                    .clickable {
                        onSettings()
                    }
            )

            Spacer(modifier = Modifier.height(22.dp))

            Image(
                painter = painterResource(id = R.drawable.exitbutton),
                contentDescription = "Exit Button",
                modifier = Modifier
                    .size(width = 250.dp, height = 80.dp)
                    .clickable {
                        onExit()
                    }
            )
        }
    }
}