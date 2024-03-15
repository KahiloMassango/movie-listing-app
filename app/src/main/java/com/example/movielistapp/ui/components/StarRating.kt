package com.example.movielistapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StarRating(
    modifier: Modifier = Modifier,
    rating: Double,
    stars: Int = 5,
    starsColor: Color = MaterialTheme.colorScheme.primary
) {

    val filledStars = kotlin.math.floor(rating.div(2)).toInt()
    val unfilledStars = (stars - kotlin.math.ceil(rating.div(2))).toInt()
    val halfStar = !(rating.div(2).rem(1).equals(0.0))

    Row(
        verticalAlignment = Alignment.Bottom
    ) {

    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "%.1f".format(rating),
            style = MaterialTheme.typography.labelLarge
        )
        repeat(filledStars) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = starsColor
            )
        }
        if (halfStar) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Rounded.StarHalf,
                contentDescription = null,
                tint = starsColor
            )
        }
        repeat(unfilledStars) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Rounded.StarOutline,
                contentDescription = null,
                tint = starsColor
            )
        }
    }
}