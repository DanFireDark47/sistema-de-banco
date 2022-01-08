package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

import java.sql.*;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private TextField gerente;
    @FXML
    private TextField agencia;
    @FXML
    private TextField nconta;
    @FXML
    private TextField titular;
    @FXML
    private TextField tarifa;
    @FXML
    private TextField vencimento;
    @FXML
    private TextField valor;
    @FXML
    private TitledPane contacorrente;
    @FXML
    private TitledPane contapoupanca;
    @FXML
    private TextArea contas;

    ArrayList<ContaCorrente> lcc = new ArrayList();
    ArrayList<ContaPoupanca> lcp = new ArrayList();
    @FXML
    public void cadastrar() throws SQLException {
        if (contacorrente.isExpanded() && verificaTextFields()){
                ContaCorrente cc = new ContaCorrente();
                cc.setNconta(Integer.parseInt(nconta.getText()));
                cc.setAgencia(Integer.parseInt(agencia.getText()));
                cc.setGerente(gerente.getText());
                cc.setTarifa(Double.parseDouble(tarifa.getText()));
                cc.setTitular(titular.getText());
                cc.setSaldo(0);
                if (Driver.VerificaContaCC(cc)){
                    mensagemAviso("conta corrente existe");
                } else {
                    Driver.setCadastroCC(cc);
                }
            } else if (contapoupanca.isExpanded() && verificaTextFields()) {
                ContaPoupanca cp = new ContaPoupanca();
                cp.setNconta(Integer.parseInt(nconta.getText()));
                cp.setAgencia(Integer.parseInt(agencia.getText()));
                cp.setTitular(titular.getText());
                cp.setSaldo(0);
                cp.setVencimento(vencimento.getText());
                if (Driver.VerificaContaCP(cp)) {
                    mensagemAviso("conta poupanca existe");
                } else {
                    Driver.setCadastroCP(cp);
                }
            }
            limpar();
            buscaListaContas();

    }

    @FXML
    private void deposito() throws SQLException {
        if (contacorrente.isExpanded() && verificaTextFields()) {
            ContaCorrente cc = new ContaCorrente();

            cc.setNconta(Integer.parseInt(nconta.getText()));
            cc.setAgencia(Integer.parseInt(agencia.getText()));

            if(Driver.VerificaContaCC(cc)){
                ContaCorrente conta = Driver.SetContaCC(cc);
                Driver.SetDepositoCC(conta,Double.parseDouble(valor.getText()));
            }else  if(!Driver.VerificaContaCC(cc)){
                System.out.println("CC Deposito");
                mensagemAviso("conta não encontrada no sistema");
            }
        } else if(contapoupanca.isExpanded() && verificaTextFields()) {
            ContaPoupanca cp = new ContaPoupanca();

            cp.setNconta(Integer.parseInt(nconta.getText()));
            cp.setAgencia(Integer.parseInt(agencia.getText()));

            if(Driver.VerificaContaCP(cp)){
                ContaPoupanca conta = Driver.SetContaCP(cp);
                Driver.SetDepositoCP(conta, Double.parseDouble(valor.getText()));
            }else  if(!Driver.VerificaContaCP(cp)){
                System.out.println("CP Deposito");
                mensagemAviso("conta não encontrada no sistema");
            }

        }
        limpar();
        buscaListaContas();
    }

    public void saque() throws SQLException {

        if (contacorrente.isExpanded() && verificaTextFields()) {
            ContaCorrente cc = new ContaCorrente();
            cc.setNconta(Integer.parseInt(nconta.getText()));
            cc.setAgencia(Integer.parseInt(agencia.getText()));
            if (Driver.VerificaContaCC(cc)) {
                ContaCorrente conta = Driver.SetContaCC(cc);
                if (conta.getSaldo() <= 0) {
                    mensagemAviso("saldo baixo");
                } else {
                    Driver.SetSaqueCC(conta, Double.parseDouble(valor.getText()));
                }
            }else if(!Driver.VerificaContaCC(cc)){
                System.out.println("CC saque");
                mensagemAviso("conta não encontrada no sistema");
            }
            }else if (contapoupanca.isExpanded() && verificaTextFields()) {
            ContaPoupanca cp = new ContaPoupanca();
            cp.setNconta(Integer.parseInt(nconta.getText()));
            cp.setAgencia(Integer.parseInt(agencia.getText()));
            if(Driver.VerificaContaCP(cp)){
                ContaPoupanca conta = Driver.SetContaCP(cp);
                if(conta.getSaldo() <= 0){
                    mensagemAviso("saldo baixo");
                }else {
                    Driver.SetSaqueCP(conta, Double.parseDouble(valor.getText()));
                }
            }else if(!Driver.VerificaContaCP(cp)){
                System.out.println("CP saque");
                mensagemAviso("conta não encontrada no sistema");
            }
        }
        limpar();
        buscaListaContas();
    }

    private void limpar() {
        gerente.setText("");
        agencia.setText("");
        nconta.setText("");
        titular.setText("");
        tarifa.setText("");
        vencimento.setText("");
        valor.setText("");
        contacorrente.setText("");
        contapoupanca.setText("");
    }

    public static void mensagemAviso(String typo) {
        if (typo == "campo nao prenchido") {
            Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
            dialogoAviso.setTitle("Aviso");
            dialogoAviso.setHeaderText("Revise os campos");
            dialogoAviso.setContentText("Algum dos campos não foi prenchido corretamente");
            dialogoAviso.showAndWait();

        } else if (typo == "conta corrente existe") {
            Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
            dialogoAviso.setTitle("Aviso");
            dialogoAviso.setHeaderText("Conta já cadastrada");
            dialogoAviso.setContentText("Conta corrente já cadastrada no sistema");
            dialogoAviso.showAndWait();
        } else if (typo == "conta poupanca existe") {
            Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
            dialogoAviso.setTitle("Aviso");
            dialogoAviso.setHeaderText("Conta já cadastrada");
            dialogoAviso.setContentText("Conta poupaça já cadastrada no sistema");
            dialogoAviso.showAndWait();
        } else if (typo == "saldo baixo") {
            Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
            dialogoAviso.setTitle("Aviso");
            dialogoAviso.setHeaderText("Saldo da conta abaixo de zero");
            dialogoAviso.setContentText("seu saldo está em zero ou negativo");
            dialogoAviso.showAndWait();
        }else if(typo == "conta não encontrada no sistema"){
            Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
            dialogoAviso.setTitle("Aviso");
            dialogoAviso.setHeaderText("Conta não encontrada no sistema");
            dialogoAviso.setContentText("Revise suas informações e tente novamente");
            dialogoAviso.showAndWait();
        }

    }

    private void buscaListaContas() throws SQLException {
        lcc.clear();
        lcp.clear();
        String url = "jdbc:mysql://localhost:3306/contas";
        String usuario = "root";
        String senha = "";

        Connection connection = DriverManager.getConnection(url, usuario, senha);
        Statement statement = connection.createStatement();
        String sql = "select * from conta;";
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            ContaCorrente conta = new ContaCorrente();
            conta.setAgencia(rs.getInt(2));
            conta.setNconta((rs.getInt(3)));
            conta.setTitular(rs.getString(4));
            conta.setSaldo(rs.getDouble(5));
            conta.setTarifa(rs.getDouble(6));
            conta.setGerente(rs.getString(7));
            lcc.add(conta);
        }

        while(rs.next()){
            ContaPoupanca conta = new ContaPoupanca();
            conta.setAgencia(rs.getInt(2));
            conta.setNconta((rs.getInt(3)));
            conta.setTitular(rs.getString(4));
            conta.setSaldo(rs.getDouble(5));
            conta.setVencimento(rs.getString(8));
            lcp.add(conta);
        }
        contas.setText(lcc.toString() + "\n" + lcp.toString());
    }

    private boolean verificaTextFields() throws SQLException {
        if (!agencia.getText().isEmpty() && !nconta.getText().isEmpty()) {
            buscaListaContas();
            return true;

        } else {
            mensagemAviso("campo nao prenchido");
            return false;
        }

    }
}