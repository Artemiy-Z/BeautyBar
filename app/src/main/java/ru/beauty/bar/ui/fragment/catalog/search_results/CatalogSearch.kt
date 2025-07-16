package ru.beauty.bar.ui.fragment.catalog.search_results;

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.beauty.bar.navigation.presenter.catalog.search_results.CatalogSearchPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class CatalogSearch : BaseFragment() {
    override val presenter = CatalogSearchPresenter()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ComposeFunction() {
        Surface {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.surface,
                contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 16.dp),
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Поиск услуг", fontSize = 28.sp) },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    presenter.onBackPressed()
                                },

                                ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                    contentDescription = ""
                                )
                            }
                        })
                }) { padding ->
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        HorizontalDivider(thickness = 16.dp, color = Color.Transparent)

                        Button(
                            onClick = {
                                //presenter.onServiceSelected()
                            }
                        ) { Text(text = "Услуга1") }
                    }
                }
            }
        }
    }
}