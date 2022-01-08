package com.example.demo2;


import java.sql.*;

public class Driver {

    private  static Connection Connection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/contas";
        String usuario = "root";
        String senha = "";

        Connection connection = DriverManager.getConnection(url, usuario, senha);
        return connection;
    }

    public static void setCadastroCC(ContaCorrente cc) throws SQLException {
        Connection connection = Connection();
        try {

            String sql = "INSERT INTO `contas`.`conta` (`agencia`, `nconta`, `titular`, `saldo`, `tarifa`, `gerente`) VALUES (?, ?, ?, '0', ?, ?);";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, cc.getAgencia());
            statement.setInt(2, cc.getNconta());
            statement.setString(3, cc.getTitular());
            statement.setDouble(4,cc.getTarifa());
            statement.setString(5,cc.getGerente());

            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();

        }
    }

    public static void setCadastroCP(ContaPoupanca cp) throws SQLException {
        Connection connection = Connection();

        try {

            String sql = "INSERT INTO `contas`.`conta` (`agencia`, `nconta`, `titular`, `saldo`, `vencimento`) VALUES (?, ?, ?, '0', ?);";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, cp.getAgencia());
            statement.setInt(2, cp.getNconta());
            statement.setString(3, cp.getTitular());
            statement.setString(4,cp.getVencimento());
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();

        }
    }

    public static boolean VerificaContaCC(ContaCorrente conta) throws SQLException {
        Connection connection = Connection();

        Statement statement = connection.createStatement();
        String sql = "select * from conta;";
        ResultSet rs = statement.executeQuery(sql);

        boolean bool = false;


        while(rs.next()){
            if(conta.getAgencia() == rs.getInt(2) && conta.getNconta() == rs.getInt(3)){
                bool =  true;
            }
        }
        return bool;
    }

    public static boolean VerificaContaCP(ContaPoupanca conta) throws SQLException {
        Connection connection = Connection();
        Statement statement = connection.createStatement();
        String sql = "select * from conta;";
        ResultSet rs = statement.executeQuery(sql);
        boolean bool = false;

        while(rs.next()){
            if(conta.getAgencia() == rs.getInt(2) && conta.getNconta() == rs.getInt(3)){
                bool = true;
            }
        }
        return bool;
    }

    public static void SetDepositoCC(ContaCorrente cc,Double deposito) throws SQLException {
        Connection connection = Connection();

        cc.deposito(deposito);
        int id = getIDCC(cc);

        try {


            String sql = "UPDATE `contas`.`conta` SET `saldo` = ? WHERE (`id` = ?);";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDouble(1,  cc.getSaldo());
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
}

    private static int getIDCC(ContaCorrente cc) throws SQLException {
        Connection connection = Connection();
        Statement statement = connection.createStatement();
        String sql = "select * from conta;";
        ResultSet rs = statement.executeQuery(sql);
        int id = 0;

        while(rs.next()){
        if(cc.getAgencia() == rs.getInt(2) && cc.getNconta() == rs.getInt(3)){
            id = rs.getInt(1);

        }}
        return id;
    }

    public static ContaCorrente SetContaCC(ContaCorrente cc) throws SQLException {
        Connection connection = Connection();
        ContaCorrente conta = new ContaCorrente();
        Statement statement = connection.createStatement();
        String sql = "select * from conta;";

        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
        if(cc.getAgencia() == rs.getInt(2 ) && cc.getNconta() == rs.getInt(3)){

            conta.setAgencia(rs.getInt(2));
            conta.setNconta((rs.getInt(3)));
            conta.setTitular(rs.getString(4));
            conta.setSaldo(rs.getDouble(5));
            conta.setTarifa(rs.getDouble(6));
            conta.setGerente(rs.getString(7));

        }
        }
        return conta;
    }

    public static void SetDepositoCP(ContaPoupanca cp,Double deposito) throws SQLException {
        Connection connection = Connection();

        cp.deposito(deposito);
        int id = getIDCP(cp);

        try {


            String sql = "UPDATE `contas`.`conta` SET `saldo` = ? WHERE (`id` = ?);";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDouble(1,  cp.getSaldo());
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private static int getIDCP(ContaPoupanca cp) throws SQLException {
        Connection connection = Connection();
        Statement statement = connection.createStatement();
        String sql = "select * from conta;";
        ResultSet rs = statement.executeQuery(sql);
        int id = 0;

        while(rs.next()){
            if(cp.getAgencia() == rs.getInt(2) && cp.getNconta() == rs.getInt(3)){
                id = rs.getInt(1);

            }
        }
        return id;
    }

    public static ContaPoupanca SetContaCP(ContaPoupanca cp) throws SQLException {
        Connection connection = Connection();
        ContaPoupanca conta = new ContaPoupanca();
        Statement statement = connection.createStatement();
        String sql = "select * from conta;";

        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            if(cp.getAgencia() == rs.getInt(2 ) && cp.getNconta() == rs.getInt(3)){

                conta.setAgencia(rs.getInt(2));
                conta.setNconta((rs.getInt(3)));
                conta.setTitular(rs.getString(4));
                conta.setSaldo(rs.getDouble(5));
                conta.setVencimento(rs.getString(8));


            }
        }
        return conta;
    }

    public static void SetSaqueCC(ContaCorrente conta, double saque) throws SQLException {
        Connection connection = Connection();

        conta.saque(saque);
        int id = getIDCC(conta);

        try {


            String sql = "UPDATE `contas`.`conta` SET `saldo` = ? WHERE (`id` = ?);";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDouble(1,  conta.getSaldo());
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static void SetSaqueCP(ContaPoupanca conta, double saque) throws SQLException {
        Connection connection = Connection();

        conta.saque(saque);
        int id = getIDCP(conta);

        try {


            String sql = "UPDATE `contas`.`conta` SET `saldo` = ? WHERE (`id` = ?);";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDouble(1,  conta.getSaldo());
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}