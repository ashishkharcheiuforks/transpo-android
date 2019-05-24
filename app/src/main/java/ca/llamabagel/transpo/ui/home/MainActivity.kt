/*
 * Copyright (c) 2019 Derek Ellis. Subject to the MIT license.
 */

package ca.llamabagel.transpo.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import ca.llamabagel.transpo.R
import ca.llamabagel.transpo.di.inject
import ca.llamabagel.transpo.ui.search.SearchActivity
import ca.llamabagel.transpo.utils.startActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inject(this)

        setSupportActionBar(findViewById(R.id.toolbar))

        val navController = findNavController(R.id.navHostFragment)
        findViewById<BottomNavigationView>(R.id.bottomNavigation)
            .setupWithNavController(navController)

        findViewById<EditText>(R.id.search_bar).setOnClickListener {
            startActivity<SearchActivity>(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.main, menu)

        return super.onCreateOptionsMenu(menu)
    }
}
