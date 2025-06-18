package id.my.bandroid.expensetracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.my.bandroid.expensetracker.databinding.ItemLayoutBinding
import id.my.bandroid.expensetracker.model.Expense
import java.util.Locale

class ExpenseAdapter(
    private val onItemClick: (Expense) -> Unit,
    private val onItemLongClick: (Expense) -> Unit
) :
    ListAdapter<Expense, ExpenseAdapter.ViewHolder>(ItemCallback) {
    inner class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val ItemCallback = object : DiffUtil.ItemCallback<Expense>() {
            override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = getItem(position)
        holder.binding.apply {
            amount.text = "Rp. ${String.format(Locale.getDefault(), "%.2f", expense.amount)}"
            description.text = expense.description
            container.setOnClickListener {
                onItemClick(expense)
            }
            container.setOnLongClickListener {
                onItemLongClick(expense)
                true
            }
        }
    }


}