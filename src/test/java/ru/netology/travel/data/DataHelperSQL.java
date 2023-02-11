package ru.netology.travel.data;


import lombok.SneakyThrows;
//import lombok.var;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import ru.netology.travel.data.TablesSQL.CreditRequestEntity;
import ru.netology.travel.data.TablesSQL.OrderEntity;
import ru.netology.travel.data.TablesSQL.PaymentEntity;

import java.sql.Connection;
import java.sql.DriverManager;


public class DataHelperSQL {
    private DataHelperSQL() {
    }

    private static QueryRunner runner = new QueryRunner();

    @SneakyThrows
    public static Connection getConnection() {
        var url = System.getProperty("db.url");
        var username = System.getProperty("db.user");
        var password = System.getProperty("db.password");
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    @SneakyThrows
    public static OrderEntity getOrderEntityLast() {
        var lastOrderEntitySQL = "SELECT * FROM order_entity ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            var lastOrderEntity = runner.query(conn, lastOrderEntitySQL,
                    new BeanHandler<>(OrderEntity.class));
            return (OrderEntity) lastOrderEntity;
        }
    }

    @SneakyThrows
    public static PaymentEntity getPaymentEntityLast() {
        var lastPaymentEntitySQL = "SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            var lastPaymentEntity = runner.query(conn, lastPaymentEntitySQL,
                    new BeanHandler<>(PaymentEntity.class));
            return lastPaymentEntity;
        }
    }

    @SneakyThrows
    public static CreditRequestEntity getCreditRequestEntityLast() {
        var lastCreditEntitySQL = "SELECT * FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            var lastCreditEntity = runner.query(conn, lastCreditEntitySQL,
                    new BeanHandler<>(CreditRequestEntity.class));
            return lastCreditEntity;
        }
    }


}