package ovh.geoffrey_druelle.weatherforecast.ui.forecast

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.weatherforecast.R
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication
import ovh.geoffrey_druelle.weatherforecast.core.BaseFragment
import ovh.geoffrey_druelle.weatherforecast.databinding.ForecastListFragmentBinding
import ovh.geoffrey_druelle.weatherforecast.ui.main.MainActivity
import ovh.geoffrey_druelle.weatherforecast.utils.extension.obs
import timber.log.Timber

class ForecastListFragment : BaseFragment<ForecastListFragmentBinding>() {

    @LayoutRes
    override fun getLayoutResId(): Int = R.layout.forecast_list_fragment

    private lateinit var viewModel: ForecastListViewModel

    private var exit: Boolean = false
    private var swipeCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel = getViewModel()
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initListObs()
        setSwipeRefreshLayout()
        implementBackCallback()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOtherObs()
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            swipeCount += 1
            if (swipeCount > 0)
                viewModel.requestNewForecastDatas(viewModel.liveCityId.value!!)
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun initOtherObs() {
        viewModel.liveCityId.obs(this) { value ->
            Timber.d("liveCityId = $value")
            viewModel.checkForNewForecastRequest(value)
        }
    }

    private fun initListObs() {
        viewModel.forecastList.obs(this) {
            initCityObs()
        }
    }

    private fun initCityObs() {
        viewModel.initCityLiveData()
        viewModel.city?.obs(this) {
            val title =
                String.format(getString(R.string.city_country_s), viewModel.city?.value?.name, viewModel.city?.value?.country)
            (activity as MainActivity).viewModel.appBarTitle.set(title)
        }
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
            Toast.makeText(
                WeatherForecastApplication.appContext,
                getString(R.string.press_back),
                Toast.LENGTH_SHORT
            ).show()
            exit = true
            Handler().postDelayed({ exit = false }, 3000)
        }
    }
}


