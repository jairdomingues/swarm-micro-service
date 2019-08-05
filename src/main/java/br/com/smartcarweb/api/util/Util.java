package br.com.smartcarweb.api.util;

import java.util.Random;

public class Util {

	 private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	 private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	   
	 public static String retirarMascara(String texto) {
		 return texto.replaceAll("\\D", "");
	 }
	
	 public static boolean isValidCPF(String cpf) {
        if ((cpf==null) || (cpf.length()!=11)) return false;

        Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
     }

     public static boolean isValidCNPJ(String cnpj) {
        if ((cnpj==null)||(cnpj.length()!=14)) return false;

        Integer digito1 = calcularDigito(cnpj.substring(0,12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString());
     }
     
     private static int calcularDigito(String str, int[] peso) {
         int soma = 0;
         for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
         }
         soma = 11 - soma % 11;
         return soma > 9 ? 0 : soma;
     }     
     
 	public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
	}
	
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::    This function converts decimal degrees to radians                         :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::    This function converts radians to decimal degrees                         :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }	

    public static String generateRandomProductActivationCode()	{
	    String out_str;
	     
	    byte[] byte_arry = new byte[6];    // 16-characters
	    int randInt = 0;
	 
	    Random rand_generator = new Random();
	 
	    for( int ii = 0 ; ii < 6 ; ii++) {
	        randInt = rand_generator.nextInt(9);  // 10 + 26 + 26 = 62  , gives range [0-61]
	        if( randInt <= 9) {
	            byte_arry[ii] = (byte) ((randInt + 48) & 0xFF);         //digits
	        } else {
	            if( randInt > 9 && randInt <= 35) {
	                byte_arry[ii] = (byte) ((randInt + 55) & 0xFF); //uppercase letters
	            } else {
	                byte_arry[ii] = (byte) ((randInt + 61) & 0xFF); //lowercase letters
	            }
	        }
	    }
	    out_str = new String(byte_arry);
	 
	    return out_str;
	}
    
}
