package bujangdatuk.datukbujang.datuk.slidepuzzle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import bujangdatuk.datukbujang.datuk.slidepuzzle.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToPuzzle(View view){
        Intent intent = new Intent(MainActivity.this, PuzzleActivity.class);
        startActivity(intent);
    }

    public void goToAbout(View view){
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }

}
