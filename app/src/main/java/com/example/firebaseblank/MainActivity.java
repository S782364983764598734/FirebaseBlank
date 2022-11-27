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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //List<String> myList;
    List<String> playerList = Arrays.asList("Beal", "Booker", "Murray","Spongebob","Sandy Cheeks","Walter");
    List<String> draftList;
    int roomNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //myList = new ArrayList<>();
        draftList = new ArrayList<>();
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    public void draftFunction(View view) {
        EditText choice = (EditText) findViewById(R.id.editText);
        int num = Integer.parseInt(choice.getText().toString());

        if (num >= 0 && num < playerList.size()) {
            if (!playerList.get(Integer.parseInt(choice.getText().toString())).equals("Picked")) {
                TextView player = (TextView) findViewById(R.id.textView);
                draftList.add(playerList.get(Integer.parseInt(choice.getText().toString())));
                System.out.println(draftList);
                playerList.set(num, "Picked");
                System.out.println(playerList);
                joinRoom(view);
            } else {
                Toast.makeText(getApplicationContext(), "That player Has already been picked", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please Select a Value within the List", Toast.LENGTH_LONG).show();
        }
    }

    public void addRoom(View view) {
        Toast.makeText(getApplicationContext(), "List is " + playerList.size(), Toast.LENGTH_LONG).show();
        myRef.child("Room" + roomNum).child("Player List").setValue(playerList)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(getApplicationContext(),"List is uploaded successfully",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void joinRoom(View view) {
        myRef.child("Room " + roomNum).child("Player List").setValue(playerList);

    }

    public void room1(View view) {
        roomNum = 1;
    }
    public void room2(View view)
    {
        roomNum = 2;
    }
    public void room3(View view){
        myRef.child("Room " + roomNum).child("Player List").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                 if(!task.isSuccessful())
                 {
                     Log.e("firebase", "Error getting data", task.getException());
                 }
                 else{
                     Log.d("firebase", String.valueOf(task.getResult().getValue()));
                     String efby = String.valueOf(task.getResult().getValue());
                 }
            }
        });
    }

}