package gr.dipae.thesisfitnessapp.ui.base

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.util.ext.drawBehindSystemUI
import gr.dipae.thesisfitnessapp.util.ext.hideSystemUI

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    abstract fun getViewBinding(): VB

    open fun getOrientation(): Int = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT

    open fun getStatusBarType(): StatusBarType = StatusBarType.DARK

    open fun getStatusBarColor(): Int? = null

    open fun getNavigationBarColor(): Int? = null

    open fun isFullscreen(): Boolean = false

    open fun drawBehindSystemUI(): Boolean = false

    open fun isImmersiveSystemUI(): Boolean = false

    open fun isInnerFragment(): Boolean = false

    override fun onResume() {
        if (drawBehindSystemUI()) activity?.drawBehindSystemUI()
        if (isImmersiveSystemUI()) activity?.hideSystemUI()
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // remove splash background
        activity?.window?.setBackgroundDrawable(null)
        super.onViewCreated(view, savedInstanceState)
        if (activity?.requestedOrientation != getOrientation()) {
            activity?.requestedOrientation = getOrientation()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        updateStatusBar()
        _binding = getViewBinding()
        return _binding?.root
    }

    private fun updateStatusBar() {
        if (!isInnerFragment()) {
            updateStatusBarType(getStatusBarType(), getStatusBarColor(), isFullscreen(), drawBehindSystemUI(), isImmersiveSystemUI())
        }
    }

    private fun updateStatusBarType(
        statusBarType: StatusBarType,
        @ColorRes statusBarColor: Int?,
        isFullscreen: Boolean,
        drawBehindSystemUI: Boolean,
        isImmersiveSystemUI: Boolean
    ) {
        when {
            drawBehindSystemUI -> activity?.drawBehindSystemUI()
            isImmersiveSystemUI -> activity?.hideSystemUI()
            statusBarType == StatusBarType.LIGHT -> {
                if (isFullscreen) {
                    activity?.window?.statusBarColor = handleStatusBarColor(statusBarColor)
                    activity?.window?.navigationBarColor = handleNavigationBarColor()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        activity?.window?.decorView?.systemUiVisibility =
                            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
                    }
                } else {
                    activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    context?.let {
                        activity?.window?.statusBarColor = ContextCompat.getColor(it, statusBarColor ?: R.color.white)
                    }
                }
            }

            statusBarType == StatusBarType.DARK -> {
                if (isFullscreen) {
                    activity?.window?.statusBarColor = handleStatusBarColor(statusBarColor)
                    activity?.window?.navigationBarColor = handleNavigationBarColor()
                    activity?.window?.decorView?.systemUiVisibility =
                        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                } else {
                    activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    context?.let {
                        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        activity?.window?.statusBarColor = ContextCompat.getColor(it, statusBarColor ?: R.color.black)
                    }
                }
            }
        }
    }

    private fun handleStatusBarColor(statusBarColor: Int?): Int {
        return if (statusBarColor != null) {
            ContextCompat.getColor(requireContext(), statusBarColor)
        } else {
            Color.TRANSPARENT
        }
    }

    private fun handleNavigationBarColor(): Int {
        return if (getNavigationBarColor() != null) {
            ContextCompat.getColor(requireContext(), getNavigationBarColor()!!)
        } else {
            Color.TRANSPARENT
        }
    }
}

enum class StatusBarType {
    LIGHT, DARK
}