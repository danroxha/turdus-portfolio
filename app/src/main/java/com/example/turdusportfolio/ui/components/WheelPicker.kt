package com.example.turdusportfolio.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.turdusportfolio.ui.theme.TurdusPortfolioTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WheelPicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    count: Int,
    size: DpSize = DpSize(128.dp, 128.dp),
    onSelectionChanged: (index: Int) -> Unit = {},
    backgroundContent: (@Composable (size: DpSize) -> Unit)? = {
        WheelPickerDefaults.Background(size = it)
    },
    itemContent: @Composable LazyItemScope.(index: Int) -> Unit,
) {
    val lazyListState = rememberLazyListState(startIndex)

    LaunchedEffect(lazyListState, onSelectionChanged) {
        snapshotFlow { lazyListState.firstVisibleItemScrollOffset }
            .map { calculateSnappedItemIndex(lazyListState) }
            .distinctUntilChanged()
            .collectLatest {
                onSelectionChanged(it)
            }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        backgroundContent?.invoke(size)

        LazyRow(
            modifier = Modifier
                .wrapContentHeight()
                .width(size.width),
            state = lazyListState,
            contentPadding = PaddingValues(0.dp),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState),
        ) {
            items(count) { index ->
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(size.width / COLUMN_COUNT)
                        .alpha(
                            calculateAnimatedAlpha(
                                lazyListState = lazyListState,
                                index = index,
                            ),
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    itemContent(index)
                }
            }
        }
    }
}

@Composable
fun WheelTextPicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    texts: List<String>,
    color: Color = Color.White,
    size: DpSize = DpSize(128.dp, 128.dp),
    onSelectionChanged: (index: Int) -> Unit = {},
    backgroundContent: (@Composable (size: DpSize) -> Unit)? = {
        WheelPickerDefaults.Background(size = it)
    },
) {
    WheelPicker(
        modifier = modifier,
        startIndex = startIndex,
        count = remember(texts) { texts.size },
        size = size,
        onSelectionChanged = onSelectionChanged,
        backgroundContent = backgroundContent,
    ) {
        WheelPickerDefaults.Item(text = texts[it], color = color)
    }
}

private fun LazyListState.snapOffsetForItem(itemInfo: LazyListItemInfo): Int {
    val startScrollOffset = 0
    val endScrollOffset = layoutInfo.let { it.viewportEndOffset - it.afterContentPadding }
    return startScrollOffset + (endScrollOffset - startScrollOffset - itemInfo.size) / 2
}

private fun LazyListState.distanceToSnapForIndex(index: Int): Int {
    val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
    if (itemInfo != null) {
        return itemInfo.offset - snapOffsetForItem(itemInfo)
    }
    return 0
}

private fun calculateAnimatedAlpha(
    lazyListState: LazyListState,
    index: Int,
): Float {
    val distanceToIndexSnap = lazyListState.distanceToSnapForIndex(index).absoluteValue
    val viewPortWidth = lazyListState.layoutInfo.viewportSize.width.toFloat()
    val singleViewPortWidth = viewPortWidth / COLUMN_COUNT
    return if (distanceToIndexSnap in 0..singleViewPortWidth.toInt()) {
        1.2f - (distanceToIndexSnap / singleViewPortWidth)
    } else {
        0.2f
    }
}

private fun calculateSnappedItemIndex(lazyListState: LazyListState): Int {
    return lazyListState.layoutInfo.visibleItemsInfo
        .maxBy { calculateAnimatedAlpha(lazyListState, it.index) }
        .index
}

object WheelPickerDefaults {
    @Composable
    fun Background(size: DpSize) {
        Surface(
            modifier = Modifier
                .size(size.width, size.height / COLUMN_COUNT),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            content = {},
        )
    }

    @Composable
    fun Item(text: String, color: Color = Color.White) {
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
        )
    }
}

private const val COLUMN_COUNT = 3

@Composable
@Preview(showBackground = true)
fun WheelTextPickerPreviews() {
    TurdusPortfolioTheme {
        WheelTextPicker(
//            texts = listOf(" ", "1º", "2º", "3º", "4º", "5º", " ")
            texts = listOf("1º", "2º", "3º", "4º", "5º")
        )
    }
}