package com.example.calculator

import android.animation.ValueAnimator
import android.content.res.Configuration
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.vm.CalculatorViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CalculatorViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.snackbarMessage.observe(this){ message ->
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
        }

        binding.inputEditText.showSoftInputOnFocus = false
        binding.inputEditText.requestFocus()

        setupButtonListeners()
        setContentView(view)
    }



    private val onButtonTouchListener: View.OnTouchListener =
        View.OnTouchListener { view, motionEvent ->
            val button = view as MaterialButton
            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> radiusAnimation(button)
                MotionEvent.ACTION_BUTTON_RELEASE -> view.performClick()
            }
            return@OnTouchListener view.onTouchEvent(motionEvent)
        }

    private fun setupButtonListeners() {
        setTouchListenerRecursively(binding.root)
    }


    private fun setTouchListenerRecursively(view: View) {
        if (view is MaterialButton) {
            view.setOnTouchListener(onButtonTouchListener)
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                setTouchListenerRecursively(child)
            }
        }
    }

    private fun radiusAnimation(button: MaterialButton) {
        val animator = ValueAnimator.ofFloat(0.45f, 1f)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            button.cornerRadius = if (isHorizontalLayout()) {
                (value * 150).toInt()
            } else {
                (value * 70).toInt()
            }
        }
        animator.start()
    }

    private fun isHorizontalLayout(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

}

