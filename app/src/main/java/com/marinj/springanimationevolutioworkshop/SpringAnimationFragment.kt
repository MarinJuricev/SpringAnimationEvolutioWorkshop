package com.marinj.springanimationevolutioworkshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.spring_animation.*

class SpringAnimationFragment : Fragment() {

    private val springForce: SpringForce by lazy(LazyThreadSafetyMode.NONE) {
        SpringForce(0f).apply {
            stiffness = SpringForce.STIFFNESS_MEDIUM
            dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        }
    }

    private val springAnimationTranslationX: SpringAnimation by lazy(LazyThreadSafetyMode.NONE) {
        imageToAnimate.springAnimationOf(DynamicAnimation.TRANSLATION_X).setSpring(springForce)
    }

    private val springAnimationTranslationY: SpringAnimation by lazy(LazyThreadSafetyMode.NONE) {
        imageToAnimate.springAnimationOf(DynamicAnimation.TRANSLATION_Y).setSpring(springForce)
    }

    var xDiffInTouchPointAndViewTopLeftCorner: Float = -1f
    var yDiffInTouchPointAndViewTopLeftCorner: Float = -1f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.spring_animation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        imageToAnimate.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {

                MotionEvent.ACTION_DOWN -> {
                    xDiffInTouchPointAndViewTopLeftCorner = motionEvent.rawX - view.x
                    yDiffInTouchPointAndViewTopLeftCorner = motionEvent.rawY - view.y

                    springAnimationTranslationX.cancel()
                    springAnimationTranslationY.cancel()
                }

                MotionEvent.ACTION_MOVE -> {
                    imageToAnimate.x = motionEvent.rawX - xDiffInTouchPointAndViewTopLeftCorner
                    imageToAnimate.y = motionEvent.rawY - yDiffInTouchPointAndViewTopLeftCorner
                }

                MotionEvent.ACTION_UP -> {
                    springAnimationTranslationX.start()
                    springAnimationTranslationY.start()
                }
            }
            true
        }
    }
}
