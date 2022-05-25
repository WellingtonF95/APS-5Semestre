package project_server.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author oner
 */
public class Servidor extends javax.swing.JFrame {

    /**
     * Creates new form Server
     */
    ServerSocket ss;

    HashMap clientColl = new HashMap();

    public Servidor() {
        try {
            initComponents();
            ss = new ServerSocket(2089);
            this.sStatus.setText("Servidor iniciado.");

            new ClientAccept().start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class ClientAccept extends Thread {

        public void run() {
            while (true) {
                try {
                    Socket s = ss.accept();
                    String i = new DataInputStream(s.getInputStream()).readUTF();
                    if (clientColl.containsKey(i)) {
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("Você já está registrado.....!!");
                    } else {
                        clientColl.put(i, s);
                    }
                    msgBox.append(i + " Entrou no chat. \n");
                    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                    dout.writeUTF("");
                    new MsgRead(s, i).start();
                    new PrepareClientList().start();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class MsgRead extends Thread {

        Socket s;
        String ID;

        private MsgRead(Socket s, String ID) {
            this.s = s;
            this.ID = ID;
        }

        public void run() {
            while (!clientColl.isEmpty()) {
                try {
                    String i = new DataInputStream(s.getInputStream()).readUTF();
                    if (i.equals("mkoighteazdcvgyhujb096785542AXTY")) {
                        clientColl.remove(ID);
                        msgBox.append(ID + ": Desconectou do chat. \n");
                        new PrepareClientList().start();

                        Set k = clientColl.keySet();
                        Iterator itr = k.iterator();
                        while (itr.hasNext()) {
                            String key = (String) itr.next();
                            if (!key.equalsIgnoreCase(ID)) {
                                try {
                                     new DataOutputStream(((Socket)clientColl.get(key)).getOutputStream()).writeUTF(ID+": Desconectou do chat!");
                                } catch (Exception ex) {
                                    clientColl.remove(key);
                                    msgBox.append(key + ": Desconectou do chat!");
                                    new PrepareClientList().start();
                                }
                            }

                        }
                    } else if (i.contains("#4344554@@@@@67667@@")) {
                        i = i.substring(20);
                        StringTokenizer st = new StringTokenizer(i, ":");
                        String id = st.nextToken();
                        i = st.nextToken();
                        try {
                            new DataOutputStream(((Socket) clientColl.get(id)).getOutputStream()).writeUTF("< " + ID + " to " + id + " > " + i);

                        } catch (Exception ex) {
                            clientColl.remove(id);
                            msgBox.append(id + "Desconectou do chat.");
                            new PrepareClientList().start();
                        }

                    } else {
                        Set k = clientColl.keySet();
                        Iterator itr = k.iterator();
                        while (itr.hasNext()) {
                            String key = (String) itr.next();
                            if (!key.equalsIgnoreCase(ID)) {
                                try {
                                    new DataOutputStream(((Socket) clientColl.get(key)).getOutputStream()).writeUTF("< " + ID + " to all > " + i);
                                } catch (Exception ex) {
                                    clientColl.remove(key);
                                    msgBox.append(key + ": Desconectou do chat!");
                                    new PrepareClientList().start();
                                }
                            }

                        }

                    }
                } catch (IOException ex) {
       
                }
            }
        }
    }

    class PrepareClientList extends Thread {

        public void run() {
            try {
                String ids = "";
                Set k = clientColl.keySet();
                Iterator itr = k.iterator();
                while (itr.hasNext()) {
                    String key = (String) itr.next();
                    ids += key + ",";

                }
                if (ids.length() != 0) {
                    ids = ids.substring(0, ids.length() - 1);
                }

                itr = k.iterator();

                while (itr.hasNext()) {
                    String key = (String) itr.next();
                    try {
                        new DataOutputStream(((Socket) clientColl.get(key)).getOutputStream()).writeUTF(":;.,/=" + ids);
                    } catch (Exception ex) {
                        clientColl.remove(key);
                        msgBox.append(key + " Desconectou!");

                    }

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        msgBox = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        sStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MultServer");

        jPanel2.setBackground(new java.awt.Color(0, 153, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        msgBox.setColumns(20);
        msgBox.setRows(5);
        jScrollPane1.setViewportView(msgBox);

        jLabel1.setBackground(new java.awt.Color(0, 204, 255));
        jLabel1.setText("Server Status:");

        sStatus.setText(".........................................................");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(sStatus))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Servidor.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Servidor.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Servidor.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Servidor.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Servidor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea msgBox;
    private javax.swing.JLabel sStatus;
    // End of variables declaration//GEN-END:variables
}
