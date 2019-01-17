package com.aimprosoft.android.optima.centralizedApp.util;

import android.util.Log;

import com.j256.ormlite.logger.LoggerFactory;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;


/**
 * <p>
 * Classe che espone i metodi (statici) per il calcolo di un codice fiscale.
 * <p>
 * Il numero di codice fiscale delle persone fisiche � costituito da
 * un'espressione alfanumerica di sedici caratteri. I primi quindici caratteri
 * sono indicativi dei dati anagrafici di ciascun soggetto secondo l'ordine
 * seguente:
 * <ol>
 * <li>tre caratteri alfabetici per il cognome;</li>
 * <li>tre caratteri alfabetici per il nome;</li>
 * <li>due caratteri numerici per l'anno di nascita;</li>
 * <li>un carattere alfabetico per il mese di nascita;</li>
 * <li>due caratteri numerici per il giorno di nascita ed il sesso</li>
 * <li>quattro caratteri, di cui uno alfabetico e tre (alfa)numerici per il
 * comune italiano o per lo Stato estero di nascita.</li>
 * <li>Il sedicesimo carattere, alfabetico, ha funzione di controllo.</li>
 * 
 * 
 * @author ggallo
 */

public class CodiceFiscaleUtils {
    private static String TAG = "CodiceFiscaleUtils";
    private static final com.j256.ormlite.logger.Logger logger = LoggerFactory.getLogger(CodiceFiscaleUtils.class);
    
    /**
     * Costruttore privato per inibire l'istanzizione di una classe che ha solo
     * metodi statici
     */
    private CodiceFiscaleUtils() {
    }

    /**
     * Passando in input i dati anagrafici della persona, restituisce il codice
     * fiscale calcolato. Se uno dei parametri in input � null o stringa vuota
     * restituisce una stringa vuota.
     * 
     * @param cognome Il cognome della persona
     * @param nome Il nome della persona
     * @param sesso Il sesso della persona M o F
     * @param data La data di nascita della persona
     * @param comune Il codice del comune di nascita della persona
     * @return String - il codice fiscale calcolato in base a parametri in
     *         input. Se uno dei parametri in input � null o stringa vuota
     *         restituisce una stringa vuota.
     */

    public static String calcolaCf(String cognome, String nome, String sesso,
            Date data, String comune) {
        // controllo gli argomenti
        String[] args = new String[] { cognome, nome, sesso, comune };
        for (String s : args) {
            if (isEmpty(s)) {
                throwEmptyArgException();
            }
        }
        if (data == null) {
            throwEmptyArgException();
        }
        
        sesso = sesso.toUpperCase().trim();
        if (!("M".equals(sesso) || "F".equals(sesso))) {
            throw new IllegalArgumentException(
                    "L'argomento 'sesso' deve essere la stringa 'm'o 'f'");
        }

        nome = nome.toUpperCase().trim();
        if (!CHAR_ALLOWED.matcher(nome).matches()) {
            throwIllegalArgException("nome");
        } else {
            nome = sostituzioneChar(nome);
        }

        cognome = cognome.toUpperCase().trim();
        if (!CHAR_ALLOWED.matcher(cognome).matches()) {
            throwIllegalArgException("cognome");
        } else {
            cognome = sostituzioneChar(cognome);
        }

        comune = comune.toUpperCase().trim();
        if (!CODICE_COMUNE_ALLOWED.matcher(comune).matches()) {

            Log.e(TAG, "COMUNE NON VALIDO [{}], " + comune);
//            logger.info("COMUNE NON VALIDO [{}] ", comune);
            throw new IllegalArgumentException(
                    "L'argomento 'comune' non sembra essere un codice valido");
        }

        StringBuilder codfisc = new StringBuilder();
        codfisc.append(calcolaCodCognome(cognome));
        codfisc.append(calcolaCodNome(nome));
        codfisc.append(calcolaCodDt(data, sesso));
        codfisc.append(comune);
        codfisc.append(calcolaCharControllo(codfisc));

        return codfisc.toString();

    }


    private static boolean isEmpty(String s)
    {
        return s == null || s.trim().length() == 0;
    }

    private static void throwEmptyArgException()
    {
        throw new IllegalArgumentException("non sono permessi parametri nulli o vuoti");
    }

    private static void throwIllegalArgException(String arg_name)
    {
        throw new IllegalArgumentException((new StringBuilder("L'argomento '")).append(arg_name).append("' non pu\362 contenere caratteri speciali.").toString());
    }

    private static String ottieniConsVoc(String stringa, boolean conson)
    {
        StringBuilder testo = new StringBuilder();
        int i = 0;
        char valChar[] = stringa.toCharArray();
        for(i = 0; i < valChar.length; i++)
            if(isVowel(valChar[i]) ^ conson)
                testo.append(valChar[i]);

        return testo.toString();
    }

    private static StringBuilder calcolaCodCognome(String stringa)
    {
        StringBuilder codice = new StringBuilder();
        codice.append((new StringBuilder(String.valueOf(ottieniConsVoc(stringa, true)))).append(ottieniConsVoc(stringa, false)).toString());
        if(codice.length() > 3)
            codice = new StringBuilder(codice.substring(0, 3));
        for(int i = codice.length(); i < 3; i++)
            codice.append("X");

        return codice;
    }

    private static StringBuilder calcolaCodNome(String stringa)
    {
        StringBuilder codice = new StringBuilder(ottieniConsVoc(stringa, true));
        if(codice.length() >= 4)
            codice = codice.delete(1, 2);
        codice.append(ottieniConsVoc(stringa, false));
        if(codice.length() > 3)
            codice = codice.replace(0, codice.length(), codice.substring(0, 3));
        for(int i = codice.length(); i < 3; i++)
            codice.append("X");

        return codice;
    }

    private static StringBuilder calcolaCodDt(Date dtNasc, String sesso)
    {
        StringBuilder cod = new StringBuilder();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dtNasc);
        Integer giorno = Integer.valueOf(cal.get(5));
        Integer mese = Integer.valueOf(cal.get(2));
        Integer anno = Integer.valueOf(cal.get(1));
        cod.append(anno.toString().substring(2, 4));
        cod.append(getCodiceMese(mese.intValue()));
        if(sesso.equals("M"))
        {
            cod.append(String.format("%02d", new Object[]{
                    giorno
            }));
        } else
        {
            giorno = Integer.valueOf(giorno.intValue() + 40);
            cod.append(giorno.toString());
        }
        return cod;
    }

    private static Character calcolaCharControllo(StringBuilder codfisc)
    {
        Integer somma = new Integer(0);
        for(int i = 0; i < codfisc.length(); i++)
        {
            int k = Character.getNumericValue(codfisc.charAt(i));
            if(i % 2 == 0)
                somma = Integer.valueOf(somma.intValue() + EVEN_ODD_CHAR_CODES[1][k]);
            else
                somma = Integer.valueOf(somma.intValue() + EVEN_ODD_CHAR_CODES[0][k]);
        }

        System.out.println(somma);
        System.out.println(somma.intValue() % 26 + 10);
        System.out.println(Character.forDigit(somma.intValue() % 26 + 10, 35));
        System.out.println(Character.toUpperCase(Character.forDigit(somma.intValue() % 26 + 10, 35)));
        return Character.valueOf(Character.toUpperCase(Character.forDigit(somma.intValue() % 26 + 10, 36)));
    }

    private static String sostituzioneChar(String value)
    {
        for(int i = 0; i < CHAR_SOSTITUZIONE[1].length; i++)
            value = value.replaceAll(CHAR_SOSTITUZIONE[0][i], CHAR_SOSTITUZIONE[1][i]);
        return value;
    }

    private static char getCodiceMese(int mese)
    {
        return codici_mesi[mese];
    }

    private static boolean isVowel(char c)
    {
        return VOCALE_ALLOWED.matcher(String.valueOf(c)).matches();
    }

    private static final String CARATTERE_SOSTITUTO = "X";
    private static char codici_mesi[] = {
        'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 
        'S', 'T'
    };
    private static int EVEN_ODD_CHAR_CODES[][] = {
        {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
            10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 
            20, 21, 22, 23, 24, 25
        }, {
            1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 
            1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 
            2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 
            16, 10, 22, 25, 24, 23
        }
    };
    private static final String CHAR_SOSTITUZIONE[][] = {
        {
            "[\300]", "[\310]", "[\311]", "[\314]", "[\322]", "[\331]", "[\\s]", "[']"
        }, {
            "A", "E", "E", "I", "O", "U", "", ""
        }
    };
    private static final int ROW_REGEX = 0;
    private static final int ROW_SOST = 1;
    private static final Pattern CHAR_ALLOWED = Pattern.compile("[A-Z\300\310\311\314\322\331' ]+");
    private static final Pattern CODICE_COMUNE_ALLOWED = Pattern.compile("[A-Z][0-9]{3}");
    private static final Pattern VOCALE_ALLOWED = Pattern.compile("[AEIOU]");

}
