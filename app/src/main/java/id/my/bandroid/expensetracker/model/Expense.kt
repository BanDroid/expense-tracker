package id.my.bandroid.expensetracker.model

import java.util.UUID

data class Expense(
    val id: String = UUID.randomUUID().toString(),
    val description: String,
    val amount: Double
)