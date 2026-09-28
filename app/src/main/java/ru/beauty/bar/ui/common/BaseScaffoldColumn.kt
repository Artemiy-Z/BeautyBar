package ru.beauty.bar.ui.common

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.beauty.bar.ui.fragment.BaseFragment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffoldColumn(
    baseFragment: BaseFragment, content: @Composable (() -> Unit),
    replaceColumn: @Composable (() -> Unit)? = null,
    titleText: String,
    onBackButtonClick: (() -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    bringIntoViewRequester: BringIntoViewRequester? = null,
    backgroundColor: Color = Color.Transparent,
    floatingActionButton: @Composable (()->Unit) = {}
) {
    Scaffold(
        floatingActionButtonPosition = FabPosition.EndOverlay,
        floatingActionButton = floatingActionButton,
        containerColor =
            if (backgroundColor != Color.Transparent)
                backgroundColor
            else
                MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 64.dp),
        topBar = {
            TopAppBar(
                expandedHeight = 64.dp,
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                colors =
                    if (backgroundColor != Color.Transparent)
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = backgroundColor,
                            scrolledContainerColor = backgroundColor,
                        )
                    else
                        TopAppBarDefaults.topAppBarColors(),
                title = {
                    Text(
                        text = titleText,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    if (onBackButtonClick != null) {
                        IconButton(
                            onClick = {
                                baseFragment.presenter.onBackPressed()
                            },

                            ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = ""
                            )
                        }
                    }
                },
            )
        }) { padding ->
        Surface(
            color =
                if (backgroundColor != Color.Transparent)
                    backgroundColor
                else
                    MaterialTheme.colorScheme.surface
        ) {
            if (replaceColumn == null) {
                Box(
                    modifier = if (bringIntoViewRequester != null)
                        Modifier
                            .bringIntoViewRequester(bringIntoViewRequester)
                            .padding(padding)
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
                    else
                        Modifier
                            .padding(padding)
                            .fillMaxWidth()
                            .verticalScroll(scrollState),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        content()
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp, 64.dp, 16.dp, 64.dp)
                ) {
                    replaceColumn()
                }
            }
        }
    }
}