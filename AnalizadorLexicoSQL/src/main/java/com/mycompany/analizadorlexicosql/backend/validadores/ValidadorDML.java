/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexicosql.backend.validadores;

import com.mycompany.analizadorlexicosql.backend.TipoToken;
import com.mycompany.analizadorlexicosql.backend.Token;
import java.util.List;

/**
 *
 * @author mynordma
 */
public class ValidadorDML {
    
    public boolean esInsert(List<Token> tokens) {
        if (tokens.size() < 6 || !tokens.get(0).getValor().equalsIgnoreCase("INSERT")
                || !tokens.get(1).getValor().equalsIgnoreCase("INTO")) {
            return false;
        }

        int i = 2;

        // Debe haber un identificador después de "INSERT INTO"
        if (tokens.get(i++).getTipo() != TipoToken.IDENTIFICADOR) {
            return false;
        }

        // Se espera "(", seguido de identificadores separados por comas y luego ")"
        if (!tokens.get(i++).getValor().equals("(")) {
            return false;
        }

        // Verificar los identificadores dentro del paréntesis
        while (i < tokens.size() && tokens.get(i).getTipo() == TipoToken.IDENTIFICADOR) {
            i++;
            if (i < tokens.size() && tokens.get(i).getValor().equals(",")) {
                i++;
            } else {
                break;
            }
        }

        // Debe haber un ")" después de los identificadores
        if (i >= tokens.size() || !tokens.get(i++).getValor().equals(")")) {
            return false;
        }

        // Debe haber "VALUES"
        if (i >= tokens.size() || !tokens.get(i++).getValor().equalsIgnoreCase("VALUES")) {
            return false;
        }

        return tokens.get(i++).getValor().equals("(");
    }

    public boolean esUpdate(List<Token> tokens) {
        if (tokens.size() < 4 || !tokens.get(0).getValor().equalsIgnoreCase("UPDATE")) {
            return false;
        }

        int i = 1;

        // Debe haber un identificador después de "UPDATE"
        if (tokens.get(i++).getTipo() != TipoToken.IDENTIFICADOR) {
            return false;
        }
        return !(i >= tokens.size() || !tokens.get(i++).getValor().equalsIgnoreCase("SET"));
    }

    public boolean esSelect(List<Token> tokens) {
        if (tokens.size() < 4 || !tokens.get(0).getValor().equalsIgnoreCase("SELECT")) {
            return false;
        }

        int i = 1; 

        if (!(tokens.get(i).getValor().equals("*") || tokens.get(i).getTipo() == TipoToken.IDENTIFICADOR)) {
            return false;
        }
        i++;

        // Verificar si hay un "FROM"
        if (i >= tokens.size() || !tokens.get(i++).getValor().equalsIgnoreCase("FROM")) {
            return false;
        }
        // Verificar identificador de la tabla

        return tokens.get(i++).getTipo() == TipoToken.IDENTIFICADOR;
    }

    public boolean esDelete(List<Token> tokens) {
        if (tokens.size() < 4 || !tokens.get(0).getValor().equalsIgnoreCase("DELETE")) {
            return false;
        }

        int i = 1; 

        // Verificar "FROM"
        if (tokens.get(i++).getValor().equalsIgnoreCase("FROM")) {
            // Verificar el identificador de la tabla
            if (tokens.get(i++).getTipo() != TipoToken.IDENTIFICADOR) {
                return false;
            }

            // Validar cláusula WHERE opcional
            if (i < tokens.size() && tokens.get(i).getValor().equalsIgnoreCase("WHERE")) {
                i++;
            }
        } else {
            return false;
        }

        return true;
    }

}
