/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

/**
 *
 * @author joan.morales
 */

public class AES {
    
    public static String[][] clavesRound = new String[4][4];
    public static String[][] estadoActual = new String[4][4];
    public static String[][] clavesTotales = new String[4][44];
    
    public static String[] claveRoundActual = {"01","02","04","08","10","20","40","80","1B","36"};

    public static String estadoInicial[][] = {{"32","88","31","e0"},
        {"43","5a","31","37"},
        {"F6","30","98","07"},
        {"a8","8d","a2","34"}};
    
    public static String claveInicial[][] = {{"2b","28","ab","09"},
        {"7e","ae","F7","CF"},
        {"15","d2","15","4F"},
        {"16","a6","88","3c"}};
    
    public static String SBox[][] = {
        {"63","7C","77","7B","F2","6B","6F","C5","30","01","67","2B","FE","D7","AB","76"},
        {"CA","82","C9","7D","FA","59","47","F0","AD","D4","A2","AF","9C","A4","72","C0"},
        {"B7","FD","93","26","36","3F","F7","CC","34","A5","E5","F1","71","D8","31","15"},
        {"04","C7","23","C3","18","96","05","9A","07","12","80","E2","EB","27","B2","75"},
        {"09","83","2C","1A","1B","6E","5A","A0","52","3B","D6","B3","29","E3","2F","84"},
        {"53","D1","00","ED","20","FC","B1","5B","6A","CB","BE","39","4A","4C","58","CF"},
        {"D0","EF","AA","FB","43","4D","33","85","45","F9","02","7F","50","3C","9F","A8"},
        {"51","A3","40","8F","92","9D","38","F5","BC","B6","DA","21","10","FF","F3","D2"},
        {"CD","0C","13","EC","5F","97","44","17","C4","A7","7E","3D","64","5D","19","73"},
        {"60","81","4F","DC","22","2A","90","88","46","EE","B8","14","DE","5E","0B","DB"},
        {"E0","32","3A","0A","49","06","24","5C","C2","D3","AC","62","91","95","E4","79"},
        {"E7","C8","37","6D","8D","D5","4E","A9","6C","56","F4","EA","65","7A","AE","08"},
        {"BA","78","25","2E","1C","A6","B4","C6","E8","DD","74","1F","4B","BD","8B","8A"},
        {"70","3E","B5","66","48","03","F6","0E","61","35","57","B9","86","C1","1D","9E"},
        {"E1","F8","98","11","69","D9","8E","94","9B","1E","87","E9","CE","55","28","DF"},
        {"8C","A1","89","0D","BF","E6","42","68","41","99","2D","0F","B0","54","BB","16"}
             
    };
   public static String galois[][] = {{"02","03","01","01"},
       {"01","02","03","01"},
       {"01","01","02","03"},
       {"03","01","01","02"}};
    public static String InverseSbox[][] = {
            {"52","09","6A","D5","30","36","A5","38","BF","40","A3","9E","81","F3","D7","FB"},
            {"7C","E3","39","82","9B","2F","FF","87","34","8E","43","44","C4","DE","E9","CB"},
            {"54","7B","94","32","A6","C2","23","3D","EE","4C","95","0B","42","FA","C3","4E"},
            {"08","2E","A1","66","28","D9","24","B2","76","5B","A2","49","6D","8B","D1","25"},
            {"72","F8","F6","64","86","68","98","16","D4","A4","5C","CC","5D","65","B6","92"},
            {"6C","70","48","50","FD","ED","B9","DA","5E","15","46","57","A7","8D","9D","84"},
            {"90","D8","AB","00","8C","BC","D3","0A","F7","E4","58","05","B8","B3","45","06"},
            {"D0","2C","1E","8F","CA","3F","0F","02","C1","AF","BD","03","01","13","8A","6B"},
            {"3A","91","11","41","4F","67","DC","EA","97","F2","CF","CE","F0","B4","E6","73"},
            {"96","AC","74","22","E7","AD","35","85","E2","F9","37","E8","1C","75","DF","6E"},
            {"47","F1","1A","71","1D","29","C5","89","6F","B7","62","0E","AA","18","BE","1B"},
            {"FC","56","3E","4B","C6","D2","79","20","9A","DB","C0","FE","78","CD","5A","F4"},
            {"1F","DD","A8","33","88","07","C7","31","B1","12","10","59","27","80","EC","5F"},
            {"60","51","7F","A9","19","B5","4A","0D","2D","E5","7A","9F","93","C9","9C","EF"},
            {"A0","E0","3B","4D","AE","2A","F5","B0","C8","EB","BB","3C","83","53","99","61"},
            {"17","2B","04","7E","BA","77","D6","26","E1","69","14","63","55","21","0C","7D"}
            
    };
    //https://www.youtube.com/watch?v=BctlBJ2NdHk
    //http://www.binaryhexconverter.com/hex-to-binary-converter
    //http://www.binaryhexconverter.com/binary-to-hex-converter
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        obtenerClave(claveInicial);
        mostrarMatriz(clavesTotales);
        for(int a = 0; a <= 10; a ++) {
            if(a==0) {
                for(int b =0; b < estadoActual.length; b++) {
                    for (int c = 0; c < estadoActual[0].length; c++) {
                        estadoActual[b][c]=XOR(estadoInicial[b][c], claveInicial[b][c]);
                    }
                }
                mostrarMatriz(estadoActual);
            } else if(a > 0 && a < 10) {
                estadoActual = subBytes(estadoActual);
                mostrarMatriz(estadoActual);
                estadoActual = shiftRows(estadoActual);
                mostrarMatriz(estadoActual);
                estadoActual = mixColumns(estadoActual);
                mostrarMatriz(estadoActual);
                clavesRound = obtenerSiguienteClave(a);
                mostrarMatriz(clavesRound);
                for(int b =0; b < estadoActual.length; b++) {
                    for (int c = 0; c < estadoActual[0].length; c++) {
                        estadoActual[b][c]=XOR(estadoActual[b][c], clavesRound[b][c]);
                    }
                }
                mostrarMatriz(estadoActual);
            } else if(a == 10) {
                estadoActual = subBytes(estadoActual);
                mostrarMatriz(estadoActual);
                estadoActual = shiftRows(estadoActual);
                mostrarMatriz(estadoActual);
                clavesRound = obtenerSiguienteClave(a);
                mostrarMatriz(clavesRound);
                for(int b =0; b < estadoActual.length; b++) {
                    for (int c = 0; c < estadoActual[0].length; c++) {
                        estadoActual[b][c]=XOR(estadoActual[b][c], clavesRound[b][c]);
                    }
                }
                mostrarMatriz(estadoActual);
            }
            
        }

    }
    public static String[][] subBytes(String[][] estado) {
        String[][] estadoAux = estado;
        String posA="";
        String posB="";
        
        int x = 0;
        int y = 0;
        for(int i = 0; i < estado.length; i++) {
            for(int j = 0; j < estado[0].length; j++) {
                if(estado[i][j].length()==1){
                    estado[i][j]="0"+estado[i][j];
                }
                posA = ""+estado[i][j].charAt(0);
                posB = ""+estado[i][j].charAt(1);
                x = coordenada(posA);
                y = coordenada(posB);
                estadoAux[i][j] = SBox[x][y]; 
           }
        }
        return estadoAux;
    }
    
    public static String[][] shiftRows(String[][] estado) {
        String[][] estadoAux = estado;
        for(int i = 1; i < estado.length; i++) {
            int aux = i;
            while(aux != 0){
                String auxiliar= estadoAux[i][0];
                estadoAux[i][0] = estadoAux[i][1];
                estadoAux[i][1] = estadoAux[i][2];
                estadoAux[i][2] = estadoAux[i][3];
                estadoAux[i][3] = auxiliar;
                aux = aux -1;
            } 
        }
        return estadoAux;
    }
    public static String[][] mixColumns(String[][] estado) {
        String[][] estadoAux = new String[4][4];
        for(int z=0;z<estado.length;z++) {
            for(int i = 0; i < estado.length; i++) {
                String resultado = "00";
                String resul = "";
                for (int k= 0; k < estado.length; k++){
                    String aux1 = estado[k][z];
                    String aux2 = galois[i][k];
                    if(aux1.length()==1) {
                        aux1="0"+aux1;
                    }
                    if(aux2.length() == 1) {
                        aux2="0"+aux2;
                    }
                    if(resultado.length()==1){
                        resultado="0"+resultado;
                    }
                    aux1 = hexToBin(aux1);
                    aux2 = hexToBin(aux2);
                    String auxResult="";
                    String auxResult2 ="";
                    String aux="";
                    switch(Integer.parseInt(galois[i][k])) {
                        
                        case 1:
                            resul =hexToBin(resultado);
                            auxResult = XOR(resul,aux1);
                            break;
                        case 2:
                            auxResult = aux1+"0";
                            aux = ""+auxResult.charAt(0);
                            if(auxResult.length() > 8 && aux.equals("1")) {
                                auxResult = XOR(auxResult, "100011011");
                            }
                            while(auxResult.length() < 8) {
                                auxResult = "0"+ auxResult;
                            }
                            resul =hexToBin(resultado);
                            if(resul.length() == 8 && auxResult.length()==9) {
                                resul="0"+resul;
                            }
                            auxResult = XOR(resul,auxResult);
                            break;
                        case 3:
                            auxResult2 = aux1;
                            auxResult = aux1 + "0";
                            auxResult = XOR(auxResult, "0"+auxResult2);
                            aux = ""+auxResult.charAt(0);
                            if(auxResult.length() > 8 && aux.equals("1")) {
                                auxResult = XOR(auxResult, "100011011");
                            }
                            while(auxResult.length() < 8) {
                                auxResult = "0"+ auxResult;
                            }
                            while(auxResult2.length() < 8) {
                                auxResult2 = "0"+ auxResult2;
                            }
                            resul =hexToBin(resultado);
                            auxResult = XOR(resul,auxResult);
                            break;
                    }
                    resultado = binToHexa(auxResult);
                    if(resultado.length()==1){
                        resultado="0"+resultado;
                    }
                }
                estadoAux[i][z] = resultado;
                mostrarMatriz(estadoAux);

            }
        }
        return estadoAux;
    }
    
    public static String hexToBin(String hex) {
        String bin = "";
        String binFragment = "";
        int iHex;
        hex = hex.trim();
        hex = hex.replaceFirst("0x", "");

        for(int i = 0; i < hex.length(); i++){
            iHex = Integer.parseInt(""+hex.charAt(i),16);
            binFragment = Integer.toBinaryString(iHex);

            while(binFragment.length() < 4){
                binFragment = "0" + binFragment;
            }
            bin += binFragment;
        }
    return bin;
    }
    
    public static String binToHexa(String bin) {
        return Long.toHexString(Long.parseLong(bin,2));    
    }
    
    public static int coordenada(String coord) {
        int x = 0;
        switch(coord.toUpperCase()) {
                    case "A":
                        x = 10;
                        break;
                    case "B":
                        x = 11;
                        break;
                    case "C":
                        x = 12;
                        break;
                    case "D":
                        x = 13;
                        break;
                    case "E":
                        x = 14;
                        break;
                    case "F":
                        x=15;
                        break;
                    default:
                        x = Integer.parseInt(coord);
                }
        return x;
    }
    public static String Suma(String a, String b) {
        int aux = a.length();
        String resultado = "";
        String acarreo = "0";
        while(aux != 0 ) {
            String a1 = ""+a.charAt(aux-1);
            String a2 = ""+b.charAt(aux-1);
            if(a1.equals("0") && a2.equals("0") && acarreo.equals("0")) {
                resultado = "0" + resultado;
            } else if(a1.equals("0") && a2.equals("0") && acarreo.equals("1")) {
                resultado = "1" + resultado;
                acarreo = "0";
            } else if(a1.equals("0") && a2.equals("1") && acarreo.equals("0")) {
                resultado = "1" + resultado;
                acarreo = "0";
            } else if(a1.equals("0") && a2.equals("1") && acarreo.equals("1")) {
                resultado = "0" + resultado;
                acarreo = "1";
            } else if(a1.equals("1") && a2.equals("0") && acarreo.equals("0")) {
                resultado = "1" + resultado;
                acarreo = "0";
            }else if(a1.equals("1") && a2.equals("0") && acarreo.equals("1")) {
                resultado = "0" + resultado;
                acarreo = "1";
            } else if(a1.equals("1") && a2.equals("1") && acarreo.equals("0")) {
                resultado = "0" + resultado;
                acarreo = "1";
            }else if(a1.equals("1") && a2.equals("1") && acarreo.equals("1")) {
                resultado = "1" + resultado;
                acarreo = "1";
            }
            aux--;
        }
        return resultado;
    }
    public static String Multiplicacion(String a, String b) {
        int aux = a.length();
        String resultado = "";
        while(aux != 0 ) {
            String a1 = ""+a.charAt(aux-1);
            String a2 = ""+b.charAt(aux-1);
            if(a1.equals("1") && a2.equals("1")) {
                resultado = "1"+resultado;
            } else {
                resultado = "0"+resultado;
            }
            aux--;
        }
        return resultado;
    }
    public static String XOR(String a, String b){
        String resultado ="";
        String aux1 = hexToBin(a);
        String aux2 = hexToBin(b);
        int i=0;
        while((i<aux1.length())) {
            
            String a1 = ""+aux1.charAt(i);
            String a2 = ""+aux2.charAt(i);
            if(a1.equals("0") && a2.equals("0")) {
                resultado = resultado + "0";
            } else if(a1.equals("1") && a2.equals("1")) {
                resultado = resultado + "0";
            } else if(a1.equals("0") || a2.equals("0")) {
                resultado = resultado + "1";
            }
            i++;
        }
        return binToHexa(resultado);
    }
    public static void mostrarMatriz(String[][] matriz) {
        String auxiliar[][] = matriz;
        for(int i = 0; i < auxiliar.length; i++) {
            for (int j = 0; j < auxiliar[0].length; j++) {
                System.out.print(auxiliar[i][j]+"-");
            }
            System.out.print("\n");
        }
    }
    public static void obtenerClave(String[][] clave) {
        int r;
        for( r=0; r < clave.length; r++) {
            for(int d = 0; d < clave[0].length; d++) {
                clavesTotales[r][d] = clave[r][d];
            }
        }
        String[][] claveAux = new String[4][1];
        while(r < clavesTotales[0].length) {
                int round = r/4;
                int auxR1 = r-1;
                int auxR2 = r-4;
                if((r%4)==0) {
                    
                    for(int h =0; h < claveAux.length; h++) {
                        claveAux[h][0] = clavesTotales[h][auxR1];
                    }
                    String auxiliar = claveAux[0][0];
                    claveAux[0][0] = claveAux[1][0];
                    claveAux[1][0] = claveAux[2][0];
                    claveAux[2][0] = claveAux[3][0];
                    claveAux[3][0] = auxiliar;
                    for(int h =0; h < claveAux.length; h++) {
                        if(claveAux[h][0].length() == 1) {
                            claveAux[h][0] = "0"+claveAux[h][0];
                        }
                        String posA = ""+claveAux[h][0].charAt(0);
                        String posB = ""+claveAux[h][0].charAt(1);
                        int x = coordenada(posA);
                        int y = coordenada(posB);
                        claveAux[h][0] = SBox[x][y];
                    }
                    for(int k = 0; k < clave.length; k++) {
                        String auxXOR1 = XOR(claveAux[k][0],clavesTotales[k][auxR2]);
                        if(auxXOR1.length() == 1) {
                            auxXOR1="0"+auxXOR1;
                        }
                        String auxXOR2=XOR(auxXOR1,claveRoundActual[round-1]);
                        if(k==0){
                            clavesTotales[k][r]=auxXOR2;
                        } else {
                            clavesTotales[k][r]=auxXOR1;
                        }
                    }
                }else{
                    for(int k = 0; k < clave.length; k++) {
                        if(clavesTotales[k][auxR1].length() == 1){
                            clavesTotales[k][auxR1]="0"+clavesTotales[k][auxR1];
                        } 
                        if(clavesTotales[k][auxR2].length() == 1) {
                            clavesTotales[k][auxR2]="0"+clavesTotales[k][auxR2];
                        }
                            String auxXOR = XOR(clavesTotales[k][auxR1],clavesTotales[k][auxR2]);
                            if(auxXOR.length()==1) {
                                auxXOR="0"+auxXOR;
                            }
                            clavesTotales[k][r]=auxXOR;
                    }
                }
                r++;
        }
    }
    public static String[][] obtenerSiguienteClave(int round) {
        String[][] auxiliar = new String[4][4];
        int r = round*4;
        while (r < (round*4)+4) {
            for(int i = 0; i < clavesTotales.length; i++) {
                int a = r%4;
                auxiliar[i][a] = clavesTotales[i][r];
            }
            r++;
        }
        return auxiliar;
    }
}
