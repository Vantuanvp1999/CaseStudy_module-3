package com.example.casestudy.controller;

import com.example.casestudy.model.Product;
import com.example.casestudy.service.DBConnection;
import com.example.casestudy.service.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;

    public ProductServlet() {
    }

    @Override
    public void init() {
        productService = new ProductService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                insertProduct(req, resp);
                break;
            case "edit":
                updateProduct(req, resp);
                break;

        }
    }

    private void insertProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uploads = "D:\\Module 3\\CaseStudy\\src\\main\\webapp\\resources\\uploads\\";
        File fileSaveDir = new File(uploads);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        String fileName = null;
        //Get all the parts from request and write it to the file on server

        for (Part part : req.getParts()) {
            fileName = getFileName(part);
            if (!fileName.isEmpty()) {
                part.write(uploads + fileName);
                break;
            }
        }
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        Product product = new Product(name, price, fileName);
        try {
            productService.saveWithStoreProcedure(product);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product/create.jsp");
            dispatcher.forward(req, resp);
        } catch (SQLException e) {
            DBConnection.printSQLException(e);
        }
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return "";
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uploads = "D:\\Module 3\\CaseStudy\\src\\main\\webapp\\resources\\uploads\\";
        File fileSaveDir = new File(uploads);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        String fileName = null;
        //Get all the parts from request and write it to the file on server

        for (Part part : req.getParts()) {
            fileName = getFileName(part);
            if (!fileName.isEmpty()) {
                part.write(uploads + fileName);
                break;
            }
        }
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        Product product = new Product(id, name, price, fileName);
        try {
            productService.update(product);
        } catch (SQLException e) {
            DBConnection.printSQLException(e);
        }
        resp.sendRedirect("/products");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showNewForm(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteProduct(req, resp);
                break;
            default:
                listProducts(req, resp);
                break;
        }
    }
    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            productService.delete(id);
            List<Product> listProduct = productService.findAllWithStoreProcedure();
            req.setAttribute("listProduct", listProduct);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product/list.jsp");
            dispatcher.forward(req, resp);
        } catch (SQLException e) {
            DBConnection.printSQLException(e);
        }

    }
    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product/create.jsp");
        dispatcher.forward(req, resp);
    }
    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.findById(id);
        req.setAttribute("product", product);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product/edit.jsp");
        dispatcher.forward(req, resp);
    }
    private void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> listProduct = productService.findAllWithStoreProcedure();
        req.setAttribute("listProduct", listProduct);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product/list.jsp");
        dispatcher.forward(req, resp);
    }
}
