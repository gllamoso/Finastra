package dev.gtcl.finastra.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dev.gtcl.finastra.R
import dev.gtcl.finastra.databinding.FragmentListBinding
import dev.gtcl.finastra.model.Photo
import dev.gtcl.finastra.view.list.PictureAdapter
import dev.gtcl.finastra.viewmodel.ListViewModel
import dev.gtcl.finastra.viewmodel.ViewModelFactory

class ListFragment: Fragment() {

    private var binding: FragmentListBinding? = null

    private val viewModel: ListViewModel by lazy {
        val viewModelFactory = ViewModelFactory()
        ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentListBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            listViewModel = viewModel
            recyclerView.adapter = PictureAdapter(object : PictureAdapter.PhotoClickListener{
                override fun onClick(photo: Photo) {
                    findNavController().navigate(ListFragmentDirections.actionShowDetail(photo))
                }
            })
            swipeRefreshLayout.setOnRefreshListener { viewModel.fetchPhotos() }
        }

        viewModel.apply {
            errorMessage.observe(viewLifecycleOwner, {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            })
            loading.observe(viewLifecycleOwner, {
                binding?.swipeRefreshLayout?.isRefreshing = it
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.fetchPhotos()
        return true
    }

    // To prevent
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}