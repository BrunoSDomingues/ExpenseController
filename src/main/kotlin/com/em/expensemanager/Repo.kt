package com.em.expensemanager

import org.springframework.data.mongodb.repository.MongoRepository

interface Repo : MongoRepository<Expense, String> {

    fun findByExpenseId(expenseId: String): Expense?
}