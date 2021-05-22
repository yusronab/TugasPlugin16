package com.example.week16

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.week16.api.APIClient
import com.example.week16.api.SingleResponse
import com.example.week16.databinding.ActivityDetailBinding
import com.example.week16.model.Posts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPostsById()
        iconDelete()
        btnBack()
    }

    private fun getPostsById(){
        APIClient.APIendPoint().getPostsById(intent.getIntExtra("id",0)).enqueue(object : Callback<SingleResponse<Posts>>{
            override fun onResponse(call: Call<SingleResponse<Posts>>, response: Response<SingleResponse<Posts>>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        showPostsToView(body.data)
                        Toast.makeText(applicationContext, "Data Didapatkan", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<SingleResponse<Posts>>, t: Throwable) {
                println(t.message)
            }


        })
    }

    private fun showPostsToView(posts: Posts){
        binding.tvFirstName.text = posts.first_name
        binding.tvLastName.text = posts.last_name
        binding.tvEmail.text = posts.email

    }

    private fun deletePosts(){
        APIClient.APIendPoint().delete(intent.getIntExtra("id", 0)).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful){
                    Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println(t.message)
            }

        })
    }

    private fun iconDelete(){
        binding.iconDelete.setOnClickListener {
            deletePosts()
            finish()
        }
    }

    private fun btnBack(){
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java)).also { finish() }
        }
    }
}