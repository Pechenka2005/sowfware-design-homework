package java.ru.airakozov.sd.refactoring.servlet;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.ru.airakozov.sd.refactoring.db.Db;
import java.ru.airakozov.sd.refactoring.db.DbException;
import java.ru.airakozov.sd.refactoring.model.Product;
import java.ru.airakozov.sd.refactoring.utils.HTMLUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products;
        try {
            products = Db.selectAllProducts("PRODUCT");
        } catch (DbException e) {
            System.err.println(e.getMessage());
            return;
        }

        HTMLUtils.writeHTMLDocument(
                response.getWriter(),
                List.of(),
                products.stream()
                        .map(product -> product.toString() + "</br>")
                        .collect(Collectors.joining("\n"))
        );

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}