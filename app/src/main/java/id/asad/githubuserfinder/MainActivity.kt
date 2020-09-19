package id.asad.githubuserfinder

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        usersAdapter = UsersAdapter()

        showNullData(true, "Please search for Github user in the search field", R.drawable.ic_baseline_search_96)

        search_user.apply {
            queryHint = "Search user"
            setIconifiedByDefault(false)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    showNullData(false, null, null)
                    loadData(query)
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty() || newText.isEmpty() || newText == "") {
                        showNullData(true, "Please search for Github user in the search field", R.drawable.ic_baseline_search_96)
                    }
                    else{
                        showNullData(false, null, null)
                        loadData(newText)
                    }
                    return true
                }
            })
        }
        rv_user.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = usersAdapter
        }
    }

    private fun loadData(search : String){
        mainViewModel.findListUsers(search)
        mainViewModel.getListUsers().observe(this@MainActivity, Observer {
            usersAdapter.setDataAdapter(it)
            if (usersAdapter.itemCount == 0) showNullData(true, "Github user not found", R.drawable.ic_baseline_not_found_96)
            else showNullData(false, null, null)
        })
        mainViewModel.getError().observe(this@MainActivity, Observer {
            showNullData(true, it, R.drawable.ic_baseline_error_outline_96)
        })
    }

    private fun showNullData(isNull: Boolean, message: String?, image: Int?){
        if (isNull){
            img_error.visibility = View.VISIBLE
            tv_error.visibility = View.VISIBLE
            img_error.setImageResource(image ?: R.drawable.ic_launcher_foreground)
            tv_error.text = message
            rv_user.visibility = View.GONE
        }
        else{
            img_error.visibility = View.GONE
            tv_error.visibility = View.GONE
            rv_user.visibility = View.VISIBLE
        }
    }
}