import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginUser
{
    Sql_connected sql_connected;
    private JPanel panel1;
    private JPanel loginPanel;
    private JPanel registerPanel;
    private JPanel diary_menuPanel;
    private JPanel add_diaryPanel;
    private JPanel edit_diaryPanel;
    private JTextField t_login;
    private JPasswordField t_password;
    private JButton b_register;
    private JButton b_login;
    private JLabel login_panel;
    private JPanel passwordLabel;
    private JTextField textField2;
    private JPasswordField passwordField2;
    private JButton b_cancel2;
    private JButton b_register2;
    private JPasswordField passwordField3;
    private JLabel l_login2;
    private JLabel l_repeat_password2;
    private JLabel l_password2;
    private JButton b_new3;
    private JButton b_edit3;
    private JButton b_cancel4;
    private JButton b_open4;
    private JComboBox cb_date4;
    private JPanel l_date_last_edition4;
    private JButton b_cancel5;
    private JButton b_add_diary5;
    private JTextArea t_words5;
    private JLabel l_date5;
    private JLabel l_user5;
    private JLabel password_panel;
    private JTextField loginJTextField;
    private JPasswordField passwordJTextField;
    private JButton loginJButton;

    public LoginUser() throws Exception
    {
        sql_connected = new Sql_connected();
        sql_connected.getConnection();
        sql_connected.createTable_window_log();
        b_login.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int flag_login = 0;
                String login = t_login.getText();
                String password = String.valueOf(t_password.getPassword());
                try
                {
                    flag_login = sql_connected.get(login, password);
                } catch (Exception e1)
                {
                    e1.printStackTrace();
                }
                switch (flag_login)
                {
                    case 1:
                        System.out.println("Login not correct");
                        JOptionPane.showMessageDialog(null, "Login not correct");
                        break;
                    case 2:
                        System.out.println("password not correct");
                        JOptionPane.showMessageDialog(null, "password not correct");
                        break;
                    case 3:
                        System.out.println("account active");
                        JOptionPane.showMessageDialog(null, "account active");
                        loginPanel.setVisible(false);
                        diary_menuPanel.setVisible(true);
                        break;
                    default:
                        System.out.println("error default");
                }
            }
        });
        b_register.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loginPanel.setVisible(false);
                registerPanel.setVisible(true);
            }
        });
        b_cancel2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loginPanel.setVisible(true);
                registerPanel.setVisible(false);
            }
        });
        /**
         * @b_register2 obsluga przycisku rejestracji w oknie rejestracji
         */
        b_register2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String name_log = textField2.getText();
                String password1 = String.valueOf(passwordField2.getPassword());
                String password2 = String.valueOf(passwordField3.getPassword());
                try
                {
                    if (sql_connected.get(name_log, password1) == 2 || sql_connected.get(name_log, password1) == 3)
                    {
                        JOptionPane.showMessageDialog(null, "Login is the same in database\nplease change name");
                        return;
                    }
                    else if (!password1.equals(password2))
                    {
                        JOptionPane.showMessageDialog(null, "Password are not correct!");
                        return;
                    }
                } catch (Exception e1)
                {
                    e1.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Create account\nlogin: " + name_log + "\npassword: " + password1);
                try
                {
                    sql_connected.post(name_log, password1);
                    loginPanel.setVisible(true);
                    registerPanel.setVisible(false);
                } catch (Exception e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        b_new3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                diary_menuPanel.setVisible(false);
                add_diaryPanel.setVisible(true);
            }
        });
        b_edit3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                diary_menuPanel.setVisible(false);
                edit_diaryPanel.setVisible(true);
            }
        });
    }

    public static void main(String[] args) throws Exception
    {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new LoginUser().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
