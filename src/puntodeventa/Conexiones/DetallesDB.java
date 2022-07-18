package puntodeventa.Conexiones;

import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
    ************
    **DETALLES DE PEDIDO**
    ************
*/

public class DetallesDB{
    public String cadena_con = "jdbc:mariadb://localhost:3306/classicmodels?user=root&password=";
    
    public void getComboBox(JComboBox pedidos) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select orderNumber from orders order by orderNumber");
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                String item_ped;
                item_ped = res.getString("orderNumber");
                pedidos.addItem(item_ped);
            }
           
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
    }
    
    public DefaultTableModel getDetalles() throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Num. Pedido");
        datos.addColumn("Cód. Producto");
        datos.addColumn("Cantidad");
        datos.addColumn("Precio Unitario");
        datos.addColumn("Núm. de Linea");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from orderDetails");
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[8];
                fila[0] = res.getString("orderNumber");
                fila[1] = res.getString("productCode");
                fila[2] = res.getString("quantityOrdered");
                fila[3] = res.getString("priceEach");
                fila[4] = res.getString("orderLineNumber");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }
    
    public DefaultTableModel getDetalles(String numero) throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Num. Pedido");
        datos.addColumn("Cód. Producto");
        datos.addColumn("Cantidad");
        datos.addColumn("Precio Unitario");
        datos.addColumn("Núm. de Linea");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from orderDetails where orderNumber = ?");
            sql.setString(1, numero);
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[5];
                fila[0] = res.getString("orderNumber");
                fila[1] = res.getString("productCode");
                fila[2] = res.getString("quantityOrdered");
                fila[3] = res.getString("priceEach");
                fila[4] = res.getString("orderLineNumber");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }

    public void addDetalle(String pnum, String pcod, String cant, String pre, String lin) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("insert into orderDetails values (?,?,?,?,?)");
            sql.setString(1, pnum);
            sql.setString(2, pcod);
            sql.setString(3, cant);
            sql.setString(4, pre);
            sql.setString(5, lin);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void editDetalle(String pnum, String pcod, String cant, String pre, String lin) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("update orderDetails set quantityOrdered = ?, priceEach = ?,"
                    + " orderLinenumber = ? where productCode = ? and orderNumber = ?");
            sql.setString(1, cant);
            sql.setString(2, pre);
            sql.setString(3, lin);
            sql.setString(4, pcod);
            sql.setString(5, pnum);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void deleteDetalle(String cod) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("delete from orderDetails where productCode = ?");
            sql.setString(1, cod);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
}
