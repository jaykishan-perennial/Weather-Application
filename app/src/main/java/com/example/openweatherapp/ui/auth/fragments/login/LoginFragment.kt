package com.example.openweatherapp.ui.auth.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.openweatherapp.ui.auth.fragments.AuthFragment
import dagger.hilt.android.AndroidEntryPoint
import com.example.openweatherapp.R
import com.example.openweatherapp.databinding.FragmentLoginBinding
import com.example.openweatherapp.ui.auth.components.TouchDisabler
import com.example.openweatherapp.ui.auth.components.ViewPagerFragment
import com.example.openweatherapp.utils.extension.errorMapper
import com.example.openweatherapp.utils.extension.hideKeyboard
import com.example.openweatherapp.utils.extension.showAlertDialog
import com.example.openweatherapp.utils.utility.Response

@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var viewPagerFragment: ViewPagerFragment
    private lateinit var touchDisabler: TouchDisabler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authenticationActivity = parentFragment as AuthFragment
        viewPagerFragment = authenticationActivity
        touchDisabler = authenticationActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login, container, false)

        setUpDataBinding()
        setObservers()
        setListeners()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.clearStates()
    }

    private fun setUpDataBinding() {
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setObservers() {
        loginViewModel.loginResponse.observe(viewLifecycleOwner) {
            touchDisabler.setTouchesDisabled(it is Response.Loading)
            when (it) {
                is Response.Success -> {
                    navigateToHome()
                }

                is Response.Error -> {
                    showAlertDialog(subTitle = requireContext().errorMapper(it.errorCode))
                }

                else -> {}
            }
        }
        loginViewModel.email.observe(viewLifecycleOwner) {
            loginViewModel.setLoginAllowed()
        }
        loginViewModel.password.observe(viewLifecycleOwner) {
            loginViewModel.setLoginAllowed()
        }
    }

    private fun setListeners() {
        binding.tvSignUpHere.setOnClickListener {
            viewPagerFragment.changePage(1)
        }

        binding.root.setOnClickListener(this)
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
    }

    override fun onClick(p0: View?) {
        if (p0 !is EditText) {
            requireActivity().hideKeyboard()
        }
    }
}
