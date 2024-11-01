/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexicosql.backend.validadores;

import com.mycompany.analizadorlexicosql.backend.Token;
import java.util.List;

/**
 *
 * @author mynordma
 */
public class ValidadorDML {
    
    public boolean esInsert(List<Token> tokens){
        return true;
    }
    
    public boolean esUpdate(List<Token> tokens){
        return true;
    }
    
    public boolean esSelect(List<Token> tokens){
        return true;
    }
    
    public boolean esDelete(List<Token> tokens){
        return true;
    }
}
