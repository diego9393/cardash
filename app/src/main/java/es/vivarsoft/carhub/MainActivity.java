package es.vivarsoft.carhub;

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

    private ImageButton imageButton;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButton6;
    private TextView button;
    private Button button1;
    double level=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        establecerIU();

        button=(TextView) findViewById(R.id.textView2);
        getBatteryLevel();
    }

    public void establecerIU()
    {
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
        button1 = (Button) findViewById(R.id.button1);
    }

    //nivel de bateria
    public void batclick(View v)
    {
        getBatteryLevel();
    }

    private void getBatteryLevel() {
        BroadcastReceiver bc= new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                float batteryLevel=intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                float batteryCapacity=intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                if(batteryLevel>0 && batteryCapacity>0 ){
                    level=(int) (batteryLevel*100)/  batteryCapacity;
                    button.setText("Batería: " + level + " %");
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
    //fin nivel de bateria

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

        switch (item.getItemId()) {
            case R.id.add:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://vivarsoft.tk"));
                startActivity(browserIntent);
                return true;
            case R.id.search:
                browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/quintusscipiocelsa"));
                startActivity(browserIntent);
                return true;
            case R.id.edit:
                 browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/diego9393"));
                startActivity(browserIntent);
                return true;
            case R.id.delete:
                 browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/diego9393"));
                startActivity(browserIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
