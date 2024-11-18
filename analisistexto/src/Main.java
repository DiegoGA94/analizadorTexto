package analisistexto.src;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder texto = new StringBuilder();

// Solicitar el texto al usuario
        System.out.println("Por favor, ingrese el texto:");

        while (true) {
            String linea = scanner.nextLine();
            if   (linea.isEmpty()) {
                break;
            }
            texto.append(linea).append("\n");
        }
        String textoFinal = texto.toString();

// Análisis del texto
        int espacios = contadorEspacio(textoFinal);
        int lineas = contadorLinea(textoFinal);
        int puntos = contadorPunto(textoFinal);

        int palabrasEsdrujulas = contarPalabrasEsdrujulas(textoFinal);
        int palabrasAgudas = contarPalabrasAgudas(textoFinal);
        int palabrasSobresdrujulas = contarPalabrasSobresdrujulas(textoFinal);

        int palabrasInicianConL = contarPalabrasInicianConL(textoFinal);
        int palabrasFinalizanConAdo = contarPalabrasFinalizanConAdo(textoFinal);
        int letrasT = contarLetrasT(textoFinal);
        int letrasA = contarLetrasA(textoFinal);

// Mostrar resultados
        System.out.println("Cantidad de espacios: " + espacios);
        System.out.println("Cantidad de líneas: " + lineas);
        System.out.println("Cantidad de puntos: " + puntos);
        System.out.println("Cantidad de palabras esdrújulas: " + palabrasEsdrujulas);
        System.out.println("Cantidad de palabras agudas: " + palabrasAgudas);
        System.out.println("Cantidad de palabras sobresdrújulas: " + palabrasSobresdrujulas);
        System.out.println("Cantidad de palabras que inician con la letra 'L': " + palabrasInicianConL);
        System.out.println("Cantidad de palabras que finalizan con 'ado': " + palabrasFinalizanConAdo);
        System.out.println("Cantidad de letras 'T': " + letrasT);
        System.out.println("Cantidad de letras 'A': " + letrasA);

    }
// Función para contar Espacios
    public static int contadorEspacio(String texto){

        int contadorEspacio = texto.length() - texto.replace(" ", "").length();
        return contadorEspacio;
    }
// Función para contar las lineas
    public static int contadorLinea(String texto){

        if (texto.isEmpty()) {
            return 0;
        }else {
            return texto.split("\n").length;
        }
    }
// Función para contar los puntos
    public static int contadorPunto(String texto){

        int contadorPunto = texto.length() - texto.replace(".", "").length();
        return contadorPunto;

    }

    // Función para contar palabras que inician con "L"
    public static int contarPalabrasInicianConL(String texto) {
        String[] palabras = texto.split("\\s+");
        int contador = 0;
        for (String palabra : palabras) {
            if (palabra.startsWith("L")) {
                contador++;
            }
        }
        return contador;

    }

    // Función para contar palabras que finalizan con "ado"
    public static int contarPalabrasFinalizanConAdo(String texto) {
        String[] palabras = texto.split("\\s+");
        int contador = 0;
        for (String palabra : palabras) {
            palabra = palabra.toLowerCase().replaceAll("[^a-zA-Záéíóúüñ]", "");
            if (palabra.toLowerCase().endsWith("ado")) {
                contador++;
            }
        }
        return contador;
    }

    // Función para contar palabras agudas
    public static int contarPalabrasAgudas(String texto) {
        String[] palabras = texto.split("\\s+");
        int contador = 0;

        for (String palabra : palabras) {
            if (esAguda(palabra)) {
                contador++;
            }
        }
        return contador;
    }

    // Función para contar palabras esdrújulas
    public static int contarPalabrasEsdrujulas(String texto) {
        String[] palabras = texto.split("\\s+");
        int contador = 0;
        String patrones = "\\b\\w+[áéíóúÁÉÍÓÚ]\\w+\\b";
        Pattern patronCompilado = Pattern.compile(patrones);

        for (String palabra : palabras) {

            Matcher matcher = patronCompilado.matcher(palabra);

            while (matcher.find()) {
                String palabraEncontrada = matcher.group();
                if (esEsdrujula(palabraEncontrada)) {
                    contador++;
                }
            }
        }
        return contador;
    }

    // Función para contar palabras sobresdrújulas
    public static int contarPalabrasSobresdrujulas(String texto) {
        String[] palabras = texto.split("\\s+");
        int contador = 0;

        for (String palabra : palabras) {
            if (esSobresdrujula(palabra)) {
                contador++;
            }
        }
        return contador;
    }

    // Función para determinar si una palabra es aguda
    private static boolean esAguda(String palabra) {
        Silabeador silabeador=new Silabeador();
        ArrayList<String> silabas = new ArrayList<String>();
        silabas = silabeador.silabear(palabra);
        if(silabas.size()>=1){
            String silaba = silabas.get(silabas.size()-1);
            for (char c : silaba.toCharArray()) {
                if ("áéíóúÁÉÍÓÚ".indexOf(c) != -1) {
                    return true; // Si encuentra una vocal con tilde, retorna true
                }
            }
        }
        return false;
    }

    // Función para determinar si una palabra es esdrújula
    private static boolean esEsdrujula(String palabra) {
        Silabeador silabeador=new Silabeador();
        ArrayList<String> silabas = new ArrayList<String>();
        silabas = silabeador.silabear(palabra);
        if(silabas.size()>=3){
            String silaba = silabas.get(silabas.size()-3);
            for (char c : silaba.toCharArray()) {
                if ("áéíóúÁÉÍÓÚ".indexOf(c) != -1) {
                    return true; // Si encuentra una vocal con tilde, retorna true
                }
            }
        }
        return false;
    }

    // Función para determinar si una palabra es sobresdrújula
    private static boolean esSobresdrujula(String palabra) {
        Silabeador silabeador=new Silabeador();
        ArrayList<String> silabas = new ArrayList<String>();
        silabas = silabeador.silabear(palabra);
        if(silabas.size()==4){
            String silaba = silabas.get(silabas.size()-4);

            for (char c : silaba.toCharArray()) {
                if ("áéíóúÁÉÍÓÚ".indexOf(c) != -1) {
                    return true; // Si encuentra una vocal con tilde, retorna true
                }
            }

        }else if(silabas.size()>4){
            String silaba2 = silabas.get(silabas.size()-5);
            for (char c : silaba2.toCharArray()) {
                if ("áéíóúÁÉÍÓÚ".indexOf(c) != -1) {
                    return true; // Si encuentra una vocal con tilde, retorna true
                }
            }
        }

        return false;
    }

    // Función para saber cuantas letras 'T' hay en el texto
    public static int contarLetrasT(String texto) {
        int contador = 0;
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == 'T') {
                contador++;
            }
        }
        return contador;
    }

    // Función para saber cuantas letras 'A' hay en el texto
    public static int contarLetrasA(String texto) {
        int contador = 0;
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == 'A') {
                contador++;
            }
        }
        return contador;
    }
}