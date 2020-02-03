package ovh.geoffrey_druelle.weatherforecast.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: ViewDataBinding> : Fragment(){

    @LayoutRes
    abstract fun getLayoutResId(): Int

    protected lateinit var binding: T
        private set
    protected lateinit var root: View
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            getLayoutResId(),
            container,
            false
        )

        root = binding.root

        return root
    }
}
