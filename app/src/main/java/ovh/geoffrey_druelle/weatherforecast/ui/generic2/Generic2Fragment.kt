package ovh.geoffrey_druelle.weatherforecast.ui.generic2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ovh.geoffrey_druelle.weatherforecast.R
import ovh.geoffrey_druelle.weatherforecast.core.BaseFragment
import ovh.geoffrey_druelle.weatherforecast.databinding.Generic2FragmentBinding
import ovh.geoffrey_druelle.weatherforecast.ui.generic2.Generic2ViewModel

class Generic2Fragment : BaseFragment<Generic2FragmentBinding>() {

    companion object {
        fun newInstance() = Generic2Fragment()
    }

    private lateinit var viewModel: Generic2ViewModel

    override fun getLayoutResId(): Int = R.layout.generic2_fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel = getViewModel()
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initObservers()

        return root
    }

    private fun initObservers() {
        viewModel.isGeneric.observe(viewLifecycleOwner, Observer { boolean ->
            if (boolean) switchGenericBooleanValue()
        })
    }

    private fun switchGenericBooleanValue() {
        viewModel.switchGenericBooleanValue()
    }
}
