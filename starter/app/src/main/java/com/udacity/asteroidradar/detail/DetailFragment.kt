package com.udacity.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid

        binding.asteroid = asteroid

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        binding.activityMainImageOfTheDay.contentDescription=context?.getString(R.string.image_of_the_day)
        binding.closeApproachDateTitle.contentDescription=context?.getString(R.string.close_approach_data_title)
        binding.absoluteMagnitudeTitle.contentDescription=context?.getString(R.string.absolute_magnitude_title)
        binding.helpButton.contentDescription=context?.getString(R.string.help)
        binding.estimatedDiameterTitle.contentDescription=context?.getString(R.string.estimated_diameter_title)
        binding.relativeVelocityTitle.contentDescription=context?.getString(R.string.relative_velocity_title)
        binding.distanceFromEarthTitle.contentDescription=context?.getString(R.string.distance_from_earth_title)

        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
