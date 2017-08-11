/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corrido;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author joan.morales
 */
public class Corrido {

    /**
     * @param args the command line arguments
     */
    public static String[] alphabetCiphered = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public static int[] visitados = new int[alphabetCiphered.length];

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String text = "AAAGBHNKLSTTTT";
        System.out.println("Ingrese numero de corridos hacia la izquierda");
        Scanner scanner = new Scanner(System.in);
        int numeroCorridos = Integer.parseInt(scanner.nextLine());
        int i = 0;
        String corr = text;
        while (i < numeroCorridos) {
            corr = corri(corr);
            i ++;
            System.out.println("Corrido: #"+i+ "\n");
            System.out.println(text+"\n");
            System.out.println(corr+"\n");
            Coincidencias(text, corr);
            int total = 0;
            for(int j = 0; j < visitados.length; j ++) {
                
                System.out.println(alphabetCiphered[j] +"--->"+ visitados[j]);
                total = total + visitados[j];
                visitados[j] = 0;
            }
            System.out.println("Total Coincidencias: "+ total);
        }
        
    }
    public static String corri(String text) {
        String corr = "";
        for(int i = 1; i < text.length(); i++) {
            corr = corr + text.charAt(i);
        }
        return corr;
    }
    public static void Coincidencias (String text, String textCorrido) {
        for(int i = 0; i < textCorrido.length(); i ++) {
            String pos = ""+ text.charAt(i);
            int posicion = 0;
            int coinci = 0;
            if(text.charAt(i) == textCorrido.charAt(i)) {
                for (int j = 0; j < visitados.length; j++) {
                    if(alphabetCiphered[j].equals(pos)) {
                        posicion = j;
                    }
                }
                visitados[posicion] ++;
            }
        }
    }
}
