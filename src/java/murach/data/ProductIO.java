package murach.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import murach.business.Product;

public class ProductIO {
  
    public static void addRecord(Product product, String filename) throws IOException {
        File file = new File(filename);
        PrintWriter out = new PrintWriter(
                new FileWriter(file, true));
        out.println(product.getCode() + "|"
          + product.getDescription() + "|"
          + product.getPrice());
        out.close();
    }

  public static Product getProduct(String code, String filepath) {
    try {
      File file = new File(filepath);
      BufferedReader in
        = new BufferedReader(
          new FileReader(file));

      String line = in.readLine();
      while (line != null) {
        StringTokenizer t = new StringTokenizer(line, "|");
        String productCode = t.nextToken();
        if (code.equalsIgnoreCase(productCode)) {
          String description = t.nextToken();
          double price = Double.parseDouble(t.nextToken());
          Product p = new Product();
          p.setCode(code);
          p.setDescription(description);
          p.setPrice(price);
          in.close();
          return p;
        }
        line = in.readLine();
      }
      in.close();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static ArrayList<Product> getProducts(String filepath) {
    ArrayList<Product> products = new ArrayList<Product>();
    File file = new File(filepath);
    try {
      BufferedReader in
        = new BufferedReader(
          new FileReader(file));

      String line = in.readLine();
      while (line != null) {
        StringTokenizer t = new StringTokenizer(line, "|");
        String code = t.nextToken();
        String description = t.nextToken();
        String priceAsString = t.nextToken();
        double price = Double.parseDouble(priceAsString);
        Product p = new Product();
        p.setCode(code);
        p.setDescription(description);
        p.setPrice(price);
        products.add(p);
        line = in.readLine();
      }
      in.close();
      return products;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
