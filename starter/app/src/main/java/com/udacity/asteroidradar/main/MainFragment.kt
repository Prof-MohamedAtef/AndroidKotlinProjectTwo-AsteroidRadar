package com.udacity.asteroidradar.main

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapter.AsteroidsRecyclerAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.util.LoadingStatus
import com.udacity.asteroidradar.util.ProgressBuilder.Companion.showProgress
import kotlinx.coroutines.launch

class MainFragment : Fragment() {


    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(requireActivity().application)).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.statusLoadingWheel.setBackgroundColor(Color.BLACK)

        binding.asteroidRecycler.adapter=AsteroidsRecyclerAdapter(AsteroidsRecyclerAdapter.RecyclerClickListener{
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        },null)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadingStatus.observe(viewLifecycleOwner, Observer { loadingStatus->
            if (loadingStatus.equals(LoadingStatus.LOADING)){
                showProgress(binding)
            }else if (loadingStatus.equals(LoadingStatus.DONE)){
                binding.statusLoadingWheel?.visibility = View.INVISIBLE
                binding.tvProgressValue.visibility=View.INVISIBLE
            }else if (loadingStatus.equals(LoadingStatus.ERROR)){
                binding.statusLoadingWheel?.visibility = View.INVISIBLE
                binding.tvProgressValue.visibility=View.INVISIBLE
                Toast.makeText(context,"Internet Disabled!", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.picOfDay.observe(viewLifecycleOwner, Observer { picOfDayList->
            showImageOfDay(picOfDayList)
        })

        viewModel.asteroidResponse.observe(viewLifecycleOwner, Observer { asteroidsList->
            populateRecyclerView(asteroidsList)
        })
    }



    private fun showImageOfDay(picOfDayList: List<PictureOfDay>?) {
        Picasso.with(requireContext()).load(picOfDayList?.get(0)?.url).placeholder(R.drawable.placeholder_picture_of_day).into(binding.activityMainImageOfTheDay)
        binding.textView.setText(picOfDayList?.get(0)?.title)
    }

    private fun populateRecyclerView(asteroidsList: List<Asteroid>?) {
        binding.asteroidRecycler.adapter= asteroidsList?.let {
            AsteroidsRecyclerAdapter(AsteroidsRecyclerAdapter.RecyclerClickListener{
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
            }, it)
        }
    }

    fun setProgress(progress: Int){
        binding.statusLoadingWheel.setProgress(progress)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewLifecycleOwner.lifecycleScope.launch {
            when(item.itemId){
                R.id.view_week_asteroids -> viewModel.returnWeekData()
                R.id.view_today_asteroids -> viewModel.returnTodayData()
                R.id.view_saved_asteroids -> viewModel.returnSavedAsteroids()
            }
        }
        return true
    }
}