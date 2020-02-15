package ovh.geoffrey_druelle.weatherforecast.ui.splash

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.weatherforecast.R
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.appContext
import ovh.geoffrey_druelle.weatherforecast.core.BaseFragment
import ovh.geoffrey_druelle.weatherforecast.databinding.SplashScreenFragmentBinding
import ovh.geoffrey_druelle.weatherforecast.ui.main.MainActivity
import ovh.geoffrey_druelle.weatherforecast.utils.extension.obs

class SplashScreenFragment : BaseFragment<SplashScreenFragmentBinding>() {

    private lateinit var viewModel: SplashScreenViewModel

    private var exit: Boolean = false

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
        implementBackCallback()

        return root
    }

    private fun initObservers() {
        viewModel.navToHome.obs(this) { b ->
            if (b) navigateToHome()
        }
        viewModel.isConnected.obs(this) { b ->
            if (!b) showNoConnectionSnackBar()
        }
        viewModel.noDataNoConnection.obs(this) { b ->
            if (b) showNoDataNoConnectionSnackbar()
        }
//        viewModel.succeedRequestForDatas.obs(this) { b ->
//            if (!b) showRetrySnackBar()
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

    private fun showNoConnectionSnackBar() {
        view?.let {
            Snackbar.make(it, getString(R.string.net_error), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.yes)) { navigateToHome() }
                .setAction(getString(R.string.quit)) {
                    exit = true
                    quitApp()
                }
                .show()
        }
    }

    private fun showNoDataNoConnectionSnackbar() {
        view?.let {
            Snackbar.make(it, getString(R.string.no_data_no_internet), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.quit)) {
                    exit = true
                    quitApp()
                }
                .show()
        }
    }

    private fun navigateToHome() {
        val action = R.id.action_splashScreenFragment_to_homeFragment
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun implementBackCallback() {
        val onBackPressedCallback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    quitApp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun quitApp() {
        if (exit) MainActivity.instance.finish()
        else {
            Toast.makeText(appContext, getString(R.string.press_back), Toast.LENGTH_SHORT).show()
            exit = true
            Handler().postDelayed({ exit = false }, 3000)
        }
    }
}
