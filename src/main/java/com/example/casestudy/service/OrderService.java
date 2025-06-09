package com.example.casestudy.service;

import com.example.casestudy.model.Item;
import com.example.casestudy.model.Product;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderService {
    public void checkOut(List<Item> cart) {
        String queryOrder = "{CALL sp_insert_orders(?,?,?)}";
        String queryOrderDetail = "{CALL sp_insert_order_detail(?,?,?)}";
        int orderId = -1;
        Connection connection = null;
        try{
            connection =DBConnection.getConnection();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String orderDate = format.format(new Date());
            double totalPrice =0;
            for (Item item : cart) {
                Product product = item.getProduct();
                totalPrice += product.getPrice() * item.getQuantity();
            }
            Date _orderDate = format.parse(orderDate);
            CallableStatement callableStatementOrder = connection.prepareCall(queryOrder);
            callableStatementOrder.setDate(1, new java.sql.Date(_orderDate.getTime()));
            callableStatementOrder.setDouble(2, totalPrice);
            callableStatementOrder.registerOutParameter(3, Types.INTEGER);
            callableStatementOrder.executeUpdate();
            orderId = callableStatementOrder.getInt(3);

            for (Item item : cart) {
                Product product = item.getProduct();
                CallableStatement callableStatementOrderDetail = connection.prepareCall(queryOrderDetail);
                callableStatementOrderDetail.setInt(1, orderId);
                callableStatementOrderDetail.setInt(2, product.getId());
                callableStatementOrderDetail.setInt(3, item.getQuantity());
                callableStatementOrderDetail.executeUpdate();
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());;
        } catch (SQLException e) {
            DBConnection.printSQLException(e);
        }
    }
}
