package net.nothing.jobs.utils.mysql;

import net.nothing.jobs.JobsBootstrap;
import org.bukkit.Bukkit;

import java.sql.*;

public final class SimpleMySQL implements MySQL{

    private final String host, database, user, password;
    private final int port;
    private final boolean ssl;

    private Connection connection;

    public SimpleMySQL(String host, String database, String user, String password, int port, boolean ssl){
        this.host = host;
        this.database = database;
        this.user = user;

        this.password = password;
        this.port = port;

        this.ssl = ssl;

        try {
            this.connect();
            System.out.println("[SQL] Successful connection");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.password);

    }

    @Override
    public boolean update(final String update){
        if(update == null)
            return false;
        if(connection == null)
            return false;

        boolean resultFromUpdate = false;

        try {
            final Statement statement = connection.createStatement();
            statement.executeUpdate(update);
            statement.close();

            resultFromUpdate = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultFromUpdate;
    }

    @Override
    public ResultSet query(final String query){
        if(query == null)
            return null;
        if(connection == null)
            return null;
        ResultSet resultSet = null;

        try{
            final Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        }catch (SQLException exception){
            exception.printStackTrace();
        }

        return resultSet;
    }

    @Override
    public void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
