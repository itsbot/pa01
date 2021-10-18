/*=============================================================================
|   Assignment:  pa01 - Encrypting a plaintext file using the Vigenere cipher
|
|       Author:  Patrick Iannini
|     Language:  Java
|
|   To Compile:  javac pa01.java
|
|   To Execute:  java -> java pa01 kX.txt pX.txt
|                            where kX.txt is the keytext file
|                              and pX.txt is the plaintext file
|
|         Note:  All input files are simple 8 bit ASCII input
|
|        Class:  Cis3360 - Security in Computing - Fall 2021
|   Instructor:  McAlpin
|     Due Date:  Oct 26, 2021
|
+=============================================================================*/

import java.io.*;
import java.util.stream.Collectors;

public class pa01 {
    public static void getFile(File keyFile, File textFile) {
        try {
            BufferedReader key = new BufferedReader(new FileReader(keyFile));
            BufferedReader text = new BufferedReader(new FileReader(textFile));
            sanitize(key, text);
            key.close();
            text.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void sanitize(BufferedReader key, BufferedReader text) {
        int i=0;
        String sKey = key.lines().collect(Collectors.joining()).toLowerCase().replaceAll("[^a-z]", "");
        String eKey = sKey;
        if (eKey.length()>512) {
            eKey = (String) eKey.subSequence(0,512);
        }
        while (eKey.length() < 512) {
            eKey = eKey.concat(String.valueOf(eKey.charAt(i)));
            i++;
        }
        String sText = text.lines().collect(Collectors.joining()).toLowerCase().replaceAll("[^a-z]", "");
        if (sText.length()>512) {
            sText = (String) sText.subSequence(0,512);
        }
        while (sText.length() < 512) {
            sText = sText.concat("x");
        }
        encrypt(sKey, sText, eKey);
    }
    public static void encrypt(String sKey, String sText, String eKey) {
        int i;
        StringBuilder eText = new StringBuilder();
        for (i=0; i<sText.length(); i++) {
            eText.append((char) (((sText.charAt(i) - 97) + (eKey.charAt(i) - 97)) % 26 + 97));
        }
        output(sKey, sText, eText.toString());
    }
    public static void output(String sKey, String sText, String eText) {
        System.out.println("\n\nVigenere Key:\n\n"+sKey.replaceAll("(.{80})", "$1\n"));
        System.out.println("\n\nPlaintext:\n\n"+sText.replaceAll("(.{80})", "$1\n"));
        System.out.println("\n\nCiphertext:\n\n"+eText.replaceAll("(.{80})", "$1\n"));
    }
    public static void main(String[] args) {
        File keyFile = new File(args[0]);
        File textFile = new File(args[1]);
        getFile(keyFile, textFile);
    }
}

     /*=============================================================================
      |     I Patrick Iannini (4519265) affirm that this program is
      | entirely my own work and that I have neither developed my code together with
      | any another person, nor copied any code from any other person, nor permitted
      | my code to be copied  or otherwise used by any other person, nor have I
      | copied, modified, or otherwise used programs created by others. I acknowledge
      | that any violation of the above terms will be treated as academic dishonesty.
      +=============================================================================*/