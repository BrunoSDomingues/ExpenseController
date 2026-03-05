package com.em.expensemanager

import java.time.LocalDateTime
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("expenses")
data class Expense(
    @Id
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: ObjectId = ObjectId(),
    val description: String = "",
    val place: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val paymentMethod: String = "",
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    val formattedAmount: String
        get() = "R$ $amount"

    override fun toString(): String {
        return "Expense(description='$description', amount=R$ $amount, place=$place, " +
                "createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}