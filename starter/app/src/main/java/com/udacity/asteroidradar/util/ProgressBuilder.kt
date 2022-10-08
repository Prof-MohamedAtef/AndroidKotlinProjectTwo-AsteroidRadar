package com.udacity.asteroidradar.util

import android.os.Handler
import android.view.View
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class ProgressBuilder {

    companion object{
        private var i = 0
        private val handler = Handler()

        fun showProgress(binding: FragmentMainBinding) {
            binding.statusLoadingWheel?.visibility = View.VISIBLE
            binding.tvProgressValue.visibility = View.VISIBLE
            i = binding.statusLoadingWheel!!.progress
            Thread(Runnable {
                while (i < 100) {
                    i += 1
                    // Update the progress bar and display the current value
                    handler.post(Runnable {
                        binding.statusLoadingWheel!!.progress = i
                        // setting current progress to the textview
                        binding.tvProgressValue!!.text =
                            i.toString() + "/" + binding.statusLoadingWheel!!.max
                    })
                    try {
                        Thread.sleep(100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                binding.statusLoadingWheel!!.visibility = View.INVISIBLE
                binding.tvProgressValue.visibility = View.INVISIBLE

            }).start()
        }
    }
}