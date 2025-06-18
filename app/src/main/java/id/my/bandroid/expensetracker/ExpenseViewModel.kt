package id.my.bandroid.expensetracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.my.bandroid.expensetracker.model.Expense
import java.util.UUID

class ExpenseViewModel : ViewModel() {
    private val _expenses = MutableLiveData<MutableList<Expense>>(mutableListOf())
    val expenses: LiveData<MutableList<Expense>> get() = _expenses

    val totalAmount: LiveData<Double> = MutableLiveData<Double>().apply {
        _expenses.observeForever { expenseList ->
            value = expenseList.sumOf { it.amount }
        }
    }

    fun addExpense(expense: Expense) {
        _expenses.value?.add(expense)
        _expenses.value = _expenses.value
    }

    fun deleteExpense(expense: Expense) {
        _expenses.value = _expenses.value?.filterNot { it.id == expense.id }?.toMutableList()
    }

    fun updateExpense(updatedExpense: Expense) {
        _expenses.value = _expenses.value?.map {
            if (it.id == updatedExpense.id) updatedExpense else it
        }?.toMutableList()
    }

    fun duplicateExpense(original: Expense) {
        val duplicate = original.copy(id = UUID.randomUUID().toString())
        _expenses.value = _expenses.value?.toMutableList()?.apply {
            add(duplicate)
        }
    }

}