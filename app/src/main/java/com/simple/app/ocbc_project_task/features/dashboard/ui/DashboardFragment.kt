package com.simple.app.ocbc_project_task.features.dashboard.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.simple.app.ocbc_project_task.MainActivity
import com.simple.app.ocbc_project_task.R
import com.simple.app.ocbc_project_task.common.binding.ViewBindingFragment
import com.simple.app.ocbc_project_task.common.binding.bindText
import com.simple.app.ocbc_project_task.common.binding.withViewLifecycleOwner
import com.simple.app.ocbc_project_task.databinding.FragmentDashboardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment :
    ViewBindingFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard) {

    private val dashboardViewModel: DashboardViewModel by viewModel()

    override fun initializeLayoutBinding(view: View): FragmentDashboardBinding {
        return FragmentDashboardBinding.bind(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NavHostFragment.findNavController(this@DashboardFragment).navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, onBackPressedCallback
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(dashboardViewModel) {
            withViewLifecycleOwner {
                (activity as MainActivity?)!!.setSupportActionBar(layout.toolbar)
                layout.tvAccountBalance.bindText(this, accountBalancesData)
                layout.rvMiniStatement.bindItems(this, miniStatementUiData)
                layout.btnMakeTransfer.setOnClickListener {
                    val action =
                        DashboardFragmentDirections.actionDashboardFragmentToTransferFragment()
                    view.findNavController().navigate(action)
                }
                dashboardError.observe {
                    // Show error state
                }
                getMiniStatementData()
                getAccountBalancesData()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_dashboard, menu)
    }


}