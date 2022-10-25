package dao;

import dao.ConnectionMaker;
import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

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
        Connection c;
        try {
            c = connectionMaker.getConnection();

            PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?)");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            pstmt.executeUpdate();

            pstmt.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(String id) {
        Connection c;
        try {
            c = connectionMaker.getConnection();

            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            // Execute query and save the data into ResultSet
            ResultSet rs = pstmt.executeQuery();
            User user = null;

            // If the queried data exists
            if (rs.next()) {
                user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
            }
            rs.close();
            pstmt.close();
            c.close();

            if(user == null){
                throw new EmptyResultDataAccessException(1);
            }
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll(){

        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = connectionMaker.getConnection();
            ps = c.prepareStatement("DELETE FROM users");
            ps.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();

        } finally{
            if (ps != null){
                try{
                    ps.close();
                } catch (SQLException e){

                }
            }
            if (c != null){
                try{
                    c.close();
                } catch (SQLException e){}
            }
        }
    }
    public int getCount(){
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            c = connectionMaker.getConnection();
            ps = c.prepareStatement("SELECT COUNT(*) FROUM users");
            rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1);

        }catch(SQLException e){
            e.printStackTrace();

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
        return 0;
    }
}