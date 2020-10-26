package com.ifmo.vbaydyuk.hw3.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletCommon.doGoodies(
                response,
                (Statement stmt) -> {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");
                    ServletCommon.dumpItems(response, rs, "All items that we have");
                });
    }
}
