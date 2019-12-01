package com.informatika.spsoa.calc;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView obraz;
    private TextView cislo1;
    private TextView cislo2;
    private TextView operace;

    /**
     * V onCreate provedeme inicializaci objektů TextView (pro zobrazování dat).
     *
     * @param savedInstanceState parametr je při prvním spuštění aplikace null. Slouží pro ukládání dat, například při změně orientace.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        obraz = findViewById(R.id.obrazovka);
        obraz.setText("0");

        cislo1 = findViewById(R.id.cislo1);
        cislo1.setText("NaN");

        cislo2 = findViewById(R.id.cislo2);
        cislo2.setText("NaN");

        operace = findViewById(R.id.znamenko);

        operace.setText("...");
    }

    /**
     * Privátní proměnné c1,c2,vys slouží k ukládání čísel.
     * Proměnná metoda slouží pro uchování záznamu s jakou metodou chce uživatel počítat.
     * Boolean proměnné prosel a c1Nastaven jsou pro řídící účeli aplikace. Pomocí nich ošetřujeme možné problémy se zadáváním čísel a ujištění se, že máme zadáno c1 když chceme počítat dál.
     */
    private boolean prosel = false;
    private boolean c1Nastaven = false;

    private double c1;
    private double c2;
    private double vys;
    private int metoda = 0;

    /**
     * Logika této metody je popsána v textech "Android programování 2 Calc" kapitola 1.2 Analýza a návrh aplikace. Detailní algoritmus se nachází na obrázku č. 9-Diagram připisování čísel
     *
     * @param sender díky tomuto parametru dostaneme ID tlačítka na které uživatel kliknul.
     */
    public void ZapisCislo(View sender) {
        Button tlac = (Button) sender;
        float number = Float.parseFloat(tlac.getText().toString());

        int pom = obraz.length();
        if (pom >= 9) {

            if (!prosel) {
                if (number == 0) {
                    obraz.setText("0");
                    return;
                } else {
                    obraz.setText(tlac.getText());
                    prosel = true;
                }
            }
            return;
        }
        if (!prosel) {
            if (number != 0) {
                obraz.setText(tlac.getText());
                prosel = true;
            }
        } else {
            obraz.append(tlac.getText());
        }
    }

    /**
     * Metoda se ukončí, pokud je na obrazovce už více než 8 znaků.
     * Pokud se "." na obrazovce nenachází, přidá ji na konec řetězce a nastaví true u řídící proměnné prosel.
     *
     * @param v Zajistí nám viditelnost této metody, abychom k ní mohli přistoupit z xml, tzn. metoda onclick
     */
    public void zapisCarku(View v) {
        if (obraz.getText().length() >= 8) {
            return;
        }
        String test = obraz.getText().toString();
        String hled = ".";
        if (!test.contains(hled)) {
            obraz.append(".");
            prosel = true;
        }
    }

    /**
     * Metoda která vynuluje všechny použité proměnné i zobrazovací textView.
     *
     * @param v Zajistí nám viditelnost této metody, abychom k ní mohli přistoupit z xml, tzn. metoda onclick
     */
    public void vymazVse(View v) {
        prosel = false;
        c1 = 0;
        c2 = 0;
        vys = 0;
        metoda = 5;

        obraz.setText("0");
        cislo1.setText("NaN");
        cislo2.setText("NaN");
        operace.setText("...");
    }

    /**
     * Na základě délky řetězce buď smaže poslední znak, anebo nastaví textView obraz na "0".
     *
     * @param v Zajistí nám viditelnost této metody, abychom k ní mohli přistoupit z xml, tzn. metoda onclick
     */
    public void vymazJeden(View v) {
        int delka = obraz.length();

        if (delka > 1) {
            String puvodnistring = obraz.getText().toString();
            String substring = puvodnistring.substring(0, delka - 1);
            obraz.setText(substring);
        } else if (delka > 0) {
            obraz.setText("0");
            prosel = false;
        }
    }

    /**
     * Metoda na tlačítku +/-
     * Pokud je na obrazovce 0, nestane se nic.
     * Našte řetězec a zjistí na jaké pozici je "-". Pokud výsledek není -1, nahradíme znak "-" prázdným řetězcem "".
     * Pokud jsou souřadnice znaménka "-" <=8, připíšeme "-" na začátek řetězce.
     *
     * @param v Zajistí nám viditelnost této metody, abychom k ní mohli přistoupit z xml, tzn. metoda onclick
     */
    public void zapor(View v) {
        float k = Float.parseFloat(obraz.getText().toString());
        if (k == 0) {
            return;
        }
        String test = obraz.getText().toString();
        String hled = "-";

        if (test.contains(hled)) {
            test = test.replace("-", "");
            obraz.setText(test);
        } else {
            if (test.length() <= 8) {
                test = "-" + test;
                obraz.setText(test);
            }
        }
    }


    /**
     * Protože budeme odmocňovat, je třeba vymazat všechny proměnné.
     * Pokud bude výsledek delší než 9 znaků, vytvoříme substring prvních 9 čísel. Pomocí podmínek ošetříme, aby se nezobrazovaly nežádoucí čísla.
     *
     * @param v Zajistí nám viditelnost této metody, abychom k ní mohli přistoupit z xml, tzn. metoda onclick
     */
public void odmocni(View v) {
    double number = Math.sqrt(Double.parseDouble(obraz.getText().toString()));
    vymazVse(v);

    if (number >= 0) {
        String vysledek = (String.valueOf(number));

        if (vysledek.length() >= 9) {
            String substring = vysledek.substring(0, 9);
            if (substring.equals("1.0000000") || substring.equals("0.9999998") || substring.equals("0.9999999")) {
                obraz.setText("1");
                return;
            } else {
                obraz.setText(substring);
                return;
            }
        }
        if (number != 0) {
            Double zbav = Double.parseDouble(vysledek);
            int zbav2 = (int) Math.round(zbav);
            obraz.setText(String.valueOf(zbav2));
        } else {
            obraz.setText("0");
        }
    } else {
        Toast.makeText(this, "Nelze odmocnit záporné číslo!", Toast.LENGTH_LONG).show();
        vymazVse(v);
    }
}

    /**
     * Následující metody secti, odecti, vynasob, vydel budou mít stejný princip.
     * Uloží do globální proměnné c1 číslo na obrazovce a z metodu (číslo 1-4). Také nastaví potřebné řídící proměnné.
     *
     * @param v Zajistí nám viditelnost této metody, abychom k ní mohli přistoupit z xml, tzn. metoda onclick
     */
    public void secti(View v) {
        c1 = Double.parseDouble(obraz.getText().toString());

        metoda = 1;
        obraz.setText("0");
        prosel = false;
        c1Nastaven = true;

        cislo1.setText(String.valueOf(c1));
        operace.setText("+");
    }

    public void odecti(View v) {
        c1 = Double.parseDouble(obraz.getText().toString());

        metoda = 2;
        obraz.setText("0");
        prosel = false;
        c1Nastaven = true;

        cislo1.setText(String.valueOf(c1));
        operace.setText("-");
    }

    public void vynasob(View V) {
        c1 = Double.parseDouble(obraz.getText().toString());

        metoda = 3;
        obraz.setText("0");
        prosel = false;
        c1Nastaven = true;

        cislo1.setText(String.valueOf(c1));
        operace.setText("*");
    }

    public void vydel(View V) {
        c1 = Double.parseDouble(obraz.getText().toString());

        metoda = 4;
        obraz.setText("0");
        prosel = false;
        c1Nastaven = true;

        cislo1.setText(String.valueOf(c1));
        operace.setText("/");
    }


    /**
     * Pokud se metoda nerovná 5, znamená to, že je nastavená a lze načíst do paměti c2.
     * Podle toho o jakou metodu se jedná se provede patřičná operace pomocí switch case.
     * Při dělení vznikají dlouhé řetězce čísel, proto je třeba po počítání zjistit, na jakém místě je desetinná čárka a podle toho zaokrouhlit.
     *
     * @param v Zajistí nám viditelnost této metody, abychom k ní mohli přistoupit z xml, tzn. metoda onclick
     */
    public void vypocti(View v) {
        if (c1Nastaven) {
            if (metoda != 5) {
                c2 = Double.parseDouble(obraz.getText().toString());
                cislo2.setText(String.valueOf(c2));
            }
            switch (metoda) {
                case 1: {
                    vys = c1 + c2;
                    metoda = 5;
                    break;
                }

                case 2: {
                    vys = c1 - c2;
                    metoda = 5;
                    break;
                }

                case 3: {
                    vys = c1 * c2;
                    metoda = 5;
                    break;
                }

                case 4: {

                    if (c2 != 0) {
                        vys = c1 / c2;
                        metoda = 5;
                        break;
                    } else {
                        Toast.makeText(this, "Nelze dělit nulou!", Toast.LENGTH_LONG).show();
                        vymazVse(v);
                        break;
                    }
                }
                case 5: {
                    break;
                }
            }

            if (vys == 0) {
                obraz.setText("0");
            } else {
                DecimalFormat df = new DecimalFormat("#.########");
                String vysl = df.format(vys);
                vysl = vysl.replace(",", ".");

                int poz = vysl.indexOf(".");
                if (poz >= 8) {
                    Toast.makeText(this, "Byl překročen limit 9 čísel", Toast.LENGTH_LONG).show();
                    vymazVse(v);
                    return;
                }

                if (vysl.length() >= 10) {
                    String substring = vysl.substring(0, 9);
                    obraz.setText(substring);
                    return;
                } else {
                    obraz.setText(vysl);
                }

                c1Nastaven = false;
                prosel = false;
            }
        }
    }
}