package com.alexschutz.scrybary.view.settings

import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDialogFragment
import com.alexschutz.scrybary.databinding.FragmentSuggestionBinding


class SuggestionDialogFragment : AppCompatDialogFragment(), SubmitClickListener {

    private lateinit var binding: FragmentSuggestionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSuggestionBinding.inflate(inflater, container, false)

        binding.submitListener = this

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Set window width based on device size
        val window: Window? = dialog?.window
        val size = Point()

        // Support older display methods for the time being.
        val display: Display? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) context?.display
            else activity?.windowManager?.defaultDisplay

        display?.getRealSize(size)

        val width: Int = size.x

        window?.setLayout((width * 0.75).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
    }

    override fun onSubmitClicked(v: View) {

        var hasErrors = false

        with (binding) {

            tvNameError.visibility =
                if (etName.text.isEmpty()) {
                    hasErrors = true
                    View.VISIBLE

                } else {
                    View.GONE
                }

            tvEmailError.visibility =
                if (etEmail.text.isEmpty()) {

                    hasErrors = true
                    View.VISIBLE
                } else {
                    hasErrors = hasErrors && false
                    View.GONE
                }

            tvCommentsError.visibility =
                if (etComments.text.isEmpty()) {
                    hasErrors = true
                    View.VISIBLE
                } else {
                    hasErrors = hasErrors && false
                    View.GONE
                }
        }
    }
}