package com.example.calculator

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.vm.CalculatorViewModel
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CalculatorViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]
        val view = binding.root
        binding.viewModel = viewModel
        setContentView(view)


        val editText: EditText = findViewById(R.id.inputEditText)
        editText.showSoftInputOnFocus = false
        editText.requestFocus()
        viewModel.currentOperationString.observe(this, Observer { result->
            editText.text.clear()
            editText.text.append(result)
        })

        setupButtonListeners()
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
        val rootView = findViewById<ViewGroup>(android.R.id.content)
        setTouchListenerRecursively(rootView)
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
            button.cornerRadius = (value * 150).toInt()
        }
        animator.start()
    }

}

