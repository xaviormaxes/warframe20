package com.example.warframeapp20.ui.arsenal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.warframeapp20.databinding.FragmentArsenalBinding

class ArsenalFragment : Fragment() {

    private var _binding: FragmentArsenalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArsenalBinding.inflate(inflater, container, false)
        
        setupViewPager()
        
        return binding.root
    }
    
    private fun setupViewPager() {
        // TODO: Setup ViewPager with fragments for Warframes, Weapons, Companions
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}