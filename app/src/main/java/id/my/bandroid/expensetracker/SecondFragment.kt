package id.my.bandroid.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import id.my.bandroid.expensetracker.databinding.FragmentSecondBinding
import id.my.bandroid.expensetracker.model.Expense
import java.util.Locale

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val vm: ExpenseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val expenseId = arguments?.getString("expenseId")
        var expense: Expense? = null

        binding.toolbar.navigationIcon =
            ContextCompat.getDrawable(requireContext(), R.drawable.baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        if (!expenseId.isNullOrBlank()) {
            expense = vm.expenses.value?.find { it.id == expenseId }
            if (expense != null) {
                binding.amount.editText!!.setText(
                    String.format(
                        Locale.getDefault(),
                        "%.2f",
                        expense.amount
                    )
                )
                binding.description.editText!!.setText(expense.description)
            }
        }

        binding.saveBtn.setOnClickListener {
            if (!binding.amount.editText!!.text.toString().isNullOrBlank()
                && !binding.description.editText!!.text.toString().isNullOrBlank()
            ) {
                if (expenseId.isNullOrEmpty() || expense == null) {
                    vm.addExpense(
                        Expense(
                            amount = binding.amount.editText!!.text.toString().toDouble(),
                            description = binding.description.editText!!.text.toString(),
                        )
                    )
                } else {
                    vm.updateExpense(
                        expense.copy(
                            amount = binding.amount.editText!!.text.toString().toDouble(),
                            description = binding.description.editText!!.text.toString(),
                        )
                    )
                }
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}