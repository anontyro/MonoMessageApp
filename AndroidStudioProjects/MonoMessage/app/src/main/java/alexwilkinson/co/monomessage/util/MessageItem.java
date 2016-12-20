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
    public String userLocation="";


    /**
     * Single argument construtor that takes teh message only, the rest will be filled in later.
     * @param message
     */
    public MessageItem(String message) {
        dateTime = setDateTime(); //calls the dateTime that is currently set on local device

        this.message = message; //assigns the message to the object
    }

    /**
     * Setter method that allows the sent from String to be assigned should be "local" or "web".
     * @param sentFrom
     * @return String value of sentFrom.
     */
    public String setSentFrom (String sentFrom){
        return this.sentFrom = sentFrom;
    }

    /**
     * SEtter method that allows the location to be added, currently not used fully.
     * @param userLocation String value required for the location.
     * @return the String value for the users location.
     */
    public String setUserLocation(String userLocation){
        return this.userLocation = userLocation;
    }

    /**
     * Setter method that is used to set the DateTime of the current message.
     * @return date time of the current message in String format
     */
    public String setDateTime(){

        SimpleDateFormat dateFormat = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dateFormat = new SimpleDateFormat("dd/MM/YY hh:mm");
        }

        return dateFormat.format(System.currentTimeMillis());

    }




}
