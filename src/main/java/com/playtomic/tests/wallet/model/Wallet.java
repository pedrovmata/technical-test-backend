package com.playtomic.tests.wallet.model;

import com.playtomic.tests.wallet.exception.WalletException;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    private long id;

    @NotNull
    private BigDecimal balance;


    public void updateBalance(@NotNull BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new WalletException("Error amount incorrect", 1, "Error updating balance");
        }
        this.balance = this.balance.add(amount);
    }
}
