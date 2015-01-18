package me.matero.hangman;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button playButton = (Button)findViewById(R.id.playButton);
        playButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent playIntent = new Intent(MainActivity.this,GameActivity.class);
        		startActivity(playIntent);
        	}
        });
    }
}
