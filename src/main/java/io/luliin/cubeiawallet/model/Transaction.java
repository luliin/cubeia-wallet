package io.luliin.cubeiawallet.model;

import io.luliin.cubeiawallet.response.TransactionDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Julia Wigenstedt
 * Date: 2024-09-30
 */
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "performed_by", nullable = false)
    private User performedBy;


    @PrePersist
    protected void onTransaction() {
        this.transactionDate = LocalDateTime.now();
    }

    public Transaction() {
    }

    public Transaction( Account account, BigDecimal amount, User performedBy) {
        this.account = account;
        this.amount = amount;
        this.performedBy = performedBy;
    }

    public TransactionDTO toDTO() {
        return new TransactionDTO(this.transactionId,
                this.account.toDTO(),
                this.amount,
                transactionDate);
    }
}
