package id.asad.githubuserfinder

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        usersAdapter = UsersAdapter()

        showNullView(true, "Please search for Github user in the search field")

        rv_user.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = usersAdapter
        }

        search_user.setIconifiedByDefault(false)
        search_user.queryHint = "Search User"
        search_user.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty() || newText.isEmpty() || newText == "") {
                    showNullView(true, "Please search for Github user in the search field")
                }
                else{
                    showNullView(false, null)
                    loadData(newText)
                }
                return true
            }
        })
    }

    private fun loadData(search : String){
        mainViewModel.findListUsers(search)
        mainViewModel.getResponseCode().observe(this@MainActivity, Observer {
            if (it == 200){
                mainViewModel.getListUsers().observe(this@MainActivity, Observer {
                    usersAdapter.setDataAdapter(it)
                    if (usersAdapter.itemCount == 0) showNullView(true, "Github user not found")
                    else showNullView(false, null)
                })
            }
            else{
                mainViewModel.getError().observe(this@MainActivity, Observer {
                    Log.d("MainActivity", it.toString())
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                })
            }
        })
    }

    private fun showNullView(isNull : Boolean, message : String?){
        if (isNull){
            img_error.visibility = View.VISIBLE
            tv_error.visibility = View.VISIBLE
            tv_error.text = message

            rv_user.visibility = View.GONE
        }
        else{
            img_error.visibility = View.GONE
            tv_error.visibility = View.GONE

            rv_user.visibility = View.VISIBLE
        }
    }

    private fun showLoading(state : Boolean){
        if (state) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }
}