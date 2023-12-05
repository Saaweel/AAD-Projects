import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

public class App {
    private Connection conn;

    private ArrayList<Client> clients;

    private ArrayList<Game> games;

    private ArrayList<Buy> buys;

    public App(String ip, int port, String db, String user, String pass) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mariadb://" + ip + ":" + port + "/" + db, user, pass);

        clients = new ArrayList<>();
        games = new ArrayList<>();
        buys = new ArrayList<>();
    }

    public void loadDbData() throws SQLException {
        clients = new ArrayList<>();
        games = new ArrayList<>();
        buys = new ArrayList<>();

        Statement s = conn.createStatement();

        ResultSet result = s.executeQuery("SELECT * FROM clientes");

        while (result.next()) {
            clients.add(new Client(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5)));
        }

        result = s.executeQuery("SELECT * FROM videojuegos");

        while (result.next()) {
            games.add(new Game(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getFloat(5), result.getInt(6)));
        }

        result = s.executeQuery("SELECT * FROM compras");

        while (result.next()) {
            buys.add(new Buy(result.getInt(1), result.getInt(2), result.getInt(3), result.getDate(4), result.getInt(5)));
        }

        s.close();
    }

    public void pushToDb() throws SQLException {
        // Considero que hay que utilizar transacciones ya que si no se puede volcar algun dato mejor que no se actualice ninguno ya que puede dar problemas
        // Por elemplo en el caso de que se inserte una venta pero un cliente no se haya podido crear, la venta referenciaría a un cliente que no existe

        conn.setAutoCommit(false);

        try {
            for (Client client : clients) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO clientes VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE Nombre = ?, Apellido = ?, Dirección = ?, CorreoElectrónico = ?");

                ps.setInt(1, client.getId());
                ps.setString(2, client.getName());
                ps.setString(3, client.getLastname());
                ps.setString(4, client.getStreet());
                ps.setString(5, client.getEmail());

                ps.setString(6, client.getName());
                ps.setString(7, client.getLastname());
                ps.setString(8, client.getStreet());
                ps.setString(9, client.getEmail());

                ps.execute();

                ps.close();
            }

            for (Game game : games) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO videojuegos VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE Título = ?, Género = ?, Plataforma = ?, Precio = ?, Stock = ?");

                ps.setInt(1, game.getId());
                ps.setString(2, game.getTitle());
                ps.setString(3, game.getCategory());
                ps.setString(4, game.getPlatform());
                ps.setFloat(5, game.getPrice());
                ps.setInt(6, game.getStock());

                ps.setString(7, game.getTitle());
                ps.setString(8, game.getCategory());
                ps.setString(9, game.getPlatform());
                ps.setFloat(10, game.getPrice());
                ps.setInt(11, game.getStock());

                ps.execute();

                ps.close();
            }

            for (Buy buy : buys) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO compras VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE Fecha = ?, Cantidad = ?");

                ps.setInt(1, buy.getId());
                ps.setInt(2, buy.getClientId());
                ps.setInt(3, buy.getGameId());
                ps.setDate(4, (java.sql.Date) buy.getDate());
                ps.setInt(5, buy.getAmount());

                ps.setDate(6, (java.sql.Date) buy.getDate());
                ps.setInt(7, buy.getAmount());

                ps.execute();

                ps.close();
            }

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();

            conn.rollback();
        }

        conn.setAutoCommit(true);
    }

    public void checkClient(int id, String email) throws SQLException {
        Statement s = conn.createStatement();

        ResultSet result = s.executeQuery("SELECT * FROM clientes WHERE ID = " + id);

        if (result.next() && !result.getString("CorreoElectrónico").equals(email)) {
            s.executeQuery("UPDATE clientes SET CorreoElectrónico = '" + email + "' WHERE ID = " + id);
        }

        for (Client client : clients) {
            if (client.getId() == id) {
                client.setEmail(email);
                break;
            }
        }

        s.close();
    }

    public void showMiddelRows(String table) throws SQLException {
        Statement s = conn.createStatement();

        ResultSet result = s.executeQuery("SELECT * FROM " + table);

        result.last();

        int rows = result.getRow();

        result.absolute(rows / 2 - 1);

        int columns = result.getMetaData().getColumnCount();

        for (int i = 0; i < 3; i++) {
            if (!result.next()) {
                break;
            }

            for (int j= 1; j <= columns; j++) {
                System.out.print(result.getString(j) + " ");
            }

            System.out.println();
        }
    }

    public void safeDeleteClient(int id) throws SQLException {
        Statement s = conn.createStatement();

        // Considero que hay que utilizar transacciones ya que si hay algun error al actualizar la tabla de compras y no se actualiza, no podemos dejar que se borre el cliente

        conn.setAutoCommit(false);

        try {
            s.execute("UPDATE compras SET ID_Cliente = null WHERE ID_Cliente = " + id);

            s.execute("DELETE FROM clientes WHERE ID = " + id);

            conn.commit();
        } catch (Exception e) {
            conn.rollback();

            e.printStackTrace();
        }

        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == id) {
                clients.remove(i);
                break;
            }
        }

        conn.setAutoCommit(true);

        s.close();
    }

    public void applyDiscounts() throws SQLException {
        // Método que aplique un descuento del 15% a los juegos que no se hayan vendido en el mes anterior al actual. También añadirá el texto “(OCASIÓN)” al título.

        Statement s = conn.createStatement();

        ResultSet result = s.executeQuery("SELECT * FROM videojuegos");

        while (result.next()) {
            int id = result.getInt("ID");

            ResultSet result2 = s.executeQuery("SELECT * FROM compras WHERE ID_Videojuego = " + id + " AND Fecha > DATE_SUB(NOW(), INTERVAL 1 MONTH)");

            if (!result2.next()) {
                s.execute("UPDATE videojuegos SET Título = CONCAT(Título, ' (OCASIÓN)'), Precio = Precio * 0.85 WHERE ID = " + id);
            
                for (Game game : games) {
                    if (game.getId() == id) {
                        game.setTitle(game.getTitle() + " (OCASIÓN)");
                        game.setPrice(game.getPrice() * 0.85f);
                        break;
                    }
                }
            }
        }
    }


    public void generateReport() throws SQLException {
        Statement s = conn.createStatement();

        ResultSet result = s.executeQuery("SELECT compras.Fecha, compras.Cantidad, videojuegos.Precio AS 'PrecioUnidad' FROM compras INNER JOIN videojuegos ON compras.ID_Videojuego = videojuegos.id WHERE Fecha > DATE_SUB(NOW(), INTERVAL 1 MONTH) ORDER BY Fecha ASC");

        HashMap<Integer, ArrayList<Float>> soldList = new HashMap<>();

        int month = 0;
        int year = 0;

        int totalOfGames = 0;
        float totalPrice = 0;

        while (result.next()) {
            if (month == 0) {
                month = result.getDate("Fecha").toLocalDate().getMonthValue();
                year = result.getDate("Fecha").toLocalDate().getYear();
            }

            int day = result.getDate("Fecha").toLocalDate().getDayOfMonth();
            
            for (int i = 0; i < result.getInt("Cantidad"); i++) {
                if (!soldList.containsKey(day)) {
                    ArrayList<Float> solds = new ArrayList<>();

                    totalOfGames += result.getInt("Cantidad");
                    totalPrice += result.getInt("Cantidad") * result.getFloat("PrecioUnidad");

                    solds.add(result.getInt("Cantidad") * result.getFloat("PrecioUnidad"));

                    soldList.put(day, solds);
                } else {
                    soldList.get(day).add(result.getInt("Cantidad") * result.getFloat("PrecioUnidad"));
                }
            }
        }

        result.last();
        int totalBuys = result.getRow();

        if (month != 0) {
            StringBuilder finalString = new StringBuilder();

            finalString.append("****************************************\n");
            finalString.append("INFORME MENSUAL DEL MES " + month + " DEL AÑO " + year + "\n");
            finalString.append("****************************************\n");
            finalString.append("Nº de Compras: " + totalBuys + "\n");
            finalString.append("Productos vendidos: " + totalOfGames + "\n");
            finalString.append("Precio total: " + totalPrice + "€\n");
            finalString.append("****************************************\n");
            finalString.append("DESGLOSE POR DÍAS\n");
            finalString.append("****************************************\n");

            soldList.forEach((k,v) -> {
                int dayTotalOfGames = 0;
                float dayTotalPrice = 0;

                for (Float p : v) {
                    dayTotalOfGames++;
                    dayTotalPrice += p;
                }

                finalString.append("Día " + k + ":\n");
                finalString.append("Nº de Compras: " + v.size() + "\n");
                finalString.append("Productos vendidos: " + dayTotalOfGames + "\n");
                finalString.append("Precio total: " + dayTotalPrice + "€\n");
                finalString.append("****************************************\n");
            });

            System.out.println(finalString.toString());
        } else {
            System.out.println("El pasado mes no hubo ventas");
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App("localhost", 3306, "t3examen", "root", "");

        // app.loadDbData();

        // app.pushToDb();

        // app.checkClient(101, "juan.perez@hotmail.com");

        // app.showMiddelRows("clientes");

        // app.safeDeleteClient(101);

        // app.applyDiscounts();

        app.generateReport();
    }
}
