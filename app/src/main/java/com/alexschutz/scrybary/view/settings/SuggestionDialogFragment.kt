package com.alexschutz.scrybary.view.settings

import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.alexschutz.scrybary.databinding.FragmentSuggestionBinding


class SuggestionDialogFragment : AppCompatDialogFragment(), SubmitClickListener {

    private lateinit var binding: FragmentSuggestionBinding
    private var canResend = true

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

            tvSubjectError.visibility =
                if (etSubject.text.isEmpty()) {

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

            if (!hasErrors) {
                dismiss()
                sendEmail()
                Toast.makeText(context, "Thank you for your feedback!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendEmail() {

        val intent = Intent(Intent.ACTION_SEND)

        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("manadorkapp@gmail.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, binding.etSubject.text.toString())
        intent.putExtra(Intent.EXTRA_TEXT, binding.etComments.text.toString())
        intent.type = "message/rfc822"

        startActivity(Intent.createChooser(intent, "Select email"))
    }
}