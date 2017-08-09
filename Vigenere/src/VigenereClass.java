/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joan.morales
 */
public class VigenereClass {
    
    public static String[] alphabetPlane = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public static String[] alphabetCiphered = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    
    public static String Vigenere(String text, String keyText, boolean encrypText) {
        String word = text;
        String key = keyText;
        String result;
        boolean encrypt = encrypText;
        boolean done;
        int mod = alphabetPlane.length;
        int posLetter, posKey, posResult;
        
        done = false;
        int actualLetter = 0;
        //encrypt = false;
        //word = "aozqkoh";
        //word = "DCRTYGK";
        //key = "dos";
        result = "";
        
        if(encrypt){
            word = eliminarEspacios(word);
            while(!done){
                posLetter = buscarPosicion("" + word.charAt(actualLetter));
                posKey = buscarPosicion("" + key.charAt(actualLetter % key.length()));
                posResult = (posLetter + posKey) % alphabetCiphered.length;
                result += alphabetCiphered[posResult];
                if(actualLetter == word.length() - 1){
                    done = true;
                } else {
                    actualLetter++;
                }
            }
        } else{
            while(!done){
                posLetter = buscarPosicion("" + word.charAt(actualLetter));
                posKey = buscarPosicion("" + key.charAt(actualLetter % key.length()));
                posResult = (alphabetPlane.length + posLetter - posKey) % alphabetPlane.length;
                result += alphabetPlane[posResult];
                if(actualLetter == word.length() - 1){
                    done = true;
                } else {
                    actualLetter++;
                }
            }          
        }
        return result;
        //System.out.println(result);
    }
    
    
    public static int buscarPosicion(String letter){
        for(int i = 0; i < alphabetPlane.length; i++){
            if(letter.equalsIgnoreCase(alphabetPlane[i])){
                return i;
            }
        }
        return -1;
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
