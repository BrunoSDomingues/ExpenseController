package com.em.expensemanager

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var repo: Repo

    @BeforeEach
    fun setUp() {
        repo.deleteAll()
    }

    @Test
    fun `should return all expenses`(){
        repo.save(Expense(description = "Lunch", place = "Restaurant", amount = 25.0, category = "Food", paymentMethod = "Credit Card"))
        repo.save(Expense(description = "Bus", place = "Bus Stop", amount = 5.0, category = "Transport", paymentMethod = "Cash"))

        mockMvc.perform(get("/expenses"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
    }
    @Test
    fun `should return expense by id`() {
        val expense = repo.save(Expense(description = "Lunch", place = "Restaurant", amount = 25.0, category = "Food", paymentMethod = "Credit Card"))

        mockMvc.perform(get("/expenses/${expense.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.description").value("Lunch"))
            .andExpect(jsonPath("$.place").value("Restaurant"))
            .andExpect(jsonPath("$.amount").value(25.0))
    }

    @Test
    fun `should return 404 when expense not found`() {
        mockMvc.perform(get("/expenses/999"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should add a new expense`() {
        val body = """
            {
                "description": "Lunch",
                "place": "Restaurant",
                "amount": 25.0,
                "category": "Food",
                "paymentMethod": "Credit Card"
            }
        """.trimIndent()

        mockMvc.perform(post("/expenses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.description").value("Lunch"))
            .andExpect(jsonPath("$.category").value("Food"))
    }

    @Test
    fun `should update an existing expense`() {
        val expense = repo.save(Expense(description = "Lunch", place = "Restaurant", amount = 25.0, category = "Food", paymentMethod = "Credit Card"))

        val body = """
            {
                "description": "Dinner",
                "place": "Restaurant",
                "amount": 50.0,
                "category": "Food",
                "paymentMethod": "Debit Card"
            }
        """.trimIndent()

        mockMvc.perform(put("/expenses/${expense.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.description").value("Dinner"))
            .andExpect(jsonPath("$.amount").value(50.0))
            .andExpect(jsonPath("$.paymentMethod").value("Debit Card"))
    }

    @Test
    fun `should return total for current month`() {
        repo.save(Expense(description = "Lunch", place = "Restaurant", amount = 25.0, category = "Food", paymentMethod = "Credit Card"))
        repo.save(Expense(description = "Bus", place = "Bus Stop", amount = 5.0, category = "Transport", paymentMethod = "Cash"))

        mockMvc.perform(get("/expenses/total/month"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.total").value("R$ 30.0"))
    }

    @Test
    fun `should return total for category in current month`() {
        repo.save(Expense(description = "Lunch", place = "Restaurant", amount = 25.0, category = "Food", paymentMethod = "Credit Card"))
        repo.save(Expense(description = "Dinner", place = "Restaurant", amount = 30.0, category = "Food", paymentMethod = "Debit Card"))

        mockMvc.perform(get("/expenses/total/category/Food"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.total").value("R$ 55.0"))
    }
}