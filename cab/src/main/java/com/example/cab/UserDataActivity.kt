package com.example.cab

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cab.constants.ActionConstants
import com.example.cab.constants.IntentKeys
import com.example.cab.contracts.RouteSettingActivityContract
import com.example.cab.databinding.UserDataLayoutBinding
import com.example.cab.vm.UserDataViewModel
import com.google.android.material.snackbar.Snackbar

class UserDataActivity : AppCompatActivity() {

    private lateinit var binding: UserDataLayoutBinding
    private lateinit var viewModel: UserDataViewModel

    private val activityLaunch = registerForActivityResult(RouteSettingActivityContract()) {
        when (it) {
            ActionConstants.KEY_SUCCESSFUL -> {
                enableCallTaxiButton()
                Log.d("departure_", intent.getStringExtra(IntentKeys.DEPARTURE).toString())
                getRoute()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.user_data_layout)

        viewModel = ViewModelProvider(this)[UserDataViewModel::class.java]

        binding.viewModel = viewModel

        viewModel.phone.observe(this) {
            binding.phoneTextView.text = it
        }

        viewModel.username.observe(this) {
            binding.usernameTextView.text = it
        }

        viewModel.route.observe(this) {
            binding.infoTextView.text = buildString {
                append(it.first)
                append(it.second)
            }
        }

        viewModel.changePhone(intent.getStringExtra(IntentKeys.PHONE))
        viewModel.changeUsername(intent.getStringExtra(IntentKeys.USERNAME))


        binding.setPathButton.setOnClickListener {
            activityLaunch.launch("")
        }

        binding.callTaxiButton.setOnClickListener {
            Snackbar.make(binding.root, "taxi ", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun enableCallTaxiButton() {
        binding.callTaxiButton.isEnabled = true
    }

    private fun getRoute() {
        viewModel.setRoute(
            Pair(
                intent.getStringExtra(IntentKeys.DEPARTURE),
                intent.getStringExtra(IntentKeys.ARRIVAL)
            )
        )
        Log.d("departure", intent.getStringExtra(IntentKeys.DEPARTURE).toString())
    }
}