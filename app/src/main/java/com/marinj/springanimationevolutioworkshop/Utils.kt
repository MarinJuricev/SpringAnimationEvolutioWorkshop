package com.marinj.springanimationevolutioworkshop

import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

fun <T> T.springAnimationOf(
    property: FloatPropertyCompat<T>,
    finalPosition: Float = Float.NaN
): SpringAnimation {
    return if (finalPosition.isNaN()) {
        SpringAnimation(this, property)
    } else {
        SpringAnimation(this, property, finalPosition)
    }
}

inline fun SpringAnimation.withSpringForceProperties(func: SpringForce.() -> Unit): SpringAnimation {
    if (spring == null) {
        spring = SpringForce()
    }
    spring.func()
    return this
}

