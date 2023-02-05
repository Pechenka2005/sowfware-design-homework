package java.ru.airakozov.sd.refactoring.servlet;


import java.ru.airakozov.sd.refactoring.db.Db;
import java.ru.airakozov.sd.refactoring.db.DbException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.ru.airakozov.sd.refactoring.utils.HTMLUtils;
import java.util.List;

public class ClearProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            Db.clear("PRODUCT");
        } catch (DbException e) {
            System.err.println(e.getMessage());
        }

        HTMLUtils.writeHTMLDocument(
                response.getWriter(),
                List.of("Products list cleared"),
                ""
        );

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}