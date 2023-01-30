package ru.netology.travel.data;


import lombok.SneakyThrows;
//import lombok.var;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import ru.netology.travel.data.TablesSQL.CreditRequestEntity;
import ru.netology.travel.data.TablesSQL.OrderEntity;
import ru.netology.travel.data.TablesSQL.PaymentEntity;

import java.sql.Connection;
import java.sql.DriverManager;



public class DataHelperSQL {
    private DataHelperSQL(){}

    ////ИСПОЛНИТЕЛЬ ЗАПРОСОВ///////
    private static QueryRunner runner = new QueryRunner();


    //////УСТАНОВКА СОЕДИНЕНИЯ//////
   /* @SneakyThrows
    public static Connection getConnection(){
            var url = System.getProperty("db.url");
            var username = System.getProperty("db.user");
            var password = System.getProperty("db.password");
            try(
                    var conn = DriverManager.getConnection(url, username, password
                    );

    }*/
    @SneakyThrows
    public static Connection getConnection(){
        var url = System.getProperty("db.url");
        var username = System.getProperty("db.user");
        var password = System.getProperty("db.password");
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }



    /////ПОЛУЧЕНИЕ ПОСЛЕДНЕЙ ЗАПИСИ В OrderEntity///////////
    @SneakyThrows
    public static OrderEntity getOrderEntity() {
        var lastOrderEntitySQL = "SELECT * FROM order_entity\n" +
                "ORDER BY created DESC LIMIT 1;";
        try (
                var conn = getConnection();
        ) {
            var lastOrderEntity = runner.query(getConnection(), lastOrderEntitySQL,
                    new BeanListHandler<>(OrderEntity.class));
            return (OrderEntity) lastOrderEntity;
        }
    }

    /////ПОЛУЧЕНИЕ ПОСЛЕДНЕЙ ЗАПИСИ В PaymentEntity///////////
    @SneakyThrows
    public static PaymentEntity PaymentEntity() {
        var lastPaymentEntitySQL = "SELECT * FROM payment_entity;\n" +
                "ORDER BY created DESC LIMIT 1;";
        try (
                var conn = getConnection();
        ) {
            var lastPaymentEntity = runner.query(getConnection(), lastPaymentEntitySQL,
                    new BeanListHandler<>(PaymentEntity.class));
            return (PaymentEntity) lastPaymentEntity;
        }
    }


    /////ПОЛУЧЕНИЕ ПОСЛЕДНЕЙ ЗАПИСИ В OrderEntity///////////
    @SneakyThrows
    public static CreditRequestEntity OrderEntity() {
        var lastCreditEntitySQL = "SELECT * FROM credit_request_entity;\n" +
                "ORDER BY created DESC LIMIT 1;";
        try (
                var conn = getConnection();
        ) {
            var lastCreditEntity = runner.query(getConnection(), lastCreditEntitySQL,
                    new BeanListHandler<>(PaymentEntity.class));
            return (CreditRequestEntity) lastCreditEntity;
        }
    }






}