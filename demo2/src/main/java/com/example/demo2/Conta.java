package com.example.demo2;

public abstract class Conta {
    private int agencia;
    private int nconta;
    private String titular;
    private double saldo;

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public int getNconta() {
        return nconta;
    }

    public void setNconta(int nconta) {
        this.nconta = nconta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


    public void deposito(double vlr) {
        this.saldo = this.saldo + vlr;
    }

    public void saque(double vlr) {
        this.saldo = this.saldo - vlr;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return agencia == conta.agencia && nconta == conta.nconta;
    }


    @Override
    public String toString() {
        return "Conta{" +
                "agencia=" + agencia +
                ", nconta=" + nconta +
                ", titular=" + titular +
                ", saldo=" + saldo +
                   '}' + "\n";
    }
}
