package com.nccs.sms.main;
import com.nccs.sms.gui.LoginDialog;
import javax.swing.UIManager;

public class MainSMS {
    public static void main(String[] args) {
         try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }    
         
        LoginDialog id = new LoginDialog();
        id.setLocationRelativeTo(null);
        id.setResizable(false);
        id.setVisible(true);
    }
}
