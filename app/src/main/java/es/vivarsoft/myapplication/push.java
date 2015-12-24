package es.vivarsoft.myapplication;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseInstallation;

public class push extends Application {
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "YRAWWiHGPP4MsZhHnTYxDhkIdRNjFu85m7NiGike", "u98RQ2W3FLhj5MzYLVvSZnXLchKlVw6hhF4dAK7j");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}