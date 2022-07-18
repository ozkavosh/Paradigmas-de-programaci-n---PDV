package puntodeventa.Conexiones;

import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
    ************
    **LINEAS DE PRODUCTO**
    ************
*/

public class LineasDB{
    public String cadena_con = "jdbc:mariadb://localhost:3306/classicmodels?user=root&password=";
    
    public DefaultTableModel getLineas() throws SQLException{
        DefaultTableModel datos = new DefaultTableModel(){
            @Override
            public Class<?> getColumnClass(int column) {
                if (column==3) return ImageIcon.class;
                return Object.class;
            }
        };

        datos.addColumn("Linea de Producto");
        datos.addColumn("Desc. Texto");
        datos.addColumn("Desc. HTML");
        datos.addColumn("Imagen");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from productlines");
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[4];
                fila[0] = res.getString("productLine");
                fila[1] = res.getString("textDescription");
                fila[2] = res.getString("htmlDescription");
                fila[3] = res.getString("image") == null ? res.getString("image") : new ImageIcon(res.getBytes("image"));
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }
    
    public DefaultTableModel getLineas(String linea) throws SQLException{
        DefaultTableModel datos = new DefaultTableModel(){
            @Override
            public Class<?> getColumnClass(int column) {
                if (column==3) return ImageIcon.class;
                return Object.class;
            }
        };
        
        datos.addColumn("Linea de Producto");
        datos.addColumn("Desc. Texto");
        datos.addColumn("Desc. HTML");
        datos.addColumn("Imagen");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from productlines where productLine = ?");
            sql.setString(1, linea);
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[4];
                fila[0] = res.getString("productLine");
                fila[1] = res.getString("textDescription");
                fila[2] = res.getString("htmlDescription");
                fila[3] = res.getString("image") == null ? res.getString("image") : new ImageIcon(res.getBytes("image"));
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }

    public void addLinea(String lin, String tdesc, String htmldesc, byte[] img) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("insert into productLines values (?,?,?,?)");
            sql.setString(1, lin);
            sql.setString(2, tdesc);
            sql.setString(3, htmldesc);
            sql.setBytes(4, img);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void addLinea(String lin, String tdesc, String htmldesc) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("insert into productLines values (?,?,?,?)");
            
            sql.setString(1, lin);
            
            if(tdesc == ""){
              sql.setNull(2, Types.VARCHAR); 
            }else{
              sql.setString(2, tdesc);  
            }
            
            if(htmldesc == ""){
              sql.setNull(3, Types.LONGNVARCHAR); 
            }else{
              sql.setString(3, htmldesc);  
            }
            
            sql.setNull(4, Types.BLOB);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void editLinea(String lin, String tdesc, String htmldesc, byte[] img) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            
            PreparedStatement sql = connection.prepareStatement("update productlines set textDescription = ?, htmlDescription = ?,"
                    + " image = ? where productLine = ? ");
            sql.setString(1, tdesc);
            sql.setString(2, htmldesc);
            sql.setBytes(3, img); 
            sql.setString(4, lin);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void editLinea(String lin, String tdesc, String htmldesc) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            
            PreparedStatement sql = connection.prepareStatement("update productlines set textDescription = ?, htmlDescription = ?"
                    + " where productLine = ? ");
            sql.setString(1, tdesc);
            sql.setString(2, htmldesc); 
            sql.setString(3, lin);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void editLinea(String lin) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            
            PreparedStatement sql = connection.prepareStatement("update productlines set image = ? where productLine = ? ");
            sql.setNull(1, Types.BLOB);
            sql.setString(2, lin);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void deleteLinea(String linea) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("delete from productlines where productLine = ?");
            sql.setString(1, linea);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
}
