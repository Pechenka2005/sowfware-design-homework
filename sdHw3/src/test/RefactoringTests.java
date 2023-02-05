package test;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.ru.airakozov.sd.refactoring.db.Db;
import java.ru.airakozov.sd.refactoring.db.DbException;
import java.ru.airakozov.sd.refactoring.servlet.GetProductsServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RefactoringTests {

    private static HttpServletResponse response;

    @Test
    public void getFromEmptyTable() throws IOException {
        Connection connection;
        HttpServletRequest request;
        Writer writer;
        try {
            writer = new StringWriter();
            when(response.getWriter()).thenReturn(new PrintWriter(writer));
            connection = DriverManager.getConnection(Db.getDbAddress());
            request = mock(HttpServletRequest.class);
            response = mock(HttpServletResponse.class);
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = connection.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
            when(response.getWriter()).thenReturn(new PrintWriter(writer));
            Db.clear("PRODUCT");
        } catch (SQLException | IOException | DbException e) {
            throw new RuntimeException(e.getMessage());
        }
        GetProductsServlet getServlet = new GetProductsServlet();
        getServlet.doGet(request, response);
        assertEquals(writer.toString(), "<html><body>\n</body></html>\n");
        try {
            Db.clear("PRODUCT");
            connection.close();
        } catch (SQLException | DbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
