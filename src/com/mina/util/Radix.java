/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.util;

/**
 *
 * @author mina
 */
public class Radix {
    private String chars;
    public Radix(String chars){
        this.chars = chars;
        
    }
    public String getExpression(int i){
        int number = i;
        String expr = "";
        do{
            int remainder = number % chars.length();
            expr = Character.toString(chars.charAt(remainder)) + expr;
            number = number / chars.length();
        }while(number > 0);
        return expr;
    }
    public static void main(String[] args){
        Radix r = new Radix("012345678");
        for(int i = 0;i < 100;i++){
            System.out.println(r.getExpression(i));
        }
    }

}
