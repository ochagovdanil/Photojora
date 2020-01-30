package com.example.photojora.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.photojora.R
import com.example.photojora.fragments.HistoryFragment
import com.example.photojora.fragments.PhotoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mBackPressedTime: Long = -1 // it's used to realize "Press back again to exit" feature

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check for permissions
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSIONS_CODE
            )
        } else initApp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSIONS_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                initApp() // let's initiate the application
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSIONS_CODE
                )
            }
        }
    }

    override fun onBackPressed() {
        if (mBackPressedTime + 2_000 > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(this, "Нажмите ещё раз, чтобы выйти", Toast.LENGTH_SHORT).show()
        }

        mBackPressedTime = System.currentTimeMillis()
    }

    private fun initApp() {
        // set the default fragment for BottomNavigationView - HistoryFragment
        supportFragmentManager.beginTransaction()
            .add(R.id.main_fragment, HistoryFragment()).commit()

        // set up BottomNavigationView listener
        main_bottom_navigation_view.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_navigation_view_history -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, HistoryFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_navigation_view_photo -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, PhotoFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                // we don't allow a user go to FoodFragment
                // he can do that only after PhotoFragment
                // after a taken photo and a list of recipes
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    companion object {
        private const val PERMISSIONS_CODE = 300 // we use this to check for permissions
    }
}
