package com.example.mapka;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.text.Transliterator;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,dialogOdpovedi.ExampleDialogListener {

    private GoogleMap mMap;
    private TextView dobre,spatne;
    private int spravne=0,chybne=0;


    List<Stat> seznamStatu=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        dobre=findViewById(R.id.plusovka);
        spatne=findViewById(R.id.minusovka);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapsActivity.this, R.raw.style_json));
        nacpyTamTyStaty();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                index=0;
                for(;!seznamStatu.get(index).Znacka.equals(marker);index++);
                Toast.makeText(MapsActivity.this,"Zdej název státu a jeho hlavní město",Toast.LENGTH_LONG).show();
                dialogOdpovedi exampleDialog = new dialogOdpovedi();
                exampleDialog.show(getSupportFragmentManager(), "example dialog");
                exampleDialog.setListener(MapsActivity.this);

                marker.getPosition();
                return true;
            }
        });

    }
    private int index=0;

    @Override
    public void applyTexts(String Stat, String Mesto) {
        Stat aktualka=seznamStatu.get(index);
        if(Stat.equals(aktualka.Nazev)){
            if(Mesto.equals(aktualka.Mesto)){
                spravne+=2;
            }
            else spravne++;
        }else {
            chybne++;
        }
        dobre.setText("+"+spravne);
        spatne.setText("-"+chybne);
        aktualka.Znacka.remove();
        seznamStatu.remove(aktualka);
    }

    class Stat{
        Marker Znacka;
        String Nazev,Mesto,Kod;

        public Stat(String nazev, String mesto, String kod,double latitude,double longtitude) {
            Nazev = nazev;
            Mesto = mesto;
            Kod = kod;
            LatLng tady = new LatLng(latitude,longtitude);
            Znacka= mMap.addMarker(new MarkerOptions().position(tady).title("Značka státu"));
        }
    }
    void nacpyTamTyStaty(){
            seznamStatu.add(new Stat("Česko","Praha","CZ",51.56,32.45));
            seznamStatu.add(new Stat("Afghanistan","Kabul","AF",34.51666667,69.183333));
            seznamStatu.add(new Stat("Aland Islands","Mariehamn","AX",60.116667,19.9));
            seznamStatu.add(new Stat("Albania","Tirana","AL",41.31666667,19.816667));
            seznamStatu.add(new Stat("Algeria","Algiers","DZ",36.75,3.05));
            seznamStatu.add(new Stat("American Samoa","Pago Pago","AS",-14.26666667,-170.7));
            seznamStatu.add(new Stat("Andorra","Andorra la Vella","AD",42.5,1.516667));
            seznamStatu.add(new Stat("Angola","Luanda","AO",-8.833333333,13.216667));
            seznamStatu.add(new Stat("Anguilla","The Valley","AI",18.21666667,-63.05));
            seznamStatu.add(new Stat("Antarctica","N/A","AQ",0,0));
            seznamStatu.add(new Stat("Antigua and Barbuda","Saint John’s","AG",17.11666667,-61.85));
            seznamStatu.add(new Stat("Argentina","Buenos Aires","AR",-34.58333333,-58.666667));
            seznamStatu.add(new Stat("Armenia","Yerevan","AM",40.16666667,44.5));
            seznamStatu.add(new Stat("Aruba","Oranjestad","AW",12.51666667,-70.033333));
            seznamStatu.add(new Stat("Australia","Canberra","AU",-35.26666667,149.133333));
            seznamStatu.add(new Stat("Austria","Vienna","AT",48.2,16.366667));
            seznamStatu.add(new Stat("Azerbaijan","Baku","AZ",40.38333333,49.866667));
            seznamStatu.add(new Stat("Bahamas","Nassau","BS",25.08333333,-77.35));
            seznamStatu.add(new Stat("Bahrain","Manama","BH",26.23333333,50.566667));
            seznamStatu.add(new Stat("Bangladesh","Dhaka","BD",23.71666667,90.4));
            seznamStatu.add(new Stat("Barbados","Bridgetown","BB",13.1,-59.616667));
            seznamStatu.add(new Stat("Belarus","Minsk","BY",53.9,27.566667));
            seznamStatu.add(new Stat("Belgium","Brussels","BE",50.83333333,4.333333));
            seznamStatu.add(new Stat("Belize","Belmopan","BZ",17.25,-88.766667));
            seznamStatu.add(new Stat("Benin","Porto-Novo","BJ",6.483333333,2.616667));
            seznamStatu.add(new Stat("Bermuda","Hamilton","BM",32.28333333,-64.783333));
            seznamStatu.add(new Stat("Bhutan","Thimphu","BT",27.46666667,89.633333));
            seznamStatu.add(new Stat("Bolivia","La Paz","BO",-16.5,-68.15));
            seznamStatu.add(new Stat("Bosnia and Herzegovina","Sarajevo","BA",43.86666667,18.416667));
            seznamStatu.add(new Stat("Botswana","Gaborone","BW",-24.63333333,25.9));
            seznamStatu.add(new Stat("Brazil","Brasilia","BR",-15.78333333,-47.916667));
            seznamStatu.add(new Stat("British Indian Ocean Territory","Diego Garcia","IO",-7.3,72.4));
            seznamStatu.add(new Stat("British Virgin Islands","Road Town","VG",18.41666667,-64.616667));
            seznamStatu.add(new Stat("Brunei Darussalam","Bandar Seri Begawan","BN",4.883333333,114.933333));
            seznamStatu.add(new Stat("Bulgaria","Sofia","BG",42.68333333,23.316667));
            seznamStatu.add(new Stat("Burkina Faso","Ouagadougou","BF",12.36666667,-1.516667));
            seznamStatu.add(new Stat("Burundi","Bujumbura","BI",-3.366666667,29.35));
            seznamStatu.add(new Stat("Cambodia","Phnom Penh","KH",11.55,104.916667));
            seznamStatu.add(new Stat("Cameroon","Yaounde","CM",3.866666667,11.516667));
            seznamStatu.add(new Stat("Canada","Ottawa","CA",45.41666667,-75.7));
            seznamStatu.add(new Stat("Cape Verde","Praia","CV",14.91666667,-23.516667));
            seznamStatu.add(new Stat("Cayman Islands","George Town","KY",19.3,-81.383333));
            seznamStatu.add(new Stat("Central African Republic","Bangui","CF",4.366666667,18.583333));
            seznamStatu.add(new Stat("Chad","N’Djamena","TD",12.1,15.033333));
            seznamStatu.add(new Stat("Chile","Santiago","CL",-33.45,-70.666667));
            seznamStatu.add(new Stat("China","Beijing","CN",39.91666667,116.383333));
            seznamStatu.add(new Stat("Christmas Island","The Settlement","CX",-10.41666667,105.716667));
            seznamStatu.add(new Stat("Cocos Islands","West Island","CC",-12.16666667,96.833333));
            seznamStatu.add(new Stat("Colombia","Bogota","CO",4.6,-74.083333));
            seznamStatu.add(new Stat("Comoros","Moroni","KM",-11.7,43.233333));
            seznamStatu.add(new Stat("Cook Islands","Avarua","CK",-21.2,-159.766667));
            seznamStatu.add(new Stat("Costa Rica","San Jose","CR",9.933333333,-84.083333));
            seznamStatu.add(new Stat("Cote d’Ivoire","Yamoussoukro","CI",6.816666667,-5.266667));
            seznamStatu.add(new Stat("Croatia","Zagreb","HR",45.8,16));
            seznamStatu.add(new Stat("Cuba","Havana","CU",23.11666667,-82.35));
            seznamStatu.add(new Stat("CuraÃ§ao","Willemstad","CW",12.1,-68.916667));
            seznamStatu.add(new Stat("Cyprus","Nicosia","CY",35.16666667,33.366667));
            seznamStatu.add(new Stat("Czech Republic","Prague","CZ",50.08333333,14.466667));
            seznamStatu.add(new Stat("Democratic Republic of the Congo","Kinshasa","CD",-4.316666667,15.3));
            seznamStatu.add(new Stat("Denmark","Copenhagen","DK",55.66666667,12.583333));
            seznamStatu.add(new Stat("Djibouti","Djibouti","DJ",11.58333333,43.15));
            seznamStatu.add(new Stat("Dominica","Roseau","DM",15.3,-61.4));
            seznamStatu.add(new Stat("Dominican Republic","Santo Domingo","DO",18.46666667,-69.9));
            seznamStatu.add(new Stat("Ecuador","Quito","EC",-0.216666667,-78.5));
            seznamStatu.add(new Stat("Egypt","Cairo","EG",30.05,31.25));
            seznamStatu.add(new Stat("El Salvador","San Salvador","SV",13.7,-89.2));
            seznamStatu.add(new Stat("Equatorial Guinea","Malabo","GQ",3.75,8.783333));
            seznamStatu.add(new Stat("Eritrea","Asmara","ER",15.33333333,38.933333));
            seznamStatu.add(new Stat("Estonia","Tallinn","EE",59.43333333,24.716667));
            seznamStatu.add(new Stat("Ethiopia","Addis Ababa","ET",9.033333333,38.7));
            seznamStatu.add(new Stat("Falkland Islands","Stanley","FK",-51.7,-57.85));
            seznamStatu.add(new Stat("Faroe Islands","Torshavn","FO",62,-6.766667));
            seznamStatu.add(new Stat("Federated States of Micronesia","Palikir","FM",6.916666667,158.15));
            seznamStatu.add(new Stat("Fiji","Suva","FJ",-18.13333333,178.416667));
            seznamStatu.add(new Stat("Finland","Helsinki","FI",60.16666667,24.933333));
            seznamStatu.add(new Stat("France","Paris","FR",48.86666667,2.333333));
            seznamStatu.add(new Stat("French Polynesia","Papeete","PF",-17.53333333,-149.566667));
            seznamStatu.add(new Stat("French Southern and Antarctic Lands","Port-aux-FranÃ§ais","TF",-49.35,70.216667));
            seznamStatu.add(new Stat("Gabon","Libreville","GA",0.383333333,9.45));
            seznamStatu.add(new Stat("Georgia","Tbilisi","GE",41.68333333,44.833333));
            seznamStatu.add(new Stat("Germany","Berlin","DE",52.51666667,13.4));
            seznamStatu.add(new Stat("Ghana","Accra","GH",5.55,-0.216667));
            seznamStatu.add(new Stat("Gibraltar","Gibraltar","GI",36.13333333,-5.35));
            seznamStatu.add(new Stat("Greece","Athens","GR",37.98333333,23.733333));
            seznamStatu.add(new Stat("Greenland","Nuuk","GL",64.18333333,-51.75));
            seznamStatu.add(new Stat("Grenada","Saint George’s","GD",12.05,-61.75));
            seznamStatu.add(new Stat("Guam","Hagatna","GU",13.46666667,144.733333));
            seznamStatu.add(new Stat("Guatemala","Guatemala City","GT",14.61666667,-90.516667));
            seznamStatu.add(new Stat("Guernsey","Saint Peter Port","GG",49.45,-2.533333));
            seznamStatu.add(new Stat("Guinea","Conakry","GN",9.5,-13.7));
            seznamStatu.add(new Stat("Guinea-Bissau","Bissau","GW",11.85,-15.583333));
            seznamStatu.add(new Stat("Guyana","Georgetown","GY",6.8,-58.15));
            seznamStatu.add(new Stat("Haiti","Port-au-Prince","HT",18.53333333,-72.333333));
            seznamStatu.add(new Stat("Heard Island and McDonald Islands","N/A","HM",0,0));
            seznamStatu.add(new Stat("Honduras","Tegucigalpa","HN",14.1,-87.216667));
            seznamStatu.add(new Stat("Hong Kong","N/A","HK",0,0));
            seznamStatu.add(new Stat("Hungary","Budapest","HU",47.5,19.083333));
            seznamStatu.add(new Stat("Iceland","Reykjavik","IS",64.15,-21.95));
            seznamStatu.add(new Stat("India","New Delhi","IN",28.6,77.2));
            seznamStatu.add(new Stat("Indonesia","Jakarta","ID",-6.166666667,106.816667));
            seznamStatu.add(new Stat("Iran","Tehran","IR",35.7,51.416667));
            seznamStatu.add(new Stat("Iraq","Baghdad","IQ",33.33333333,44.4));
            seznamStatu.add(new Stat("Ireland","Dublin","IE",53.31666667,-6.233333));
            seznamStatu.add(new Stat("Isle of Man","Douglas","IM",54.15,-4.483333));
            seznamStatu.add(new Stat("Israel","Jerusalem","IL",31.76666667,35.233333));
            seznamStatu.add(new Stat("Italy","Rome","IT",41.9,12.483333));
            seznamStatu.add(new Stat("Jamaica","Kingston","JM",18,-76.8));
            seznamStatu.add(new Stat("Japan","Tokyo","JP",35.68333333,139.75));
            seznamStatu.add(new Stat("Jersey","Saint Helier","JE",49.18333333,-2.1));
            seznamStatu.add(new Stat("Jordan","Amman","JO",31.95,35.933333));
            seznamStatu.add(new Stat("Kazakhstan","Astana","KZ",51.16666667,71.416667));
            seznamStatu.add(new Stat("Kenya","Nairobi","KE",-1.283333333,36.816667));
            seznamStatu.add(new Stat("Kiribati","Tarawa","KI",-0.883333333,169.533333));
            seznamStatu.add(new Stat("Kosovo","Pristina","KO",42.66666667,21.166667));
            seznamStatu.add(new Stat("Kuwait","Kuwait City","KW",29.36666667,47.966667));
            seznamStatu.add(new Stat("Kyrgyzstan","Bishkek","KG",42.86666667,74.6));
            seznamStatu.add(new Stat("Laos","Vientiane","LA",17.96666667,102.6));
            seznamStatu.add(new Stat("Latvia","Riga","LV",56.95,24.1));
            seznamStatu.add(new Stat("Lebanon","Beirut","LB",33.86666667,35.5));
            seznamStatu.add(new Stat("Lesotho","Maseru","LS",-29.31666667,27.483333));
            seznamStatu.add(new Stat("Liberia","Monrovia","LR",6.3,-10.8));
            seznamStatu.add(new Stat("Libya","Tripoli","LY",32.88333333,13.166667));
            seznamStatu.add(new Stat("Liechtenstein","Vaduz","LI",47.13333333,9.516667));
            seznamStatu.add(new Stat("Lithuania","Vilnius","LT",54.68333333,25.316667));
            seznamStatu.add(new Stat("Luxembourg","Luxembourg","LU",49.6,6.116667));
            seznamStatu.add(new Stat("Macau","N/A","MO",0,0));
            seznamStatu.add(new Stat("Macedonia","Skopje","MK",42,21.433333));
            seznamStatu.add(new Stat("Madagascar","Antananarivo","MG",-18.91666667,47.516667));
            seznamStatu.add(new Stat("Malawi","Lilongwe","MW",-13.96666667,33.783333));
            seznamStatu.add(new Stat("Malaysia","Kuala Lumpur","MY",3.166666667,101.7));
            seznamStatu.add(new Stat("Maldives","Male","MV",4.166666667,73.5));
            seznamStatu.add(new Stat("Mali","Bamako","ML",12.65,-8));
            seznamStatu.add(new Stat("Malta","Valletta","MT",35.88333333,14.5));
            seznamStatu.add(new Stat("Marshall Islands","Majuro","MH",7.1,171.383333));
            seznamStatu.add(new Stat("Mauritania","Nouakchott","MR",18.06666667,-15.966667));
            seznamStatu.add(new Stat("Mauritius","Port Louis","MU",-20.15,57.483333));
            seznamStatu.add(new Stat("Mexico","Mexico City","MX",19.43333333,-99.133333));
            seznamStatu.add(new Stat("Moldova","Chisinau","MD",47,28.85));
            seznamStatu.add(new Stat("Monaco","Monaco","MC",43.73333333,7.416667));
            seznamStatu.add(new Stat("Mongolia","Ulaanbaatar","MN",47.91666667,106.916667));
            seznamStatu.add(new Stat("Montenegro","Podgorica","ME",42.43333333,19.266667));
            seznamStatu.add(new Stat("Montserrat","Plymouth","MS",16.7,-62.216667));
            seznamStatu.add(new Stat("Morocco","Rabat","MA",34.01666667,-6.816667));
            seznamStatu.add(new Stat("Mozambique","Maputo","MZ",-25.95,32.583333));
            seznamStatu.add(new Stat("Myanmar","Rangoon","MM",16.8,96.15));
            seznamStatu.add(new Stat("Namibia","Windhoek","NA",-22.56666667,17.083333));
            seznamStatu.add(new Stat("Nauru","Yaren","NR",-0.5477,166.920867));
            seznamStatu.add(new Stat("Nepal","Kathmandu","NP",27.71666667,85.316667));
            seznamStatu.add(new Stat("Netherlands","Amsterdam","NL",52.35,4.916667));
            seznamStatu.add(new Stat("New Caledonia","Noumea","NC",-22.26666667,166.45));
            seznamStatu.add(new Stat("New Zealand","Wellington","NZ",-41.3,174.783333));
            seznamStatu.add(new Stat("Nicaragua","Managua","NI",12.13333333,-86.25));
            seznamStatu.add(new Stat("Niger","Niamey","NE",13.51666667,2.116667));
            seznamStatu.add(new Stat("Nigeria","Abuja","NG",9.083333333,7.533333));
            seznamStatu.add(new Stat("Niue","Alofi","NU",-19.01666667,-169.916667));
            seznamStatu.add(new Stat("Norfolk Island","Kingston","NF",-29.05,167.966667));
            seznamStatu.add(new Stat("North Korea","Pyongyang","KP",39.01666667,125.75));
            seznamStatu.add(new Stat("Northern Cyprus","North Nicosia","NULL",35.183333,33.366667));
            seznamStatu.add(new Stat("Northern Mariana Islands","Saipan","MP",15.2,145.75));
            seznamStatu.add(new Stat("Norway","Oslo","NO",59.91666667,10.75));
            seznamStatu.add(new Stat("Oman","Muscat","OM",23.61666667,58.583333));
            seznamStatu.add(new Stat("Pakistan","Islamabad","PK",33.68333333,73.05));
            seznamStatu.add(new Stat("Palau","Melekeok","PW",7.483333333,134.633333));
            seznamStatu.add(new Stat("Palestine","Jerusalem","PS",31.76666667,35.233333));
            seznamStatu.add(new Stat("Panama","Panama City","PA",8.966666667,-79.533333));
            seznamStatu.add(new Stat("Papua New Guinea","Port Moresby","PG",-9.45,147.183333));
            seznamStatu.add(new Stat("Paraguay","Asuncion","PY",-25.26666667,-57.666667));
            seznamStatu.add(new Stat("Peru","Lima","PE",-12.05,-77.05));
            seznamStatu.add(new Stat("Philippines","Manila","PH",14.6,120.966667));
            seznamStatu.add(new Stat("Pitcairn Islands","Adamstown","PN",-25.06666667,-130.083333));
            seznamStatu.add(new Stat("Poland","Warsaw","PL",52.25,21));
            seznamStatu.add(new Stat("Portugal","Lisbon","PT",38.71666667,-9.133333));
            seznamStatu.add(new Stat("Puerto Rico","San Juan","PR",18.46666667,-66.116667));
            seznamStatu.add(new Stat("Qatar","Doha","QA",25.28333333,51.533333));
            seznamStatu.add(new Stat("Republic of Congo","Brazzaville","CG",-4.25,15.283333));
            seznamStatu.add(new Stat("Romania","Bucharest","RO",44.43333333,26.1));
            seznamStatu.add(new Stat("Russia","Moscow","RU",55.75,37.6));
            seznamStatu.add(new Stat("Rwanda","Kigali","RW",-1.95,30.05));
            seznamStatu.add(new Stat("Saint Barthelemy","Gustavia","BL",17.88333333,-62.85));
            seznamStatu.add(new Stat("Saint Helena","Jamestown","SH",-15.93333333,-5.716667));
            seznamStatu.add(new Stat("Saint Kitts and Nevis","Basseterre","KN",17.3,-62.716667));
            seznamStatu.add(new Stat("Saint Lucia","Castries","LC",14,-61));
            seznamStatu.add(new Stat("Saint Martin","Marigot","MF",18.0731,-63.0822));
            seznamStatu.add(new Stat("Saint Pierre and Miquelon","Saint-Pierre","PM",46.76666667,-56.183333));
            seznamStatu.add(new Stat("Saint Vincent and the Grenadines","Kingstown","VC",13.13333333,-61.216667));
            seznamStatu.add(new Stat("Samoa","Apia","WS",-13.81666667,-171.766667));
            seznamStatu.add(new Stat("San Marino","San Marino","SM",43.93333333,12.416667));
            seznamStatu.add(new Stat("Sao Tome and Principe","Sao Tome","ST",0.333333333,6.733333));
            seznamStatu.add(new Stat("Saudi Arabia","Riyadh","SA",24.65,46.7));
            seznamStatu.add(new Stat("Senegal","Dakar","SN",14.73333333,-17.633333));
            seznamStatu.add(new Stat("Serbia","Belgrade","RS",44.83333333,20.5));
            seznamStatu.add(new Stat("Seychelles","Victoria","SC",-4.616666667,55.45));
            seznamStatu.add(new Stat("Sierra Leone","Freetown","SL",8.483333333,-13.233333));
            seznamStatu.add(new Stat("Singapore","Singapore","SG",1.283333333,103.85));
            seznamStatu.add(new Stat("Sint Maarten","Philipsburg","SX",18.01666667,-63.033333));
            seznamStatu.add(new Stat("Slovakia","Bratislava","SK",48.15,17.116667));
            seznamStatu.add(new Stat("Slovenia","Ljubljana","SI",46.05,14.516667));
            seznamStatu.add(new Stat("Solomon Islands","Honiara","SB",-9.433333333,159.95));
            seznamStatu.add(new Stat("Somalia","Mogadishu","SO",2.066666667,45.333333));
            seznamStatu.add(new Stat("Somaliland","Hargeisa","NULL",9.55,44.05));
            seznamStatu.add(new Stat("South Africa","Pretoria","ZA",-25.7,28.216667));
            seznamStatu.add(new Stat("South Georgia and South Sandwich Islands","King Edward Point","GS",-54.283333,-36.5));
            seznamStatu.add(new Stat("South Korea","Seoul","KR",37.55,126.983333));
            seznamStatu.add(new Stat("South Sudan","Juba","SS",4.85,31.616667));
            seznamStatu.add(new Stat("Spain","Madrid","ES",40.4,-3.683333));
            seznamStatu.add(new Stat("Sri Lanka","Colombo","LK",6.916666667,79.833333));
            seznamStatu.add(new Stat("Sudan","Khartoum","SD",15.6,32.533333));
            seznamStatu.add(new Stat("Suriname","Paramaribo","SR",5.833333333,-55.166667));
            seznamStatu.add(new Stat("Svalbard","Longyearbyen","SJ",78.21666667,15.633333));
            seznamStatu.add(new Stat("Swaziland","Mbabane","SZ",-26.31666667,31.133333));
            seznamStatu.add(new Stat("Sweden","Stockholm","SE",59.33333333,18.05));
            seznamStatu.add(new Stat("Switzerland","Bern","CH",46.91666667,7.466667));
            seznamStatu.add(new Stat("Syria","Damascus","SY",33.5,36.3));
            seznamStatu.add(new Stat("Taiwan","Taipei","TW",25.03333333,121.516667));
            seznamStatu.add(new Stat("Tajikistan","Dushanbe","TJ",38.55,68.766667));
            seznamStatu.add(new Stat("Tanzania","Dar es Salaam","TZ",-6.8,39.283333));
            seznamStatu.add(new Stat("Thailand","Bangkok","TH",13.75,100.516667));
            seznamStatu.add(new Stat("The Gambia","Banjul","GM",13.45,-16.566667));
            seznamStatu.add(new Stat("Timor-Leste","Dili","TL",-8.583333333,125.6));
            seznamStatu.add(new Stat("Togo","Lome","TG",6.116666667,1.216667));
            seznamStatu.add(new Stat("Tokelau","Atafu","TK",-9.166667,-171.833333));
            seznamStatu.add(new Stat("Tonga","Nuku’alofa","TO",-21.13333333,-175.2));
            seznamStatu.add(new Stat("Trinidad and Tobago","Port of Spain","TT",10.65,-61.516667));
            seznamStatu.add(new Stat("Tunisia","Tunis","TN",36.8,10.183333));
            seznamStatu.add(new Stat("Turkey","Ankara","TR",39.93333333,32.866667));
            seznamStatu.add(new Stat("Turkmenistan","Ashgabat","TM",37.95,58.383333));
            seznamStatu.add(new Stat("Turks and Caicos Islands","Grand Turk","TC",21.46666667,-71.133333));
            seznamStatu.add(new Stat("Tuvalu","Funafuti","TV",-8.516666667,179.216667));
            seznamStatu.add(new Stat("Uganda","Kampala","UG",0.316666667,32.55));
            seznamStatu.add(new Stat("Ukraine","Kyiv","UA",50.43333333,30.516667));
            seznamStatu.add(new Stat("United Arab Emirates","Abu Dhabi","AE",24.46666667,54.366667));
            seznamStatu.add(new Stat("United Kingdom","London","GB",51.5,-0.083333));
            seznamStatu.add(new Stat("United States","Washington","US",38.883333,-77));
            seznamStatu.add(new Stat("Uruguay","Montevideo","UY",-34.85,-56.166667));
            seznamStatu.add(new Stat("US Minor Outlying Islands","Washington","UM",38.883333,-77));
            seznamStatu.add(new Stat("US Virgin Islands","Charlotte Amalie","VI",18.35,-64.933333));
            seznamStatu.add(new Stat("Uzbekistan","Tashkent","UZ",41.31666667,69.25));
            seznamStatu.add(new Stat("Vanuatu","Port-Vila","VU",-17.73333333,168.316667));
            seznamStatu.add(new Stat("Vatican City","Vatican City","VA",41.9,12.45));
            seznamStatu.add(new Stat("Venezuela","Caracas","VE",10.48333333,-66.866667));
            seznamStatu.add(new Stat("Vietnam","Hanoi","VN",21.03333333,105.85));
            seznamStatu.add(new Stat("Wallis and Futuna","Mata-Utu","WF",-13.95,-171.933333));
            seznamStatu.add(new Stat("Western Sahara","El-AaiÃºn","EH",27.153611,-13.203333));
            seznamStatu.add(new Stat("Yemen","Sanaa","YE",15.35,44.2));
            seznamStatu.add(new Stat("Zambia","Lusaka","ZM",-15.41666667,28.283333));
            seznamStatu.add(new Stat("Zimbabwe","Harare","ZW",-17.81666667,31.033333));
    }
}
