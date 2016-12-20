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
    //GUI fields
    private EditText etUserMsg;
    private Button buSendMsg;

    //firebase vars
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    //user defined vars
    private String lastSent = "";
    private ArrayList<MessageItem>messageList = new ArrayList<>();
    private int numberOfMessages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup the basics needed for the view
        etUserMsg =(EditText)findViewById(R.id.etActivityMainUserMessage);
        database = FirebaseDatabase.getInstance();

        //keeps the data even when restarted
        database.setPersistenceEnabled(true);
        myRef = database.getReference("msg");

        //call private method to handle the listview setup
        setupListView();






    }
    /*
    Setup the listview field and assigns my custom ArrayAdaptor to the list to be used,
    Adds the read from database code to ensure that the list is updated with whatever value has
    been added to the database and will check how many messages have been received before backing
    up to the local database.
     */
    private void setupListView(){

        ListView lvMainLog = (ListView)findViewById(R.id.lvMainLog);
        final MainListAdapter myAdapter = new MainListAdapter(getApplicationContext(),messageList);
        lvMainLog.setAdapter(myAdapter);

        //read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            /**
             * Override method that listens for the database message to get updated information and
             * will save the messages to a local log every X number of messages (currently set at 10)
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String returnedVal = dataSnapshot.getValue(String.class);
                MessageItem item = new MessageItem(returnedVal);

                myAdapter.add(item);
                numberOfMessages++;

                if(numberOfMessages >= 10){
                    //TODO: save the messages locally to allow the user to keep a log
                    System.out.println("Backup messages HERE");
                    numberOfMessages = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
    will push the message from the user to the database
     */
    private void sendMessage (String message){

        //write to the database
        myRef.setValue(message);

    }

    /**
     * Button click method that will trigger the validation of the current message inserted,
     * if the message is valid it will add it to the database. If not the user will get a Toast
     * displaying why not.
     * @param view
     */
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

    /*
    Message validation method that will check the message conforms to standards to allow it to be
    sent out.
     */
    private boolean messageValidation(String message){

        if(message.equalsIgnoreCase("")){
            return false;
        }
        if (message.length() > 100) {
            return false;
        }

        return true;
    }

    /**
     * Inner class that is used to control the ListView, this ArrayAdapter will pull the details
     * and display them to the user
     */
    public class MainListAdapter extends ArrayAdapter<MessageItem>{

        public MainListAdapter(Context context, ArrayList<MessageItem>messages){
            super(context,0,messages);
        }

        /**
         * Override method to create the listView which will display the items in the activity_main_item_object.xml
         * view in the list, and will identify crudly if the data has been sent from local or online
         * @param position item position within the arrayList.
         * @param convertView the main view for this list which is activity_main_item_object.xml.
         * @param parent main view of the activity.
         * @return convertView is returned and displays the values from the ArrayList.
         */
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
