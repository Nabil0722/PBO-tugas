package tugaspbo;

import java.sql.*;
import java.util.Scanner;

public class Tugaspbo {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1/penjualan";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Insert Data");
            System.out.println("2. Update Data");
            System.out.println("3. Delete Data");
            System.out.println("4. Show Data");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    insert();
                    break;
                case 2:
                    update();
                    break;
                case 3:
                    delete();
                    break;
                case 4:
                    show();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void insert() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter kode barang: ");
        String kode_brg = scanner.nextLine();
        System.out.print("Enter nama barang: ");
        String nama_brg = scanner.nextLine();
        System.out.print("Enter satuan: ");
        String satuan = scanner.nextLine();
        System.out.print("Enter stok: ");
        int stok = scanner.nextInt();
        System.out.print("Enter stok minimal: ");
        int stok_min = scanner.nextInt();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "INSERT INTO barang (kd_brg, nm_brg, satuan, stok_brg, stok_min) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, kode_brg);
            ps.setString(2, nama_brg);
            ps.setString(3, satuan);
            ps.setInt(4, stok);
            ps.setInt(5, stok_min);

            ps.execute();

            stmt.close();
            conn.close();
            System.out.println("Data inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter kode barang to update: ");
        String kode_brg = scanner.nextLine();
        System.out.print("Enter new nama barang: ");
        String nama_brg = scanner.nextLine();
        System.out.print("Enter new satuan: ");
        String satuan = scanner.nextLine();
        System.out.print("Enter new stok: ");
        int stok = scanner.nextInt();
        System.out.print("Enter new stok minimal: ");
        int stok_min = scanner.nextInt();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "UPDATE barang SET nm_brg = ?, satuan = ?, stok_brg = ?, stok_min = ? WHERE kd_brg = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nama_brg);
            ps.setString(2, satuan);
            ps.setInt(3, stok);
            ps.setInt(4, stok_min);
            ps.setString(5, kode_brg);

            ps.execute();

            stmt.close();
            conn.close();
            System.out.println("Data updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter kode barang to delete: ");
        String kode_brg = scanner.nextLine();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "DELETE FROM barang WHERE kd_brg = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, kode_brg);

            ps.execute();

            stmt.close();
            conn.close();
            System.out.println("Data deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void show() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT * FROM barang");
            int i = 1;
            while (rs.next()) {
                System.out.println("Data ke-" + i);
                System.out.println("Kode Barang: " + rs.getString("kd_brg"));
                System.out.println("Nama Barang: " + rs.getString("nm_brg"));
                System.out.println("Satuan: " + rs.getString("satuan"));
                System.out.println("Stok: " + rs.getInt("stok_brg"));
                System.out.println("Stok minimal: " + rs.getInt("stok_min"));
                i++;
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}