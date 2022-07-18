package puntodeventa.Conexiones;

import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
    ************
    **PEDIDOS**
    ************
*/

public class PedidosDB{
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
    
    public DefaultTableModel getPedidos() throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Num. Pedido");
        datos.addColumn("Fecha");
        datos.addColumn("Requerido");
        datos.addColumn("Enviado");
        datos.addColumn("Estado");
        datos.addColumn("Comentarios");
        datos.addColumn("Num. Cliente");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from orders");
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[7];
                fila[0] = res.getString("orderNumber");
                fila[1] = res.getString("orderDate");
                fila[2] = res.getString("requiredDate");
                fila[3] = res.getString("shippedDate");
                fila[4] = res.getString("status");
                fila[5] = res.getString("comments");
                fila[6] = res.getString("customerNumber");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }
    
    public DefaultTableModel getPedidos(String numero) throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Num. Pedido");
        datos.addColumn("Fecha");
        datos.addColumn("Requerido");
        datos.addColumn("Enviado");
        datos.addColumn("Estado");
        datos.addColumn("Comentarios");
        datos.addColumn("Num. Cliente");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from orders where orderNumber = ? or customerNumber = ?");
            sql.setString(1, numero);
            sql.setString(2, numero);
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[7];
                fila[0] = res.getString("orderNumber");
                fila[1] = res.getString("orderDate");
                fila[2] = res.getString("requiredDate");
                fila[3] = res.getString("shippedDate");
                fila[4] = res.getString("status");
                fila[5] = res.getString("comments");
                fila[6] = res.getString("customerNumber");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }
    
    public void addPedido(String num, String fec, String com, String c_num) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("insert into orders values (?,now(),?,?,?,?,?)");
            sql.setString(1, num);
            sql.setString(2, fec);
            sql.setNull(3, Types.DATE);
            sql.setString(4, "In Process");
            sql.setString(5, com);
            sql.setString(6, c_num);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void editPedido(String num, String fec, String p_fec, String e_fec, String est, String com, String c_num) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("update orders set orderNumber = ? ,orderDate = ?, requiredDate = ?, shippedDate = ?, "
                    + "status = ?, comments = ?, customerNumber = ? where orderNumber = ?");
            sql.setString(1, num);
            sql.setString(2, fec);
            sql.setString(3, p_fec);
            sql.setString(4, e_fec);  
            sql.setString(5, est);
            sql.setString(6, com);
            sql.setString(7, c_num);
            sql.setString(8, num);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void editPedido(String num, String fec, String p_fec, String est, String com, String c_num) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("update orders set orderNumber = ? ,orderDate = ?, requiredDate = ?, shippedDate = ?, "
                    + "status = ?, comments = ?, customerNumber = ? where orderNumber = ?");
            sql.setString(1, num);
            sql.setString(2, fec);
            sql.setString(3, p_fec);
            sql.setNull(4, Types.DATE);
            sql.setString(5, est);
            sql.setString(6, com);
            sql.setString(7, c_num);
            sql.setString(8, num);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void editPedido(String num, int tipo) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            
            String consulta = tipo == 1 ? "update orders set shippedDate = now(), status = ? where orderNumber = ?" : "update orders set shippedDate = ?, status = ? where orderNumber = ?";
            
            PreparedStatement sql = connection.prepareStatement(consulta);
            
            if(tipo == 1){
                sql.setString(1, "Shipped");
                sql.setString(2, num);
            }else{
                sql.setNull(1, Types.DATE);
                sql.setString(2, "Cancelled");
                sql.setString(3, num);
            }

            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    
    public void deletePedido(String cod) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("delete from orders where orderNumber = ?");
            sql.setString(1, cod);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
}
