package puntodeventa.Conexiones;

import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
    ************
    **EMPLEADOS**
    ************
*/

public class EmpleadosDB{
    public String cadena_con = "jdbc:mariadb://localhost:3306/classicmodels?user=root&password=";
    
    public void getComboBox(JComboBox oficinas, JComboBox reporta) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement getReporta = connection.prepareStatement("select employeeNumber from employees order by employeeNumber");
            PreparedStatement getOficinas = connection.prepareStatement("select officeCode from offices order by officeCode");
            
            ResultSet resRep = getReporta.executeQuery();
            ResultSet resOfi = getOficinas.executeQuery();
            
            reporta.addItem("");
            while(resRep.next()){
                String item_rep;
                item_rep = resRep.getString("employeeNumber");
                reporta.addItem(item_rep);
            }
            
            while(resOfi.next()){
                String item_ofi;
                item_ofi = resOfi.getString("officeCode");
                oficinas.addItem(item_ofi);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
    }
    
    public DefaultTableModel getEmpleados() throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Num. Empleado");
        datos.addColumn("Apellido");
        datos.addColumn("Nombre");
        datos.addColumn("Extensión");
        datos.addColumn("Correo");
        datos.addColumn("Cod. Oficina");
        datos.addColumn("Se reporta a");
        datos.addColumn("Puesto");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from employees");
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[8];
                fila[0] = res.getString("employeeNumber");
                fila[1] = res.getString("lastName");
                fila[2] = res.getString("firstName");
                fila[3] = res.getString("extension");
                fila[4] = res.getString("email");
                fila[5] = res.getString("officeCode");
                fila[6] = res.getString("reportsTo");
                fila[7] = res.getString("jobTitle");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }
    
    public DefaultTableModel getEmpleados(String codigo) throws SQLException{
        DefaultTableModel datos = new DefaultTableModel();
        
        datos.addColumn("Num. Empleado");
        datos.addColumn("Apellido");
        datos.addColumn("Nombre");
        datos.addColumn("Extensión");
        datos.addColumn("Correo");
        datos.addColumn("Cod. Oficina");
        datos.addColumn("Se reporta a");
        datos.addColumn("Puesto");
        
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("select * from employees where employeeNumber = ?");
            sql.setString(1, codigo);
            
            ResultSet res = sql.executeQuery();
            
            while(res.next()){
                Object[] fila = new Object[8];
                fila[0] = res.getString("employeeNumber");
                fila[1] = res.getString("lastName");
                fila[2] = res.getString("firstName");
                fila[3] = res.getString("extension");
                fila[4] = res.getString("email");
                fila[5] = res.getString("officeCode");
                fila[6] = res.getString("reportsTo");
                fila[7] = res.getString("jobTitle");
                datos.addRow(fila);
            }
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error!", 1);
                System.out.println(e.getMessage());
        }
        
        return datos;
    }

    public void addEmpleado(String cod, String ape, String nom, String ext, String corr, String ofi, String rep, String pue) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("insert into employees values (?,?,?,?,?,?,?,?)");
            sql.setString(1, cod);
            sql.setString(2, ape);
            sql.setString(3, nom);
            sql.setString(4, ext);
            sql.setString(5, corr);
            sql.setString(6, ofi);
            if(rep.length() > 0){
              sql.setString(7, rep);  
            }else{
              sql.setNull(7, Types.SMALLINT);  
            }
            sql.setString(8, pue);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void editEmpleado(String cod, String ape, String nom, String ext, String corr, String ofi, String rep, String pue) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("update employees set employeeNumber = ?, lastName = ?, firstName = ?, "
                    + "extension = ?, email = ?, officeCode = ?, reportsTo = ?, jobTitle = ?"
                    + "where employeeNumber = ?");
            sql.setString(1, cod);
            sql.setString(2, ape);
            sql.setString(3, nom);
            sql.setString(4, ext);
            sql.setString(5, corr);
            sql.setString(6, ofi);
            if(rep.length() > 0){
              sql.setString(7, rep);  
            }else{
              sql.setNull(7, Types.SMALLINT);  
            }
            sql.setString(8, pue);
            sql.setString(9, cod);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
    
    public void deleteEmpleado(String cod) throws SQLException{
        try{
            Connection connection = DriverManager.getConnection(cadena_con);
            PreparedStatement sql = connection.prepareStatement("delete from employees where employeeNumber = ?");
            sql.setString(1, cod);
            
            ResultSet res = sql.executeQuery();
            
            connection.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Revise los campos e intente nuevamente", "Error!", 1);
                System.out.println(e.getMessage());
        }

    }
}
