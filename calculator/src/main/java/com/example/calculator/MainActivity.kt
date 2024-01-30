package com.example.calculator

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.model.NumberButton
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.inputEditText)
        editText.showSoftInputOnFocus = false
        editText.requestFocus()
        val numericButtons = arrayOf(
            NumberButton(R.id.button_one, this),
            NumberButton(R.id.button_two, this),
            NumberButton(R.id.button_three, this),
            NumberButton(R.id.button_four, this),
            NumberButton(R.id.button_five, this),
            NumberButton(R.id.button_six, this),
            NumberButton(R.id.button_seven, this),
            NumberButton(R.id.button_eight, this),
            NumberButton(R.id.button_nine, this),
            NumberButton(R.id.button_zero, this)
        )

        setupButtonListeners()
        setupNumberClickListener(numericButtons, editText)
    }

    private val onButtonTouchListener: View.OnTouchListener =
        View.OnTouchListener { view, motionEvent ->
            val button = view as MaterialButton
            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> radiusAnimation(button)
                MotionEvent.ACTION_UP -> view.performClick()
            }
            return@OnTouchListener view.onTouchEvent(motionEvent)
        }

    private fun setupButtonListeners() {
        val rootView = findViewById<ViewGroup>(android.R.id.content)
        setTouchListenerRecursively(rootView)
    }

    private fun setupNumberClickListener(
        numberButtons: Array<NumberButton>,
        editText: EditText
    ){
        numberButtons.forEach {
            it.setOnClickListener(editText)
        }
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



