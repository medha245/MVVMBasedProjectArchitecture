package com.medha.myapplication

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.medha.myapplication.api.Resource
import com.medha.myapplication.apiModels.response.RevenueDataResponse
import com.medha.myapplication.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScrollingActivity : AppCompatActivity() {

    private val viewmodel:HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        viewmodel.getDashboardData("https://www.google.com").observe(this, Observer {
            if(it.status == Resource.Status.LOADING){
                // show your loading state dialog or whatever
            }else if(it.status == Resource.Status.SUCCESS){
                // by deafult : it.data will give you the response type you mentioned
                // it.parsed data will give you the parsed data from the class that you passed as Network call
                // constructor
                if(it.parsedData is RevenueDataResponse){
                    val response = it.parsedData as RevenueDataResponse

                    // do whatever you want to do further with parsed data
                }else if(it.status == Resource.Status.ERROR){
                    // show toast with message :
                        // it.apiError.errorMessage contains the parsed error message from the error
                            // received which is being automatcally parsed from ErrorUtils.parseError
                                // when we receive error/failure in api call
                    Toast.makeText(this, it.apiError?.errorMessage?:"",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}