import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Sql_connected
{
    public Connection getConnection() throws Exception
    {
        try
        {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://127.0.0.1:3306/diary?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String username = "root";
            String password = "zaq12wsx";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("connection to serwer");
            return con;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public void createTable_window_log() throws Exception
    {
        try{
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS window_log(id int NOT NULL AUTO_INCREMENT, login varchar(10), password varchar(10), PRIMARY KEY(id))");
            create.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }finally
        {
            System.out.println("create table complete");
        }
    }
    public void post(String m_login, String m_password) throws Exception
    {
        String login = m_login;
        String password = m_password;

        try{
            Connection con = getConnection();
            PreparedStatement post = con.prepareStatement("INSERT INTO window_log (login, password) VALUES ('"+login+"', '"+password+"')");
            post.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }finally
        {
            System.out.println("insert complete");
        }
    }
    public int get(String login_user, String password_user) throws Exception
    {
        int flag_login_and_password = 1;
        try{
            Connection con = getConnection();
            PreparedStatement get = con.prepareStatement("SELECT * FROM window_log WHERE login='"+login_user+"'");
            ResultSet result = get.executeQuery();
            while (result.next())
            {
                if (result.getString("password").equals(password_user))
                {
                    flag_login_and_password = 3;
                }
                else
                    flag_login_and_password = 2;
            }
        }catch (Exception e){
            System.out.println(e);
            flag_login_and_password = 0;
        }
        return flag_login_and_password;
    }

}
