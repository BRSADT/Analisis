/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author Samantha
 */
public class AnalisisLSSm extends javax.swing.JFrame {

    private String[][] data;
    AutomataLexico tokenLex;
    AnalisisSemantico AS;
    arrayListLexico lista;

    /**
     * Creates new form AnalisisLSSm
     */
    public AnalisisLSSm() {

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        jLabel1.setText("Debug");

        jMenu2.setText("File");

        jMenuItem1.setMnemonic('N');
        jMenuItem1.setText("New");
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Open");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Tools");

        jMenuItem3.setText("Run");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem4.setText("Debug run");
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

//Run
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        ArrayList<String> lineaCompleta = new ArrayList<String>();
        lista = new arrayListLexico();
        boolean encontrado = false;
        String tokentipo = "";

        HashMap<String, String> tokensitos = new HashMap<String, String>();
        StringTokenizer separador;

        int linea = 0;
        StringTokenizer st = new StringTokenizer(jTextArea1.getText(), " ");
        String[] parts = jTextArea1.getText().split("\n");

        tokensitos.put("time", "Libreria");
        tokensitos.put("and", "Reservada");
        tokensitos.put("as", "Reservada");
        tokensitos.put("assert", "Reservada");
        tokensitos.put("break", "Reservada");
        tokensitos.put("class", "Reservada");
        tokensitos.put("continue", "Reservada");
        tokensitos.put("def", "Reservada");
        tokensitos.put("del", "Reservada");
        tokensitos.put("elif", "Reservada");
        tokensitos.put("else", "Reservada");
        tokensitos.put("except", "Reservada");
        tokensitos.put("exec", "Reservada");
        tokensitos.put("finally", "Reservada");
        tokensitos.put("for", "Reservada");
        tokensitos.put("from", "Reservada");
        tokensitos.put("global", "Reservada");
        tokensitos.put("if", "Reservada");
        tokensitos.put("import", "Reservada");
        tokensitos.put("in", "Reservada");
        tokensitos.put("is", "Reservada");
        tokensitos.put("lambda", "Reservada");
        tokensitos.put("not", "Reservada");
        tokensitos.put("or", "Reservada");
        tokensitos.put("pass", "Reservada");
        tokensitos.put("print", "Reservada");
        tokensitos.put("raise", "Reservada");
        tokensitos.put("return", "Reservada");
        tokensitos.put("try", "Reservada");
        tokensitos.put("while", "Reservada");
        tokensitos.put("with", "Reservada");
        tokensitos.put("yield", "Reservada");
        tokensitos.put("==", "Operador Comparacion");
        tokensitos.put("!=", "Operador Comparacion");
        tokensitos.put("<", "Operador Comparacion");
        tokensitos.put(">", "Operador Comparacion");
        tokensitos.put("++", "Operador");
        tokensitos.put("--", "Operador");
        tokensitos.put("<=", "Operador Comparacion");
        tokensitos.put(">=", "Operador Comparacion");
        tokensitos.put("+", "Operador");
        tokensitos.put("-", "Operador");
        tokensitos.put("*", "Operador");
        tokensitos.put("**", "Operador");
        tokensitos.put("/", "Operador");
        tokensitos.put("//", "Operador");
        tokensitos.put("%", "Operador");
        tokensitos.put("&", "Operador");
        tokensitos.put("|", "Operador");
        tokensitos.put("^", "Operador");
        tokensitos.put("~", "Operador");
        tokensitos.put("<<", "Operador");
        tokensitos.put(">>", "Operador");
        tokensitos.put("(", "Delimitadores");
        tokensitos.put(")", "Delimitadores");
        tokensitos.put("[", "Delimitadores");
        tokensitos.put("]", "Delimitadores");
        tokensitos.put("{", "Delimitadores");
        tokensitos.put("}", "Delimitadores");
        tokensitos.put(",", "Delimitadores");
        tokensitos.put(":", "Delimitadores");
        tokensitos.put(";", "Delimitadores");
        tokensitos.put(".", "Delimitadores");
        tokensitos.put("`", "Delimitadores");
        tokensitos.put("=", "Delimitadores");
        tokensitos.put(";", "Delimitadores");
        tokensitos.put("+=", "Delimitadores");
        tokensitos.put("*=", "Delimitadores");
        tokensitos.put("/=", "Delimitadores");
        tokensitos.put("//=", "Delimitadores");
        tokensitos.put("%=", "Delimitadores");
        tokensitos.put("&=", "Delimitadores");
             tokensitos.put("\"", "Delimitadores");
        tokensitos.put("|=", "Delimitadores");
        tokensitos.put("^=", "Delimitadores");
        tokensitos.put(">>=", "Delimitadores");
        tokensitos.put("<<=", "Delimitadores");
        tokensitos.put("**=", "Delimitadores");

        data = new String[parts.length + 5][3];
        int conti=0;
        for (int i = 0; i < parts.length; i++) {
            conti=0;
            System.out.println(parts[i].charAt(conti));
            while(parts[i].charAt(conti)==' '){
           conti++;
           if(conti>=parts.length){
           break;
           }
            }
                    
            separador = new StringTokenizer(parts[i], " \n\t");
            encontrado = false;
            lineaCompleta.clear();
            while (separador.hasMoreTokens()) {
                String palabra = separador.nextToken();
                palabra = palabra.toLowerCase();

                if (tokensitos.containsKey(palabra)) { //palabra reservada
                   // System.out.println("palabra reservada-> " + palabra + " linea: " + String.valueOf(i));
                    lista.addToken(new lex(palabra, "", String.valueOf(i), tokensitos.get(palabra),conti));

                } else {
                  //  System.out.println("verificar-> " + palabra + " linea: " + String.valueOf(i));
                    tokenLex = new AutomataLexico(0, palabra.length(), palabra, i, lista, tokensitos,conti);

                    tokenLex.q0();
                }
                if (palabra.length() > 0) {
                    linea++;
                }
            }

// TODO add your handling code here:
        }
       
       
AS  = new AnalisisSemantico(lista);
AS.q0();

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AnalisisLSSm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnalisisLSSm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnalisisLSSm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnalisisLSSm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnalisisLSSm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    // End of variables declaration//GEN-END:variables
}
