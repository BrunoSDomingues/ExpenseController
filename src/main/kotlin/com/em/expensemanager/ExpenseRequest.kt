package com.em.expensemanager

data class ExpenseRequest(
    val description: String = "",
    val place: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val paymentMethod: String = ""
)
