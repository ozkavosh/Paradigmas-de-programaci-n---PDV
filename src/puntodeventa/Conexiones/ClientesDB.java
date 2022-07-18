package puntodeventa.Conexiones;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
    ************
    **CLIENTES**
    ************
*/

public class ClientesDB{
    public String cadena_con = "jdbc:mariadb://localhost:3306/classicmodels?user=root&password=";
    
    public DefaultTableModel getClientes() throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Num. Cliente");
        datos.addColumn("Nombre");
        datos.addColumn("Con. Apellido");
        datos.addColumn("Con. Nombre");
        datos.addColumn("Telefono");
        datos.addColumn("Dirección");
        datos.addColumn("Ciudad");
        datos.addColumn("Código Postal");
        datos.addColumn("Pais");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from customers");
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[9];
                fila[0] = res.getString("customerNumber");
                fila[1] = res.getString("customerName");
                fila[2] = res.getString("contactLastName");
                fila[3] = res.getString("contactFirstName");
                fila[4] = res.getString("phone");
                fila[5] = res.getString("addressLine1");
                fila[6] = res.getString("city");
                fila[7] = res.getString("postalCode");
                fila[8] = res.getString("country");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }
    
    public DefaultTableModel getClientes(String numero) throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Num. Cliente");
        datos.addColumn("Nombre");
        datos.addColumn("Con. Apellido");
        datos.addColumn("Con. Nombre");
        datos.addColumn("Telefono");
        datos.addColumn("Dirección");
        datos.addColumn("Ciudad");
        datos.addColumn("Código Postal");
        datos.addColumn("Pais");
        
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from customers where customerNumber = ?");
            sql.setString(1,numero);
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[9];
                fila[0] = res.getString("customerNumber");
                fila[1] = res.getString("customerName");
                fila[2] = res.getString("contactLastName");
                fila[3] = res.getString("contactFirstName");
                fila[4] = res.getString("phone");
                fila[5] = res.getString("addressLine1");
                fila[6] = res.getString("city");
                fila[7] = res.getString("postalCode");
                fila[8] = res.getString("country");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }
    
    public void addCliente(String num, String nom, String c_ape, String c_nom, String tel, String dir, String ciu, String cp, String pai) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("insert into customers values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            sql.setString(1, num);
            sql.setString(2, nom);
            sql.setString(3, c_ape);
            sql.setString(4, c_nom);
            sql.setString(5, tel);
            sql.setString(6, dir);
            sql.setNull(7, Types.VARCHAR);
            sql.setString(8, ciu);
            sql.setNull(9, Types.VARCHAR);
            sql.setString(10, cp);
            sql.setString(11, pai);
            sql.setNull(12, Types.SMALLINT);
            sql.setString(13, "0");
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void editCliente(String num, String nom, String c_ape, String c_nom, String tel, String dir, String ciu, String cp, String pai) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("update customers set customerNumber = ?, customerName = ?, contactLastName = ?, "
                    + "contactFirstName = ?, phone = ?, addressLine1 = ?, city = ?, postalCode = ?, country = ?"
                    + "where customerNumber = ?");
            sql.setString(1, num);
            sql.setString(2, nom);
            sql.setString(3, c_ape);
            sql.setString(4, c_nom);
            sql.setString(5, tel);
            sql.setString(6, dir);
            sql.setString(7, ciu);
            sql.setString(8, cp);
            sql.setString(9, pai);
            sql.setString(10, num);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void deleteCliente(String cod) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("delete from customers where customerNumber = ?");
            sql.setString(1, cod);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
}
