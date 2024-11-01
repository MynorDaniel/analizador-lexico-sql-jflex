/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexicosql.backend;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author mynordma
 */
public class Reporte {
    
    // Método para crear el reporte léxico
    public void crearReporteLexico(ArrayList<Token> tokens) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Reporte Léxico</title></head><body>");
        html.append("<h1>Reporte Léxico</h1>");
        html.append("<table border='1'>");
        html.append("<tr><th>Token</th><th>Línea</th><th>Columna</th></tr>");
        
        for (Token token : tokens) {
            html.append("<tr>");
            html.append("<td>").append(token.getValor()).append("</td>");
            html.append("<td>").append(token.getLinea()).append("</td>");
            html.append("<td>").append(token.getColumna()).append("</td>");
            html.append("</tr>");
        }
        
        html.append("</table></body></html>");
        exportarHtml("reporte_lexico.html", html.toString());
    }

    // Método para crear el reporte sintáctico
    public void crearReporteSintactico(ArrayList<Token> tokens) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Reporte Sintáctico</title></head><body>");
        html.append("<h1>Reporte Sintáctico</h1>");
        html.append("<table border='1'>");
        html.append("<tr><th>Token</th><th>Línea</th><th>Columna</th></tr>");
        
        for (Token token : tokens) {
            html.append("<tr>");
            html.append("<td>").append(token.getValor()).append("</td>");
            html.append("<td>").append(token.getLinea()).append("</td>");
            html.append("<td>").append(token.getColumna()).append("</td>");
            html.append("</tr>");
        }
        
        html.append("</table></body></html>");
        exportarHtml("reporte_sintactico.html", html.toString());
    }

    // Método para crear el reporte de tablas encontradas
    public void crearReporteTablasEncontradas(ArrayList<Token> tokens) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Reporte Tablas Encontradas</title></head><body>");
        html.append("<h1>Reporte Tablas Encontradas</h1>");
        html.append("<table border='1'>");
        html.append("<tr><th>Token</th><th>Línea</th><th>Columna</th></tr>");
        
        for (Token token : tokens) {
            html.append("<tr>");
            html.append("<td>").append(token.getValor()).append("</td>");
            html.append("<td>").append(token.getLinea()).append("</td>");
            html.append("<td>").append(token.getColumna()).append("</td>");
            html.append("</tr>");
        }
        
        html.append("</table></body></html>");
        exportarHtml("reporte_tablas_encontradas.html", html.toString());
    }

    // Método para crear el reporte de tablas modificadas
    public void crearReporteTablasModificadas(ArrayList<Token> tokens) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Reporte Tablas Modificadas</title></head><body>");
        html.append("<h1>Reporte Tablas Modificadas</h1>");
        html.append("<table border='1'>");
        html.append("<tr><th>Token</th><th>Línea</th><th>Columna</th></tr>");
        
        for (Token token : tokens) {
            html.append("<tr>");
            html.append("<td>").append(token.getValor()).append("</td>");
            html.append("<td>").append(token.getLinea()).append("</td>");
            html.append("<td>").append(token.getColumna()).append("</td>");
            html.append("</tr>");
        }
        
        html.append("</table></body></html>");
        exportarHtml("reporte_tablas_modificadas.html", html.toString());
    }

    // Método para exportar el contenido HTML a un archivo
    private void exportarHtml(String nombreArchivo, String contenidoHtml) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write(contenidoHtml);
            System.out.println("Reporte exportado a: " + nombreArchivo);
        } catch (IOException e) {
        }
    }
}
