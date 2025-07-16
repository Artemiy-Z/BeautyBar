package ru.beauty.bar.ui.fragment.user.main;

import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.launch
import ru.beauty.bar.R
import ru.beauty.bar.navigation.presenter.user.main.UserMainPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class UserMain : BaseFragment() {
    override val presenter = UserMainPresenter()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    override fun ComposeFunction() {
        Surface {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.surface,
                contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 16.dp),
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Главная", fontSize = 28.sp) },
                    )
                }) { padding ->
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        HorizontalDivider(thickness = 16.dp, color = Color.Transparent)

                        AppLogo()

                        Button(
                            onClick = {
                                presenter.onAccountPressed()
                            }
                        ) {
                            Text(text = "Ваш аккаунт")
                        }

                        Button(
                            onClick = {
                                presenter.onBackPressed()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text(text = "Выйти")
                        }

                        val pageCount = 2
                        val pagerState = rememberPagerState { pageCount }
                        val scope = rememberCoroutineScope()

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(ShapeDefaults.Medium)
                                .background(MaterialTheme.colorScheme.secondary),
                            contentAlignment = Alignment.TopCenter
                        )
                        {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier.fillMaxSize(),
                            ) { page: Int ->
                                Column(
                                    Modifier
                                        .padding(16.dp)
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    when (page) {
                                        0 -> {
                                            Image(
                                                bitmap = ImageBitmap.imageResource(R.drawable.service_collage),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .width(160.dp)
                                                    .aspectRatio(1f)
                                            )

                                            Text(
                                                text = "1) Смотрите доступные услуги",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSecondary
                                            )

                                            Text(
                                                text = "2) Запишитесь на прием",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSecondary
                                            )

                                            Button(
                                                onClick = {
                                                    presenter.onCatalogPressed()
                                                }
                                            ) {
                                                Text(
                                                    text = "Перейти в каталог",
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 15.sp
                                                )
                                            }
                                        }

                                        1 -> {
                                            Image(
                                                bitmap = ImageBitmap.imageResource(R.drawable.checklist),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .width(160.dp)
                                                    .aspectRatio(1f),
                                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
                                            )

                                            Text(
                                                text = "1) Посмотрите свои записи",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSecondary
                                            )

                                            Text(
                                                text = "2) Настройте уведомления",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSecondary
                                            )

                                            Text(
                                                text = "3) Отмена записи",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSecondary
                                            )

                                            Button(
                                                onClick = {
                                                    presenter.onBookingsPressed()
                                                }
                                            ) {
                                                Text(
                                                    text = "Перейти к записям",
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 15.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(64.dp)
                                        .background(
                                            Brush.horizontalGradient(
                                                colors =
                                                    listOf(
                                                        Color(0x00000000),
                                                        Color(0x40000000),
                                                    )
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                val newPage = Math.clamp(
                                                    (pagerState.currentPage + 1).toLong(),
                                                    0,
                                                    1
                                                ).toInt()
                                                pagerState.animateScrollToPage(newPage);
                                            }
                                        },
                                        enabled = pagerState.currentPage == 0,
                                        colors = IconButtonDefaults.iconButtonColors(
                                            contentColor = MaterialTheme.colorScheme.onSecondary,
                                            disabledContentColor = Color.Transparent
                                        )
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                            contentDescription = null,
                                        )
                                    }
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(64.dp)
                                        .background(
                                            Brush.horizontalGradient(
                                                colors =
                                                    listOf(
                                                        Color(0x40000000),
                                                        Color(0x00000000),
                                                    )
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                val newPage = Math.clamp(
                                                    (pagerState.currentPage - 1).toLong(),
                                                    0,
                                                    1
                                                ).toInt()
                                                pagerState.animateScrollToPage(newPage);
                                            }
                                        },
                                        enabled = pagerState.currentPage == 1,
                                        colors = IconButtonDefaults.iconButtonColors(
                                            contentColor = MaterialTheme.colorScheme.onSecondary,
                                            disabledContentColor = Color.Transparent
                                        )
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                            contentDescription = null,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}