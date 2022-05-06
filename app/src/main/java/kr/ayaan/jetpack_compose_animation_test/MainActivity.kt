package kr.ayaan.jetpack_compose_animation_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import kr.ayaan.jetpack_compose_animation_test.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var state: LazyListState
//    val upAnimatedFloat = by remember { Animatable(-20f) }
//    val downAnimatedFloat = remember { Animatable(20f) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.testComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                var itemsListState by remember { mutableStateOf(arrayListOf(0, 1, 2, 3, 4, 5,6,7,8,9)) }
                state = rememberLazyListState()
                val upAnimationY = rememberInfiniteTransition().animateFloat(
                    initialValue = 20f,
                    targetValue = -20f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )
                val downAnimationY = rememberInfiniteTransition().animateFloat(
                    initialValue = -20f,
                    targetValue = 20f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )

//                val upAnimatedFloat = remember { Animatable(-20f) }
//                val downAnimatedFloat = remember { Animatable(20f) }
//                Log.e("ASdasd","sadasdasd")
////                LaunchedEffect(upAnimatedFloat, downAnimatedFloat) {
//                    downAnimatedFloat.animateTo(
//                        targetValue = -20f, animationSpec = infiniteRepeatable(
//                            animation = tween(1000, easing =  LinearEasing),
//                            repeatMode = RepeatMode.Reverse
//                        )
//                    )
//
//                    upAnimatedFloat.animateTo(
//                        targetValue = 20f, animationSpec = infiniteRepeatable(
//                            animation = tween(1000, easing =  LinearEasing),
//                            repeatMode = RepeatMode.Reverse
//                        )
//                    )
//                }

                MaterialTheme {
                    LazyRow(
                        state = state,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        items(itemsListState.size,
                            key = { it }) {
                            val odd = it % 2 == 0
                            AnimationCard(if(odd) upAnimationY.value else downAnimationY.value)
                        }
                    }
                }
            }
        }

    }

    @Composable
    fun AnimationCard(y: Float) {

        val defaultWidth = 150.dp
        val defaultHeight = 200.dp


        Card(
            modifier = Modifier
                .size(
                    width = defaultWidth,
                    height = defaultHeight
                )
                .offset(y = y.dp) // offSet 으로 위치값 조절
                .background(color = Color.Black)
        ) {
            Text("Animation Card")
        }
    }
}