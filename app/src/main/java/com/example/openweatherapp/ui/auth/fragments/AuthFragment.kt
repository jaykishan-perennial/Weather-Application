package com.example.openweatherapp.ui.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import com.example.openweatherapp.R
import com.example.openweatherapp.databinding.FragmentAuthBinding
import com.example.openweatherapp.ui.auth.components.AuthViewPagerAdapter
import com.example.openweatherapp.ui.auth.components.TouchDisabler
import com.example.openweatherapp.ui.auth.components.ViewPagerFragment
import com.example.openweatherapp.ui.auth.fragments.login.LoginFragment
import com.example.openweatherapp.ui.auth.fragments.signup.SignUpFragment

@AndroidEntryPoint
class AuthFragment : Fragment(), ViewPagerFragment, TouchDisabler {


    private lateinit var fragments: List<Fragment>

    private lateinit var binding: FragmentAuthBinding

    private lateinit var authViewPagerAdapter: AuthViewPagerAdapter


    private val tabTitles = listOf(
        R.string.action_login, R.string.sign_up
    )
    private val tabMsg = listOf(
        R.string.welcome_back, R.string.join_now,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(layoutInflater)

        fragments = listOf(LoginFragment(), SignUpFragment())

        authViewPagerAdapter = AuthViewPagerAdapter(fragments, childFragmentManager, lifecycle)

        binding.viewpager.adapter = authViewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.setText(tabTitles[position])
            tab.tag = tabMsg[position]
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.tvSubHeading.setText(tab.tag.toString().toInt())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        return binding.root
    }

    override fun changePage(index: Int) {
        binding.viewpager.currentItem = index
    }

    override fun setTouchesDisabled(disable: Boolean) {
        binding.disableTouches = disable
    }
}