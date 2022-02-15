package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.text = "."
            lastDot = true
            lastNumeric = false
        }
    }

    fun onOperator(view: View) {
        tvInput?.text.let {
            if (lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            val tvValue = tvInput?.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    // substring remove string at index
                    tvValue.substring(1)
                }

                if (tvValue.contains("-")) {
                    var splitString = tvValue.split("-")
                    // 99-1
                    /// [99, -, 1]
                    var firstValue = splitString[0]
                    var secondValue = splitString[2]

                    if (prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }
                    tvInput?.text = removeZeroAfterDot((firstValue.toDouble() - secondValue.toDouble()).toString())
                } else if(tvValue.contains("+")) {
                    var splitString = tvValue.split("+")
                    // 99-1
                    /// [99, -, 1]
                    var firstValue = splitString[0]
                    var secondValue = splitString[2]

                    if (prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }
                    tvInput?.text = removeZeroAfterDot((firstValue.toDouble() + secondValue.toDouble()).toString())
                } else if(tvValue.contains("/")) {
                    var splitString = tvValue.split("/")
                    // 99-1
                    /// [99, -, 1]
                    var firstValue = splitString[0]
                    var secondValue = splitString[2]

                    if (prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }
                    tvInput?.text = removeZeroAfterDot((firstValue.toDouble() / secondValue.toDouble()).toString())
                } else if(tvValue.contains("*")) {
                    var splitString = tvValue.split("*")
                    // 99-1
                    /// [99, -, 1]
                    var firstValue = splitString[0]
                    var secondValue = splitString[2]

                    if (prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }
                    tvInput?.text = removeZeroAfterDot((firstValue.toDouble() * secondValue.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result

        if (value.contains(".0")) {
            // 99.0 ==> 99
            value = result.substring(0, value.length - 2)
        }

        return value
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("-") || value.contains("+")
        }
    }
}