package dto;

import lombok.AllArgsConstructor;
import models.MyRuntimeException;
import models.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class ProductJdbcRepository implements ProductRepository
{
    private final String url;
    private final String user;
    private final String password;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                url,
                user,
                password);
    }

    @Override
    public void insertByOne(List<Product> productList) {
        long sum = 0;
        String query = "insert into product (id, name, type, price) values (?, ?, ?, ?)";

        try (Connection connection = getConnection()) {
            PreparedStatement productStatement = connection.prepareStatement(query);

            for (Product product : productList) {
                productStatement.setInt(1, product.getId());
                productStatement.setString(2, product.getName());
                productStatement.setString(3, product.getType());
                productStatement.setDouble(4, product.getPrice());
                long start = System.currentTimeMillis();
                productStatement.executeUpdate();
                long end = System.currentTimeMillis();
                sum += (end - start);
            }
            productStatement.close();
            System.out.println("inserted all in " + sum + " milliseconds");

        } catch (SQLException e) {
            throw new MyRuntimeException(e.getSQLState(), e.getMessage());
        }
    }

    @Override
    public void insertByBatches(List<List<Product>> productBatchesList) {
        long sum = 0;
        String query = "insert into product (id, name, type, price) values (?, ?, ?, ?)";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement productStatement = connection.prepareStatement(query);

            for (List<Product> batch : productBatchesList) {
                for (Product product : batch) {
                    productStatement.setInt(1, product.getId());
                    productStatement.setString(2, product.getName());
                    productStatement.setString(3, product.getType());
                    productStatement.setDouble(4, product.getPrice());
                    productStatement.addBatch();
                }
                long start = System.currentTimeMillis();
                productStatement.executeBatch();
                connection.commit();
                long end = System.currentTimeMillis();
                sum += (end - start);

            }

            productStatement.close();
            System.out.println("Inserted all in " + sum + " milliseconds");

        }
        catch (SQLException e) {
            throw new MyRuntimeException(e.getSQLState(), e.getMessage());
        }
    }
}
