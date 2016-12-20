package alexwilkinson.co.monomessage.util;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alex on 20/12/2016.
 */

public class MessageItem {
    public String message;
    public String dateTime;
    public String sentFrom = "web";


    public MessageItem(String message) {
        dateTime = setDateTime();
        this.message = message;
    }

    public void setSentFrom (String sentFrom){
        this.sentFrom = sentFrom;
    }


    public String setDateTime(){

        SimpleDateFormat dateFormat = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dateFormat = new SimpleDateFormat("dd/MM/YY hh:mm");
        }

        return dateFormat.format(System.currentTimeMillis());

    }




}
