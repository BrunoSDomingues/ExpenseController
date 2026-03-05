package com.em.expensemanager

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.converter.HttpMessageNotReadableException
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class ErrorResponse(val message: String)

@RestController
@RequestMapping("/expenses")
class Controllers(@field:Autowired val repo: Repo) {
    @GetMapping()
    fun returnExpenses(): List<Expense> {
        return repo.findAll()
    }

    @GetMapping("/{id}")
    fun getExpenseById(@PathVariable id: String): ResponseEntity<Expense> {
        val expense = repo.findByIdOrNull(id)
        return if (expense != null)
            ResponseEntity.ok(expense)
        else
            throw NoSuchElementException("Expense with id '$id' not found")
    }

    @PostMapping
    fun addExpense(@RequestBody request: ExpenseRequest): ResponseEntity<Expense> {
        val expense = Expense(
            description = request.description,
            place = request.place,
            amount = request.amount,
            category = request.category,
            paymentMethod = request.paymentMethod
        )
        val savedExpense = repo.save(expense)
        return ResponseEntity.status(201).body(savedExpense)
    }

    @PutMapping("/{id}")
    fun updateExpense(@PathVariable id: String, @RequestBody request: ExpenseRequest): ResponseEntity<Expense> {
        val expense = repo.findByIdOrNull(id)
            ?: throw NoSuchElementException("Expense with id '$id' not found")
        val savedExpense = repo.save(Expense(
            id = expense.id,
            description = request.description,
            place = request.place,
            amount = request.amount,
            category = request.category,
            paymentMethod = request.paymentMethod,
            createdAt = expense.createdAt,
            updatedAt = LocalDateTime.now()
        ))
        return ResponseEntity.ok(savedExpense)
    }

    @GetMapping("/category/{category}")
    fun getExpensesByCategory(@PathVariable category: String): ResponseEntity<List<Expense>> {
        val expenses = repo.findByCategory(category)
        return if (expenses.isNotEmpty())
            ResponseEntity.ok(expenses)
        else
            throw NoSuchElementException("No expenses found for category '$category'")
    }

    @GetMapping("/payment/{paymentMethod}")
    fun getExpensesByPaymentMethod(@PathVariable paymentMethod: String): ResponseEntity<List<Expense>> {
        val expenses = repo.findByPaymentMethod(paymentMethod)
        return if (expenses.isNotEmpty())
            ResponseEntity.ok(expenses)
        else
            throw NoSuchElementException("No expenses found for payment method '$paymentMethod'")
    }

    @GetMapping("/total/day/{date}")
    fun getTotalForDay(@PathVariable date: String): ResponseEntity<Map<String, Any>> {
        val day = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        val start = day.atStartOfDay()
        val end = day.atTime(LocalTime.MAX)
        val total = repo.findByCreatedAtBetween(start, end).sumOf { it.amount }
        return ResponseEntity.ok(mapOf(
            "date" to date,
            "total" to "R$ $total"
        ))
    }

    @GetMapping("/total/month")
    fun getTotalForCurrentMonth(): ResponseEntity<Map<String, Any>> {
        val now = LocalDateTime.now()
        val start = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
        val end = now.withDayOfMonth(now.month.length(now.toLocalDate().isLeapYear)).withHour(23).withMinute(59).withSecond(59)
        val total = repo.findByCreatedAtBetween(start, end).sumOf { it.amount }
        return ResponseEntity.ok(mapOf(
            "month" to now.month.toString(),
            "year" to now.year.toString(),
            "total" to "R$ $total"
        ))
    }

    @GetMapping("/total/category/{category}")
    fun getTotalForCategory(@PathVariable category: String): ResponseEntity<Map<String, Any>> {
        val now = LocalDateTime.now()
        val start = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
        val end = now.withDayOfMonth(now.month.length(now.toLocalDate().isLeapYear)).withHour(23).withMinute(59).withSecond(59)
        val expenses = repo.findByCategoryAndCreatedAtBetween(category, start, end)
        val total = expenses.sumOf { it.amount }
        return if (expenses.isNotEmpty())
            ResponseEntity.ok(mapOf(
                "category" to category,
                "month" to now.month.toString(),
                "year" to now.year.toString(),
                "total" to "R$ $total"
            ))
        else
            throw NoSuchElementException("No expenses found for category '$category' in ${now.month} ${now.year}")
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(404).body(ErrorResponse(e.message ?: "Not found"))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleBadRequest(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(400).body(ErrorResponse(e.message ?: "Bad request"))
    }
}