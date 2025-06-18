package id.my.bandroid.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import id.my.bandroid.expensetracker.adapter.ExpenseAdapter
import id.my.bandroid.expensetracker.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val vm: ExpenseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.totalAmount.observe(viewLifecycleOwner) { total ->
            binding.collapsingToolbar.title = "Total: Rp. ${
                String.format(
                    "%.2f",
                    total
                )
            }"
        }
        vm.expenses.observe(viewLifecycleOwner) { expenses ->
            (binding.expenseList.adapter as ExpenseAdapter).submitList(expenses)
        }

        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider_vertical)
            ?.let { dividerItemDecoration.setDrawable(it) }
        binding.expenseList.adapter = ExpenseAdapter(
            onItemClick = { expense ->
                val bundle = bundleOf(
                    "expenseId" to expense.id
                )
                findNavController().navigate(
                    R.id.action_FirstFragment_to_SecondFragment,
                    args = bundle
                )
            },
            onItemLongClick = { expense ->
                val menuBottomSheet = ExpenseBottomSheet { type ->
                    when (type) {
                        MenuCallback.DUPLICATE -> vm.duplicateExpense(expense)
                        MenuCallback.DELETE -> vm.deleteExpense(expense)
                    }
                }
                menuBottomSheet.show(parentFragmentManager, ExpenseBottomSheet.TAG)
            }
        )
        binding.expenseList.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}