/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.analizadorlexicosql.frontend;

import com.mycompany.analizadorlexicosql.backend.*;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.text.*;

/**
 *
 * @author mynordma
 */
public class FramePrincipal extends javax.swing.JFrame {
    
    private ArrayList<Estructura> estructurasSQL;
    private ArrayList<Token> errores;
    private ArrayList<Token> erroresSintactico;

    public FramePrincipal() {
        initComponents();
        this.setSize(800, 700);
        this.setTitle("Analizador Léxico SQL");
        this.setResizable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        generarGraficoBtn = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 102));

        jScrollPane2.setViewportView(jTextPane1);

        jButton1.setText("Analizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        generarGraficoBtn.setText("Generar Gráficos");
        generarGraficoBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                generarGraficoBtnMouseClicked(evt);
            }
        });
        jMenuBar1.add(generarGraficoBtn);

        jMenu1.setText("ReporteLexico");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu2.setText("ReporteSintactico");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(364, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(359, 359, 359))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String input = jTextPane1.getText();
        AnalizadorSQL analizadorLexico = new AnalizadorSQL(new StringReader(input));
        
        // Leer y guardar todos los posibles tokens
        try {
            while (analizadorLexico.yylex() != AnalizadorSQL.YYEOF) {}
            analizadorLexico.yylex();       
        } catch (IOException e) {
        }
        ArrayList<Token> tokens = analizadorLexico.getTokens();
        errores = analizadorLexico.getErrores();
        
        AnalizadorSintactico analizadorSintactico= new AnalizadorSintactico(tokens);
        estructurasSQL = analizadorSintactico.getEstructurasSQL();
        erroresSintactico = analizadorSintactico.getErrores();
        
        pintarTokens(tokens);
        //imprimirTokens(tokens);
        imprimirEstructuras(estructurasSQL);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void generarGraficoBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generarGraficoBtnMouseClicked
        Graficador graficador = new Graficador();
        graficador.eliminarArchivos("graficos");
        for (Estructura estructura : estructurasSQL) {
            graficador.generarDiagrama(estructura);
        }
        mostrarMensaje("Confirmación", "Gráficos generados en la ruta relativa.");
    }//GEN-LAST:event_generarGraficoBtnMouseClicked

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        Reporte reporte = new Reporte();
        reporte.crearReporteLexico(errores);
        System.out.println("Reporte lexico creado");
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        Reporte reporte = new Reporte();
        reporte.crearReporteSintactico(erroresSintactico);
        System.out.println("Reporte sintactico creado");
    }//GEN-LAST:event_jMenu2MouseClicked

    private void mostrarMensaje(String titulo, String mensaje){
        JDialog dialogo = new JDialog(this, titulo, true);
        dialogo.setSize(300, 150);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new java.awt.GridLayout(3, 2));
        
        javax.swing.JOptionPane.showMessageDialog(dialogo, mensaje, "Mensaje", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void imprimirEstructuras(ArrayList<Estructura> estructuras){
        for (Estructura estructura : estructuras) {
            System.out.println("INICIO ESTRUCTURA");
            for (Token token : estructura.getTokens()) {
                System.out.println(token.toString());
            }
            System.out.println("FIN ESTRUCTURA");
        }
    }
    
    private void pintarTokens(ArrayList<Token> tokens) {
        StyledDocument doc = jTextPane1.getStyledDocument();
        jTextPane1.setText("");

        for (Token token : tokens) {
            // Definir un color para el token según su TipoToken
            Color color = switch (token.getTipo()) {
                case CREATE, DATABASE, TABLE, KEY, NULL, PRIMARY, UNIQUE, FOREIGN, REFERENCES, ALTER, ADD, COLUMN,
                     TYPE, DROP, CONSTRAINT, IF, EXISTS, CASCADE, ON, DELETE, SET, UPDATE, INSERT, INTO,
                     VALUES, SELECT, FROM, WHERE, AS, GROUP, ORDER, BY, ASC, DESC, LIMIT, JOIN -> Color.ORANGE;

                case INTEGER, BIGINT, VARCHAR, DECIMAL, DATE, TEXT, BOOLEAN, SERIAL -> new Color(128, 0, 128); // Morado

                case ENTERO -> Color.BLUE;

                case DECIMAL_VAL -> Color.BLUE;

                case FECHA -> Color.YELLOW;

                case CADENA -> Color.GREEN;

                case IDENTIFICADOR -> new Color(255, 0, 255); // Fucsia

                case BOOLEANO, SUM, AVG, COUNT, MAX, MIN -> Color.BLUE;

                case PARENTESIS_ABRE, PARENTESIS_CIERRA, COMA, PUNTO_Y_COMA, PUNTO, IGUAL,
                     MAS, MENOS, POR, DIVISION, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL -> Color.BLACK;

                case AND, OR, NOT -> Color.ORANGE;

                case COMENTARIO_LINEA -> Color.GRAY;

                default -> Color.BLACK;
            };

            // Crear un estilo para el token con el color determinado
            SimpleAttributeSet estilo = new SimpleAttributeSet();
            StyleConstants.setForeground(estilo, color);

            // Insertar el token en el documento con el estilo correspondiente
            try {
                doc.insertString(doc.getLength(), token.getValor() + " ", estilo);
            } catch (BadLocationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void imprimirTokens(ArrayList<Token> tokens){
        for (Token token : tokens) {
            System.out.println(token.toString());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu generarGraficoBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}