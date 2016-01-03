package es.vivarsoft.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class bateria extends ActionBarActivity {

    private Button button;
    private ImageButton imageButton10;
    private ImageView imageView;
    private TextView batteryLevel, batteryVoltage, batteryTemperature,
            batteryTechnology, batteryStatus, batteryHealth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_bateria);
        String versionapp = getApplicationContext().getResources().getString(R.string.version);
        String appname = getApplicationContext().getResources().getString(R.string.app_name);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(appname);
        actionBar.setSubtitle(versionapp);

        batteryLevel = (TextView)findViewById(R.id.batterylevel);
        batteryVoltage = (TextView)findViewById(R.id.batteryvoltage);
        batteryTemperature = (TextView)findViewById(R.id.batterytemperature);
        batteryTechnology = (TextView)findViewById(R.id.batterytechology);
        batteryStatus = (TextView)findViewById(R.id.batterystatus);
        batteryHealth = (TextView)findViewById(R.id.batteryhealth);
        imageView = (ImageView)findViewById(R.id.imageView);

        this.registerReceiver(this.myBatteryReceiver,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private BroadcastReceiver myBatteryReceiver
            = new BroadcastReceiver(){

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            if (arg1.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
                batteryLevel.setText("Batería: "
                        + String.valueOf(arg1.getIntExtra("level", 0)) + "%");
                batteryVoltage.setText("Voltage: "
                        + String.valueOf((float)arg1.getIntExtra("voltage", 0)/1000) + "V");
                batteryTemperature.setText("Temperatura: "
                        + String.valueOf((float)arg1.getIntExtra("temperature", 0)/10) + "c");
                batteryTechnology.setText("Tecnología: " + arg1.getStringExtra("technology"));

                int status = arg1.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
                String strStatus;
                if (status == BatteryManager.BATTERY_STATUS_CHARGING){
                    strStatus = "Cargando";
                } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING){
                    strStatus = "Descargando";
                } else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING){
                    strStatus = "No cargada";
                } else if (status == BatteryManager.BATTERY_STATUS_FULL){
                    strStatus = "Llena";
                } else {
                    strStatus = "Desconocido";
                }
                batteryStatus.setText("Estado: " + strStatus);

                int health = arg1.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN);
                String strHealth;
                if (health == BatteryManager.BATTERY_HEALTH_GOOD){
                    strHealth = "Buena";
                } else if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT){
                    strHealth = "Sobrecalentamiento";
                } else if (health == BatteryManager.BATTERY_HEALTH_DEAD){
                    strHealth = "Muerta";
                } else if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE){
                    strHealth = "Sobre Voltage";
                } else if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE){
                    strHealth = "Fallo desconocido";
                } else{
                    strHealth = "Desconocido";
                }
                batteryHealth.setText("Salud: " + strHealth);
                int nivelbateria = arg1.getIntExtra("level", 0);

                if ((nivelbateria <= 100)&& (nivelbateria >= 91) && (strStatus == "Cargando"))
                {
                    imageView.setImageResource(R.drawable.batcar100);
                }

                else if ((nivelbateria <= 90) && (nivelbateria >= 81) && (strStatus == "Cargando"))
                {
                    imageView.setImageResource(R.drawable.batcar90);
                }

                else if ((nivelbateria <= 80) && (nivelbateria >= 61) && (strStatus == "Cargando"))
                {
                    imageView.setImageResource(R.drawable.batcar80);
                }

                else if ((nivelbateria <= 60) && (nivelbateria >= 41) && (strStatus == "Cargando"))
                {
                    imageView.setImageResource(R.drawable.batcar60);
                }

                else if ((nivelbateria <= 40) && (nivelbateria >= 31) && (strStatus == "Cargando"))
                {
                    imageView.setImageResource(R.drawable.batcar40);
                }

                else if ((nivelbateria <= 30) && (nivelbateria >= 21) && (strStatus == "Cargando"))
                {
                    imageView.setImageResource(R.drawable.batcar30);
                }

                else if ((nivelbateria <= 20) && (strStatus == "Cargando"))
                {
                    imageView.setImageResource(R.drawable.batcar20);
                }

                else if ((nivelbateria <= 100)&& (nivelbateria >= 91))
                {
                    imageView.setImageResource(R.drawable.battery);
                }

                else if ((nivelbateria <= 90) && (nivelbateria >= 81))
                {
                    imageView.setImageResource(R.drawable.battery90);
                }

                else if ((nivelbateria <= 80) && (nivelbateria >= 61))
                {
                    imageView.setImageResource(R.drawable.battery80);
                }

                else if ((nivelbateria <= 60) && (nivelbateria >= 41))
                {
                    imageView.setImageResource(R.drawable.battery60);
                }

                else if ((nivelbateria <= 40) && (nivelbateria >= 31))
                {
                    imageView.setImageResource(R.drawable.battery40);
                }

                else if ((nivelbateria <= 30) && (nivelbateria >= 21))
                {
                    imageView.setImageResource(R.drawable.battery30);
                }

                else if (nivelbateria <= 20)
                {
                    imageView.setImageResource(R.drawable.battery20);
                }
            }
        }

    };

    public void showApps(View v){
        Intent i = new Intent(this, AppsListActivity.class);
        startActivity(i);
    }

    public void homeclick (View v)
    {
        Intent homeclick = new Intent(this, MainActivity.class);
        startActivity(homeclick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bateria, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent ad = new Intent(this, acerca.class);
            startActivity(ad);
            return true;
        }

        if (id == R.id.action_bateria)
        {
            Intent intent = new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
