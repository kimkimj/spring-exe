package dao;

public class UserFactory {
    public UserDao awsUserDao() {
        return new UserDao(new AwsConnectionMaker());
    }
}