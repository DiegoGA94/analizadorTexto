package analisistexto.src;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Divide palabras en sílabas y localiza la sílaba tónica.
 * @author Jorge Tercero
 */
public class Silabeador
{
    static String[][] conversiones = {
            {"ch", "@"},{"ll", "#"},{"gue", "%e"},{"gué", "%é"},{"gui", "%i"},{"guí", "%í"},
            {"qu", "&"},{"rr", "$"},{"ya","|a"},{"ye","|e"},{"yi","|i"},{"yo","|o"},{"yu","|u"}
    };
    static char[] abiertas = {'a', 'á', 'e', 'é', 'o', 'ó'};
    static char[] cerradas = {'i', 'u', 'ü', 'y'};
    static char[] cerradas_tilde = {'í', 'ú'};
    public Pattern patron_tilde = Pattern.compile(".*(á|é|í|ó|ú).*");
    Pattern patron_vocal_n_s = Pattern.compile(".*(á|é|í|ó|ú|a|e|i|o|u|n|s)");
    char enye = 'ñ';

    public static char[] getVocales() {
        int size = abiertas.length+cerradas.length+cerradas_tilde.length;
        char[] vocales = new char[size];
        int i = 0;
        for(int j = 0; j<abiertas.length; j++) {
            vocales[i]=abiertas[j];
            i++;
        }
        for(int j = 0; j<cerradas.length; j++) {
            vocales[i]=cerradas[j];
            i++;
        }
        for(int j = 0; j<cerradas_tilde.length; j++) {
            vocales[i]=cerradas_tilde[j];
            i++;
        }
        return vocales;
    }

    /**
     * Devuelve una colección de sílabas representando la palabra <code>w</code>.
     */
    public static ArrayList<String> silabear(String w)
    {
        w = format(w);
        int corte;
        String silaba;
        ArrayList<String> silabas = new ArrayList<String>();
        while(w.length()>0)
        {
            corte = next_s(w)+1;
            silaba = unformat( w.substring(0,corte) );
            w = w.substring(corte);
            silabas.add(silaba);
        }
        return silabas;
    }


    public static int next_s(String w)
    {
        int hacheIntercalada = 0;
        char[] a = w.toCharArray();

        // excepción: subrayar
        if (a[0]=='s' && a[1]=='u' && a[2]=='b' && a[3]=='r') return 2;

        int vocal=0;
        boolean found=false;
        while (vocal<a.length && !found) {
            found = esVocal(a[vocal]);
            if (!found) vocal++;
        }

        // sabemos que todas las letras anteriores a vocal + vocal forman parte de la sílaba, veamos las siguientes

        // vocal es la última vocal de la palabra: no hay más sílabas
        if (ultimaVocal(vocal, a)) return w.length()-1;

        int l1 = vocal+1;
        if (a[l1]=='h') {
            l1++;
            hacheIntercalada = 1;
        }

        // l1 es la última letra
        if (l1+1==a.length)
        {
            if (esVocal(a[l1]) && isHiato(a[vocal], a[l1])) return vocal;
            else return l1+hacheIntercalada;
        }

        int l2 = l1+1;
        if (a[l2]=='h') {
            l2++;
            hacheIntercalada = 1;
        }
        if (esConsonante(a[l1]) && esVocal(a[l2])) // VCV
        {
            return vocal;
        }
        else if (esConsonante(a[l1]) && esConsonante(a[l2])) // VCC
        {
            String[] cc = {"tr","gr","pr","br","bl","fr","fl","cl","dr","pl"};
            char[] tokenchar = {a[l1], a[l2]};
            String token = new String(tokenchar).toLowerCase();
            for (String s: cc) {
                if (s.equals(token)) return vocal;
            }
            if ("ns".equals(token))
            {
                if (a.length>l2+1 && esConsonante(a[l2+1])) return l2; // caso traNSporte
            }
            return l1+hacheIntercalada; // baRCo
        }
        else if (esVocal(a[l1])) // VV?
        {
            if (isHiato(a[vocal], a[l1])) return vocal+hacheIntercalada;
            else return vocal+next_s(w.substring(l1))+1+hacheIntercalada;
        }
        return 0;
    }

    public static boolean ultimaVocal(int vocal, char[] a)
    {
        for (int i=vocal+1; i<a.length; i++)
        {
            if (esVocal(a[i]))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean isHiato(char v1, char v2)
    {
        // si una de ellas es cerrada y lleva tilde
        for (char c: cerradas_tilde) {
            if (c==v1 || c==v2) return true;
        }
        // si las dos son abiertas
        for (char c1: abiertas) {
            if (c1==v1) {
                for (char c2: abiertas) {
                    if (c2==v2) return true;
                }
            }
        }
        // si son iguales (aa, ii)
        return (v1==v2);
    }

    public static boolean esVocal(Character l) {
        for (char c: getVocales()) {
            if (Character.toLowerCase(l)==c) return true;
        }
        return false;
    }
    public static boolean esConsonante(char l) {
        return (!esVocal(l));
    }

    public static String format(String w)
    {
        if (w==null) w = "";
        for (int i=0; i<conversiones.length; i++) {
            w = w.replace(conversiones[i][0], conversiones[i][1]);
        }
        // caso cacahuete, la h intercalada sí dividide sílaba
        if (w.startsWith("cacah")) w = w.replace("h", "¬");
        return w;
    }
    public static String unformat(String w)
    {
        if (w==null) w = "";
        for (int i=0; i<conversiones.length; i++) {
            w = w.replace(conversiones[i][1], conversiones[i][0]);
        }
        // caso cacahuete, la h intercalada sí dividide sílaba
        w = w.replace("¬", "h");
        return w;
    }

}