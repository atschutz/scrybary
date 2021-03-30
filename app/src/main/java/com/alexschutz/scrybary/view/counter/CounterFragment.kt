package com.alexschutz.scrybary.view.counter

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.navigation.Navigation
import com.alexschutz.scrybary.R
import com.alexschutz.scrybary.databinding.FragmentCounterFullBinding
import com.alexschutz.scrybary.view.BackButtonFragment
import kotlin.random.Random

class CounterFragment : BackButtonFragment(), FlipClickListener, RefreshClickListener {

    private lateinit var binding: FragmentCounterFullBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCounterFullBinding.inflate(inflater, container, false)

        binding.backListener = this
        binding.flipListener = this
        binding.refreshListener = this

        val preferences = requireContext()
            .getSharedPreferences("SHARED PREFS", Context.MODE_PRIVATE)

        val numberOfPlayers = preferences.getInt(getString(R.string.pref_number_of_players), 2)

        binding.root.keepScreenOn = preferences.getBoolean(
            getString(R.string.pref_keep_screen_on),
            true
        )

        // Set view visibility based on number of players.
        with(binding) {

            when (numberOfPlayers) {
                1 -> {
                    rotateView(binding.root, container)
                    llBackFlipRefreshVert.visibility = View.VISIBLE
                }
                2 -> {
                    llBackFlipRefresh.visibility = View.VISIBLE
                    dividerLine.visibility = View.VISIBLE
                    p2Container.visibility = View.VISIBLE
                }
                3 -> {
                    rotateView(binding.root, container)
                    ll3pBackFlipRefresh.visibility = View.VISIBLE
                    dividerLineHalf.visibility = View.VISIBLE
                    verticalDividerLine.visibility = View.VISIBLE
                    llSecondColumn.visibility = View.VISIBLE
                }
                4 -> {
                    rotateView(binding.root, container)
                    llBackFlipRefresh.visibility = View.VISIBLE
                    dividerLine.visibility = View.VISIBLE
                    verticalDividerLine.visibility = View.VISIBLE
                    llSecondColumn.visibility = View.VISIBLE
                    p2Container.visibility = View.VISIBLE
                }

            }

            // TODO make this less bad.
            // Quick and dirty way to set all the keys and system preferences.
            p1Container.lifeCounter.setButtonsWithKey(getString(R.string.p1_life))
            p2Container.lifeCounter.setButtonsWithKey(getString(R.string.p2_life))
            p3Container.lifeCounter.setButtonsWithKey(getString(R.string.p3_life))
            p4Container.lifeCounter.setButtonsWithKey(getString(R.string.p4_life))

            with(p1Container.buttonContainer) {

                counterStart.setButtonsWithKey(getString(R.string.p1_box_1))
                counterMiddle.setButtonsWithKey(getString(R.string.p1_box_2))
                counterEnd.setButtonsWithKey(getString(R.string.p1_box_3))
            }

            if (numberOfPlayers >= 2) {
                with(p2Container.buttonContainer) {

                    counterStart.setButtonsWithKey(getString(R.string.p2_box_1))
                    counterMiddle.setButtonsWithKey(getString(R.string.p2_box_2))
                    counterEnd.setButtonsWithKey(getString(R.string.p2_box_3))
                }
            }

            if (numberOfPlayers >= 3) {

                p2Container.lifeCounter.scaleTextSizeWhenThreeOrMorePlayers()
                p3Container.lifeCounter.scaleTextSizeWhenThreeOrMorePlayers()
                p4Container.lifeCounter.scaleTextSizeWhenThreeOrMorePlayers()

                with(p2Container.buttonContainer) {

                    counterStart.scaleTextSizeWhenThreeOrMorePlayers()
                    counterMiddle.scaleTextSizeWhenThreeOrMorePlayers()
                    counterEnd.scaleTextSizeWhenThreeOrMorePlayers()
                }

                with(p3Container.buttonContainer) {

                    counterStart.scaleTextSizeWhenThreeOrMorePlayers()
                    counterMiddle.scaleTextSizeWhenThreeOrMorePlayers()
                    counterEnd.scaleTextSizeWhenThreeOrMorePlayers()
                }

                with(p4Container.buttonContainer) {

                    counterStart.scaleTextSizeWhenThreeOrMorePlayers()
                    counterMiddle.scaleTextSizeWhenThreeOrMorePlayers()
                    counterEnd.scaleTextSizeWhenThreeOrMorePlayers()
                }
            }

            if (numberOfPlayers == 4) {
                p1Container.lifeCounter.scaleTextSizeWhenThreeOrMorePlayers()

                with(p1Container.buttonContainer) {

                    counterStart.scaleTextSizeWhenThreeOrMorePlayers()
                    counterMiddle.scaleTextSizeWhenThreeOrMorePlayers()
                    counterEnd.scaleTextSizeWhenThreeOrMorePlayers()
                }
            }

            with(p3Container.buttonContainer) {

                counterStart.setButtonsWithKey(getString(R.string.p3_box_1))
                counterMiddle.setButtonsWithKey(getString(R.string.p3_box_2))
                counterEnd.setButtonsWithKey(getString(R.string.p3_box_3))
            }

            with(p4Container.buttonContainer) {

                counterStart.setButtonsWithKey(getString(R.string.p4_box_1))
                counterMiddle.setButtonsWithKey(getString(R.string.p4_box_2))
                counterEnd.setButtonsWithKey(getString(R.string.p4_box_3))
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

            fadeIn.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    clHeads.visibility = if (isHeads) View.VISIBLE else View.GONE
                    clTails.visibility = if (isHeads) View.GONE else View.VISIBLE

                    clFlip.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animation?) {
                    clFlip.startAnimation(wait)
                }

                override fun onAnimationRepeat(animation: Animation?) {}

            })
            wait.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    clFlip.startAnimation(fadeOut)
                }

                override fun onAnimationRepeat(animation: Animation?) {}

            })
            fadeOut.setAnimationListener(object : Animation.AnimationListener {
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

    override fun onRefreshClicked(view: View) {

        with(binding) {
            p1Container.refresh()
            p2Container.refresh()
            p3Container.refresh()
            p4Container.refresh()
        }
    }

    // Rotates the screen without having to change orientation. 
    private fun rotateView(view: View, vg: ViewGroup?) {

        vg?.let {

            with (view) {
                rotation = 90f
                translationX = ((vg.width - vg.height) / 2).toFloat()
                translationY = ((vg.height - vg.width) / 2).toFloat()

                with (layoutParams) {
                    height = vg.width
                    width = vg.height
                }
                requestLayout()
            }
        }
    }
}