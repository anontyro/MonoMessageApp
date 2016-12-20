package alexwilkinson.co.monomessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText etUserMsg;
    private Button buSendMsg;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    protected ArrayList<String>messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserMsg =(EditText)findViewById(R.id.etActivityMainUserMessage);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("msg");


        ListView lvMainLog = (ListView)findViewById(R.id.lvMainLog);

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,messageList);

        lvMainLog.setAdapter(adapter);



        //read

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String returnedVal = dataSnapshot.getValue(String.class);
                adapter.add(returnedVal);
                System.out.println("From the database the value is: "+returnedVal);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    private void sendMessage (){


        //write to the database
        myRef.setValue(etUserMsg.getText().toString());

    }


    public void sendMsg(View view) {
        if(!etUserMsg.getText().toString().equalsIgnoreCase("")) {
            sendMessage();
            etUserMsg.setText("");
        }else {
            Toast.makeText(getApplicationContext(),"Enter some text",Toast.LENGTH_LONG);
        }
    }

    private class MainListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }









}
