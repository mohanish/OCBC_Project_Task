package com.simple.app.ocbc_project_task.features.dashboard.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.simple.app.ocbc_project_task.MainActivity
import com.simple.app.ocbc_project_task.R
import com.simple.app.ocbc_project_task.common.binding.ViewBindingFragment
import com.simple.app.ocbc_project_task.common.binding.bindText
import com.simple.app.ocbc_project_task.common.binding.withViewLifecycleOwner
import com.simple.app.ocbc_project_task.databinding.FragmentDashboardBinding
import com.simple.app.ocbc_project_task.features.login.ui.LoginFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment :
    ViewBindingFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard) {

    private val dashboardViewModel: DashboardViewModel by viewModel()

    override fun initializeLayoutBinding(view: View): FragmentDashboardBinding {
        return FragmentDashboardBinding.bind(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(dashboardViewModel) {
            withViewLifecycleOwner {
                setupToolbar()
                layout.tvAccountBalance.bindText(this, accountBalancesData)
                layout.rvMiniStatement.bindItems(this, miniStatementUiData)
                layout.btnMakeTransfer.setOnClickListener {
                    val action =
                        DashboardFragmentDirections.actionDashboardFragmentToTransferFragment()
                    view.findNavController().navigate(action)
                }
                dashboardError.observe {
                    Toast.makeText(context, R.string.error_dashboard, Toast.LENGTH_LONG).show()
                }
                getMiniStatementData()
                getAccountBalancesData()
            }
        }
    }

    private fun setupToolbar() {
        (activity as MainActivity?)!!.setSupportActionBar(layout.toolbar)
        layout.toolbar.title = "Dashboard"
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_dashboard, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuLogout -> {
                val action = DashboardFragmentDirections.actionDashboardFragmentToLoginFragment()
                view?.findNavController()?.navigate(action)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}