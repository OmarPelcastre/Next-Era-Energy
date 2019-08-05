/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package next.era.energy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author omarpelcastre
 */
public class MetodosDataBase {
    
    
    public static Coneccion conexionDB = new Coneccion();
    public static PreparedStatement ps;
    public static ResultSet rs;
    public static String sql;
    public static int resultado;
    
    public int createUser(String nombre, String email, String password, String telefono){
        int result = 0;
        
        Connection conexion = null;
        
        String sentencia = ("INSERT INTO socios (nombre, email, password, telefono) VALUES (?,?,?,?)");
        
        try{
            conexion = conexionDB.getConnection();
            ps = conexion.prepareStatement(sentencia);
            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, telefono);
            result = ps.executeUpdate();
            //ps.close();
            //conexion.close();
            
        }catch(SQLException e){
            System.out.println("error: "+ e);
        }
        
        
        return result;
        
    }
    public int createReport(int socio, String title, String description, Float cost){
        int result = 0;
        
        Connection conexion;
        
        String sentencia = ("INSERT INTO avances (socio, titulo, descripcion, costo) VALUES (?,?,?,?)");
        
        try{
            conexion = conexionDB.getConnection();
            ps = conexion.prepareStatement(sentencia);
            ps.setInt(1, socio);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setFloat(4, cost);
            result = ps.executeUpdate();
            //ps.close();
            
            
        }catch(SQLException e){
            System.out.println("error: "+ e);
        }
        
        
        return result;
        
    }
    
    public String buscarNombre(String correo) throws SQLException{
        String busqueda = null;
        
        Connection conexion = conexionDB.getConnection();
        
        String sentencia = ("SELECT nombre FROM socios WHERE correo = '"+ correo + "'");
        ps = conexion.prepareStatement(sentencia);
        rs = ps.executeQuery();
        if(rs.next()){
            String nombre = rs.getString("nombre");
            busqueda = nombre;
        }
        
        
        return busqueda;
        
    }
    
    
    
    public float getTotalCost(int id) throws SQLException{
        
        
        Connection conexion = conexionDB.getConnection();
        
        String sentencia = ("SELECT costo FROM avances WHERE socio = '"+ id + "'");
        ps = conexion.prepareStatement(sentencia);
        rs = ps.executeQuery();
        float total = 0;
        while(rs.next()){
            float costo = rs.getFloat("costo");
            total+=costo;
        }
        
        
        return total;
        
    }
    
    public ObservableList<String> getAvances(int socio) throws SQLException{
        
        
        
        //FXMLDocumentController controller = new FXMLDocumentController();
        ObservableList<String> reportes = FXCollections.observableArrayList();

        Connection conexion = conexionDB.getConnection();
        
        String sentencia = ("SELECT titulo FROM avances WHERE socio = '"+ socio + "'");
        ps = conexion.prepareStatement(sentencia);
        rs = ps.executeQuery();
        while(rs.next()){
            String nombre = rs.getString("titulo");
            System.out.println(nombre);
            reportes.add(nombre);
            
        }
        
        return reportes;
        
    }
    
    public ObservableList<String> getSocios() throws SQLException{
        
        
        
        //FXMLDocumentController controller = new FXMLDocumentController();
        ObservableList<String> socios = FXCollections.observableArrayList();

        Connection conexion = conexionDB.getConnection();
        
        String sentencia = ("SELECT nombre FROM socios");
        ps = conexion.prepareStatement(sentencia);
        rs = ps.executeQuery();
        while(rs.next()){
            String nombre = rs.getString("nombre");
            System.out.println(nombre);
            socios.add(nombre);
            
        }
        
        return socios;
        
    }
    
    
    
    public int getSocioId(String correo) throws SQLException{
        
        int result = 0;
        
        try {
            Connection conexion = conexionDB.getConnection();
            String sentencia = ("SELECT id FROM socios WHERE email = '"+ correo + "'");
            ps = conexion.prepareStatement(sentencia);
            rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                return id;
            }
        }catch(SQLException e){
            System.out.println("error socio id " + e);
        }
        
        
        return result;
        
    }
    public int getSocioIdWithName(String nombre) throws SQLException{
        
        int result = 0;
        
        try {
            Connection conexion = conexionDB.getConnection();
            String sentencia = ("SELECT id FROM socios WHERE nombre = '"+ nombre + "'");
            ps = conexion.prepareStatement(sentencia);
            rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                return id;
            }
        }catch(SQLException e){
            System.out.println("error socio id " + e);
        }
        
        
        return result;
        
    }
    public Reporte getReporte(String title) throws SQLException{
        
        Reporte reporte = new Reporte();
        
        try {
            Connection conexion = conexionDB.getConnection();
            String sentencia = ("SELECT * FROM avances WHERE titulo = '"+ title + "'");
            ps = conexion.prepareStatement(sentencia);
            rs = ps.executeQuery();
            if(rs.next()){
                String titulo = rs.getString("titulo");
                reporte.setTitle(titulo);
                String descripcion = rs.getString("descripcion");
                reporte.setDescription(descripcion);
                int costo = rs.getInt("costo");
                reporte.setCost(costo); 
            }
        }catch(SQLException e){
            System.out.println("error socio id " + e);
        }
        
        
        return reporte;
        
    }
    
    
    public String login(String email, String password){
        String busqueda = null;
        Connection conexion;
        
        try{
            conexion = conexionDB.getConnection();
            String sentencia = ("SELECT email,password FROM socios WHERE email = '"
                    + email + "' && password = '" + password + "'");
            
            ps = conexion.prepareStatement(sentencia);
            rs = ps.executeQuery();
            if(rs.next()){
                busqueda = "usuario encontrado";
            }else
                busqueda = "usuario no encontrado";
            
            
            
        }catch(SQLException e){
            System.out.println("error login: "+e);
        }
        
        return busqueda;
    }
    
    
}
