package es.vivarsoft.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    double level=-1;
    private ImageButton imageButton;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButton6;
    private ImageButton imageButton7;
    private TextView batteryLevel, batteryVoltage, batteryTemperature,
            batteryTechnology, batteryStatus, batteryHealth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        establecerIU();
        IntentFilter intentFilter =new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        /*bateria*/
        batteryLevel = (TextView)findViewById(R.id.batterylevel);
        batteryVoltage = (TextView)findViewById(R.id.batteryvoltage);
        batteryTemperature = (TextView)findViewById(R.id.batterytemperature);
        batteryTechnology = (TextView)findViewById(R.id.batterytechology);
        batteryStatus = (TextView)findViewById(R.id.batterystatus);
        batteryHealth = (TextView)findViewById(R.id.batteryhealth);

        this.registerReceiver(this.myBatteryReceiver,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        /*fin bateria*/
    }

    public void showApps(View v){
        Intent i = new Intent(this, AppsListActivity.class);
        startActivity(i);
    }

    private BroadcastReceiver myBatteryReceiver
            = new BroadcastReceiver(){

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            if (arg1.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
                batteryLevel.setText("Batería: "
                        + String.valueOf(arg1.getIntExtra("level", 0)) + "%");
                batteryVoltage.setText("Voltaje: "
                        + String.valueOf((float)arg1.getIntExtra("voltage", 0)/1000) + "V");
                batteryTemperature.setText("Temperatura: "
                        + String.valueOf((float)arg1.getIntExtra("temperature", 0)/10) + "c");
                batteryTechnology.setText("Tecnología: " + arg1.getStringExtra("technology"));

                int status = arg1.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
                String strStatus;
                if (status == BatteryManager.BATTERY_STATUS_CHARGING){
                    strStatus = "Cargando...";
                } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING){
                    strStatus = "Descargando";
                } else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING){
                    strStatus = "Sin cargar";
                } else if (status == BatteryManager.BATTERY_STATUS_FULL){
                    strStatus = "Entera";
                } else {
                    strStatus = "Desconocida";
                }
                batteryStatus.setText("Status: " + strStatus);

                int health = arg1.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN);
                String strHealth;
                if (health == BatteryManager.BATTERY_HEALTH_GOOD){
                    strHealth = "Buena";
                } else if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT){
                    strHealth = "Temperatura alta";
                } else if (health == BatteryManager.BATTERY_HEALTH_DEAD){
                    strHealth = "Muerta";
                } else if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE){
                    strHealth = "Sobre voltaje";
                } else if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE){
                    strHealth = "Fallo desconocido";
                } else{
                    strHealth = "Desconocido";
                }
                batteryHealth.setText("Salud: " + strHealth);

                /*int con la carga de la bateria*/
                int baterialevel = arg1.getIntExtra("level", 0);
                /*notificaciones*/
                if(baterialevel >= 95)
                {
                    notificacioncarga();
                }

                if(baterialevel <= 25)
                {
                    showNotification();
                }
            }
        }

    };

    /*Contruir interface*/
    public void establecerIU()
    {
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
        imageButton7 = (ImageButton) findViewById(R.id.imageButton7);
    }

    /*notificacion carga de bateria*/
    public void notificacioncarga()
    {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, ShowNotificationDetailActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(r.getString(R.string.notification_title))
                .setSmallIcon(R.drawable.noticon)
                .setContentTitle(r.getString(R.string.notification_cargatit))
                .setContentText(r.getString(R.string.notification_cargatext))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

        try {
            Uri notificationbeep = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ring = RingtoneManager.getRingtone(getApplicationContext(), notificationbeep);
            ring.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, ShowNotificationDetailActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(r.getString(R.string.notification_title))
                .setSmallIcon(R.drawable.noticon)
                .setContentTitle(r.getString(R.string.notification_title))
                .setContentText(r.getString(R.string.notification_text))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

        try {
            Uri notificationbeep = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ring = RingtoneManager.getRingtone(getApplicationContext(), notificationbeep);
            ring.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*fin bateria*/

    public void openclock(View v)
    {
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.android.vending");
        startActivity(LaunchIntent);
    }
    public void imageclick(View v)
    {
        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
        i.setData(Uri.parse("geo:40.416947,-3.703528?q=40.416947,-3.703528"));
        startActivity(i);
    }

    public void clickweb(View v)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.google.com"));
        startActivity(browserIntent);
    }

    public void clickwhatsapp(View v)
    {
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
        startActivity(LaunchIntent);
    }

    public void clickmusic(View v)
    {
        Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
        startActivityForResult(intent, 0);
    }

    public void clickcam (View v)
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            //case android.R.id.home:
                //setContentView(R.layout.activity_main);
                //return true;
        }
        if (id == R.id.action_settings) {
           // setContentView(R.layout.acercade);
            Intent ad = new Intent(this, acerca.class);
            startActivity(ad);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
