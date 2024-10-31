/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexicosql.backend;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mynordma
 */
public class Graficador {
    
    public void generarDiagrama(Estructura estructura){
        StringBuilder sql = new StringBuilder();
        for (Token token : estructura.getTokens()) {
            sql.append(token.getValor()).append(" ");
        }
        generarDiagramaDesdeSQL(sql.toString());
        generarHtmlConImagenes("graficos");
    }
    
    private void generarDiagramaDesdeSQL(String sql) {
        String tipoInstruccion = identificarInstruccion(sql);
        StringBuilder labelBuilder = new StringBuilder("<TABLE BORDER='1' CELLBORDER='1' CELLSPACING='0'>");

        switch (tipoInstruccion) {
            case "CREATE" -> labelBuilder.append(generarTablaDesdeCreate(sql));
            case "ALTER" -> labelBuilder.append(generarTablaDesdeAlter(sql));
            case "DROP" -> labelBuilder.append(generarTablaDesdeDrop(sql));
            default -> {
                System.out.println("Instrucción SQL no soportada.");
                return;
            }
        }
        labelBuilder.append("</TABLE>");
        
        Node tableNode = node("tabla").with(Label.html(labelBuilder.toString()));
        Graph graph = graph("SQLDiagram").directed().with(tableNode);

        int numeroDeImagenes = contarImagenesPng("graficos");
        
        try {
            Graphviz.fromGraph(graph).width(500).render(Format.PNG).toFile(new File("graficos/grafico-" + numeroDeImagenes + ".png"));
            System.out.println("Diagrama generado a partir de SQL con éxito.");
        } catch (IOException e) {
        }
    }

    private String identificarInstruccion(String sql) {
        if (sql.trim().toUpperCase().startsWith("CREATE TABLE")) {
            return "CREATE";
        } else if (sql.trim().toUpperCase().startsWith("ALTER TABLE")) {
            return "ALTER";
        } else if (sql.trim().toUpperCase().startsWith("DROP TABLE")) {
            return "DROP";
        }
        return "UNKNOWN";
    }

    private String generarTablaDesdeCreate(String sql) {
        Pattern tableNamePattern = Pattern.compile("CREATE TABLE (\\w+)\\s*\\(");
        Matcher tableNameMatcher = tableNamePattern.matcher(sql);
        String tableName = tableNameMatcher.find() ? tableNameMatcher.group(1) : "Tabla";
        
        StringBuilder builder = new StringBuilder();
        builder.append("<TR><TD BGCOLOR='lightgrey' COLSPAN='2'>").append(tableName).append("</TD></TR>");
        
        Pattern columnPattern = Pattern.compile("(\\w+)\\s+(\\w+(?:\\(\\d+(?:,\\d+)?\\))?)(?:\\s+(PRIMARY KEY|NOT NULL|UNIQUE)*)?");
        Matcher columnMatcher = columnPattern.matcher(sql);

        while (columnMatcher.find()) {
            String columnName = columnMatcher.group(1);
            String dataType = columnMatcher.group(2);
            String constraints = columnMatcher.group(3) != null ? columnMatcher.group(3) : "";

            builder.append("<TR><TD ALIGN='LEFT'>").append(columnName)
                    .append("</TD><TD ALIGN='LEFT'>").append(dataType).append(" ").append(constraints).append("</TD></TR>");
        }
        return builder.toString();
    }

    private String generarTablaDesdeAlter(String sql) {
        Pattern alterPattern = Pattern.compile("ALTER TABLE (\\w+)");
        Matcher alterMatcher = alterPattern.matcher(sql);
        String tableName = alterMatcher.find() ? alterMatcher.group(1) : "Tabla";
        
        StringBuilder builder = new StringBuilder();
        builder.append("<TR><TD BGCOLOR='lightgrey' COLSPAN='2'>ALTER: ").append(tableName).append("</TD></TR>");
        
        if (sql.toUpperCase().contains("ADD COLUMN")) {
            Pattern addColumnPattern = Pattern.compile("ADD COLUMN (\\w+)\\s+(\\w+(?:\\(\\d+(?:,\\d+)?\\))?)");
            Matcher addColumnMatcher = addColumnPattern.matcher(sql);

            if (addColumnMatcher.find()) {
                builder.append("<TR><TD ALIGN='LEFT'>Añadir Columna</TD><TD ALIGN='LEFT'>")
                        .append(addColumnMatcher.group(1)).append(": ").append(addColumnMatcher.group(2)).append("</TD></TR>");
            }
        } else if (sql.toUpperCase().contains("DROP COLUMN")) {
            Pattern dropColumnPattern = Pattern.compile("DROP COLUMN (\\w+)");
            Matcher dropColumnMatcher = dropColumnPattern.matcher(sql);

            if (dropColumnMatcher.find()) {
                builder.append("<TR><TD ALIGN='LEFT'>Eliminar Columna</TD><TD ALIGN='LEFT'>")
                        .append(dropColumnMatcher.group(1)).append("</TD></TR>");
            }
        } else if (sql.toUpperCase().contains("ADD CONSTRAINT")) {
            Pattern constraintPattern = Pattern.compile("ADD CONSTRAINT (\\w+)\\s+(UNIQUE|FOREIGN KEY|PRIMARY KEY)\\s*\\((\\w+)\\)");
            Matcher constraintMatcher = constraintPattern.matcher(sql);

            if (constraintMatcher.find()) {
                builder.append("<TR><TD ALIGN='LEFT'>Añadir Restricción</TD><TD ALIGN='LEFT'>")
                        .append(constraintMatcher.group(2)).append(" en ").append(constraintMatcher.group(3))
                        .append(" (").append(constraintMatcher.group(1)).append(")</TD></TR>");
            }
        } else if (sql.toUpperCase().contains("ALTER COLUMN")) {
            Pattern alterColumnPattern = Pattern.compile("ALTER COLUMN (\\w+)\\s+TYPE\\s+(\\w+(?:\\(\\d+(?:,\\d+)?\\))?)");
            Matcher alterColumnMatcher = alterColumnPattern.matcher(sql);

            if (alterColumnMatcher.find()) {
                builder.append("<TR><TD ALIGN='LEFT'>Modificar Columna</TD><TD ALIGN='LEFT'>")
                        .append(alterColumnMatcher.group(1)).append(" a ").append(alterColumnMatcher.group(2)).append("</TD></TR>");
            }
        }
        return builder.toString();
    }

    private String generarTablaDesdeDrop(String sql) {
        Pattern dropPattern = Pattern.compile("DROP TABLE IF EXISTS (\\w+)");
        Matcher dropMatcher = dropPattern.matcher(sql);
        String tableName = dropMatcher.find() ? dropMatcher.group(1) : "Tabla";

        return "<TR><TD BGCOLOR='lightgrey'>DROP</TD><TD>" + tableName + "</TD></TR>";
    }
    
    private void generarHtmlConImagenes(String ruta) {
        // Obtener todos los archivos .png de la ruta especificada
        File carpeta = new File(ruta);
        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

        // Verificar si se encontraron archivos
        if (archivos == null || archivos.length == 0) {
            System.out.println("No se encontraron archivos .png en la ruta especificada.");
            return;
        }

        // Crear el contenido HTML
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<!DOCTYPE html>\n<html>\n<head>\n<title>Imagenes PNG</title>\n</head>\n<body>\n");
        
        for (File archivo : archivos) {
            // Agregar cada imagen al contenido HTML
            String nombreArchivo = archivo.getName();
            htmlContent.append("<img src=\"").append(nombreArchivo).append("\" alt=\"")
                       .append(nombreArchivo).append("\" style=\"max-width: 100%; height: auto;\" />\n");
        }
        
        htmlContent.append("</body>\n</html>");

        // Guardar el HTML en un archivo
        String nombreHtml = "imagenes.html";
        File archivoHtml = new File(carpeta, nombreHtml);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoHtml))) {
            writer.write(htmlContent.toString());
            System.out.println("Archivo HTML generado: " + archivoHtml.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo HTML: " + e.getMessage());
        }
    }
    
    private int contarImagenesPng(String ruta) {
        File carpeta = new File(ruta);

        File[] archivosPng = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
        return (archivosPng != null) ? archivosPng.length : 0;
    }
     
    public void eliminarArchivos(String ruta) {
        File carpeta = new File(ruta);

        // Listar todos los archivos en la carpeta
        File[] archivos = carpeta.listFiles();
        
        if (archivos != null) {
            for (File archivo : archivos) {
                // Verificar si el archivo es .html o .png y eliminarlo
                if (archivo.isFile() && (archivo.getName().toLowerCase().endsWith(".html") || archivo.getName().toLowerCase().endsWith(".png"))) {
                    boolean eliminado = archivo.delete();
                    if (eliminado) {
                        System.out.println("Archivo eliminado: " + archivo.getName());
                    } else {
                        System.err.println("No se pudo eliminar el archivo: " + archivo.getName());
                    }
                }
            }
        }
    }
}