/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.analizadorlexicosql.backend;

/**
 *
 * @author mynordma
 */
public enum TipoToken {
    // Palabras clave de SQL
    CREATE,
    DATABASE,
    TABLE,
    KEY,
    NULL,
    PRIMARY,
    UNIQUE,
    FOREIGN,
    REFERENCES,
    ALTER,
    ADD,
    COLUMN,
    TYPE,
    DROP,
    CONSTRAINT,
    IF,
    EXISTS,
    CASCADE,
    ON,
    DELETE,
    SET,
    UPDATE,
    INSERT,
    INTO,
    VALUES,
    SELECT,
    FROM,
    WHERE,
    AS,
    GROUP,
    ORDER,
    BY,
    ASC,
    DESC,
    LIMIT,
    JOIN,

    // Tipos de datos
    INTEGER,
    BIGINT,
    VARCHAR,
    DECIMAL,
    DATE,
    TEXT,
    BOOLEAN,
    SERIAL,

    // Valores
    ENTERO,         
    DECIMAL_VAL,     
    FECHA,        
    CADENA,      
    BOOLEANO,        

    // Funciones de agregación
    SUM,
    AVG,
    COUNT,
    MAX,
    MIN,

    // Operadores
    MAS,        
    MENOS,      
    POR,        
    DIVISION, 

    // Operadores relacionales
    MENOR,       
    MAYOR,       
    MENOR_IGUAL, 
    MAYOR_IGUAL, 
    IGUAL,      

    // Operadores lógicos
    AND,
    OR,
    NOT,

    // Símbolos
    PARENTESIS_ABRE,  
    PARENTESIS_CIERRA,
    COMA,             
    PUNTO_Y_COMA,     
    PUNTO,            

    // Comentarios
    COMENTARIO_LINEA,

    // Identificadores y otros
    IDENTIFICADOR,    
    OTRO              
}
