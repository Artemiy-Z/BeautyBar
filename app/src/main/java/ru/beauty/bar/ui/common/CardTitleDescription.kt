package ru.beauty.bar.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardTitleDescription(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    image: ImageBitmap? = null,
    imageLink: String = "",
    onClick: (() -> Unit)? = null,
    button: @Composable (() -> Unit) = {},
    backgroundColor: Color = Color.Unspecified,
    maxLines: Int = 4,
    contentScale: ContentScale = ContentScale.Fit
) {
    Column {
        Card(
            onClick = { onClick?.invoke() },
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            )
        ) {
            Box {
                Column {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (image != null)
                                Image(
                                    bitmap = image,
                                    contentDescription = "",
                                    contentScale = contentScale,
                                    alignment = Alignment.Center,
                                    modifier = Modifier
                                        .width(128.dp)
                                        .aspectRatio(1f)
                                        .clip(ShapeDefaults.ExtraSmall)
                                )
                            else if (imageLink.isNotEmpty()) {
                                LoadAsyncImage(
                                    model = imageLink,
                                    contentDescription = null,
                                    modifier = Modifier.width(128.dp).aspectRatio(1f)
                                        .clip(ShapeDefaults.ExtraSmall),
                                    contentScale = contentScale
                                )
                            }
                        }
                        VerticalDivider(thickness = 5.dp, color = Color.Transparent)
                        Column(
                            modifier = Modifier.weight(2f)
                        ) {
                            Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = description,
                                maxLines = maxLines,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    button()
                }
            }
        }
        HorizontalDivider(thickness = 8.dp, color = Color.Transparent)
    }
}