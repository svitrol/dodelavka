package com.example.jsonreader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Hodnota {
    public Calendar kdy;
    public int id;
    public double hodnota;
    public Hodnota(String id,String hodnota,String cas) throws ParseException {
        this.id=Integer.decode(id);
        this.hodnota=Double.parseDouble(hodnota);
        kdy=Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//"2019-11-28 22:00:00"
        kdy.setTime(sdf.parse(cas));
    }
}
