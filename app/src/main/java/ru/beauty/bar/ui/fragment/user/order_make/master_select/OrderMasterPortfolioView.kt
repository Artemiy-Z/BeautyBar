package ru.beauty.bar.ui.fragment.user.order_make.master_select;

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import ru.beauty.bar.App
import ru.beauty.bar.R
import ru.beauty.bar.dataLayer.UriUrl
import ru.beauty.bar.navigation.presenter.user.order_make.master_select.OrderMasterPortfolioViewPresenter
import ru.beauty.bar.ui.fragment.BaseFragment

class OrderMasterPortfolioView : BaseFragment() {
    override val presenter = OrderMasterPortfolioViewPresenter()

    @Composable
    @Preview
    override fun ComposeFunction() {

        if (App.INSTANCE.sharedData.viewedPortfolio == null) {
            App.INSTANCE.mainInterface.showErrorMessage(
                "Не выбран мастер для редактирования!",
                dismissCallback = {
                    presenter.onBackPressed()
                }
            )
            return
        }
        val viewedPortfolio = App.INSTANCE.sharedData.viewedPortfolio!!

        val imageList = remember {
            MutableLiveData<SnapshotStateList<UriUrl>>(SnapshotStateList<UriUrl>())
        }

        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            App.INSTANCE.mainInterface.toggleLoading(true)

            imageList.value?.addAll(presenter.loadPortfolio(viewedPortfolio))

            App.INSTANCE.mainInterface.toggleLoading(false)
        }

        BaseScaffoldColumn(
            titleText = "Портфолио мастера",
            onBackButtonClick = {
                presenter.onBackPressed()
            },
            replaceColumn = {
                val lazyGridState = rememberLazyGridState()

                LazyVerticalGrid(
                    state = lazyGridState,
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    imageList.let {
                        imageList.value?.size?.let { it1 ->
                            items(it1) { index: Int ->
                                val item = imageList.value!![index]
                                if(item.url.isNotEmpty() || item.uri != null) {
                                    Box {
                                        LoadAsyncImage(
                                            model = item.uri ?: item.url,
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                }
                                else {
                                    imageList.value?.remove(item)
                                }
                            }
                        }
                    }
                }
            },
            content = {}
        )
    }
}