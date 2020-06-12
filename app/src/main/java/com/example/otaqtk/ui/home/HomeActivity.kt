package com.example.otaqtk.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.otaqtk.R
import com.example.otaqtk.databinding.ActivityHomeBinding
import com.example.otaqtk.ui.fragments.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private const val TAG_ONE = "first"
private const val TAG_SECOND = "second"
private const val TAG_THIRD = "third"
private const val TAG_FOURTH = "fourth"
private const val TAG__FIFTH = "fifth"

class HomeActivity : AppCompatActivity() {
    private var currentTag: String = TAG_ONE
    var currentMenuItemId: Int = R.id.button_home
    private var oldTag: String = TAG_ONE
    private var currentFragment: Fragment = HomeFragment()


    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this
        binding.bottomMenu.itemIconTintList = null



        if (savedInstanceState == null) loadFirstFragment()
        chooseFragment()
    }

    private fun chooseFragment() {

        binding.bottomMenu.setOnNavigationItemSelectedListener { item ->


            if (currentMenuItemId != item.itemId) {
                val fragment: Fragment
                oldTag = currentTag

                currentMenuItemId = item.itemId

                when (currentMenuItemId) {
                    R.id.button_home -> {
                        currentTag = TAG_ONE
                        fragment = HomeFragment()
                        loadFragment(fragment, currentTag)

                    }
                    R.id.button_wanted -> {
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            currentTag = TAG_SECOND
                            fragment = WantedFragment()
                            loadFragment(fragment, currentTag)
                        } else {
                            currentTag = TAG__FIFTH
                            fragment = NotRegisteredFragment()
                            loadFragment(fragment, currentTag)
                        }

                    }
                    R.id.button_otaqtk -> {
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            currentTag = TAG_THIRD
                            fragment = OtaqtkFragment()
                            loadFragment(fragment, currentTag)
                        } else {
                            currentTag = TAG__FIFTH
                            fragment = NotRegisteredFragment()
                            loadFragment(fragment, currentTag)
                        }

                    }
                    R.id.button_profile -> {
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            currentTag = TAG_FOURTH
                            fragment = ProfileFragment()
                            loadFragment(fragment, currentTag)
                        } else {
                            currentTag = TAG__FIFTH
                            fragment = NotRegisteredFragment()
                            loadFragment(fragment, currentTag)
                        }
                    }
                }

            }
            return@setOnNavigationItemSelectedListener true
        }
        false
    }

    private fun loadFirstFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        currentFragment = HomeFragment()
        transaction.add(R.id.fragment_container, currentFragment, TAG_ONE)
        transaction.commit()
    }

    private fun loadFragment(fragment: Fragment, tag: String) {
        if (currentFragment !== fragment) {
            val ft = supportFragmentManager.beginTransaction()

            if (fragment.isAdded) {
                ft.hide(currentFragment).show(fragment)
            } else {
                ft.hide(currentFragment).add(R.id.fragment_container, fragment, tag)
            }
            currentFragment = fragment

            ft.commit()

        }
    }
}