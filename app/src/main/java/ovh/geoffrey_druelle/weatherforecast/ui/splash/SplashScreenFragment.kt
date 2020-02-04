package ovh.geoffrey_druelle.weatherforecast.ui.splash

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.weatherforecast.R
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.appContext
import ovh.geoffrey_druelle.weatherforecast.core.BaseFragment
import ovh.geoffrey_druelle.weatherforecast.databinding.SplashScreenFragmentBinding

class SplashScreenFragment : BaseFragment<SplashScreenFragmentBinding>() {

    companion object {
        fun newInstance() = SplashScreenFragment()
    }

    private lateinit var viewModel: SplashScreenViewModel

    @LayoutRes
    override fun getLayoutResId(): Int = R.layout.splash_screen_fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel = getViewModel()
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val splashLayout = root.findViewById<ConstraintLayout>(R.id.splash_layout)
        initBackgroundAnimation(splashLayout)
        initImageAnimation(splashLayout)
        initObservers()

        return root
    }

    private fun initObservers() {
//        viewModel.navToHome.obs(this, {
//
//        })
//        viewModel.isConnected.obs(this) { b ->
//            if (!b) showNoConnectionSnackBar()
//        }
//        viewModel.succeedRequestForDatas.obs(this) { b ->
//            if (!b) showRetrySnackBar()
//        }
//        viewModel.isConnectionNeeded.obs(this) { b ->
//            if (b) show
//        }
    }

    private fun initBackgroundAnimation(splashLayout: ConstraintLayout) {
        val animationDrawable = splashLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    private fun initImageAnimation(splashLayout: ConstraintLayout) {
        val sunImage = splashLayout.findViewById<ImageView>(R.id.sun_image)
        val rotationAnimation: Animation = AnimationUtils.loadAnimation(appContext, R.anim.rotate)
        sunImage.startAnimation(rotationAnimation)

    }
}
