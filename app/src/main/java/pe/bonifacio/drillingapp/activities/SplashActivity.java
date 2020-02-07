package pe.bonifacio.drillingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import pe.bonifacio.drillingapp.R;

public class SplashActivity extends AppCompatActivity {

    private TextView etlema;
    private ImageView ivRedrilsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivRedrilsa=(ImageView)findViewById(R.id.iv_Redrilsa);
        etlema=(TextView)findViewById(R.id.et_Lema);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytranstion);
        ivRedrilsa.startAnimation(myanim);
        etlema.startAnimation(myanim);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        final Intent i = new Intent(this,SignUpActivity.class);
        Thread time = new Thread(){
            public void run (){
                try{
                    sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();

                }
            }
        };
        time.start();
    }

}
