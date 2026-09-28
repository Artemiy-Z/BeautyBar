package ru.beauty.bar.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import ru.beauty.bar.R

@Composable
fun AppLogo() {
    Image(
        bitmap = ImageBitmap.imageResource(R.mipmap.ic_launcher_foreground),
        contentDescription = "",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .width(180.dp)
            .height(180.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
    )
}