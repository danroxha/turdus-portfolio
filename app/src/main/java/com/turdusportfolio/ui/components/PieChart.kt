package com.turdusportfolio.ui.components

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turdusportfolio.ui.theme.TurdusDefault
import com.turdusportfolio.ui.theme.TurdusPortfolioTheme
import java.lang.Integer.max
import java.util.regex.Pattern
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.min

// https://github.com/MatthiasKerat/PieChartYT
@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    radius:Float = 500f,
    innerRadius:Float = radius / 2,
    transparentWidth:Float = 70f,
    labelColor: Color = Color.Black,
    labelSize: TextUnit = 21.sp,
    enableSubtitle: Boolean = false,
    enableTapped: Boolean = !enableSubtitle,
    input:List<PieChartInput>,
    onTagContentCenter: (() -> Unit?)? = null,
    backgroundColorCenterContent: Color = Color.Gray,
    centerContent: @Composable()(() -> Unit) = {}
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var inputList by remember {
        mutableStateOf(input)
    }
    var isCenterTapped by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .wrapContentSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .size(radius.dp)
        ){
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(true) {
                        detectTapGestures(
                            onTap = { offset ->
                                val tapAngleInDegrees = (-atan2(
                                    x = circleCenter.y - offset.y,
                                    y = circleCenter.x - offset.x
                                ) * (180f / PI).toFloat() - 90f).mod(360f)
                                val centerClicked = if (tapAngleInDegrees < 90) {
                                    offset.x < circleCenter.x + innerRadius && offset.y < circleCenter.y + innerRadius
                                } else if (tapAngleInDegrees < 180) {
                                    offset.x > circleCenter.x - innerRadius && offset.y < circleCenter.y + innerRadius
                                } else if (tapAngleInDegrees < 270) {
                                    offset.x > circleCenter.x - innerRadius && offset.y > circleCenter.y - innerRadius
                                } else {
                                    offset.x < circleCenter.x + innerRadius && offset.y > circleCenter.y - innerRadius
                                }

                                if (centerClicked) {
                                    inputList = inputList.map {
                                        it.copy(isTapped = !isCenterTapped)
                                    }
                                    isCenterTapped = !isCenterTapped
                                } else {
                                    val anglePerValue = 360f / input.sumOf {
                                        it.value
                                    }
                                    var currAngle = 0f
                                    inputList.forEach { pieChartInput ->
                                        currAngle += pieChartInput.value * anglePerValue
                                        if (tapAngleInDegrees < currAngle) {
                                            val description = pieChartInput.description
                                            inputList = inputList.map {
                                                if (description == it.description) {
                                                    it.copy(isTapped = !it.isTapped)
                                                } else {
                                                    it.copy(isTapped = false)
                                                }
                                            }
                                            return@detectTapGestures
                                        }
                                    }
                                }
                            }
                        )
                    }
            ){
                val width = size.width
                val height = size.height
                circleCenter = Offset(x= width/2f,y= height/2f)

                val totalValue = input.sumOf {
                    it.value
                }
                val anglePerValue = 360f/totalValue
                var currentStartAngle = 0f

                inputList.forEach { pieChartInput ->
                    val scale = if(pieChartInput.isTapped && !enableSubtitle && enableTapped) 1.1f else 1.0f
                    val angleToDraw = pieChartInput.value * anglePerValue
                    scale(scale){
                        drawArc(
                            color = pieChartInput.color,
                            startAngle = currentStartAngle,
                            sweepAngle = angleToDraw,
                            useCenter = true,
                            size = Size(
                                width = radius*2f,
                                height = radius*2f
                            ),
                            topLeft = Offset(
                                (width-radius*2f)/2f,
                                (height - radius*2f)/2f
                            )
                        )
                        currentStartAngle += angleToDraw
                    }
                    var rotateAngle = currentStartAngle-angleToDraw/2f-90f
                    var factor = 1f
                    if(rotateAngle>90f){
                        rotateAngle = (rotateAngle+180).mod(360f)
                        factor = -0.92f
                    }

                    val percentage = (pieChartInput.value/totalValue.toFloat()*100).toInt()

                    drawContext.canvas.nativeCanvas.apply {
                        if(percentage>3){
                            rotate(rotateAngle){
                                drawText(
                                    "$percentage %",
                                    circleCenter.x,
                                    circleCenter.y+(radius-(radius-innerRadius)/2f)*factor,
                                    Paint().apply {
                                        textSize = 13.sp.toPx()
                                        textAlign = Paint.Align.CENTER
                                        color = Color.White.toArgb()
                                    }
                                )
                            }
                        }
                    }
                    if(pieChartInput.isTapped && !enableSubtitle && enableTapped){
                        val tabRotation = currentStartAngle - angleToDraw - 90f
                        rotate(tabRotation){
                            drawRoundRect(
                                topLeft = circleCenter,
                                size = Size(12f,radius*1.2f),
                                color = Color.Transparent,
                                cornerRadius = CornerRadius(15f,15f)
                            )
                        }
                        rotate(tabRotation+angleToDraw){
                            drawRoundRect(
                                topLeft = circleCenter,
                                size = Size(12f,radius*1.2f),
                                color = Color.Transparent,
                                cornerRadius = CornerRadius(15f,15f)
                            )
                        }
                        rotate(rotateAngle){
                            drawContext.canvas.nativeCanvas.apply {
                                drawText(
                                    "${pieChartInput.description}: ${pieChartInput.value}",
                                    circleCenter.x,
                                    circleCenter.y + radius*1.3f*factor,
                                    Paint().apply {
                                        textSize = labelSize.toPx()
                                        textAlign = Paint.Align.CENTER
                                        color = labelColor.toArgb()
                                        isFakeBoldText = true
                                    }
                                )
                            }
                        }
                    }
                }

                if(inputList.first().isTapped && !enableSubtitle && enableTapped){
                    rotate(-90f){
                        drawRoundRect(
                            topLeft = circleCenter,
                            size = Size(12f,radius*1.2f),
                            color = Color.Transparent,
                            cornerRadius = CornerRadius(15f,15f)
                        )
                    }
                }
                drawContext.canvas.nativeCanvas.apply {
                    drawCircle(
                        circleCenter.x,
                        circleCenter.y,
                        innerRadius,
                        Paint().apply {
                            color = Color.White.copy(alpha = 0.6f).toArgb()
                            setShadowLayer(10f,0f,0f, backgroundColorCenterContent.toArgb())
                        }
                    )
                }

                drawCircle(
                    color = Color.White.copy(0.2f),
                    radius = innerRadius+transparentWidth/2f
                )

            }

            centerContent()

        }

        if(enableSubtitle) {
            Column(
                verticalArrangement = Arrangement.Bottom,
            ) {
                input.forEach { item ->
                    Row() {
                        Text(text = " ", modifier = Modifier.background(item.color))
                        Text(text = " ${item.description}", color = labelColor)
                    }
                }
            }
        }
    }
}

@Composable
fun PieChartSimple(
    modifier: Modifier = Modifier,
    radius:Float = 500f,
    innerRadius:Float = radius / 2,
    labelColor: Color = Color.Black,
    labelSize: TextUnit = 21.sp,
    enableSubtitle: Boolean = false,
    enableTapped: Boolean = !enableSubtitle,
    input:List<PieChartInput>,
) {
    var circleCenter by remember { mutableStateOf(Offset.Zero) }

    var inputList by remember { mutableStateOf(input) }

    Column(
        modifier = Modifier
            .wrapContentSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .size(radius.dp)
        ){
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(true) {
                        detectTapGestures(
                            onTap = { offset ->
                                val tapAngleInDegrees = (-atan2(
                                    x = circleCenter.y - offset.y,
                                    y = circleCenter.x - offset.x
                                ) * (180f / PI).toFloat() - 90f).mod(360f)

                                val anglePerValue = 360f / input.sumOf {
                                    it.value
                                }
                                var currAngle = 0f
                                inputList.forEach { pieChartInput ->
                                    currAngle += pieChartInput.value * anglePerValue
                                    if (tapAngleInDegrees < currAngle) {
                                        val description = pieChartInput.description
                                        inputList = inputList.map {
                                            if (description == it.description) {
                                                it.copy(isTapped = !it.isTapped)
                                            } else {
                                                it.copy(isTapped = false)
                                            }
                                        }
                                        return@detectTapGestures
                                    }
                                }

                            }
                        )
                    }
            ){
                val width = size.width
                val height = size.height
                circleCenter = Offset(x= width/2f,y= height/2f)

                val totalValue = input.sumOf {
                    it.value
                }
                val anglePerValue = 360f/totalValue
                var currentStartAngle = 0f

                var factorPiece = 1f
                var pieChartInputSelected: PieChartInputSelected? = null

                inputList.forEachIndexed{index, pieChartInput ->
                    val scale = if(pieChartInput.isTapped && !enableSubtitle && enableTapped) 1.1f else 1.0f
                    val angleToDraw = pieChartInput.value * anglePerValue
                    scale(scale){
                        drawArc(
                            color = pieChartInput.color,
                            startAngle = currentStartAngle,
                            sweepAngle = angleToDraw,
                            useCenter = true,
                            size = Size(
                                width = radius*2f,
                                height = radius*2f
                            ),
                            topLeft = Offset(
                                (width-radius*2f)/2f,
                                (height - radius*2f)/2f
                            )
                        )
                        currentStartAngle += angleToDraw
                    }
                    var rotateAngle = currentStartAngle-angleToDraw/2f-90f
                    var factor = 1f
                    if(rotateAngle>90f){
                        rotateAngle = (rotateAngle+180).mod(360f)
                        factor = -0.92f
                    }

                    val percentage = (pieChartInput.value/totalValue.toFloat()*100).toInt()

                    drawContext.canvas.nativeCanvas.apply {
                        if(percentage>3){
                            rotate(rotateAngle){
                                drawText(
                                    "$percentage %",
                                    circleCenter.x,
                                    circleCenter.y+(radius-(radius-innerRadius)/2f)*factor,
                                    Paint().apply {
                                        textSize = 13.sp.toPx()
                                        textAlign = Paint.Align.CENTER
                                        color = Color.White.toArgb()
                                    }
                                )
                            }
                        }
                    }
                    if(pieChartInput.isTapped && !enableSubtitle && enableTapped){
                        val tabRotation = currentStartAngle - angleToDraw - 90f
                        factorPiece = factor
                        pieChartInputSelected = PieChartInputSelected(
                            description = pieChartInput.description,
                            color = pieChartInput.color,
                        )

                        rotate(tabRotation){
                            drawRoundRect(
                                topLeft = circleCenter,
                                size = Size(12f,radius*1.2f),
                                color = Color.Transparent,
                                cornerRadius = CornerRadius(15f,15f)
                            )
                        }
                        rotate(tabRotation+angleToDraw){
                            drawRoundRect(
                                topLeft = circleCenter,
                                size = Size(12f,radius*1.2f),
                                color = Color.Transparent,
                                cornerRadius = CornerRadius(15f,15f)
                            )
                        }
                    }
                }

                if(pieChartInputSelected != null) {
                    var floatSquare = Offset(
                        x = circleCenter.x + (radius - (radius - innerRadius) / 2f) * factorPiece,
                        y = circleCenter.y + (radius - (radius - innerRadius) / 2f) * factorPiece,
                    )
                    val size = Size(radius * 1.2f, radius * 1.2f)

                    drawRoundRect(
                        topLeft = Offset(
                            x = if (floatSquare.x > circleCenter.x) circleCenter.x - radius else circleCenter.x + 2f,
                            y = if (floatSquare.y > circleCenter.y) circleCenter.y - radius else circleCenter.y + 2f,
                        ),
                        size = size,
                        color = Color(0xC9000000),
                        cornerRadius = CornerRadius(15f, 15f)
                    )


                    drawRoundRect(
                        topLeft = Offset(
                            x = if (floatSquare.x > circleCenter.x) circleCenter.x - radius + 8f else circleCenter.x + 8f,
                            y = if (floatSquare.y > circleCenter.y) circleCenter.y - radius + 8f else circleCenter.y + 8f,
                        ),
                        size = size.copy(width = size.width * 0.07f, height = size.height * 0.07f),
                        color = pieChartInputSelected!!.color,
                        cornerRadius = CornerRadius(100f, 100f)
                    )

                    val horizontalPadding = 50f
                    val verticalPadding = 90f

                    drawContext.canvas.nativeCanvas.apply {
                        transformStringInSlices(
                            text = pieChartInputSelected!!.description,
                            fontSizeInPixel = labelSize.toPx(),
                            size = size,
                        ).forEachIndexed { lineNumber, line ->
                            var innerRect = Offset(
                                x = circleCenter.x - radius + horizontalPadding,
                                y = circleCenter.y - radius + verticalPadding
                            )

                            val textPosition = Offset(
                                x = if (floatSquare.x > circleCenter.x) innerRect.x else circleCenter.x + horizontalPadding,
                                y = if (floatSquare.y > circleCenter.y) innerRect.y + (lineNumber * labelSize.toPx()
                                    .toInt()) else circleCenter.y + verticalPadding + (lineNumber * labelSize.toPx()
                                    .toInt()),
                            )

                            drawText(
                                "$line",
                                textPosition.x,
                                textPosition.y,
                                Paint().apply {
                                    textSize = labelSize.toPx()
                                    color = Color.White.toArgb()
                                    isFakeBoldText = true
                                }
                            )
                        }
                    }
                }

                if(inputList.first().isTapped && !enableSubtitle && enableTapped){
                    pieChartInputSelected = null
                    rotate(-90f){
                        drawRoundRect(
                            topLeft = circleCenter,
                            size = Size(12f,radius*1.2f),
                            color = Color.Transparent,
                            cornerRadius = CornerRadius(15f,15f)
                        )
                    }
                }
            }

        }

        if(enableSubtitle) {
            Column(
                verticalArrangement = Arrangement.Bottom,
            ) {
                input.forEach { item ->
                    Row() {
                        Text(text = " ", modifier = Modifier.background(item.color))
                        Text(text = " ${item.description}", color = labelColor)
                    }
                }
            }
        }
    }
}


fun transformStringInSlices(
    text: String,
    fontSizeInPixel: Float,
    size: Size,
): List<String> {
    var amountCharsBylines = (size.width / (fontSizeInPixel / 1.8)).toInt()
    var slices = (amountCharsBylines * fontSizeInPixel.toInt() / size.width.toInt()) + 1

    var currentLine = ""
    var lineNumber = 0
    var endIndex = 0
    var startIndex = 0
    var selectPieField = text

    var result = mutableListOf<String>()

    while(endIndex != selectPieField.length) {

        try {
            startIndex = max(min(selectPieField.length, lineNumber * amountCharsBylines), 0)
            endIndex = max(min(selectPieField.length, lineNumber * amountCharsBylines + amountCharsBylines), 0)

            currentLine = selectPieField.substring(
                startIndex = startIndex,
                endIndex = endIndex
            )

            var pattern = Pattern.compile("(^\\s+)(.+)", Pattern.CASE_INSENSITIVE);
            var match = pattern.matcher(currentLine)

            if(match.matches()) {
                var stringBuilder = StringBuilder(selectPieField)
                stringBuilder.delete(startIndex, min(stringBuilder.capacity(), startIndex + match.group(1).length))
                selectPieField = stringBuilder.toString()
                continue
            }

            result.add(currentLine)

        }catch (e: Exception) {
            Log.e("CALC", "${e.message}")
        }

        lineNumber++

    }

    return result
}
private data class PieChartInputSelected(
    val description: String,
    val color: Color,
)
data class PieChartInput(
    val color:Color,
    val value:Int,
    val description:String,
    val isTapped:Boolean = false
)

@Composable
@Preview
fun PieChartPreview() {
    TurdusPortfolioTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(TurdusDefault.Padding.small)
            ,
            verticalArrangement = Arrangement.spacedBy(space = TurdusDefault.Padding.extraLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                "Preferred Programming Languages",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = TurdusDefault.Padding.extraLarge)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ){
                PieChart(
                    modifier = Modifier
                        .size(500.dp),
                    input = listOf(
                        PieChartInput(
                            color = Color.Blue,
                            value = 29,
                            description = "Python"
                        ),
                        PieChartInput(
                            color = Color.Magenta,
                            value = 21,
                            description = "Swift"
                        ),
                        PieChartInput(
                            color = Color.Yellow,
                            value = 32,
                            description = "JavaScript"
                        ),
                        PieChartInput(
                            color = Color.Red,
                            value = 18,
                            description = "Java"
                        ),
                        PieChartInput(
                            color = Color.Cyan,
                            value = 12,
                            description = "Ruby"
                        ),
                        PieChartInput(
                            color = Color.Green,
                            value = 38,
                            description = "Kotlin"
                        ),
                    )
                ) {
                    Text(text = "150 persons were asked")
                }
            }
        }
    }
}

@Composable
@Preview
fun PieChartSimplePreview() {
    TurdusPortfolioTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(TurdusDefault.Padding.small)
            ,
            verticalArrangement = Arrangement.spacedBy(TurdusDefault.Padding.extraLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                "Preferred Programming Languages",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = TurdusDefault.Padding.extraLarge)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ){
                PieChartSimple(
                    modifier = Modifier
                        .size(500.dp),
                    input = listOf(
                        PieChartInput(
                            color = Color.Blue,
                            value = 29,
                            description = "Python"
                        ),
                        PieChartInput(
                            color = Color.Magenta,
                            value = 21,
                            description = "Swift"
                        ),
                        PieChartInput(
                            color = Color.Yellow,
                            value = 32,
                            description = "JavaScript"
                        ),
                        PieChartInput(
                            color = Color.Red,
                            value = 18,
                            description = "Java"
                        ),
                        PieChartInput(
                            color = Color.Cyan,
                            value = 12,
                            description = "Ruby"
                        ),
                        PieChartInput(
                            color = Color.Green,
                            value = 38,
                            description = "Kotlin"
                        ),
                    )
                )
            }
        }
    }
}
