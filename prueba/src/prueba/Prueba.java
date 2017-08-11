/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import java.util.Random;

/**
 *
 * @author joan.morales
 */
public class Prueba {
    public static int[][]  alfabeto = new int[5][5];
    public static String letraA;
    public static String letraB;
    public static String[] alphabetPlane = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public static String[] alphabetCiphered = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public static String clave;
    public static int[] visitados = new int[alphabetPlane.length - 1];
    public static String[] newAlphabetPlane = new String[alphabetPlane.length - 1];
        public static String[] newAlphabetCiphered = new String[alphabetPlane.length - 1];
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        letraA = "i";
        letraB = "j";
        clave = "ADGFX";
        String text = "cualquiera";
        
        int aux=0;
        for(int i= 0; i < alphabetPlane.length; i++) {
            if(alphabetPlane[i].equals(letraA)) {
                aux = aux - 1;
            } else {
                newAlphabetPlane[aux] = alphabetPlane[i];
            }
            aux ++;
        }
        int minimum = 0;
        int maximum = 25;
        int randomNum;
            
        for(int j = 0; j < 5; j ++) {
            for(int k = 0; k < 5; k++) {
                randomNum = minimum + (int)(Math.random() * maximum);
                while(fueVisitado(randomNum)) {
                    randomNum = minimum + (int)(Math.random() * maximum);
                }
                System.out.println("arroje esto: " + randomNum);
                alfabeto[j][k] = randomNum;
                visitados[randomNum] = 1;
            }
        }
        System.out.println(encrypt(text));
        System.out.println(decrypt(encrypt(text)));
        
    }
    public static String buscarPos(char letra) {
        String letra2 = ""+letra;
        for(int x = 0; x < 5; x ++) {
            for(int y = 0; y < 5; y++) {
                if(letraA.equals(letra2)) {
                    letra2 = letraB;
                }
                if(letra2.equals(newAlphabetPlane[alfabeto[x][y]])){
                    return "" + x + y;
                }
            }
        }
        return null;
    }
    public static String decrypt(String text) {
        char par1;
        char par2;
        int posFil=0;
        int posCol=0;
        String desencriptado = "";
        for(int f = 0; f < text.length(); f++) {
            par1=text.charAt(f);
            f++;
            par2=text.charAt(f);
            for(int g = 0; g < clave.length(); g ++) {
                if(clave.charAt(g) == par1) {
                    posFil = g;
                }
                if(clave.charAt(g) == par2) {
                    posCol = g;
                }
            }
            desencriptado += newAlphabetPlane[alfabeto[posFil][posCol]];
        }
        return (desencriptado);
    }
    public static String encrypt(String text) {
        String encriptado = "";
        String posicion = "";
        for(int i = 0; i < text.length(); i++) {
            posicion = buscarPos(text.charAt(i));
            System.out.println("posicion" + posicion);
            encriptado += clave.charAt(Integer.parseInt(""+posicion.charAt(0)));
            encriptado += clave.charAt(Integer.parseInt(""+posicion.charAt(1)));
        }
        return encriptado;
    }
    public static boolean fueVisitado(int random) {
        if(visitados[random] == 1) {
            return true;
        } 
        return false;
    }
}
