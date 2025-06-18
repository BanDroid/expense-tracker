package id.my.bandroid.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.my.bandroid.expensetracker.databinding.BottomsheetMenuBinding

enum class MenuCallback {
    DUPLICATE,
    DELETE,
}

class ExpenseBottomSheet(
    private val callback: (type: MenuCallback) -> Unit
) : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "ExpenseBottomSheet"
    }

    private var _binding: BottomsheetMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.menuDuplicate.setOnClickListener {
            callback(MenuCallback.DUPLICATE)
            dismiss()
        }
        binding.menuDelete.setOnClickListener {
            callback(MenuCallback.DELETE)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}