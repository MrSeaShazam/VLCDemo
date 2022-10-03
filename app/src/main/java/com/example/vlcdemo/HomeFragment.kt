package com.example.vlcdemo

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vlcdemo.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private val binding: FragmentHomeBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            playBtn.setOnClickListener {
                goToVideoPlayerFragment()
            }
            pasteLinkBtn.setOnClickListener {
                pasteCopiedText()
            }
        }
    }

    private fun pasteCopiedText() {
        (requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).apply {
            val item = primaryClip?.getItemAt(0)
            binding.urlLinkEt.setText(item?.text.toString())
        }
    }

    private fun goToVideoPlayerFragment() {
        binding.apply {
            if (urlLinkEt.text.toString().isNotEmpty()) {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToVideoPlayerFragment(urlLinkEt.text.toString())
                findNavController().navigate(action)
            }
        }
    }

}