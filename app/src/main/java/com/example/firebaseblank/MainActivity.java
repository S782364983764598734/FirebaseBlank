package com.example.firebaseblank;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    List<String> myList;
    List<String> playerList =  Arrays.asList("Beal","Booker", "Murray");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myList = new ArrayList<>();
    }
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    String name = "Eric";
    List<String> draftList;

    public void draftFunction(View view)
    {
        EditText num = (EditText)findViewById(R.id.editText);
        TextView player = (TextView)findViewById(R.id.textView);
        myList.add(playerList.get(Integer.parseInt(num.getText().toString())));
        System.out.println(myList.get(0));
    }



    public void newRoom(View view)
    {
        myRef.child("Room 2").child("Player List").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

    public void addRoom(View view)
    {
        for(int i = 0; i < playerList.size(); i++)
        {
            myList.add(playerList.get(i));
        }
        Toast.makeText(getApplicationContext(),"List is " + myList.size(),Toast.LENGTH_LONG).show();
        myRef.child("Room 2").child("Player List").setValue(myList)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            //Toast.makeText(getApplicationContext(),"List is uploaded successfully",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }



}