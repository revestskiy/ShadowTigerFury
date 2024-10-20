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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shadows.tigers.fury.ui.theme.nujnoefont

val mainActivity: MainActivity?
    @Composable
    get() {
        return LocalContext.current as? MainActivity
    }

@Preview
@Composable
fun ExitScreen(onBack: () -> Unit = {}) {
    val activity = mainActivity
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
                Image(painter = painterResource(id = R.drawable.homebutton),
                    contentDescription = "Home Button",
                    modifier = Modifier
                        .size(55.dp)
                        .clickable {
                            onBack()
                        })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.exitbutton),
                contentDescription = "Exit Button",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(290.dp, 65.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "ARE YOU SURE YOU WANT\nTO EXIT?",
                fontFamily = nujnoefont,
                fontSize = 29.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(52.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.yesbutton),
                    contentDescription = "Yes Button",
                    modifier = Modifier
                        .size(170.dp, 70.dp)
                        .clickable {
                            activity?.finishAndRemoveTask()
                        })

                Spacer(modifier = Modifier.height(16.dp))

                Image(painter = painterResource(id = R.drawable.nobutton),
                    contentDescription = "No Button",
                    modifier = Modifier
                        .size(170.dp, 70.dp)
                        .clickable {
                            onBack()
                        })
            }
        }
    }
}