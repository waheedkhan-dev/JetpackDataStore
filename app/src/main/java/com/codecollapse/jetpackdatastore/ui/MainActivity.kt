package com.codecollapse.jetpackdatastore.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.codecollapse.jetpackdatastore.databinding.ActivityMainBinding
import com.codecollapse.jetpackdatastore.viewmodel.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: DataStoreViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            lifecycleScope.launch {
                mainActivityViewModel.getCounterValue()
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect {
                        this@apply.textInput.text = it.toString()
                    }
            }

            buttonIncrement.setOnClickListener {
                lifecycleScope.launch {
                    mainActivityViewModel.incrementCount()
                }
            }

            buttonDecrement.setOnClickListener {
                lifecycleScope.launch {
                    if(textInput.text.toString()!="0"){
                        mainActivityViewModel.decrementCount()
                    }
                }
            }
        }
    }
}