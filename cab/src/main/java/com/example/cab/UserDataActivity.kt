package com.example.cab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cab.databinding.UserDataLayoutBinding
import com.example.cab.vm.UserDataViewModel

class UserDataActivity : AppCompatActivity() {

    private lateinit var binding: UserDataLayoutBinding
    private lateinit var viewModel: UserDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.user_data_layout)
        viewModel = ViewModelProvider(this)[UserDataViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.phone.observe(this){
            binding.phoneTextView.text = it
        }

        viewModel.username.observe(this){
            binding.usernameTextView.text = it
        }

        viewModel.changePhone(intent.getStringExtra("PHONE"))
        viewModel.changeUsername(intent.getStringExtra("USERNAME"))

    }
}