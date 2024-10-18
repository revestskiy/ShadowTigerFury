package com.shadow.tiger.fury
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shadow.tiger.fury.ui.theme.nujnoefont
import kotlinx.coroutines.launch

@Composable
fun LevelsScreen(onBack: () -> Unit = {}, onLevel: (Int) -> Unit = {},) {
    val pagerState = rememberPagerState { 4 }
    val scope = rememberCoroutineScope()
    var selectedIndex by remember { mutableStateOf(null as Int?) }
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
                        .size(55.dp)
                        .clickable(onClick = onBack)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "LEVEL",
                fontFamily = nujnoefont,
                fontSize = 40.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .paint(
                        painter = painterResource(id = R.drawable.backgroundsettings),
                        contentScale = ContentScale.FillBounds
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                ) { page ->
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(9) { index ->
                            val levelNumber = index + 1 + page * 9
                            val starsEarned = Prefs.getStarsForLevel(levelNumber)
                            val isLevelUnlocked = levelNumber <= Prefs.hightestLevelUnlocked
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clickable {
                                            if (isLevelUnlocked) selectedIndex = index + page * 9
                                        }
                                        .border(
                                            width = 2.dp,
                                            color = if (selectedIndex == index + page * 9) Color(0xFFE46736) else Color.Transparent,
                                            shape = RoundedCornerShape(10)
                                        )
                                        .alpha(
                                            if (isLevelUnlocked) 1f else 0.5f
                                        )
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.levelbutton),
                                        contentDescription = "Level Button"
                                    )
                                    Text(
                                        text = "${index + 1 + page * 9}",
                                        fontFamily = nujnoefont,
                                        fontSize = 24.sp,
                                        color = Color.White
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                                    modifier = Modifier
                                        .alpha(
                                            if (isLevelUnlocked) 1f else 0.5f
                                        )
                                ) {
                                    repeat(3) { starIndex ->
                                        Image(
                                            painter = painterResource(
                                                id = if (starsEarned > starIndex) R.drawable.star else R.drawable.outlined_star
                                            ),
                                            contentDescription = "Star",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }

                        }
                    }
                }


                CustomDotsIndicator(
                    totalDots = 4,
                    selectedIndex = pagerState.currentPage,
                    modifier = Modifier.padding(16.dp),
                    inactiveColor = Color.White
                ) {
                    scope.launch { pagerState.animateScrollToPage(it) }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.gobutton),
                contentDescription = "Go Button",
                modifier = Modifier
                    .size(170.dp, 70.dp)
                    .clickable {
                        selectedIndex?.let { onLevel(it + 1) }
                    }
            )
        }
    }
}
@Composable
fun CustomDotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    inactiveColor: Color,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until totalDots) {
            if (i == selectedIndex) {
                Image(
                    painter = painterResource(id = R.drawable.ic_selected),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(16.dp),
                    contentScale = ContentScale.FillBounds
                )
            } else {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(10.dp)
                        .shadow(
                            elevation = 2.dp,
                            shape = CircleShape
                        )
                        .background(
                            color = inactiveColor,
                            shape = CircleShape
                        )
                        .clickable {
                            onClick(i)
                        }
                )
            }
        }
    }
}

