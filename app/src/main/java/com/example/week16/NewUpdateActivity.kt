package com.example.week16

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.week16.api.APIClient
import com.example.week16.api.ListResponse
import com.example.week16.api.SingleResponse
import com.example.week16.databinding.ActivityNewUpdateBinding
import com.example.week16.model.Posts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()
        btnPostData()
        btnUpdateData()
    }

    private fun postData(){
        val first_name = binding.etFirstName.text.toString()
        val last_name = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()
        APIClient.APIendPoint().addPost(first_name, last_name, email).enqueue(object : Callback<ListResponse<Posts>>{
            override fun onResponse(call: Call<ListResponse<Posts>>, response: Response<ListResponse<Posts>>) {
                if (response.isSuccessful){
                    val body = response.body()
                    Toast.makeText(applicationContext, "Successfuly", Toast.LENGTH_SHORT).show()
                    println("Success add data"+ body)
                }
            }

            override fun onFailure(call: Call<ListResponse<Posts>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    private fun btnPostData(){
        binding.btnAdd.setOnClickListener {
            postData()
            finish()
        }
    }

    private fun setData(){
        binding.etFirstName.setText(intent.getStringExtra("first_name"))
        binding.etLastName.setText(intent.getStringExtra("last_name"))
        binding.etEmail.setText(intent.getStringExtra("email"))
    }

    private fun updateData(){
        val id = intent.getIntExtra("id",1)
        val first_name = binding.etFirstName.text.toString()
        val last_name = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()
        APIClient.APIendPoint().updatePost(id, first_name, last_name, email).enqueue(object : Callback<SingleResponse<Posts>>{
            override fun onResponse(call: Call<SingleResponse<Posts>>, response: Response<SingleResponse<Posts>>) {
                if (response.isSuccessful){
                    val body = response
                    Toast.makeText(applicationContext, "Data has been Updated", Toast.LENGTH_SHORT).show()
                    println("Update data "+body+" is success")
                }
            }

            override fun onFailure(call: Call<SingleResponse<Posts>>, t: Throwable) {
                println("ERROR "+t.message)
            }

        })
    }

    private fun btnUpdateData(){
        binding.btnUpdate.setOnClickListener {
            updateData()
            finish()
        }
    }
}