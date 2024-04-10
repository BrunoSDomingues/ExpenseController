package com.em.expensemanager

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("expenses")
data class Expense (
    @Id
    val id: ObjectId = ObjectId(),
    val description: String = "",
    val amount: Double = 0.0,
    @Field("expense_id")
    val expenseId: String = ""
) {
    override fun toString(): String {
        return "Expense(description='$description', amount=R$ $amount)"
    }
}