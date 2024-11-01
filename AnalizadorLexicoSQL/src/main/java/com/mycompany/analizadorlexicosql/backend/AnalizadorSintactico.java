/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexicosql.backend;

import com.mycompany.analizadorlexicosql.backend.validadores.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mynordma
 */
public class AnalizadorSintactico {
    
    private final ArrayList<Token> tokensInput;
    private final ArrayList<Estructura> errores = new ArrayList<>();

    public ArrayList<Token> getErrores() {
        ArrayList<Token> erroresToken = new ArrayList<>();
        return erroresToken;
    }

    public AnalizadorSintactico(ArrayList<Token> tokensInput) {
        this.tokensInput = tokensInput;
    }

    public ArrayList<Estructura> getEstructurasSQL() {
        ArrayList<Estructura> estructurasSinValidar = separarTokens();
        ArrayList<Estructura> estructurasValidas = validarEstructuras(estructurasSinValidar);
        return estructurasValidas;
    }
    
    private ArrayList<Estructura> separarTokens(){
        ArrayList<Estructura> estructuras = new ArrayList<>();
        ArrayList<Token> currentTokens = new ArrayList<>();

        for (Token token : tokensInput) {
            // Agregar el token a la estructura actual
            currentTokens.add(token);

            if (token.getTipo() == TipoToken.PUNTO_Y_COMA) {
                // Crear una nueva estructura con los tokens actuales y agregarla a la lista
                estructuras.add(new Estructura(new ArrayList<>(currentTokens)));
                currentTokens.clear();
            }
        }

        if (!currentTokens.isEmpty()) {
            estructuras.add(new Estructura(new ArrayList<>(currentTokens)));
        }

        return estructuras;
    }

    private ArrayList<Estructura> validarEstructuras(ArrayList<Estructura> estructurasSinValidar){
        ArrayList<Estructura> estructurasValidas = new ArrayList<>();
        for (Estructura estructura : estructurasSinValidar) {
            if(estructuraValida(estructura)){
                estructurasValidas.add(estructura);
            }else{
                errores.add(estructura);
            }
        }
        return estructurasValidas;
    }

    private boolean estructuraValida(Estructura estructura) {
        ValidadorDDL validadorDDL = new ValidadorDDL();
        ValidadorDML validadorDML = new ValidadorDML();
        List<Token> tokens = estructura.getTokens();
        boolean estructuraValida = false;
        
        if(validadorDDL.esCreate(tokens)){
            estructuraValida = true;
        }else if(validadorDDL.esModificador(tokens)){
            estructuraValida = true;
        }else if(validadorDML.esInsert(tokens)){
            estructuraValida = true;
        }else if(validadorDML.esSelect(tokens)){
            estructuraValida = true;
        }else if(validadorDML.esDelete(tokens)){
            estructuraValida = true;
        }else if(validadorDML.esUpdate(tokens)){
            estructuraValida = true;
        }
        return estructuraValida;
    }
}
