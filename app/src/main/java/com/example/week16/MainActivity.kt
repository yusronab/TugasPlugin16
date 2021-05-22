package com.example.week16

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week16.adapter.MainAdapter
import com.example.week16.api.APIClient
import com.example.week16.api.ListResponse
import com.example.week16.databinding.ActivityMainBinding
import com.example.week16.model.Posts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mainAdapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAllPosts()
        btnAddData()
    }
    fun getAllPosts(){
        APIClient.APIendPoint().getAllPosts().enqueue(object: Callback<ListResponse<Posts>>{
            override fun onResponse(call: Call<ListResponse<Posts>>, response: Response<ListResponse<Posts>>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        showToRecycler(body.data)
                        Toast.makeText(applicationContext, "Data Didapatkan", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ListResponse<Posts>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    fun showToRecycler(posts: List<Posts>){
        mainAdapter = MainAdapter(posts, object : MainAdapter.onAdapterClick{
            override fun onClick(posts: Posts) {
                startActivity(Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra("id", posts.id)
                })
            }

            override fun onUpdate(posts: Posts) {
                startActivity(Intent(this@MainActivity, NewUpdateActivity::class.java).apply {
                    putExtra("id", posts.id)
                    putExtra("first_name", posts.first_name)
                    putExtra("last_name", posts.last_name)
                    putExtra("email", posts.email)
                })
            }

        })
        binding.recyclerView.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun btnAddData(){
        binding.btnAddData.setOnClickListener {
            startActivity(Intent(this, NewUpdateActivity::class.java))
        }
    }
}