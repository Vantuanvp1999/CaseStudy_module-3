package com.example.casestudy.controller;

import com.example.casestudy.model.Item;
import com.example.casestudy.model.Product;
import com.example.casestudy.service.OrderService;
import com.example.casestudy.service.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "ShoppingCartServlet", urlPatterns = "/carts")
public class ShoppingCartServlet extends HttpServlet {
    private ProductService productService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        orderService = new OrderService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "add":
                showCart(request, response);
                break;
            case "checkout":
                checkout(request, response);
                break;
            case "delete":
                deleteCartItem(request, response);
                break;
            case "update":
                updateCart(request, response);
                break;

            default:
                listProduct(request, response);
        }
    }

    private void showCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        if (session.getAttribute("cart") == null) {
            List<Item> cart = new ArrayList<>();
            Product product = productService.findById(id);
            cart.add(new Item(1, product));
            session.setAttribute("cart", cart);
        } else {
            List<Item> cart = (List<Item>) session.getAttribute("cart");
            int index = getIndex(id, session);
            if (index == -1) {
                cart.add(new Item(1, productService.findById(id)));
            } else {
                int quantity = cart.get(index).getQuantity() + 1;
                cart.get(index).setQuantity(quantity);
            }
            session.setAttribute("cart", cart);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cart/list.jsp");
        dispatcher.forward(request, response);
    }


    private int getIndex(int id, HttpSession session) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        for (int i = 0; i < cart.size(); i++) {
            Product product = cart.get(i).getProduct();
            if (product.getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        orderService.checkOut(cart);
        session.removeAttribute("cart");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cart/list.jsp");
        dispatcher.forward(request, response);
    }

    private void listProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        session.setAttribute("cart", cart);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cart/list.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        int id = Integer.parseInt(request.getParameter("id"));
        Iterator<Item> iterator = cart.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getProduct().getId() == id) {
                iterator.remove();
            }
        }
        session.setAttribute("cart", cart);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cart/list.jsp");
        dispatcher.forward(request, response);
    }
    private void updateCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        for (Item item : cart) {
            if (item.getProduct().getId() == id) {
                item.setQuantity(quantity);
                break;
            }
        }
        session.setAttribute("cart", cart);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cart/list.jsp");
        dispatcher.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            HttpSession session = request.getSession();
            List<Item> cart = (List<Item>) session.getAttribute("cart");

            for (Item item : cart) {
                if (item.getProduct().getId() == id) {
                    item.setQuantity(quantity);
                    break;
                }
            }

            session.setAttribute("cart", cart);
            response.sendRedirect("/carts"); // hoặc cart.jsp tùy bạn
        }
    }

}



