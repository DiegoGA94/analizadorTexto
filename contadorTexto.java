import java.util.Scanner;

public class contadorTexto {

    public static void main(String[] args) {
        
        Scanner teclado = new Scanner(System.in);
        StringBuilder texto = new StringBuilder();

        System.out.println("Ingrese un texto (deje una linea vacia para finalizar):");

        //String texto = teclado.useDelimiter("\\Z").next(); -> este es el modelo antiguo 
        while (true) {
            String linea = teclado.nextLine();
            if   (linea.isEmpty()) {
                break;
            }
            texto.append(linea).append("\n");
        }
        String textoFinal = texto.toString();

        int espacios = contadorEspacio(textoFinal);
        int lineas = contadorLinea(textoFinal);
        int puntos = contadorPunto(textoFinal);


        System.out.println("Cantidad de espacios: " + espacios);
        System.out.println("Cantidad de líneas: " + lineas);
        System.out.println("Cantidad de puntos: " + puntos);


        teclado.close();
    }
    public static int contadorEspacio(String texto){

        int contadorEspacio = texto.length() - texto.replace(" ", "").length();
            return contadorEspacio;
    }

    public static int contadorLinea(String texto){

        if (texto.isEmpty()) {
            return 0;  
        }else {
           return texto.split("\n").length;
        }
    }

    public static int contadorPunto(String texto){
        
        int contadorPunto = texto.length() - texto.replace(".", "").length();
            return contadorPunto;

    }

}
