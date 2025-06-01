package com.example.tipcalculator

import org.junit.Test
import java.text.NumberFormat
import kotlin.test.assertEquals

class TipCalculatorTests {

    @Test
    fun calculateTip_20PrecentNoRoundup() {
        val amount = 10.00
        val tipPercent = 20.00
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = calculateTip(amount = amount, tipPercent = tipPercent, roundUp = false)
        assertEquals(expectedTip, actualTip)
    }
}