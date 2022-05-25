/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
/**
 *
 * @author renat
 */
public class FuncionarioDAO {
    
    ResultSet rs;
    Connection conn;
    PreparedStatement pstm;
    String[] lista = new String[50];
    
    public void CadastrarAudio(int id, String audio_path) { 
        String SQL = "INSERT INTO Audios(id_func, audio_file, audio_name) VALUES(?,?,?)";
        
        conn = new Conexao().conectaBD();
        
        try {
            pstm = conn.prepareStatement(SQL);
            pstm.setInt(1, id);
            
        
            File file = new File(audio_path);
            FileInputStream input = new FileInputStream(file);
            
            
            pstm.setBinaryStream(2, input);
            pstm.setString(3, audio_path);
            
            
            pstm.execute();
            pstm.close();
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public String[] LerAudio(int id) { 
        String SQL = "SELECT * FROM Audios where id_func = ?";
        
        conn = new Conexao().conectaBD();
        
        try {
            pstm = conn.prepareStatement(SQL);
            pstm.setInt(1, id);
            ResultSet rs_audios = pstm.executeQuery();
            
            int index = 0;
            while(rs_audios.next()) {
                String value = rs_audios.getString("audio_name");
                value = value.replaceAll(".wav", "");
                lista[index] = value;
                index++;
                
            }

        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return lista;
    }
    
    public String OuvirAudio(String audio_name) { 
        String SQL2 = "SELECT id FROM Audios WHERE audio_name = ?";
        String SQL = "SELECT F.usuario, A.audio_file, A.audio_name FROM Audios A INNER JOIN Funcionarios F ON F.id = A.id_func WHERE A.id = ?";
        
        conn = new Conexao().conectaBD();
        
        String full_audio_name = "";
      
        
        try {
            int id_audio = 0;
            pstm = conn.prepareStatement(SQL2);
            pstm.setString(1, audio_name + ".wav");
            
            ResultSet rs_id = pstm.executeQuery();
            
            while(rs_id.next()) { 
                id_audio = rs_id.getInt("id");
            }
            
            pstm = conn.prepareStatement(SQL);
            pstm.setInt(1, id_audio);
       
            ResultSet rs_audio = pstm.executeQuery();
            
            pstm = conn.prepareStatement(SQL);
            pstm.setInt(1, id_audio);
            
            ResultSet rs_name = pstm.executeQuery();
            
            while(rs_name.next()) { 
                full_audio_name = rs_name.getString("audio_name");
            }
            
            
            File file = new File(full_audio_name);
            FileOutputStream output = new FileOutputStream(file);
            
            
            while(rs_audio.next()) { 
                InputStream input = rs_audio.getBinaryStream("audio_file");
                
                byte[] buffer = new byte[1024];
                while(input.read(buffer) > 0) { 
                    output.write(buffer);
                }
            }
           

        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        
        return full_audio_name;
    }
    
    public void CadastrarFuncionario(String nome, String senha) { 
        String SQL = "INSERT INTO Funcionarios(usuario, senha) VALUES(?,?)";
        
        conn = new Conexao().conectaBD();
        
        try {
            pstm = conn.prepareStatement(SQL);
            pstm.setString(1, nome);
            pstm.setString(2, senha);
            
            pstm.execute();
            pstm.close();
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public int ChecagemDeConta(String nome, String senha_passada) { 
        String SQL = "SELECT id, senha FROM Funcionarios where usuario = ?";
        int id = -1;
        String senha = "";
        
        conn = new Conexao().conectaBD();
        
        try {      
            pstm = conn.prepareStatement(SQL);
            pstm.setString(1, nome);
            
            rs = pstm.executeQuery();
            while(rs.next()) {
                id = rs.getInt("id");
                senha = rs.getString("senha");
            }
            
            
         
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
           if(id != -1) { 
                if(senha_passada.equals(senha)) { 
                    //Correto
                    return id;
                }
                else { 
                    return -1;
                }
            }
            else { 
                return -1;
            }
        
        
    }
    
}
