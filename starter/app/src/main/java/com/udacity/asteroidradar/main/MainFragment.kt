package com.udacity.asteroidradar.main

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.util.LoadingStatus
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {


    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.statusLoadingWheel.setBackgroundColor(Color.BLACK)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadingStatus.observe(viewLifecycleOwner, Observer { loadingStatus->
            if (loadingStatus.equals(LoadingStatus.LOADING)){
                setProgress(0)
            }else if (loadingStatus.equals(LoadingStatus.DONE)){
                setProgress(10)
            }else if (loadingStatus.equals(LoadingStatus.ERROR)){
                setProgress(10)
                Toast.makeText(context,"Internet Disabled!", Toast.LENGTH_LONG).show()
            }
        })


    }

    fun setProgress(progress: Int){
        binding.statusLoadingWheel.setProgress(progress)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}