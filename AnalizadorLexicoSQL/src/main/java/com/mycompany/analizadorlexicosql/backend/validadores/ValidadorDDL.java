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
public class ValidadorDDL {
    
    public boolean esCreate(List<Token> tokens) {
        // Verificar que al menos haya tokens suficientes para "CREATE DATABASE <identificador> ;"
        if (tokens.isEmpty() || tokens.size() < 4) {
            return false;
        }

        // Verificar si el primer token es "CREATE"
        if (tokens.get(0).getTipo() != TipoToken.CREATE) {
            return false;
        }

        // Validar estructura "CREATE DATABASE <identificador> ;"
        if (tokens.get(1).getTipo() == TipoToken.DATABASE) {
            return validarCreateDatabase(tokens);
        }

        // Validar estructura "CREATE TABLE <identificador> ;"
        if (tokens.get(1).getTipo() == TipoToken.TABLE) {
            return validarCreateTable(tokens);
        }

        return false;
    }

    private boolean validarCreateDatabase(List<Token> tokens) {
        // Verificar que la estructura sea exactamente: CREATE DATABASE <identificador> ;
        return tokens.size() == 4 &&
               tokens.get(2).getTipo() == TipoToken.IDENTIFICADOR &&
               tokens.get(3).getTipo() == TipoToken.PUNTO_Y_COMA;
    }

    private boolean validarCreateTable(List<Token> tokens) {
        // Verificar que la estructura sea exactamente: CREATE TABLE <identificador> ;
        return tokens.size() >= 4 &&
               tokens.get(2).getTipo() == TipoToken.IDENTIFICADOR &&
               tokens.get(3).getTipo() == TipoToken.PARENTESIS_ABRE &&
               tokens.get(4).getTipo() == TipoToken.IDENTIFICADOR &&
               tipoDeDato(tokens.get(5).getTipo());
    }

    private boolean tipoDeDato(TipoToken tipo) {
        return tipo == TipoToken.INTEGER ||
               tipo == TipoToken.BIGINT ||
               tipo == TipoToken.VARCHAR ||
               tipo == TipoToken.DECIMAL ||
               tipo == TipoToken.DATE ||
               tipo == TipoToken.TEXT ||
               tipo == TipoToken.BOOLEAN ||
               tipo == TipoToken.SERIAL;
    }
    
    public boolean esModificador(List<Token> tokens) {
        Token primerToken = tokens.get(0);
        Token segundoToken = tokens.get(1);

        // Verifica que la instrucción inicie con "ALTER TABLE" o "DROP TABLE"
        if (primerToken.getTipo() == TipoToken.ALTER && segundoToken.getTipo() == TipoToken.TABLE) {
            // Comprobar estructura de "ALTER TABLE <identificador> ..."
            if (tokens.get(2).getTipo() == TipoToken.IDENTIFICADOR) {
                Token tercerToken = tokens.get(3);
                return switch (tercerToken.getTipo()) {
                    case ADD -> validarAdd(tokens.subList(4, tokens.size()));
                    case ALTER -> validarAlter(tokens.subList(4, tokens.size()));
                    case DROP -> validarDrop(tokens.subList(4, tokens.size()));
                    default -> false;
                };
            } else {
            }
        } else if (primerToken.getTipo() == TipoToken.DROP && segundoToken.getTipo() == TipoToken.TABLE) {
            // Comprobar estructura de "DROP TABLE IF EXISTS <identificador> CASCADE"
            if (tokens.get(2).getTipo() == TipoToken.IF &&
                tokens.get(3).getTipo() == TipoToken.EXISTS &&
                tokens.get(4).getTipo() == TipoToken.IDENTIFICADOR &&
                tokens.get(5).getTipo() == TipoToken.CASCADE) {
                return true;
            }
        }

        return false;
    }

    // Métodos auxiliares para cada tipo de modificación, sin verificar tipos de datos

    private boolean validarAdd(List<Token> tokens) {
        if (tokens.size() < 3) return false;
        Token primerToken = tokens.get(0);

        // Validar "ADD COLUMN <identificador>"
        if (primerToken.getTipo() == TipoToken.COLUMN &&
            tokens.get(1).getTipo() == TipoToken.IDENTIFICADOR) {
            return true;
        }

        // Validar "ADD CONSTRAINT <identificador>"
        if (primerToken.getTipo() == TipoToken.CONSTRAINT &&
            tokens.get(1).getTipo() == TipoToken.IDENTIFICADOR) {
            if (tokens.get(2).getTipo() == TipoToken.UNIQUE &&
                tokens.get(3).getTipo() == TipoToken.PARENTESIS_ABRE &&
                tokens.get(4).getTipo() == TipoToken.IDENTIFICADOR &&
                tokens.get(5).getTipo() == TipoToken.PARENTESIS_CIERRA) {
                return true;
            }
            if (tokens.get(2).getTipo() == TipoToken.FOREIGN &&
                tokens.get(3).getTipo() == TipoToken.KEY &&
                tokens.get(4).getTipo() == TipoToken.PARENTESIS_ABRE &&
                tokens.get(5).getTipo() == TipoToken.IDENTIFICADOR &&
                tokens.get(6).getTipo() == TipoToken.PARENTESIS_CIERRA &&
                tokens.get(7).getTipo() == TipoToken.REFERENCES &&
                tokens.get(8).getTipo() == TipoToken.IDENTIFICADOR &&
                tokens.get(9).getTipo() == TipoToken.PARENTESIS_ABRE &&
                tokens.get(10).getTipo() == TipoToken.IDENTIFICADOR &&
                tokens.get(11).getTipo() == TipoToken.PARENTESIS_CIERRA) {
                return true;
            }
        }
        return false;
    }

    private boolean validarAlter(List<Token> tokens) {
        // Validar "ALTER COLUMN <identificador>"
        return tokens.get(0).getTipo() == TipoToken.COLUMN &&
               tokens.get(1).getTipo() == TipoToken.IDENTIFICADOR;
    }

    private boolean validarDrop(List<Token> tokens) {
        // Validar "DROP COLUMN <identificador>"
        return tokens.get(0).getTipo() == TipoToken.COLUMN &&
               tokens.get(1).getTipo() == TipoToken.IDENTIFICADOR;
    }


}
