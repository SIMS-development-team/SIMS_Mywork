package com.example.mywork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_operateOption extends AppCompatActivity implements View.OnClickListener {

    private Button bt_add;
    private Button bt_modify;
    private Button bt_remove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate_option);

        Intent intent = getIntent();
        bt_add = findViewById(R.id.btn_add);
        bt_modify = findViewById(R.id.btn_modify);
        bt_remove = findViewById(R.id.btn_remove);

        bt_add.setOnClickListener(this);
        bt_modify.setOnClickListener(this);
        bt_remove.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_add:
                intent = new Intent(getApplicationContext(),activity_add.class);
                startActivity(intent);
                break;
            case R.id.btn_modify:
                intent =new Intent(getApplicationContext(),activity_allStudent.class);
                startActivity(intent);
                break;
            case  R.id.btn_remove:
                intent = new Intent(getApplicationContext(),activity_remove.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}