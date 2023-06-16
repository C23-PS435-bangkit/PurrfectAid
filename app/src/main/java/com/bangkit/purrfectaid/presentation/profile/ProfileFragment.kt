package com.bangkit.purrfectaid.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.purrfectaid.MainViewModel
import com.bangkit.purrfectaid.R
import com.bangkit.purrfectaid.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

//        binding.btnLogout.setOnClickListener {
//            lifecycleScope.launch {
//                if (viewModel.logout()) {
//                    Toast.makeText(requireContext(), "Berhasil logout", Toast.LENGTH_SHORT).show()
//                    val toOpening = ProfileFragmentDirections.actionProfileFragmentToOpeningFirstFragment()
//                    findNavController().navigate(toOpening)
//                } else {
//                    Toast.makeText(requireContext(), "Gagal logout", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }

        val sectionsPagerAdapter = ProfileSectionPagerAdapter(requireActivity() as AppCompatActivity)

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.setIcon(TAB_ICON[position])
        }.attach()

        val dataUser = sharedViewModel.user.value
        binding.apply {
            tvName.text = dataUser?.user_name
            tvEmail.text = dataUser?.user_email
        }

        return binding.root
    }


    companion object{
        private val TAB_ICON = listOf(
            R.drawable.post_profile,
            R.drawable.saved_post,
            R.drawable.saved_diagnose
        )
    }
}
