package com.example.casestudy.service;

import com.example.casestudy.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductService implements IGeneralService<Product> {
    private static final String SELECT_PRODUCT_BY_ID = "select * from product where id = ?";
    private static final String UPDATE_PRODUCT_SQL = "update product set name = ?,price= ?, image= ? where id = ?;";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM product where id = ?;";

    @Override
    public List<Product> findAllWithStoreProcedure() {
        List<Product> products = new ArrayList<Product>();
        String query = "{CALL sp_get_products()}";
        try{
            Connection connection =DBConnection.getConnection();
            CallableStatement statement;
            statement = connection.prepareCall(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String image = resultSet.getString("image");
                Product product = new Product(id, name, price, image);
                products.add(product);
            }

        } catch (SQLException e) {
            DBConnection.printSQLException(e);
        }
        return products;
    }

    @Override
    public void saveWithStoreProcedure(Product entity) throws SQLException {
        String query = "{CALL sp_insert_product(?,?,?)}";
        try{
            Connection connection =DBConnection.getConnection();
            CallableStatement statement = connection.prepareCall(query);
            statement.setString(1, entity.getName());
            statement.setDouble(2, entity.getPrice());
            statement.setString(3, entity.getImage());
            statement.executeUpdate();
        } catch (SQLException e) {
            DBConnection.printSQLException(e);
        }
    }

    @Override
    public Product findById(int id) {
       Product product = null;
    try{
        Connection connection =DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int productId = resultSet.getInt("id");
            String productName = resultSet.getString("name");
            double productPrice = resultSet.getDouble("price");
            String productImage = resultSet.getString("image");
            product = new Product(productId, productName, productPrice, productImage);
        }
    } catch (SQLException e) {
        DBConnection.printSQLException(e);
    }
    return product;

    }

    @Override
    public boolean update(Product entity) throws SQLException {
        Boolean updated = false;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getPrice());
            preparedStatement.setString(3, entity.getImage());
            preparedStatement.setInt(4, entity.getId());
            updated = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            DBConnection.printSQLException(e);
        }
        return updated;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        boolean deleted = false;
        try{
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL);
            preparedStatement.setInt(1, id);
            deleted = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            DBConnection.printSQLException(e);
        }
        return deleted;
    }
}
