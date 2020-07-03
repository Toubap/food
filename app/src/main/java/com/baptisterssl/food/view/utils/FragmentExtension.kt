package com.baptisterssl.food.view.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

fun FragmentManager.replaceFragment(fragment: Fragment, @IdRes fragmentContainer: Int) {
    commit { replace(fragmentContainer, fragment) }
}