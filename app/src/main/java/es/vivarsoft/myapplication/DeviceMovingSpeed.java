package es.vivarsoft.myapplication;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceMovingSpeed extends ActionBarActivity {
    Context context;

    private TextView textView2;
    private TextView textView9;
    private ImageView imageView2;
    private TextView textView11;
    private TextView textView12;
    private TextView textView13;
    private TextView textView15;
    private TextView textView16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_moving_speed);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView9 = (TextView)findViewById(R.id.textView9);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        textView11 = (TextView)findViewById(R.id.textView11);
        textView12 = (TextView)findViewById(R.id.textView12);
        textView13 = (TextView)findViewById(R.id.textView13);
        textView15 = (TextView)findViewById(R.id.textView15);
        textView16 = (TextView)findViewById(R.id.textView16);

        /**/
        final LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);


        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                location.getLatitude();
                textView9.setText(location.getSpeed() + " mph");
                float speed = (float) ((location.getSpeed()*3600)/1000);
                textView2.setText(speed + " km/h");
                double latituddouble = location.getLatitude();
                double longituddouble = location.getLongitude();
                textView11.setText("Latitud: " + latituddouble);
                textView12.setText("Longitud: " + longituddouble);
                textView13.setText("Altitud: " + (int) location.getAltitude() + " Metros");
                textView16.setText("Precisión: " + (int) location.getAccuracy() + " Metros");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) { }
            public void onProviderEnabled(String provider)
            {
                textView15.setText("Estado: GPS activado");
            }
            public void onProviderDisabled(String provider) {
                textView15.setText("Estado: GPS desactivado");
                Toast.makeText(getApplicationContext(),
                        "Active el sensor GPS", Toast.LENGTH_LONG)
                        .show();
            }

        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        /**/
    }

    public void showApps(View v){
        Intent i = new Intent(this, AppsListActivity.class);
        startActivity(i);
    }

    public void homeclick(View v)
    {
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.menu_device_moving_speed, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // TODO Auto-generated method stub

        switch (item.getItemId()) {
            case R.id.menu_save:
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);                return true;
            case R.id.menu_settings:
                Intent ad = new Intent(this, acerca.class);
                startActivity(ad);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
