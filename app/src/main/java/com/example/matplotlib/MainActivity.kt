package com.example.matplotlib

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.matplotlib.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!Python.isStarted())
            Python.start(AndroidPlatform(this))

        val py = Python.getInstance()
        val pyObj = py.getModule("script")

        binding.plotBtn.setOnClickListener{
            try {
                val plotImg = pyObj.callAttr("main", binding.xPoints.text.toString(), binding.yPoints.text.toString()).toString()
                val plotImgData = Base64.decode(plotImg, Base64.DEFAULT)
                val plotImgBitmap = BitmapFactory.decodeByteArray(plotImgData, 0, plotImgData.size)
                binding.plotImg.setImageBitmap(plotImgBitmap)
            }
            catch (e: Exception){
                Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}