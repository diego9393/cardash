package es.vivarsoft.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends ActionBarActivity {

    double level=-1;
    private ImageButton imageButton;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButton6;
    private ImageButton imageButton7;
    private ImageButton imageButton8;
    private ImageButton imageButton9;
    private TextView batteryLevel;
    private TextView textView18;
    private Button button2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        establecerIU();
        wifibuttontext();

        /*localizacion*/
        final LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);


        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                location.getLatitude();
                float speed = (float) ((location.getSpeed()*3600)/1000);
                textView18.setText("Velocidad: " + speed + " km/h");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) { }
            public void onProviderEnabled(String provider)
            {
            }
            public void onProviderDisabled(String provider) {
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        /*fin localizacion*/

        /*deep link*/
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        /*fin deep link*/

        IntentFilter intentFilter =new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        /*bateria*/
        batteryLevel = (TextView)findViewById(R.id.batterylevel);

        this.registerReceiver(this.myBatteryReceiver,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        /*fin bateria*/
    }

    private void wifibuttontext()
    {
        WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.getWifiState() == 1)
        {
            button2.setText("WIFI: On");
        }
        else if (wifiManager.getWifiState() == 3)
        {
            button2.setText("WIFI: Off");
        }
    }

    private BroadcastReceiver myBatteryReceiver
            = new BroadcastReceiver(){

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            if (arg1.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
                batteryLevel.setText("Batería: "
                        + String.valueOf(arg1.getIntExtra("level", 0)) + "%");
                /*int con la carga de la bateria*/
                int baterialevel = arg1.getIntExtra("level", 0);
                int status = arg1.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
                /*notificaciones*/
                if((baterialevel == 95) && (status == BatteryManager.BATTERY_STATUS_CHARGING))
                {
                    notificacioncarga();
                }

                if((baterialevel == 25) && (status == BatteryManager.BATTERY_STATUS_DISCHARGING))
                {
                    showNotification();
                }
            }
        }

    };

    /*Contruir interface*/
    public void establecerIU()
    {
        String versionapp = getApplicationContext().getResources().getString(R.string.version);
        String appname = getApplicationContext().getResources().getString(R.string.app_name);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(appname);
        actionBar.setSubtitle(versionapp);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
        imageButton7 = (ImageButton) findViewById(R.id.imageButton7);
        textView18 = (TextView) findViewById(R.id.textView18);
        button2 = (Button) findViewById(R.id.button2);
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

    public void wifionclick (View v)
    {
        WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.getWifiState() == 1)
        {
            wifiManager.setWifiEnabled(true);
            button2.setText("Wifi: Off");
        }
        else if (wifiManager.getWifiState() == 3)
        {
            wifiManager.setWifiEnabled(false);
            button2.setText("Wifi: On");
        }
    }

    public void showApps(View v){
        Intent i = new Intent(this, AppsListActivity.class);
        startActivity(i);
    }

    public void relojonclick(View v)
    {
        Intent intent = new Intent("android.intent.action.QUICK_CLOCK");
        startActivityForResult(intent, 0);

    }

    public void bateriaclick(View v)
    {
        Intent i = new Intent(this, bateria.class);
        startActivity(i);
    }

    public void movimiento (View v)
    {
        Intent mov = new Intent(this, DeviceMovingSpeed.class);
        startActivity(mov);
    }

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

    public void clickassist(View v)
    {
        Intent LaunchIntent = new Intent("android.intent.action.ASSIST");
        startActivity(LaunchIntent);
    }

    public void clickmusic(View v)
    {
        Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
        startActivityForResult(intent, 0);
    }

    public void clickcam (View v)
    {
        Intent i = new Intent(this, weather.class);
        startActivity(i);
        //startActivity(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE));
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
