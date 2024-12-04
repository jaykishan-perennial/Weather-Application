package com.example.openweatherapp.ui.auth.fragments.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.openweatherapp.R
import com.example.openweatherapp.databinding.FragmentSignUpBinding
import com.example.openweatherapp.ui.auth.components.TouchDisabler
import com.example.openweatherapp.ui.auth.components.ViewPagerFragment
import com.example.openweatherapp.ui.auth.fragments.AuthFragment
import com.example.openweatherapp.utils.extension.errorMapper
import com.example.openweatherapp.utils.extension.hideKeyboard
import com.example.openweatherapp.utils.extension.showAlertDialog
import com.example.openweatherapp.utils.utility.Response
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()

    private lateinit var viewPagerFragment: ViewPagerFragment
    private lateinit var touchDisabler: TouchDisabler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authenticationActivity = parentFragment as AuthFragment
        viewPagerFragment = authenticationActivity
        touchDisabler = authenticationActivity

        setObservers()
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_sign_up, container, false)

        setUpDataBinding()
        setListeners()

        return binding.root
    }

    private fun setUpDataBinding() {
        binding.signUpViewModel = signUpViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setListeners() {
        binding.tvLoginHere.setOnClickListener {
            viewPagerFragment.changePage(0)
        }

        binding.root.setOnClickListener(this)
    }

    private fun setObservers() {
        signUpViewModel.apply {
            signUpState.observe(this@SignUpFragment) {
                touchDisabler.setTouchesDisabled(it is Response.Loading)
                when (it) {
                    is Response.Error ->
                        showAlertDialog(
                            mainTitle = getString(R.string.title_sign_up_failed),
                            subTitle = requireContext().errorMapper(it.errorCode)
                        )

                    is Response.Success -> {
                        navigateToHome()
                    }

                    else -> {}
                }
            }

            password.observe(this@SignUpFragment) {
                if (confirmPassword.value.isNullOrEmpty().not()) {
                    validateBothNewPasswordsSame()
                }
            }

            confirmPassword.observe(this@SignUpFragment) {
                validateBothNewPasswordsSame()
            }
        }
    }

    override fun onClick(p0: View?) {
        if (p0 !is EditText) {
            requireActivity().hideKeyboard()
        }
    }

}