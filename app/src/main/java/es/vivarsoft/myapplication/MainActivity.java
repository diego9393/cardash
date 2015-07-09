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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    double level=-1;
    private Button button;
    private TextView textView2;
    private ImageButton imageButton;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButton6;
    private ImageButton imageButton7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getBatteryLevel();
        establecerIU();
    }

    public void showApps(View v){
        Intent i = new Intent(this, AppsListActivity.class);
        startActivity(i);
    }

    /*Contruir interface*/
    protected void establecerIU()
    {
        button = (Button) findViewById(R.id.button);
        textView2 = (TextView) findViewById(R.id.textView2);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
        imageButton7 = (ImageButton) findViewById(R.id.imageButton7);

    }

    /*bateria*/
    private void getBatteryLevel() {
        BroadcastReceiver bc= new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                float batteryLevel=intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                float batteryCapacity=intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                if(batteryLevel>0 && batteryCapacity>0 ){
                    level=(int) (batteryLevel*100)/  batteryCapacity;
                    textView2.setText("Batería: " + level + " %");
                }

                if(level >= 95)
                {
                    notificacioncarga();
                }

                if(level <= 25)
                {
                    showNotification();
                }


            }
        };
        IntentFilter intentFilter =new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(bc, intentFilter);
    }
    /*llamar bateria*/
    public void bateriaup(View v)
    {
        getBatteryLevel();
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
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.music");
        startActivity(LaunchIntent);
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
            case android.R.id.home:
                setContentView(R.layout.activity_main);
                return true;
        }
        if (id == R.id.action_settings) {
            setContentView(R.layout.acercade);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
