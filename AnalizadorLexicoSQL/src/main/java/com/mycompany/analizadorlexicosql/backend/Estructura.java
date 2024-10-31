/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexicosql.backend;

import java.util.ArrayList;

/**
 *
 * @author mynordma
 */
public class Estructura {
    
    private final ArrayList<Token> tokens;

    public Estructura(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }
}
