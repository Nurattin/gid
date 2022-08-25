package com.travel.gid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.travel.gid.R
import com.travel.gid.databinding.HomeFragmentBinding
import com.travel.gid.ui.home.compose.HomeAppBar
import com.travel.gid.ui.home.compose.back_layer.HomeBackLayerContent
import com.travel.gid.ui.home.compose.back_layer.HomeScreen
import com.travel.gid.ui.home.compose.front_layer.GidScreen
import com.travel.gid.ui.home.compose.front_layer.TourScreen
import com.travel.gid.ui.home.view_model.HomeViewModel
import com.travel.gid.utils.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ANIMATED_CONTENT_ANIMATION_DURATION = 300


@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var networkConnection: ConnectionLiveData
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: HomeFragmentBinding

    @OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

        return ComposeView(requireContext()).apply {
            setContent {
                BackdropScaffold(

                    scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
                    frontLayerScrimColor = Color.Unspecified,
                    backLayerBackgroundColor = Color.White,
                    appBar = { HomeAppBar() },
                    backLayerContent = {
                        HomeBackLayerContent(
                            onClickChangeCategories = { newScreenPos ->
                                viewModel.changeCategoriesScreen(newScreenPos)
                            },
                            onRefresh = { viewModel.refreshData() }
                        )
                    },
                    frontLayerContent = {
                        AnimatedContent(
                            targetState = viewModel.currentScreenPosition.value,
                            transitionSpec = {
                                val direction = if (initialState.ordinal < targetState.ordinal)
                                    AnimatedContentScope.SlideDirection.Left else AnimatedContentScope
                                    .SlideDirection.Right

                                slideIntoContainer(
                                    towards = direction,
                                    animationSpec = tween(ANIMATED_CONTENT_ANIMATION_DURATION)
                                ) with
                                        slideOutOfContainer(
                                            towards = direction,
                                            animationSpec = tween(
                                                ANIMATED_CONTENT_ANIMATION_DURATION
                                            )
                                        ) using SizeTransform(
                                    clip = false,
                                    sizeAnimationSpec = { _, _ ->
                                        tween(
                                            ANIMATED_CONTENT_ANIMATION_DURATION,
                                            easing = EaseInOut
                                        )
                                    }
                                )
                            }
                        ) { targetState ->
                            when (targetState) {
                                HomeScreen.TourScreen -> TourScreen(
                                    onClickShowAllTour = { findNavController().navigate(R.id.tourFragment) }
                                )
                                HomeScreen.GidScreen -> {
                                    viewModel.getGidList()
                                    GidScreen()
                                }
                                HomeScreen.PlaceScreen -> {}
                                HomeScreen.HotelScreen -> {}
                            }
                        }
                    },
                    frontLayerElevation = 5.dp,
                    frontLayerShape = RoundedCornerShape(10.dp)
                ) {
                }
            }
        }
    }
}