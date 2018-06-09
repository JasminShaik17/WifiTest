package cubex.mahesh.wifitest

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.ListView
import android.widget.Switch

class MainActivity : AppCompatActivity() {

    var s1:Switch? = null
    var lview:ListView? = null

    var wManager:WifiManager? = null
    var vib:Vibrator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        s1 = findViewById(R.id.s1)
        lview = findViewById(R.id.lview)
        vib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        wManager = applicationContext.
                getSystemService(Context.WIFI_SERVICE)
                as WifiManager
        var state = wManager?.wifiState
        if(state == 0 || state == 1){
            s1?.isChecked = false   //s1.setChedcked(true);
        }else if(state == 2 || state==3){
            s1?.isChecked=true
        }

        s1?.setOnCheckedChangeListener(
                object : CompoundButton.OnCheckedChangeListener  {
                    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    wManager?.setWifiEnabled(isChecked)
                        vib?.vibrate(5000)
                    }
                })

    }
    fun getScanResults(v:View)
    {
    var  list =  wManager?.scanResults   // List<ScanResult>
     var temp_list = mutableListOf<String>()
      for(result in list!!)
      {
         temp_list.add("${result.SSID}   \n   ${result.frequency}")
      }
        var adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice,
                temp_list)
        lview?.adapter = adapter
    }
    fun getPairedNetworks(v:View)
    {
        var  list =  wManager?.configuredNetworks   // List<WifiCofiguration>
        var temp_list = mutableListOf<String>()
        for(result in list!!)
        {
            temp_list.add("${result.SSID}   \n   ${result.status}")
        }
        var adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice,
                temp_list)
        lview?.adapter = adapter

    }
}
