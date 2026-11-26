package com.visiotech.minibancogradle;

import com.visiotech.minibancogradle.domain.exception.SaldoInsuficienteException;
import com.visiotech.minibancogradle.domain.model.Cuenta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CuentaTest {

    private Cuenta cuentaConSaldo(String saldo){
        return new Cuenta(UUID.randomUUID(), "Younes", new BigDecimal(saldo));
    }


    @Test
    void ingresarSumaAlSaldo(){
        Cuenta cuenta = cuentaConSaldo("100");

        cuenta.ingresar(new BigDecimal("50"));

        assertThat(cuenta.getSaldo()).isEqualByComparingTo("150");
    }


    @Test
    void retirarRestaDelSaldo(){
        Cuenta cuenta = cuentaConSaldo("100");

        cuenta.retirar(new BigDecimal("30"));

        assertThat(cuenta.getSaldo()).isEqualByComparingTo("70");
    }


    @Test
    void retirarMasDelSaldoLanzaExcepcionYNoCambiaElSaldo(){
        Cuenta cuenta= cuentaConSaldo("100");

        assertThatThrownBy(() -> cuenta.retirar(new BigDecimal("101")))
                .isInstanceOf(SaldoInsuficienteException.class);

        assertThat(cuenta.getSaldo()).isEqualByComparingTo("100");

    }


    @Test
    void importeCeroONegativoSeRechaza(){
        Cuenta cuenta = cuentaConSaldo("100");

        assertThatThrownBy(() -> cuenta.ingresar(BigDecimal.ZERO))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> cuenta.retirar(new BigDecimal("-5")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void noSePuedeCrearCuentaSinTitular() {
        assertThatThrownBy(() -> new Cuenta(UUID.randomUUID(), " ", BigDecimal.TEN))
                .isInstanceOf(IllegalArgumentException.class);
    }


}
