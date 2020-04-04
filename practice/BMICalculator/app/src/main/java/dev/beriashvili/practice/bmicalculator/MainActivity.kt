package dev.beriashvili.practice.bmicalculator

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun main(@Suppress("UNUSED_PARAMETER") view: View) {
        val decimalFormat = DecimalFormat("#.####")
        decimalFormat.roundingMode = RoundingMode.CEILING

        val bodyMassIndex = getBMI(weight, height)

        bmi.text = decimalFormat.format(bodyMassIndex)
        evaluation.text = getString(R.string.evaluation, evaluateBMI(bodyMassIndex))
    }

    private fun getBMI(weight: EditText, height: EditText): Double {
        return if (!weight.text.isNullOrEmpty() && !height.text.isNullOrEmpty()) {
            calculateBMI(weight.text, height.text)
        } else {
            calculateBMI(weight.hint, height.hint)
        }
    }

    private fun calculateBMI(weight: CharSequence, height: CharSequence): Double {
        return if (weight.toString().toInt() != 0) {
            (weight.toString().toDouble() / (height.toString().toDouble() / 100).pow(2.0))
        } else {
            0.0
        }
    }

    private fun evaluateBMI(bodyMassIndex: Double): String {
        val categories = HashMap<Pair<Double, Double>, String>()

        categories[Pair(0.0, 15.0)] = "Very severely underweight"
        categories[Pair(15.0, 16.0)] = "Severely underweight"
        categories[Pair(16.0, 18.5)] = "Underweight"
        categories[Pair(18.5, 25.0)] = "Normal (healthy weight)"
        categories[Pair(25.0, 30.0)] = "Overweight"
        categories[Pair(30.0, 35.0)] = "Moderately obese (Obese Class I)"
        categories[Pair(35.0, 40.0)] = "Severely obese (Obese Class II)"
        categories[Pair(40.0, Double.MAX_VALUE)] = "Very severely obese (Obese Class III)"

        for (category in categories) {
            if (bodyMassIndex >= category.key.first && bodyMassIndex <= category.key.second) {
                return category.value
            }
        }

        return ""
    }
}
