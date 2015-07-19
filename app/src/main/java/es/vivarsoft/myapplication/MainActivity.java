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
    private ImageButton imageButton8;
    private ImageButton imageButton9;

    private TextView batteryLevel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        establecerIU();
        IntentFilter intentFilter =new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        /*bateria*/
        batteryLevel = (TextView)findViewById(R.id.batterylevel);

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
        imageButton8 = (ImageButton) findViewById(R.id.imageButton8);
        imageButton9 = (ImageButton) findViewById(R.id.imageButton9);

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

    public void bateriaclick(View v)
    {
        Intent i = new Intent(this, bateria.class);
        startActivity(i);
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

    public void clickwhatsapp(View v)
    {
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.googlequicksearchbox");
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
