package ru.beauty.bar.ui.fragment.hello

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.beauty.bar.R
import ru.beauty.bar.navigation.presenter.hello.HelloPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class Hello: BaseFragment() {

    override val presenter = HelloPresenter()

    @Preview
    @Composable
    override fun ComposeFunction() {
        Surface (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Box {
                val pagesCount = 4

                val pagerState = rememberPagerState(pageCount = { pagesCount })

                Box {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        when (page) {
                            0 -> {
                                PageCompose(
                                    mainTitle = "Beauty Bar",
                                    secondaryTitle = "Красота на кончиках пальцев.",
                                    imageResourceId = R.drawable.maniqured_nails_closeup,
                                    imageContentScale = ContentScale.Crop
                                )
                            }
                            1 -> {
                                PageCompose(
                                    mainTitle = "Выбор услуг",
                                    secondaryTitle = "Широкий спектр услуг от профессионалов.",
                                    imageResourceId = R.drawable.service_collage,
                                    imageModifier = Modifier
                                        .background(MaterialTheme.colorScheme.surfaceDim)
                                        .padding(24.dp),
                                    imageContentScale = ContentScale.Fit
                                )
                            }
                            2 -> {
                                PageCompose(
                                    mainTitle = "Персонализация",
                                    secondaryTitle = "Найди своего идеального мастера.",
                                    imageResourceId = R.drawable.master_personalization,
                                    imageContentScale = ContentScale.Crop
                                )
                            }
                            3 -> {
                                PageCompose(
                                    mainTitle = "Начни прямо сейчас!",
                                    secondaryTitle = "Легкая запись, напоминания о визитах и выгодные предложения.",
                                    imageResourceId = R.drawable.flowers_background,
                                    imageContentScale = ContentScale.Crop,
                                    buttonText = "Начнем!",
                                    buttonOnClick = {
                                        presenter.onBeginPress()
                                    }
                                )
                            }
                        }
                    }
                }

                val coroutineScope = rememberCoroutineScope()

                Box(
                    modifier = Modifier
                        .height(160.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors =
                                    listOf(
                                    Color(0x00000000),
                                    Color(0x60000000)
                                    )
                            )
                        )
                        .padding(16.dp, 16.dp, 16.dp, 54.dp),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    Row {
                        TextButton(
                            onClick = {
                                coroutineScope.launch {
                                    var position = pagerState.currentPage

                                    position--

                                    if (position < 0)
                                        position = 0

                                    pagerState.animateScrollToPage(position)
                                }
                            },
                            modifier = Modifier.width(100.dp),
                            enabled = pagerState.currentPage != 0,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                            )
                        ) {
                            Text("Назад")
                        }

                        Text(
                            text = "Используйте кнопки для перемещения",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )

                        TextButton(
                            onClick = {
                                coroutineScope.launch {
                                    var position = pagerState.currentPage

                                    position++

                                    if (position > pagesCount - 1)
                                        position = pagesCount - 1

                                    pagerState.animateScrollToPage(position)
                                }
                            },
                            modifier = Modifier.width(100.dp),
                            enabled = pagerState.currentPage != pagesCount - 1,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                            )
                        ) {
                            Text("Вперед")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun PageCompose(
        mainTitle: String = "",
        secondaryTitle: String = "",
        imageResourceId: Int = -1,
        @SuppressLint("ModifierParameter")
        imageModifier: Modifier = Modifier,
        imageContentScale: ContentScale = ContentScale.Fit,
        buttonText: String? = null,
        buttonOnClick: ()->Unit = {}
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if(imageResourceId != -1) {
                val imageBitmap = ImageBitmap.imageResource(imageResourceId)
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "a photo of manicured nails",
                    modifier = imageModifier.fillMaxSize(),
                    contentScale = imageContentScale,
                )
            }
            else {
                Box(
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxSize())
            }
            ElevatedCard(
                modifier =  Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(32.dp),
                elevation = CardDefaults.elevatedCardElevation(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = mainTitle,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = secondaryTitle,
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if(buttonText != null) {
                    Button (
                        onClick = buttonOnClick
                    )
                    {
                        Text(
                            text = buttonText,
                            style = MaterialTheme.typography.headlineMedium
                            )
                    }
                }
            }
        }
    }
}