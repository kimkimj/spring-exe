package dao;

import dao.ConnectionMaker;
import domain.User;

import java.sql.*;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao() {
        this.connectionMaker = new AwsConnectionMaker();
    }

    public UserDao(ConnectionMaker connectonMaker){
        this.connectionMaker = connectonMaker;
    }

    public void add(User user) {
        try {
            Connection con = connectionMaker.getConnection();

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?)");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            pstmt.executeUpdate();

            pstmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(String id) {
        try {
            Connection con = connectionMaker.getConnection();

            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            rs.next();
            domain.User user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));

            rs.close();
            pstmt.close();
            con.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll(){
        try{
            Connection c = connectionMaker.getConnection();

            PreparedStatement ps=c.prepareStatement("DELETE FROM users");
            ps.executeUpdate();

            ps.close();
            c.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public int getCount(){
        try{
            Connection c= connectionMaker.getConnection();
            PreparedStatement ps=c.prepareStatement("SELECT COUNT(*) FROUM users");
            ResultSet result=ps.executeQuery();
            result.next();

            int count=result.getInt(1);

            result.close();
            ps.close();
            c.close();

            return count;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}