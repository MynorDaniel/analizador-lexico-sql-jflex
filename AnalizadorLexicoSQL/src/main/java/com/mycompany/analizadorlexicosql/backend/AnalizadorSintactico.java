/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexicosql.backend;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mynordma
 */
public class AnalizadorSintactico {
    
    private final ArrayList<Token> tokensInput;
    private final ArrayList<Estructura> errores = new ArrayList<>();

    public ArrayList<Estructura> getErrores() {
        return errores;
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
        List<Token> tokens = estructura.getTokens();
        if (tokens.isEmpty()) return false;

        Token firstToken = tokens.get(0);

        return switch (firstToken.getValor().toUpperCase()) {
            case "CREATE" -> validarCreate(tokens);
            case "INSERT" -> validarInsert(tokens);
            case "SELECT" -> validarSelect(tokens);
            case "UPDATE" -> validarUpdate(tokens);
            case "DELETE" -> validarDelete(tokens);
            case "DROP" -> validarDelete(tokens);
            case "ALTER" -> validarDelete(tokens);
            default -> false;
        };
    }

    private boolean validarCreate(List<Token> tokens) {
        if (tokens.size() < 3) return false;

        Token secondToken = tokens.get(1);
        if ("DATABASE".equalsIgnoreCase(secondToken.getValor())) {
            // Verificar estructura: CREATE DATABASE <identificador>;
            return tokens.size() == 4 &&
                   tokens.get(2).getTipo() == TipoToken.IDENTIFICADOR &&
                   tokens.get(3).getTipo() == TipoToken.PUNTO_Y_COMA;
        } else if ("TABLE".equalsIgnoreCase(secondToken.getValor())) {
            // Verificar estructura de tabla con campos y llaves opcionales.
            return validarCreateTable(tokens);
        }
        return false;
    }

    private boolean validarCreateTable(List<Token> tokens) {
        // Comprobar la estructura de CREATE TABLE <identificador> ( ... );
        int parenIndex = getParenIndex(tokens);
        int endParenIndex = getEndParenIndex(tokens);

        if (parenIndex == -1 || endParenIndex == -1 || endParenIndex <= parenIndex) {
            return false;
        }

        // Validar que los elementos dentro de los paréntesis sigan la estructura correcta.
        List<Token> fieldsTokens = tokens.subList(parenIndex + 1, endParenIndex);
        return validarCamposYLlaves(fieldsTokens) && tokens.get(endParenIndex + 1).getTipo() == TipoToken.PUNTO_Y_COMA;
    }

    private boolean validarCamposYLlaves(List<Token> tokens) {
        System.out.println("INICIO PARENTESIS");
        for (Token token : tokens) {
            System.out.println(token.toString());
        }
        System.out.println("FIN PARENTESIS");
        return true;
    }
    
    private boolean validarInsert(List<Token> tokens) {
        // Estructura esperada: INSERT INTO <identificador> (...) VALUES (...);
        return true; 
    }

    private boolean validarSelect(List<Token> tokens) {
        // Estructura esperada: SELECT ... FROM ... [WHERE ...] [GROUP BY ...] [ORDER BY ...];
        return true; 
    }

    private boolean validarUpdate(List<Token> tokens) {
        // Estructura esperada: UPDATE <identificador> SET ... [WHERE ...];
        return true; 
    }

    private boolean validarDelete(List<Token> tokens) {
        // Estructura esperada: DELETE FROM <identificador> [WHERE ...];
        return true; 
    }

    private int getParenIndex(List<Token> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.getTipo() == TipoToken.PARENTESIS_ABRE) {
                return i;
            }
        }
        return -1;
    }

    private int getEndParenIndex(List<Token> tokens) {
        for (int i = tokens.size() - 1; i >= 0; i--) {
            Token token = tokens.get(i);
            if (token.getTipo() == TipoToken.PARENTESIS_CIERRA) {
                return i;
            }
        }
        return -1;
    }

}
