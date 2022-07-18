package puntodeventa.Conexiones;

import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
    ************
    **PRODUCTOS**
    ************
*/

public class ProductosDB{
    public String cadena_con = "jdbc:mariadb://localhost:3306/classicmodels?user=root&password=";
    
    public void getComboBox(JComboBox lineas) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select productLine from productlines order by productLine");
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                String item_rep;
                item_rep = res.getString("productLine");
                lineas.addItem(item_rep);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
    }
    
    public DefaultTableModel getProductos() throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Cód. Producto");
        datos.addColumn("Nombre");
        datos.addColumn("Linea");
        datos.addColumn("Escala");
        datos.addColumn("Vendedor");
        datos.addColumn("Descripcion");
        datos.addColumn("Stock");
        datos.addColumn("Precio");
        datos.addColumn("MSRP");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from products");
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[9];
                fila[0] = res.getString("productCode");
                fila[1] = res.getString("productName");
                fila[2] = res.getString("productLine");
                fila[3] = res.getString("productScale");
                fila[4] = res.getString("productVendor");
                fila[5] = res.getString("productDescription");
                fila[6] = res.getString("quantityInStock");
                fila[7] = res.getString("buyPrice");
                fila[8] = res.getString("MSRP");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }
    
    public DefaultTableModel getProductos(String codigo) throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Cód. Producto");
        datos.addColumn("Nombre");
        datos.addColumn("Linea");
        datos.addColumn("Escala");
        datos.addColumn("Vendedor");
        datos.addColumn("Descripcion");
        datos.addColumn("Stock");
        datos.addColumn("Precio");
        datos.addColumn("MSRP");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            
            PreparedStatement sql = connection.prepareStatement("select * from products where productCode = ? or productLine = ?");
            sql.setString(1, codigo);
            sql.setString(2, codigo);
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[9];
                fila[0] = res.getString("productCode");
                fila[1] = res.getString("productName");
                fila[2] = res.getString("productLine");
                fila[3] = res.getString("productScale");
                fila[4] = res.getString("productVendor");
                fila[5] = res.getString("productDescription");
                fila[6] = res.getString("quantityInStock");
                fila[7] = res.getString("buyPrice");
                fila[8] = res.getString("MSRP");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }

    public void addProducto(String cod, String nom, String lin, String esc, String vend, String desc, String stock, String pre, String msrp) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("insert into products values (?,?,?,?,?,?,?,?,?)");
            sql.setString(1, cod);
            sql.setString(2, nom);
            sql.setString(3, lin);
            sql.setString(4, esc);
            sql.setString(5, vend);
            sql.setString(6, desc);
            sql.setString(7, stock);  
            sql.setString(8, pre);
            sql.setString(9, msrp);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void editProducto(String cod, String nom, String lin, String esc, String vend, String desc, String stock, String pre, String msrp) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("update products set productName = ?, productLine = ?, productScale = ?, "
                    + "productVendor = ?, productDescription = ?, quantityInStock = ?, buyPrice = ?, MSRP = ?"
                    + "where productCode = ?");
            sql.setString(1, nom);
            sql.setString(2, lin);
            sql.setString(3, esc);
            sql.setString(4, vend);
            sql.setString(5, desc);
            sql.setString(6, stock);
            sql.setString(7, pre);  
            sql.setString(8, msrp);
            sql.setString(9, cod);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void deleteProducto(String cod) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("delete from products where productCode = ?");
            sql.setString(1, cod);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
}
