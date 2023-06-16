package org.d3if6706210130.appmovie

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.d3if6706210130.appmovie.Fragment.DataFragment
import org.d3if6706210130.appmovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val CHANNEL_ID = "updater"
        const val PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnCari.setOnClickListener{
            showFragment()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = getString(R.string.channel_desc)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager?
            manager?.createNotificationChannel(channel)
        }


    }

    private fun showFragment(){
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val mFragment = DataFragment()

        val textCari = binding.etCari.text
        val mBundle = Bundle()
        mBundle.putString("carimovie", textCari.toString())
        mFragment.arguments = mBundle

        mFragmentTransaction.replace(R.id.fl_data,mFragment).commit()
    }
}