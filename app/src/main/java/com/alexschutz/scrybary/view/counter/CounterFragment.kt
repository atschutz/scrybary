package com.alexschutz.scrybary.view.counter

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.navigation.Navigation
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentCounterBinding
import com.alexschutz.scrybary.view.BackButtonFragment
import kotlin.random.Random

class CounterFragment : BackButtonFragment(), FlipClickListener {

    private lateinit var binding: FragmentCounterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCounterBinding.inflate(inflater, container, false)

        binding.backListener = this
        binding.flipListener = this

        with(binding) {

            // Quick and dirty way to set all the keys and system preferences.
            bottomLifeCounter.setButtonsWithKey(getString(R.string.p1_life))
            topLifeCounter.setButtonsWithKey(getString(R.string.p2_life))

            for (child in bottomBtnContainer.findViewById<LinearLayout>(R.id.ll_button_holder).children) {
                if (child.id == R.id.start_counter) (child as CounterButton).setButtonsWithKey(
                    getString(R.string.p1_box_1)
                )
                if (child.id == R.id.middle_counter) (child as CounterButton).setButtonsWithKey(
                    getString(R.string.p1_box_2)
                )
                if (child.id == R.id.end_counter) (child as CounterButton).setButtonsWithKey(
                    getString(R.string.p1_box_3)
                )
            }

            for (child in topBtnContainer.findViewById<LinearLayout>(R.id.ll_button_holder).children) {
                if (child.id == R.id.start_counter) (child as CounterButton).setButtonsWithKey(
                    getString(R.string.p2_box_1)
                )
                if (child.id == R.id.middle_counter) (child as CounterButton).setButtonsWithKey(
                    getString(R.string.p2_box_2)
                )
                if (child.id == R.id.end_counter) (child as CounterButton).setButtonsWithKey(
                    getString(R.string.p2_box_3)
                )
            }

            btnRefresh.setOnClickListener {



                topBtnContainer.refresh()
                bottomBtnContainer.refresh()
                topLifeCounter.refresh()
                bottomLifeCounter.refresh()
            }
        }

        return binding.root
    }

    override fun onBackPressed(v: View) {
        super.onBackPressed(v)
        Navigation.findNavController(v).navigate(R.id.action_counterFragment_to_menuFragment)
    }

    override fun onFlipClicked(v: View) {

        with(binding.layoutCoin) {

            val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            val wait = AnimationUtils.loadAnimation(context, R.anim.wait)
            val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)

            val isHeads = Random.nextBoolean()

            fadeIn.setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    clHeads.visibility = if (isHeads) View.VISIBLE else View.GONE
                    clTails.visibility = if (isHeads) View.GONE else View.VISIBLE

                    clFlip.visibility = View.VISIBLE
                }
                override fun onAnimationEnd(animation: Animation?) { clFlip.startAnimation(wait) }
                override fun onAnimationRepeat(animation: Animation?) {}

            })
            wait.setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) { clFlip.startAnimation(fadeOut) }
                override fun onAnimationRepeat(animation: Animation?) {}

            })
            fadeOut.setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    clFlip.visibility = View.GONE
                    clHeads.visibility = View.GONE
                    clTails.visibility = View.GONE

                    clFlip.animation = null
                }
                override fun onAnimationRepeat(animation: Animation?) {}
            })

            clFlip.startAnimation(fadeIn)
            view?.invalidate()
        }
    }
}