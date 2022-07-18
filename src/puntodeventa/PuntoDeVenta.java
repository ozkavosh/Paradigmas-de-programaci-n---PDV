package puntodeventa;

import puntodeventa.Ventanas.Inicio;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class PuntoDeVenta {

    public static void main(String[] args){
        new Inicio().setVisible(true); //Mostrar la ventana de Inicio
    }
    
    public void setFavicon(JFrame form){
        //Establecer el icono de la ventana
        ImageIcon favicon = new ImageIcon(getClass().getResource("/img/pdv_favicon.png"));
        form.setIconImage(favicon.getImage());
    }
    
    public void setPosition(JFrame form){
        //Centrar la ventana
        form.setLocationRelativeTo(null);
        form.pack();
    }
    
    public Icon setIcono(String url, JButton boton){
        //Posicionar una imagen dentro de un boton
        ImageIcon img = new ImageIcon(getClass().getResource(url));
        
        int ancho = boton.getWidth() - 15;
        int alto = boton.getHeight() - 15;
        
        ImageIcon icono = new ImageIcon(img.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT));
        
        return icono;
    }
    
    public void switchPanels(JPanel panel, JLayeredPane contenedor){
        //Cambiar de Paneles dentro de un contenedor
        contenedor.removeAll();
        contenedor.add(panel);
        contenedor.repaint();
        contenedor.revalidate();
    }
    
    public void setEnterButton(JFrame form, JButton boton){
        form.getRootPane().setDefaultButton(boton);
    }
    
}
