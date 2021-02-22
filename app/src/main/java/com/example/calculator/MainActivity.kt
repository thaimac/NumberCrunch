package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException
import android.media.MediaPlayer

class MainActivity : AppCompatActivity() {
    lateinit var input: TextView //keeps track of and displays user input
    var lastKeyNumeric: Boolean = false //flag to see if last key pressed was a number
    var stateError: Boolean = false
    var lastDot: Boolean = false //flag to see if last key pressed was the decimal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = findViewById(R.id.textView) //bind input to textview
    }

    //function to append a number
    fun onNum(view: View) {
        if (stateError) {
            input.text = (view as Button).text
            stateError = false
        } else {
            input.append((view as Button).text)
        }
        lastKeyNumeric = true
    }

    //function to append a decimal
    fun onDec(view: View) {
        if(lastKeyNumeric && !stateError && !lastDot) {
            input.append(".")
            lastKeyNumeric = false
            lastDot = true
        }
    }

    //function to append an operator
    fun helloOperator(view: View) {
        if(lastKeyNumeric && !stateError) {
            input.append((view as Button).text)
            lastKeyNumeric = false
            lastDot = false
        }
    }

    //function to clear the screen
    fun clearView(view: View) {
        this.input.text = ""
        lastKeyNumeric = false
        lastDot = false
        stateError = false
    }

    //function to delete most recently entered input
    fun backspace(view: View) {
        if(input.length() == 0) {
            return
        } else {
            this.input.text = input.text.substring(0, input.length() - 1)
        }
    }

    //function to calculate given expression 
    fun calculate(view: View) {
        if (lastKeyNumeric && !stateError) {
            val text = input.text.toString()
            val expr = ExpressionBuilder(text).build()
            try {
                val result = expr.evaluate()
                input.text = result.toString()
                lastDot = true
            } catch (ex: ArithmeticException) {
                input.text = "Invalid expression"
                stateError = true
                lastKeyNumeric = false
            }
        }
    }
}