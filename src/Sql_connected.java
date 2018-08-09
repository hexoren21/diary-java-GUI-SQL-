import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
            System.out.println("create Table_window_log complete");
        }
    }
    public void createTable_diary_text() throws Exception
    {
        try{
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS diary_text(id_diary_text int NOT NULL AUTO_INCREMENT, date varchar(19), title varchar(40), text_diary TEXT, PRIMARY KEY(id_diary_text))");
            create.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }finally
        {
            System.out.println("create Table_diary complete");
        }
    }
    public void createTable_edit_diary() throws Exception
    {
        try{
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS diary_edit(id_edit int NOT NULL AUTO_INCREMENT, id int, id_diary_text int, data_edit varchar(20), PRIMARY KEY(id_edit))");
            create.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }finally
        {
            System.out.println("create Table_edit_diary complete");
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
    public void post_diary(String m_title, String m_text_diary, String m_date) throws Exception
    {
        String title = m_title;
        String text_diary = m_text_diary;
        String date = m_date;

        try{
            Connection con = getConnection();
            PreparedStatement post = con.prepareStatement("INSERT INTO diary_text VALUES (NULL, '"+date+"', '"+title+"', '"+text_diary+"')");
            post.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }finally
        {
            System.out.println("insert complete");
        }
    }
    public void post_edit_diary(int id_, int id_diary_text, String m_date) throws Exception
    {
        try{
            Connection con = getConnection();
            PreparedStatement post = con.prepareStatement("INSERT INTO diary_edit VALUES (NULL, '"+id_+"', '"+id_diary_text+"', '"+m_date+"')");
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
    public int get_id(String login_user) throws Exception
    {
        int id=0;
        try{
            Connection con = getConnection();
            PreparedStatement get = con.prepareStatement("SELECT * FROM window_log WHERE login='"+login_user+"'");
            ResultSet result = get.executeQuery();
            while (result.next())
            {
               id = result.getInt("id");

            }
        }catch (Exception e){
            System.out.println(e);
        }
        return id;
    }
    public int get_id_diary_text(String title) throws Exception
    {
        int id=0;
        try{
            Connection con = getConnection();
            PreparedStatement get = con.prepareStatement("SELECT * FROM diary_text WHERE title='"+title+"'");
            ResultSet result = get.executeQuery();
            while (result.next())
            {
                id = result.getInt("id_diary_text");

            }
        }catch (Exception e){
            System.out.println(e);
        }
        return id;
    }
    public ArrayList<String> get_date_from_date_text(String user) throws Exception
    {
        ArrayList<String> table = new ArrayList<>();
        String data_before = ".";
        String data_after;
        try{
            Connection con = getConnection();
            PreparedStatement get = con.prepareStatement("SELECT diary_text.date FROM diary_text, diary_edit, window_log" +
                                                                 " WHERE window_log.login='"+user+"' and window_log.id = diary_edit.id and " +
                                                                 "diary_edit.id_diary_text = diary_text.id_diary_text");
            ResultSet result = get.executeQuery();
            while (result.next())
            {
                data_after = result.getString("date");
                data_after = data_after.substring(0, 10);
                if(!data_after.equals(data_before))
                {
                    table.add(data_after);
                    data_before = data_after;
                }


            }
        }catch (Exception e){
            System.out.println(e);
        }
        return table;
    }
    public ArrayList<String> get_title_from_date_text(String date1) throws Exception
    {
        ArrayList<String> table = new ArrayList<>();
        try{
            Connection con = getConnection();
            PreparedStatement get = con.prepareStatement("SELECT diary_text.title FROM diary_text WHERE date LIKE '"+date1+'%'+"'");
            ResultSet result = get.executeQuery();
            while (result.next())
            {
                table.add(result.getString("title"));

            }
        }catch (Exception e){
            System.out.println(e);
        }
        return table;
    }
    public String get_last_data_eition(String title1) throws Exception
    {
        String data_edition = "";
        try{
            Connection con = getConnection();
            PreparedStatement get = con.prepareStatement("SELECT diary_edit.data_edit FROM diary_edit, diary_text WHERE " +
                                                                 "diary_text.title = '"+title1+"' and diary_text.id_diary_text = diary_edit.id_diary_text");
            ResultSet result = get.executeQuery();
            while (result.next())
            {
                data_edition = result.getString("data_edit");

            }
        }catch (Exception e){
            System.out.println(e);
        }
        return data_edition;
    }
    public String get_diary_text(String title1) throws Exception
    {
        String diary_text = "";
        try
        {
            Connection con = getConnection();
            PreparedStatement get = con.prepareStatement("SELECT diary_text.text_diary FROM diary_text WHERE " +
                                                                 "diary_text.title = '" + title1 + "'");
            ResultSet result = get.executeQuery();
            while (result.next())
            {
                diary_text = result.getString("text_diary");

            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return diary_text;
    }
    public String update_diary_text(String title1, String date1, String text_diary) throws Exception
    {
        String diary_text = "";
        try
        {
            Connection con = getConnection();
            PreparedStatement update = con.prepareStatement("UPDATE diary_text SET diary_text.text_diary = '"+text_diary+"' WHERE diary_text.title = '"+title1+"'");
            update.executeUpdate();
            update = con.prepareStatement("UPDATE diary_edit INNER JOIN diary_text ON diary_text.id_diary_text=diary_edit.id_diary_text" +
                                                  " AND diary_text.title = '"+title1+"' " +
                                                  "SET diary_edit.data_edit = '"+date1+"'");
            update.executeUpdate();

        } catch (Exception e)
        {
            System.out.println(e);
        }
        return diary_text;
    }
}
