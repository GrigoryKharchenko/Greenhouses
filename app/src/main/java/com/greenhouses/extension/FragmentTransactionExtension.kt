package com.greenhouses.extension

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace

inline fun <reified F : Fragment> Fragment.addFragmentWithArgs(containerId: Int, args: Bundle) {
    parentFragmentManager.commit {
        setReorderingAllowed(true)
        addToBackStack(F::class.java.simpleName)
        replace<F>(containerId, args = args)
    }
}

inline fun <reified F : Fragment> Fragment.replaceFragmentWithArgs(containerId: Int, args: Bundle) {
    parentFragmentManager.commit {
        setReorderingAllowed(true)
        replace<F>(containerId, args = args)
    }
}
