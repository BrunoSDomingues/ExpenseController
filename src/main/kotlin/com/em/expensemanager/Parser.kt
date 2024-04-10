package com.em.expensemanager

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Scanner


class Parser (private val scanner: Scanner){
    fun parseStringOnlyLetters(): String {
        while (true) {
            val input = scanner.nextLine().trim()

            if (input.matches(Regex("^[a-zA-Z]+$"))){
                return input
            }
            else println("Invalid string! (must contain A-Z chars only)")
        }
    }

    fun parseDouble(): Double {
        while (true){
            try {
                return scanner.nextLine().toDouble()
            } catch (e: NumberFormatException){
                println("Value must be a double!")
            }
        }
    }

    fun parseInt(): Int {
        while (true){
            try {
                return scanner.nextLine().toInt()
            } catch (e: NumberFormatException){
                println("Value must be a int!")
            }
        }
    }

    fun getDateInput(prompt: String): LocalDate {
        while (true) {
            try {
                println(prompt)
                val dateString = scanner.nextLine().trim()
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                return LocalDate.parse(dateString, formatter)
            } catch (e: Exception) {
                println("Invalid date! (must be in DD/MM/YYYY format)")
            }
        }
    }
}