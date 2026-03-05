package com.em.expensemanager

import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

interface Repo : MongoRepository<Expense, String> {
    fun findByCreatedAtBetween(start: LocalDateTime, end: LocalDateTime): List<Expense>
    fun findByCategory(category: String): List<Expense>
    fun findByPaymentMethod(paymentMethod: String): List<Expense>
    fun findByCategoryAndCreatedAtBetween(category: String, start: LocalDateTime, end: LocalDateTime): List<Expense>
}