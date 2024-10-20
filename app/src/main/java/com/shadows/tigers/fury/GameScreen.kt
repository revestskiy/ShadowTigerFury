package com.shadows.tigers.fury

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shadows.tigers.fury.ui.theme.nujnoefont
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.random.Random

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameScreen(
    level: Int = 1,
    targetScore: Int = when (level) {
        in 1..9 -> 7 + (level - 1) * 5   // Levels 1-9: 5 to 30 points
        in 10..18 -> 34+ (level - 10) * 5 // Levels 10-18: 30 to 80 points
        in 19..36 -> 85 + (level - 19) * 10 // Levels 19-36: 80 to 150 points
        else -> 0
    },
    onLevel: (Int) -> Unit,
    onBack: () -> Unit = {}
) {
    var score by remember { mutableIntStateOf(0) }
    var timeLeft by remember { mutableIntStateOf(
        when (level) {
            in 1..9 -> 60000 + (level - 1) * 10000  // Levels 1-9: 60 to 120 seconds
            in 10..18 -> 120000 + (level - 10) * 15000 // Levels 10-18: 2 to 2.5 minutes
            in 19..36 -> 180000 + (level - 19) * 20000 // Levels 19-36: Up to 4 minutes
            else -> 0
        }
    ) }
    var ninjaPosition by remember { mutableFloatStateOf(0f) }
    val ninjaSpeed = 5f
    var items by remember { mutableStateOf(listOf<Item>()) }
    var gameOver by remember { mutableStateOf(false) }
    var levelCompleted by remember { mutableStateOf(false) }
    var isMovingLeft by remember { mutableStateOf(false) }
    var isMovingRight by remember { mutableStateOf(false) }
    var isSettings by remember { mutableStateOf(false) }
    var health by remember { mutableIntStateOf(3) } // Track health

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000L)
            if (!isSettings) {
                timeLeft -= 1000
            }
        } else {
            gameOver = true
        }
    }
    if (isSettings) {
        BackHandler {
            isSettings = false
        }
        SettingsScreen {
            isSettings = false
        }
    } else if (!gameOver && !levelCompleted) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.game_bg),
                    contentScale = ContentScale.FillBounds
                ),
            contentAlignment = Alignment.Center
        ) {
            val maxHeight = constraints.maxHeight
            val screenWidth = constraints.maxWidth * 0.3f
            val tigerHeight = maxHeight * 0.1f
            LaunchedEffect(isMovingLeft, isMovingRight) {
                while (isMovingLeft || isMovingRight) {
                    delay(16L)

                    if (isMovingLeft) {
                        val leftLimit = -screenWidth + 80f
                        if (ninjaPosition > leftLimit) {
                            ninjaPosition -= ninjaSpeed
                        } else {
                            ninjaPosition = leftLimit
                            isMovingLeft = false
                        }
                    }

                    if (isMovingRight) {
                        val rightLimit = screenWidth - 80f
                        if (ninjaPosition < rightLimit) {
                            ninjaPosition += ninjaSpeed
                        } else {
                            ninjaPosition = rightLimit
                            isMovingRight = false
                        }
                    }
                }
            }

            items.forEach { item ->
                CoinItem(
                    item.x,
                    item.y,
                    item.drawableRes
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "score",
                        fontFamily = nujnoefont,
                        fontSize = 24.sp,
                        color = Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "$score",
                        fontSize = 32.sp,
                        modifier = Modifier.padding(8.dp),
                        color = Color.White,
                        fontFamily = nujnoefont
                    )
                }

                Text(
                    text = timeLeft.millisFormatted,
                    fontSize = 24.sp,
                    color = Color.White,
                    fontFamily = nujnoefont
                )

                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(health) {
                        Image(
                            painter = painterResource(id = R.drawable.heart),
                            contentDescription = "Heart",
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(40.dp)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .offset(
                        x = ninjaPosition.dp,
                        y = (tigerHeight).dp
                    )
                    .size(80.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ninja),
                    contentDescription = "Ninja",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .pointerInteropFilter {
                        when (it.action) {
                            android.view.MotionEvent.ACTION_DOWN -> {
                                isMovingLeft = true
                            }

                            android.view.MotionEvent.ACTION_UP,
                            android.view.MotionEvent.ACTION_CANCEL -> {
                                isMovingLeft = false
                            }
                        }
                        true
                    }
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .pointerInteropFilter {
                        when (it.action) {
                            android.view.MotionEvent.ACTION_DOWN -> {
                                isMovingRight = true
                            }

                            android.view.MotionEvent.ACTION_UP,
                            android.view.MotionEvent.ACTION_CANCEL -> {
                                isMovingRight = false
                            }
                        }
                        true
                    })
            }

            Image(
                painter = painterResource(id = R.drawable.homebutton),
                contentDescription = "Exit",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .size(60.dp)
                    .clickable(onClick = onBack),
                contentScale = ContentScale.Fit
            )
            Image(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = "Settings",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(60.dp)
                    .clickable(onClick = {
                        isSettings = true
                    }),
                contentScale = ContentScale.Fit
            )
            LaunchedEffect(Unit) {
                items += generateCoins(level, screenWidth, maxHeight)
                while (timeLeft > 0) {
                    delay(10000L)
                    items += generateCoins(level, screenWidth, maxHeight)
                }
            }
            LaunchedEffect(Unit) {
                while (timeLeft > 0) {
                    delay(16L)
                    items = items.map { item ->
                        val newItem =
                            item.copy(y = item.y + 4)
                        if (newItem.y > maxHeight) {
                            Item(
                                (-screenWidth.roundToInt()..screenWidth.roundToInt()).random()
                                    .toFloat(),
                                Random.nextFloat() * -maxHeight - 50f
                            )
                        } else {
                            newItem
                        }
                    }
                    items = items.filter { item ->
                        val itemCollected =
                            item.y > (tigerHeight) && item.y < (tigerHeight + 80) &&
                                    abs(item.x - ninjaPosition) < 50
                        val planetCollected = itemCollected && item.drawableRes == R.drawable.ic_bomb
                        if (planetCollected) {
                            health -= 1
                            if (health <= 0) {
                                gameOver = true
                                levelCompleted = false
                            }
                        } else if (itemCollected) {
                            score += 1
                        }
                        !itemCollected
                    }
                    if (score >= targetScore) {
                        levelCompleted = true
                    }
                }
            }
        }
    } else {
        GameResultScreen(
            isWin = levelCompleted,
            level = level,
            score = score,
            health = health,
            onBack = onBack,
            onLevel = onLevel
        )
    }
}

// Coin data class to store position
data class Item(val x: Float, val y: Float, val drawableRes: Int = R.drawable.ic_bomb)

@Composable
fun CoinItem(x: Float, y: Float, drawableRes: Int = R.drawable.ic_bomb) {
    Image(
        modifier = Modifier
            .offset(x = x.dp, y = y.dp)
            .size(40.dp),
        painter = painterResource(id = drawableRes),
        contentDescription = "Coin",
        contentScale = ContentScale.Fit
    )
}

fun generateCoins(level: Int, screenWidth: Float, height: Int): List<Item> {
    val numberOfCoins = 3 + level
    val listDrawables = listOf(
        R.drawable.ic_bomb,
        R.drawable.icon_x5f_1,
        R.drawable.icon_x5f_2,
        R.drawable.icon_x5f_3,
        R.drawable.icon_x5f_4,
        R.drawable.icon_x5f_5,
        R.drawable.icon_x5f_6,
        R.drawable.icon_x5f_7,
        R.drawable.icon_x5f_8,
        R.drawable.icon_x5f_9,
    )
    return List(numberOfCoins) {
        Item(
            (-screenWidth.roundToInt()..screenWidth.roundToInt()).random().toFloat(),
            Random.nextFloat() * -height - 50f,
            listDrawables.random()
        )
    }
}


val Number.millisFormatted: String
    get() = SimpleDateFormat("mm:ss", Locale.getDefault()).format(this)

@Composable
fun GameResultScreen(
    isWin: Boolean,
    level: Int,
    score: Int,
    health: Int,
    onBack: () -> Unit,
    onLevel: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        Prefs.addScore(score)
        if (isWin) {
            if (Prefs.hightestLevelUnlocked <= level) Prefs.hightestLevelUnlocked = level + 1
            if (Prefs.getStarsForLevel(level) < health) {
                Prefs.saveStarsForLevel(level, health)
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = if (isWin) R.drawable.win_bg else R.drawable.game_bg),
                contentScale = ContentScale.FillBounds
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = if (isWin) R.drawable.completebutton else R.drawable.losebutton),
                contentDescription = if (isWin) "Complete" else "Lose",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(290.dp, 65.dp)
            )

            Box(
                modifier = Modifier
                    .paint(
                        painter = painterResource(id = R.drawable.backgroundsettings),
                        contentScale = ContentScale.Fit
                    )
                    .size(360.dp, 280.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        repeat(3) { index ->
                            val starDrawable = if (isWin && health > index) R.drawable.star else R.drawable.outlined_star
                            Image(
                                painter = painterResource(id = starDrawable),
                                contentDescription = "Star",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }

                    Text(
                        text = if (isWin) "Level $level" else "YOU LOST ALL\nLIVES",
                        fontFamily = nujnoefont,
                        fontSize = 30.sp,
                        color = Color(0xffF79100),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.homebutton),
                            contentDescription = "Home Button",
                            modifier = Modifier
                                .size(70.dp)
                                .clickable(onClick = onBack)
                        )

                        Image(
                            painter = painterResource(id = if (isWin) R.drawable.play_btn else R.drawable.retrybutton),
                            contentDescription = if (isWin) "Next Level" else "Retry",
                            modifier = Modifier
                                .size(70.dp)
                                .clickable(onClick = {
                                    if (isWin) onLevel(level + 1) else onLevel(level)
                                }) // Replace with proper navigation logic
                        )
                    }
                }
            }
        }
    }
}