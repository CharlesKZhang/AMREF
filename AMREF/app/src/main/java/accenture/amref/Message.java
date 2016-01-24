package accenture.amref;

/**
 * Author: Charles Zhang
 * date: 1/14/15
 * Purpose: Creating object Message to be used
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Message implements Parcelable{
    //private variables
    String _txt;
    String _type;
    String _subtype;
    String _answer;
    String _file;
      // Empty constructor
    public Message(){

    }
    // constructor
    public Message(String txt, String type, String subtype, String answer, String file){
        this._txt = txt;
        this._type = type;
        this._subtype = subtype;
        this._answer = answer;
        this._file = file;
    }

    // constructor
    public Message(String txt, String type, String subtype){
        this._txt = txt; this._type = type; this._subtype = subtype;
    }
    // constructor
    public Message(String txt, String type, String subtype, String answer){
        this._txt = txt; this._type = type; this._subtype = subtype; this._answer = answer;
    }


    // getting text
    public String getTxt(){
        return this._txt;
    }

    // setting text
    public void setTxt(String txt){
        this._txt = txt;
    }

    // getting type
    public String getType(){
        return this._type;
    }

    // setting type
    public void setType(String type){
        this._type = type;
    }

    // getting subtype
    public String getSubType(){
        return this._subtype;
    }

    // setting subtype
    public void setSubType(String subtype){
        this._subtype = subtype;
    }

    // getting answer
    public String getAnswer(){
        return this._answer;
    }

    // setting answer
    public void setAnswer(String answer){
        this._answer = answer;
    }

    // getting answer
    public String getFile(){
        return this._file;
    }

    // setting answer
    public void setFile(String file){
        this._file = file;
    }

    // Required for using Parcelable
    private Message(Parcel in) {
        _txt = in.readString();
        _type = in.readString();
        _subtype = in.readString();
        _answer = in.readString();
        _file = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(_txt);
        out.writeString(_type);
        out.writeString(_subtype);
        out.writeString(_answer);
        out.writeString(_file);
    }


    public int describeContents() {
        return 0;
    }


    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}


