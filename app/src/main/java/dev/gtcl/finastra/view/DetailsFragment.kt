package dev.gtcl.finastra.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.gtcl.finastra.databinding.FragmentDetailsBinding

class DetailsFragment: Fragment() {

    private var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val photo = DetailsFragmentArgs.fromBundle(requireArguments()).photo
        binding = FragmentDetailsBinding.inflate(inflater).apply {
            this.photo = photo
        }
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}