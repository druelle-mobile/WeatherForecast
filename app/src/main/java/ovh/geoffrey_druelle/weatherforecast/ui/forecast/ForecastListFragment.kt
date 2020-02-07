package ovh.geoffrey_druelle.weatherforecast.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.weatherforecast.R
import ovh.geoffrey_druelle.weatherforecast.core.BaseFragment
import ovh.geoffrey_druelle.weatherforecast.databinding.ForecastListFragmentBinding
import ovh.geoffrey_druelle.weatherforecast.ui.main.MainActivity
import ovh.geoffrey_druelle.weatherforecast.utils.extension.obs

class ForecastListFragment : BaseFragment<ForecastListFragmentBinding>() {

    companion object {
        fun newInstance() = ForecastListFragment()
    }

    @LayoutRes
    override fun getLayoutResId(): Int = R.layout.forecast_list_fragment

    private lateinit var viewModel: ForecastListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel = getViewModel()
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initListObs()


        return root
    }

    private fun initListObs() {
        viewModel.forecastList.obs(this) {
            initCityObs()
        }
    }

    private fun initCityObs() {
        viewModel.initCityLiveData()
        viewModel.city.obs(this) {
            val title =
                String.format("%s, %s", viewModel.city.value?.name, viewModel.city.value?.country)
            (activity as MainActivity).viewModel.appBarTitle.set(title)
        }
    }

}
