package com.programando.intentandolo.blocnotas;

import com.programando.intentandolo.blocnotas.io.IOUtils;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Ivan Salas Corrales 
 * <http://programandoointentandolo.com/>
 */
public class BlocNotas extends Observable {

    JFrame ventana;
    JTextArea notas;

    public BlocNotas() {

        ventana = new JFrame("Mi bloc de Notas");
        
        ventana.setJMenuBar(buildMenus());
        
        initializeGUI();

        // Cra un area de texto con scroll y lo añade a la ventana 
        initializeTextArea();
        
        ventana.setVisible(true);

    }
    
    private void initializeGUI(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            
            
            ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);            
        
        } catch (ClassNotFoundException |InstantiationException |IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(BlocNotas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initializeTextArea(){
        // Cra un area de texto con scroll y lo añade a la ventana 
        notas = new JTextArea();
        JScrollPane scrollNotas = new JScrollPane(notas);
        ventana.add(scrollNotas);
    }
    
    private JMenuBar buildMenus(){
        // Inicializa todos los elementos del menu
        JMenuBar menu = new JMenuBar();

        JMenu archivo = new JMenu("Archivo");
        JMenu ayuda = new JMenu("Ayuda");

        JMenuItem nuevo = new JMenuItem("Nuevo");
        JMenuItem abrir = new JMenuItem("Abrir...");
        JMenuItem guardar = new JMenuItem("Guardar");
        JMenuItem salir = new JMenuItem("Salir");
        JMenuItem acercaDe = new JMenuItem("Acerca de...");

        // Añade los elementos al menu
        archivo.add(nuevo);
        archivo.add(abrir);
        archivo.add(guardar);
        archivo.add(salir);
        ayuda.add(acercaDe);

        menu.add(archivo);
        menu.add(ayuda);
        
        
        // Asigna a cada menuItem su listener
        nuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notas.setText("");
            }
        });
        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivo();
            }
        });
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarArchivo();
            }
        });
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        return menu;
    }

    public void abrirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(ventana)) {
            File archivo = fileChooser.getSelectedFile();
            FileReader lector = null;
            try {
                lector = new FileReader(archivo);
                BufferedReader bfReader = new BufferedReader(lector);

                String lineaFichero;
                StringBuilder contenidoFichero = new StringBuilder();

                // Recupera el contenido del fichero
                while ((lineaFichero = bfReader.readLine()) != null) {
                    contenidoFichero.append(lineaFichero);
                    contenidoFichero.append("\n");
                }

                // Pone el contenido del fichero en el area de texto
                notas.setText(contenidoFichero.toString());

            } catch (FileNotFoundException ex) {
                Logger.getLogger(BlocNotas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BlocNotas.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    lector.close();
                } catch (IOException ex) {
                    Logger.getLogger(BlocNotas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void guardarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(ventana)) {
            File archivo = fileChooser.getSelectedFile();
            
            String pathfileName = archivo.getAbsolutePath();
            String data = notas.getText();
            
            IOUtils.saveFile(pathfileName, data);            
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BlocNotas bn = new BlocNotas();
    }

}