package com.shadows.tigers.fury

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
fun SettingsScreen(onBack: () -> Unit = {}) {
    var musicVolume by remember { mutableFloatStateOf(Prefs.musicVolume) }
    var soundVolume by remember { mutableFloatStateOf(Prefs.soundVolume) }
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
                painter = painterResource(id = R.drawable.settingsbutton),
                contentDescription = "Settings Button",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(290.dp, 65.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .paint(painter = painterResource(id = R.drawable.backgroundsettings), contentScale = ContentScale.Fit)
                    .size(360.dp, 290.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(start = 62.dp, top = 32.dp)
                ) {
                    Text(
                        text = "music",
                        fontFamily = nujnoefont,
                        fontSize = 32.sp,
                        color = Color(0xFF632E2E),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )

                    Slider(
                        value = musicVolume,
                        onValueChange = {
                            musicVolume = it
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color.Yellow,
                            inactiveTrackColor = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )

                    Text(
                        text = "sound",
                        fontFamily = nujnoefont,
                        fontSize = 32.sp,
                        color = Color(0xFF632E2E),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )

                    Slider(
                        value = soundVolume,
                        onValueChange = {
                            soundVolume = it
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color.Yellow,
                            inactiveTrackColor = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth(0.7f)
                                .align(Alignment.CenterHorizontally)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.savebutton),
                contentDescription = "Save Button",
                modifier = Modifier
                    .size(170.dp, 50.dp)
                    .clickable {
                        Prefs.musicVolume = musicVolume
                        Prefs.soundVolume = soundVolume
                        SoundManager.setSoundVolume()
                        SoundManager.setMusicVolume()
                        onBack()
                    }
            )
        }
    }
}