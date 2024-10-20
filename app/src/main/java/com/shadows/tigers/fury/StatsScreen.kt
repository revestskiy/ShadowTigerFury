package com.shadows.tigers.fury

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shadows.tigers.fury.ui.theme.nujnoefont

@Preview
@Composable
fun StatsScreen(
    onBack: () -> Unit = {}
) {
    var stats by remember { mutableStateOf(Prefs.stats) }

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
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Image(
                    painter = painterResource(id = R.drawable.homebutton),
                    contentDescription = "Home Button",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            onBack()
                        }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.statsbutton2),
                contentDescription = "Settings Button",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(260.dp, 65.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .paint(
                        painter = painterResource(id = R.drawable.statsbackground),
                        contentScale = ContentScale.Fit
                    )
                    .fillMaxWidth()
                    .height(440.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    stats.forEach {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.85f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = it.num.toString() + ".",
                                fontFamily = nujnoefont,
                                fontSize = 27.sp,
                                color = Color(0xFF632E2E)
                            )

                            Text(
                                text = it.date,
                                fontFamily = nujnoefont,
                                fontSize = 24.sp,
                                color = Color(0xFF632E2E)
                            )

                            Text(
                                text = it.score.toString(),
                                fontFamily = nujnoefont,
                                fontSize = 24.sp,
                                color = Color(0xFF632E2E)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.clearbutton),
                contentDescription = "Save Button",
                modifier = Modifier
                    .size(175.dp, 50.dp)
                    .clickable {
                        Prefs.clearStats()
                        stats = Prefs.stats
                    }
            )
        }
    }
}