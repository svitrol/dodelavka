package com.example.ctecka.knizniDatabaze.Knizky;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Knizka implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Title")
    public String Title;
    @ColumnInfo(name = "Pridani")
    public Date Pridani;
    @ColumnInfo(name = "PoslAktualizace")
    public Date PoslAktualizace;
    @ColumnInfo(name = "PocetKapitol")
    public int PocetKapitol;
    @ColumnInfo(name = "ZpusobZpracovani")
    public int ZpusobZpracovani;
    @ColumnInfo(name = "UrlZdroj")
    public String UrlZdroj;
    @ColumnInfo(name = "InstrukcePodleZpusobu")
    public String InstrukcePodleZpusobu;//10-250 nebo jak pro sablonu
    @ColumnInfo(name = "Sablona")
    public String Sablona;//kroky ktere je treba udelat pro dastani kyzeneho obsahu po obdrzeni stranky
}
