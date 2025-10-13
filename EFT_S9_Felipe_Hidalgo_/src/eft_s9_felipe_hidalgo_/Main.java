/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package eft_s9_felipe_hidalgo_;
import javax.swing.*;


/**
 *
 * @author pipe-
 */
public class Main {

   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                var ctl = new controller.ClienteController();
                int n = ctl.listar().size();
                JOptionPane.showMessageDialog(null,
                    " Conexión OK. Registros: " + n,
                    "Conexión BD",
                    JOptionPane.INFORMATION_MESSAGE
                );

                // Aquí puedes abrir tu ventana principal:
                // new view.MainFrame().setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "No se pudo conectar:\n" + e.getMessage(),
                    "Conexión BD",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
    
}
