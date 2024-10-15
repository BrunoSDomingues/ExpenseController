package com.em.expensemanager

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expenses")
class Controllers(@Autowired val repo: Repo) {
    @GetMapping()
    fun returnExpenses(): List<Expense> {
        return repo.findAll()
    }

    @GetMapping("/{id}")
    fun getExpenseById(@PathVariable("id") id: String): ResponseEntity<Expense> {
        val expense = repo.findByExpenseId(id)
        return if (expense != null) ResponseEntity.ok(expense) else ResponseEntity
            .notFound().build()
    }
}