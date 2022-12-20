package com.example.supershop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputBookActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 111;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_book);

        position=this.getIntent().getIntExtra("position",0);
        String title=this.getIntent().getStringExtra("title");
        String time=this.getIntent().getStringExtra("time");

        EditText editTextTitle=findViewById(R.id.editBookTitle);
        EditText editTextTime=findViewById(R.id.editBookTime);

        if(null!=title){
            editTextTitle.setText(title);
            editTextTime.setText(time);
        }



        Button buttonOk=findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("title",editTextTitle.getText().toString());
                bundle.putString("time",editTextTime.getText().toString());
                bundle.putInt( "position",position);

                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                InputBookActivity.this.finish();
            }
        });
    }
}