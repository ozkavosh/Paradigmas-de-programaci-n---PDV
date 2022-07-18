package puntodeventa.Conexiones;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
    ************
    **OFICINAS**
    ************
*/

public class OficinasDB{
    public String cadena_con = "jdbc:mariadb://localhost:3306/classicmodels?user=root&password=";
    
    public DefaultTableModel getOficinas() throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Num. Oficina");
        datos.addColumn("Ciudad");
        datos.addColumn("Telefono");
        datos.addColumn("Direccion 1");
        datos.addColumn("Direccion 2");
        datos.addColumn("Estado");
        datos.addColumn("Pais");
        datos.addColumn("Código Postal");
        datos.addColumn("Territorio");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from offices");
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[9];
                fila[0] = res.getString("officeCode");
                fila[1] = res.getString("city");
                fila[2] = res.getString("phone");
                fila[3] = res.getString("addressLine1");
                fila[4] = res.getString("addressLine2");
                fila[5] = res.getString("state");
                fila[6] = res.getString("country");
                fila[7] = res.getString("postalCode");
                fila[8] = res.getString("territory");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }
    
    public DefaultTableModel getOficinas(String numero) throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Num. Oficina");
        datos.addColumn("Ciudad");
        datos.addColumn("Telefono");
        datos.addColumn("Direccion 1");
        datos.addColumn("Direccion 2");
        datos.addColumn("Estado");
        datos.addColumn("Pais");
        datos.addColumn("Código Postal");
        datos.addColumn("Territorio");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from offices where officeCode = ?");
            sql.setString(1,numero);
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[9];
                fila[0] = res.getString("officeCode");
                fila[1] = res.getString("city");
                fila[2] = res.getString("phone");
                fila[3] = res.getString("addressLine1");
                fila[4] = res.getString("addressLine2");
                fila[5] = res.getString("state");
                fila[6] = res.getString("country");
                fila[7] = res.getString("postalCode");
                fila[8] = res.getString("territory");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }
    
    public void addOficina(String num, String ciu, String tel, String lin1, String lin2, String est, String pai, String cp, String ter) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("insert into offices values (?,?,?,?,?,?,?,?,?)");
            sql.setString(1, num);
            sql.setString(2, ciu);
            sql.setString(3, tel);
            sql.setString(4, lin1);
            
            if(lin2.length() <= 0){
              sql.setNull(5, Types.VARCHAR); 
            }else{
              sql.setString(5, lin2);  
            }
                
            if(est.length() <= 0){
               sql.setNull(6, Types.VARCHAR);
            }else{
               sql.setString(6, est); 
            }
            
            sql.setString(7, pai);
            sql.setString(8, cp);
            sql.setString(9, ter);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void editOficina(String num, String ciu, String tel, String lin1, String lin2, String est, String pai, String cp, String ter) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("update offices set city = ?, phone = ?, addressLine1 = ?, "
                    + "addressLine2 = ?, state = ?, country = ?, postalCode = ?, territory = ?"
                    + " where officeCode = ?");
            
            sql.setString(1, ciu);
            sql.setString(2, tel);
            sql.setString(3, lin1);
            
            if(lin2.length() <= 0){
              sql.setNull(4, Types.VARCHAR); 
            }else{
              sql.setString(4, lin2);  
            }
                
            if(est.length() <= 0){
               sql.setNull(5, Types.VARCHAR);
            }else{
               sql.setString(5, est); 
            }
            
            sql.setString(6, pai);
            sql.setString(7, cp);
            sql.setString(8, ter);
            sql.setString(9, num);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void deleteOficina(String num) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("delete from offices where officeCode = ?");
            sql.setString(1, num);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
}
