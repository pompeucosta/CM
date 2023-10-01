package com.example.hw1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hw1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //not the best way but works for this case
        binding.button0.setOnClickListener {addNumberToView(0) }
        binding.button1.setOnClickListener {addNumberToView(1) }
        binding.button2.setOnClickListener {addNumberToView(2) }
        binding.button3.setOnClickListener {addNumberToView(3)}
        binding.button4.setOnClickListener {addNumberToView(4)}
        binding.button5.setOnClickListener {addNumberToView(5)}
        binding.button6.setOnClickListener {addNumberToView(6)}
        binding.button7.setOnClickListener {addNumberToView(7)}
        binding.button8.setOnClickListener {addNumberToView(8)}
        binding.button9.setOnClickListener {addNumberToView(9)}

        binding.buttonErase.setOnClickListener {
            if(binding.numberView.text.isNotEmpty()) {
                val currentNumber = binding.numberView.text.toString()
                val len = currentNumber.length
                binding.numberView.text = getString(R.string.number,currentNumber.substring(0,len-1),"")
            }
        }

        binding.buttonDial.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${binding.numberView.text}")
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }

        }
    }

    private fun addNumberToView(number: Int) {
        binding.numberView.text = getString(R.string.number,binding.numberView.text,number.toString())
    }
}