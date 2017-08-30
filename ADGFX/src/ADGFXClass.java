/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lis
 */
public class ADGFXClass {
    public static int[][]  alfabeto = new int[5][5];
    public static String[] alphabetPlane = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public static String[] alphabetCiphered = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public static int[] visitados = new int[alphabetPlane.length - 1];
    public static String[] newAlphabetPlane = new String[alphabetPlane.length - 1];
    public static String[] newAlphabetCiphered = new String[alphabetPlane.length - 1];
   
    public static String ADFGX(String texto, String key, boolean action, String letraA, String letraB, String cadena) {
        // TODO code application logic here

        //String text = "cualquiera";
        String sinEspacio = eliminarEspacios(texto);
        int aux=0;
        if(action) {
            for(int i= 0; i < alphabetPlane.length; i++) {
                if(alphabetPlane[i].equals(letraA)) {
                    aux = aux - 1;
                } else {
                    newAlphabetPlane[aux] = alphabetPlane[i];
                }
                aux ++;
            }
            
            for(int j = 0; j < 5; j ++) {
                for(int k = 0; k < 5; k++) {
                    int auxPos = (5*j)+(k+1);
                    for(int r = 0; r < newAlphabetPlane.length; r++) {
                        if(newAlphabetPlane[r].equals(""+cadena.charAt(auxPos-1))) {
                            alfabeto[j][k]=r;
                        }
                    }
                    
                }
            }
            return encrypt(sinEspacio, key, letraA, letraB);
        } else {
            return decrypt(sinEspacio, key, letraA, letraB);
        }
    }
    
    public static String buscarPos(char letra, String letraA, String letraB) {
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
    public static String decrypt(String text, String key, String letraA, String letraB) {
        char par1;
        char par2;
        int posFil=0;
        int posCol=0;
        String desencriptado = "";
        for(int f = 0; f < text.length(); f++) {
            par1=text.charAt(f);
            f++;
            par2=text.charAt(f);
            for(int g = 0; g < key.length(); g ++) {
                if(key.charAt(g) == par1) {
                    posFil = g;
                }
                if(key.charAt(g) == par2) {
                    posCol = g;
                }
            }
            desencriptado += newAlphabetPlane[alfabeto[posFil][posCol]];
        }
        return (desencriptado);
    }
    public static String encrypt(String text, String key, String letraA, String letraB) {
        String encriptado = "";
        String posicion = "";
        for(int i = 0; i < text.length(); i++) {
            posicion = buscarPos(text.charAt(i), letraA, letraB);
            System.out.println("posicion" + posicion);
            encriptado += key.charAt(Integer.parseInt(""+posicion.charAt(0)));
            encriptado += key.charAt(Integer.parseInt(""+posicion.charAt(1)));
        }
        return encriptado;
    }
    public static boolean fueVisitado(int random) {
        if(visitados[random] == 1) {
            return true;
        } 
        return false;
    }
    public static String eliminarEspacios(String text){
        String texto = "";
        for(int i = 0; i < text.length(); i++){
            if(!("" + text.charAt(i)).equals(" ")){
                texto = texto + text.charAt(i);
            }
        }
        return texto;
    }
    
}
