package com.ManageBookStore.ManageBookStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductTableChecker {

    private JdbcTemplate jdbcTemplate;

    public ProductTableChecker(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean doesTableExist() {
        DatabaseMetaData metaData;
        try {
            metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, "product", null);
            return tables.next();
        } catch (SQLException e) {
            // Handle exception appropriately
        }
        return false;
    }

    public static void main(String[] args) {
        // Assuming you have configured the data source
//        DataSource dataSource = new DataSourceConfig().dataSource();
//
//                ProductTableChecker checker = new ProductTableChecker(dataSource);
//        boolean tableExists = checker.doesTableExist();
//
//        if (tableExists) {
//            System.out.println("The 'product' table exists in the database.");
//        } else {
//            System.out.println("The 'product' table does not exist in the database.");
//        }
    }
}
