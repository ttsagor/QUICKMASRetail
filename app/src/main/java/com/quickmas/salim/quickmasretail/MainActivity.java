package com.quickmas.salim.quickmasretail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Module.Login.Login;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;

public class MainActivity extends AppCompatActivity {

    public static User global_user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DBInitialization db = new DBInitialization(this,null,null,1);
        FontSettings.setFont(this);
        global_user = new User();
        global_user.select(db,"1=1");

       /* if (cSystemInfo.getStatus()==0)
        {
            Intent intent = new Intent(MainActivity.this,
                    com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
        else*/
        {
            Intent intent = new Intent(MainActivity.this,
                    Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }


    }
}
