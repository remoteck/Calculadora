package br.ggperroni.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var lastNumeric: Boolean = false
    private var lastFloat: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastFloat = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onFloat(view: View) {
        if(lastNumeric && !lastFloat) {
            tvInput?.append(".")
            lastNumeric = false
            lastFloat = true
        }
    }

    fun onOperator(view: View) {
        //checa se um número foi digitado e um operador foi digitado, se sim não é possível digitar mais um operador
        tvInput?.text?.let {
            if(!isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastFloat = false
            }

        }
    }

    fun onEqual(view: View) {
        if(lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try {
                if(tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")) {

                    //pega os 2 valores do input e separa em array
                    val splitValue = tvValue.split("-")

                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }

                    //realiza a subtração
                    tvInput?.text = removeZeroAfterFloat((firstValue.toDouble() - secondValue.toDouble()).toString())

                } else if(tvValue.contains("+")) {

                    //pega os 2 valores do input e separa em array
                    val splitValue = tvValue.split("+")

                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }

                    //realiza a soma
                    tvInput?.text = removeZeroAfterFloat((firstValue.toDouble() + secondValue.toDouble()).toString())

                } else if(tvValue.contains("/")) {

                    //pega os 2 valores do input e separa em array
                    val splitValue = tvValue.split("/")

                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }

                    //realiza a divisão
                    tvInput?.text = removeZeroAfterFloat((firstValue.toDouble() / secondValue.toDouble()).toString())

                } else if(tvValue.contains("*")) {

                    //pega os 2 valores do input e separa em array
                    val splitValue = tvValue.split("*")

                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }

                    //realiza a multiplicação
                    tvInput?.text = removeZeroAfterFloat((firstValue.toDouble() * secondValue.toDouble()).toString())
                }

            } catch(e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterFloat(result: String): String {
        var value = result

        if(result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }

    private fun isOperatorAdded(value: String): Boolean {
        if(value.startsWith("-") && value.length == 1) {
            return false
        } else if(value.contains("/") || value.contains("*") || value.contains("+")) {
            return true
        } else if(value.length > 1) {
            val temp = value.subSequence(1, value.length - 1)
            return temp.contains("-")
        } else {
            return false
        }
    }
}