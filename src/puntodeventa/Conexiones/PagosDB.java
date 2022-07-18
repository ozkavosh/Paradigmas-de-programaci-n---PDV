package puntodeventa.Conexiones;

import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
    ************
    **PAGOS**
    ************
*/

public class PagosDB{
    public String cadena_con = "jdbc:mariadb://localhost:3306/classicmodels?user=root&password=";
    
    public void getComboBox(JComboBox cliente) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select customerNumber from customers order by customerNumber");
            
            ResultSet resCli = sql.executeQuery();

            while(resCli.next()){
                String item_cli;
                item_cli = resCli.getString("customerNumber");
                cliente.addItem(item_cli);
            }

            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
    }
    
    public DefaultTableModel getPagos() throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Num. Cliente");
        datos.addColumn("Num. Cheque");
        datos.addColumn("Fecha");
        datos.addColumn("Cantidad");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from payments");
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[4];
                fila[0] = res.getString("customerNumber");
                fila[1] = res.getString("checkNumber");
                fila[2] = res.getString("paymentDate");
                fila[3] = res.getString("amount");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }
    
    public DefaultTableModel getPagos(String numero) throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Num. Cliente");
        datos.addColumn("Num. Cheque");
        datos.addColumn("Fecha");
        datos.addColumn("Cantidad");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from payments where customerNumber = ? or checkNumber = ?");
            if(!numero.matches("[0-9]*")){
               sql.setString(1, numero);
               sql.setString(2, numero);
            }else{
               int num = Integer.parseInt(numero);
               sql.setInt(1, num);
               sql.setInt(2, num); 
            }
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[4];
                fila[0] = res.getString("customerNumber");
                fila[1] = res.getString("checkNumber");
                fila[2] = res.getString("paymentDate");
                fila[3] = res.getString("amount");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }
    
    public void addPago(String num, String cnum, String fecha, String cant) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("insert into payments values (?,?,?,?)");
            sql.setString(1, num);
            sql.setString(2, cnum);
            sql.setString(3, fecha);
            sql.setString(4, cant);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void editPago(String num, String cnum, String fecha, String cant) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("update payments set customerNumber = ?, checkNumber = ?, paymentDate = ?, amount = ? "
                    + "where checkNumber = ?");
            sql.setString(1, num);
            sql.setString(2, cnum);
            sql.setString(3, fecha);
            sql.setString(4, cant);
            sql.setString(5, cnum);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void deletePago(String num) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("delete from payments where checkNumber = ?");
            sql.setString(1, num);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
}
