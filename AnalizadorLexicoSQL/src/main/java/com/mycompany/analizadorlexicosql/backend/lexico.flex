package com.mycompany.analizadorlexicosql.backend;

import java.util.ArrayList;

%%

%{
    int linea = 1;
    int columna = 1;
    ArrayList<Token> tokens = new ArrayList<>();

    public void manejarToken(TipoToken tipo, String valor) {
        tokens.add(new Token(valor, linea, columna, tipo));
    }

    public void nuevaLinea() {
        linea++;
        columna = 1;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }
%}

%class AnalizadorSQL
%public
%unicode
%line
%column
%standalone

%%

// Palabras clave de SQL
"CREATE"               { manejarToken(TipoToken.CREATE, yytext()); }
"DATABASE"             { manejarToken(TipoToken.DATABASE, yytext()); }
"TABLE"                { manejarToken(TipoToken.TABLE, yytext()); }
"KEY"                  { manejarToken(TipoToken.KEY, yytext()); }
"NULL"                 { manejarToken(TipoToken.NULL, yytext()); }
"PRIMARY"              { manejarToken(TipoToken.PRIMARY, yytext()); }
"UNIQUE"               { manejarToken(TipoToken.UNIQUE, yytext()); }
"FOREIGN"              { manejarToken(TipoToken.FOREIGN, yytext()); }
"REFERENCES"           { manejarToken(TipoToken.REFERENCES, yytext()); }
"ALTER"                { manejarToken(TipoToken.ALTER, yytext()); }
"ADD"                  { manejarToken(TipoToken.ADD, yytext()); }
"COLUMN"               { manejarToken(TipoToken.COLUMN, yytext()); }
"TYPE"                 { manejarToken(TipoToken.TYPE, yytext()); }
"DROP"                 { manejarToken(TipoToken.DROP, yytext()); }
"CONSTRAINT"           { manejarToken(TipoToken.CONSTRAINT, yytext()); }
"IF"                   { manejarToken(TipoToken.IF, yytext()); }
"EXISTS"               { manejarToken(TipoToken.EXISTS, yytext()); }
"CASCADE"              { manejarToken(TipoToken.CASCADE, yytext()); }
"ON"                   { manejarToken(TipoToken.ON, yytext()); }
"DELETE"               { manejarToken(TipoToken.DELETE, yytext()); }
"SET"                  { manejarToken(TipoToken.SET, yytext()); }
"UPDATE"               { manejarToken(TipoToken.UPDATE, yytext()); }
"INSERT"               { manejarToken(TipoToken.INSERT, yytext()); }
"INTO"                 { manejarToken(TipoToken.INTO, yytext()); }
"VALUES"               { manejarToken(TipoToken.VALUES, yytext()); }
"SELECT"               { manejarToken(TipoToken.SELECT, yytext()); }
"FROM"                 { manejarToken(TipoToken.FROM, yytext()); }
"WHERE"                { manejarToken(TipoToken.WHERE, yytext()); }
"AS"                   { manejarToken(TipoToken.AS, yytext()); }
"GROUP"                { manejarToken(TipoToken.GROUP, yytext()); }
"ORDER"                { manejarToken(TipoToken.ORDER, yytext()); }
"BY"                   { manejarToken(TipoToken.BY, yytext()); }
"ASC"                  { manejarToken(TipoToken.ASC, yytext()); }
"DESC"                 { manejarToken(TipoToken.DESC, yytext()); }
"LIMIT"                { manejarToken(TipoToken.LIMIT, yytext()); }
"JOIN"                 { manejarToken(TipoToken.JOIN, yytext()); }

// Tipos de datos
"INTEGER"              { manejarToken(TipoToken.INTEGER, yytext()); }
"BIGINT"               { manejarToken(TipoToken.BIGINT, yytext()); }
"VARCHAR"              { manejarToken(TipoToken.VARCHAR, yytext()); }
"DECIMAL"              { manejarToken(TipoToken.DECIMAL, yytext()); }
"DATE"                 { manejarToken(TipoToken.DATE, yytext()); }
"TEXT"                 { manejarToken(TipoToken.TEXT, yytext()); }
"BOOLEAN"              { manejarToken(TipoToken.BOOLEAN, yytext()); }
"SERIAL"               { manejarToken(TipoToken.SERIAL, yytext()); }

// Funciones de agregación
"SUM"                  { manejarToken(TipoToken.SUM, yytext()); }
"AVG"                  { manejarToken(TipoToken.AVG, yytext()); }
"COUNT"                { manejarToken(TipoToken.COUNT, yytext()); }
"MAX"                  { manejarToken(TipoToken.MAX, yytext()); }
"MIN"                  { manejarToken(TipoToken.MIN, yytext()); }

// Identificadores en snake_case
[a-z_][a-z0-9_]*       { manejarToken(TipoToken.IDENTIFICADOR, yytext()); }

// Números enteros
[0-9]+                 { manejarToken(TipoToken.ENTERO, yytext()); }

// Números decimales
[0-9]+\.[0-9]+         { manejarToken(TipoToken.DECIMAL_VAL, yytext()); }

// Fechas en formato 'YYYY-MM-DD'
"'"[0-9]{4}"-"[0-9]{2}"-"[0-9]{2}"'"  { manejarToken(TipoToken.FECHA, yytext()); }

// Cadenas de texto entre comillas simples
"'"([^'\\]|\\'|\\\\)*"'"  { manejarToken(TipoToken.CADENA, yytext()); }

// Signos
"("                    { manejarToken(TipoToken.PARENTESIS_ABRE, yytext()); }
")"                    { manejarToken(TipoToken.PARENTESIS_CIERRA, yytext()); }
","                    { manejarToken(TipoToken.COMA, yytext()); }
";"                    { manejarToken(TipoToken.PUNTO_Y_COMA, yytext()); }
"."                    { manejarToken(TipoToken.PUNTO, yytext()); }
"="                    { manejarToken(TipoToken.IGUAL, yytext()); }

// Operadores aritméticos
"+"                    { manejarToken(TipoToken.MAS, yytext()); }
"-"                    { manejarToken(TipoToken.MENOS, yytext()); }
"*"                    { manejarToken(TipoToken.POR, yytext()); }
"/"                    { manejarToken(TipoToken.DIVISION, yytext()); }

// Operadores relacionales
"<"                    { manejarToken(TipoToken.MENOR, yytext()); }
">"                    { manejarToken(TipoToken.MAYOR, yytext()); }
"<="                   { manejarToken(TipoToken.MENOR_IGUAL, yytext()); }
">="                   { manejarToken(TipoToken.MAYOR_IGUAL, yytext()); }

// Operadores lógicos
"AND"                  { manejarToken(TipoToken.AND, yytext()); }
"OR"                   { manejarToken(TipoToken.OR, yytext()); }
"NOT"                  { manejarToken(TipoToken.NOT, yytext()); }

// Comentarios de línea
"--"[^"\n"]*           { manejarToken(TipoToken.COMENTARIO_LINEA, yytext()); nuevaLinea(); }

// Espacios, tabs y nuevas líneas
[ \t\r]+               { /* Ignorar espacios y tabs */ }
\n                     { nuevaLinea(); }

// Cualquier otro carácter
.                      { System.out.println("Error: " + yytext()); }
