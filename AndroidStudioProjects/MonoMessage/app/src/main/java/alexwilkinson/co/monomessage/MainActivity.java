package alexwilkinson.co.monomessage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.model.people.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import alexwilkinson.co.monomessage.util.MessageItem;

public class MainActivity extends AppCompatActivity {
    private EditText etUserMsg;
    private Button buSendMsg;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String lastSent = "";
    protected ArrayList<MessageItem>messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserMsg =(EditText)findViewById(R.id.etActivityMainUserMessage);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("msg");


        ListView lvMainLog = (ListView)findViewById(R.id.lvMainLog);

        final MainListAdapter myAdapter = new MainListAdapter(getApplicationContext(),messageList);

//        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,messageList);

        lvMainLog.setAdapter(myAdapter);



        //read

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String returnedVal = dataSnapshot.getValue(String.class);
                MessageItem item = new MessageItem(returnedVal);

                myAdapter.add(item);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    private void sendMessage (String message){


        //write to the database
        myRef.setValue(message);

    }


    public void sendMsg(View view) {
        String message = etUserMsg.getText().toString();
        if(messageValidation(message)) {
            sendMessage(message);
            lastSent = message;
            System.out.println("Length is: " +message.length());
            etUserMsg.setText("");
        }else {
            Toast.makeText(getApplicationContext(),"Enter some text",Toast.LENGTH_LONG);
        }
    }

    private boolean messageValidation(String message){

        if(message.equalsIgnoreCase("")){
            return false;
        }
        if (message.length() > 100) {
            return false;
        }

        return true;
    }

    public class MainListAdapter extends ArrayAdapter<MessageItem>{

        public MainListAdapter(Context context, ArrayList<MessageItem>messages){
            super(context,0,messages);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MessageItem message = getItem(position);

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main_item_object,
                        parent,false);
            }

            TextView tvDateTime = (TextView)convertView.findViewById(R.id.tvItemObjectDateTime);
            TextView tvMessage = (TextView)convertView.findViewById(R.id.tvItemObjectMessage);
            TextView tvLocaton = (TextView)convertView.findViewById(R.id.tvItemObjectLocation);

            if(lastSent.equalsIgnoreCase(message.message)) {
                message.setSentFrom("local");
                message.setUserLocation("Me");
            }

            System.out.println("message was sent from: "+message.sentFrom);

            tvDateTime.setText(message.dateTime);
            tvMessage.setText(message.message);
            tvLocaton.setText(message.userLocation);

            return convertView;
        }
    }









}
