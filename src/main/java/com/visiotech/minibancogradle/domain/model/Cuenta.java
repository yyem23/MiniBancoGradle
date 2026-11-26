package com.visiotech.minibancogradle.domain.model;

import com.visiotech.minibancogradle.domain.exception.SaldoInsuficienteException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Cuenta {

    private final UUID id;
    private final String titular;
    private BigDecimal saldo;


    public Cuenta(UUID id, String titular, BigDecimal saldoInicial){
        if(titular ==null || titular.isBlank()){
            throw new IllegalArgumentException("El titular es obligatorio");
        }
        if(saldoInicial==null || saldoInicial.signum() < 0){
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }

        this.id= Objects.requireNonNull(id);
        this.titular=titular;
        this.saldo=saldoInicial;
    }


    public void ingresar(BigDecimal importe){
        validarImporte(importe);
        saldo=saldo.add(importe);
    }


    public void retirar(BigDecimal importe){
        validarImporte(importe);
        if(saldo.compareTo(importe) < 0){
            throw new SaldoInsuficienteException("Saldo insuficiente en la cuenta");
        }

        saldo= saldo.subtract(importe);

    }


    public void validarImporte(BigDecimal importe){
        if(importe== null || importe.signum() <= 0){
            throw new IllegalArgumentException("El importe debe ser mayor que cero");
        }

    }

    public UUID getId() {return id;}
    public String getTitular() {return titular; }
    public BigDecimal getSaldo() {return saldo;}


    @Override
    public boolean equals(Object o){
        return o instanceof Cuenta otra && id.equals(otra.id);
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }

}
