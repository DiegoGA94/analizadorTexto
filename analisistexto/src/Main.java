import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

// Solicitar el texto al usuario
        System.out.println("Por favor, ingrese el texto:");
        String texto = scanner.useDelimiter("\\A").nextLine();

// Análisis del texto
        int palabrasEsdrujulas = contarPalabrasEsdrujulas(texto);
        int palabrasAgudas = contarPalabrasAgudas(texto);
        int palabrasSobresdrujulas = contarPalabrasSobresdrujulas(texto);

        int palabrasInicianConL = contarPalabrasInicianConL(texto);

// Mostrar resultados
        System.out.println("Cantidad de palabras esdrújulas: " + palabrasEsdrujulas);
        System.out.println("Cantidad de palabras agudas: " + palabrasAgudas);
        System.out.println("Cantidad de palabras sobresdrújulas: " + palabrasSobresdrujulas);
        System.out.println("Cantidad de palabras que inician con 'L': " + palabrasInicianConL);

    }

    // Función para contar palabras que inician con "L"
    public static int contarPalabrasInicianConL(String texto) {
        String[] palabras = texto.split("\\s+");
        int contador = 0;
        for (String palabra : palabras) {
            if (palabra.toLowerCase().startsWith("l")) {
                contador++;
            }
        }
        return contador;

    }
    // Vocales acentuadas
    private static final String VOCALES_ACENTUADAS = "[áéíóúÁÉÍÓÚ]";

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

        for (String palabra : palabras) {
            if (esEsdrujula(palabra)) {
                contador++;
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
        int posicionAcento = posicionDeAcento(palabra);
        return posicionAcento == palabra.length() - 1; // Acento en la última sílaba
    }

    // Función para determinar si una palabra es esdrújula
    private static boolean esEsdrujula(String palabra) {
        int posicionAcento = posicionDeAcento(palabra);
        return posicionAcento == palabra.length() - 3; // Acento en la antepenúltima sílaba
    }

    // Función para determinar si una palabra es sobresdrújula
    private static boolean esSobresdrujula(String palabra) {
        int posicionAcento = posicionDeAcento(palabra);
        return posicionAcento < palabra.length() - 3; // Acento antes de la antepenúltima sílaba
    }

    // Función para obtener la posición del acento
    private static int posicionDeAcento(String palabra) {
        Pattern patron = Pattern.compile(VOCALES_ACENTUADAS);
        java.util.regex.Matcher matcher = patron.matcher(palabra);

        if (matcher.find()) {
            return matcher.start(); // Devuelve la posición del acento
        }
        return -1; // Si no hay acento explícito, devuelve -1
    }
}