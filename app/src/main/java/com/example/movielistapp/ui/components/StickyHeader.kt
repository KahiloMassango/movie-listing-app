package com.example.movielistapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StickyHeader(
    modifier: Modifier = Modifier,
    title: String
) {

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .height(42.dp),
    ) {
        Text(
            modifier = modifier,
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

}