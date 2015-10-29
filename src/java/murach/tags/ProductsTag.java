package murach.tags;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import murach.business.Product;

public class ProductsTag extends BodyTagSupport {

    private Product product;
    private ArrayList<Product> products;
    int count = 0;

    private static final NumberFormat currency = NumberFormat.getCurrencyInstance();

    @Override
    public int doStartTag() {
        products = (ArrayList<Product>) pageContext.findAttribute("products");
        if (products.size() <= 0) {
            return SKIP_BODY;
        } else {
            return EVAL_BODY_BUFFERED;
        }
    }

    @Override
    public void doInitBody() {
        product = (Product) products.get(count);
        pageContext.setAttribute("code", product.getCode());
        pageContext.setAttribute("description", product.getDescription());
        double price = product.getPrice();
        String priceAsString = currency.format(price);
        pageContext.setAttribute("price", priceAsString);
        count++;
    }

    @Override
    public int doAfterBody() throws JspException {
        try {
            if (count < products.size()) {
                product = (Product) products.get(count);
                pageContext.setAttribute("code", product.getCode());
                pageContext.setAttribute("description", product.getDescription());
                pageContext.setAttribute("price", currency.format(product.getPrice()));
                count++;
                return EVAL_BODY_AGAIN;
            } else {
                JspWriter out = bodyContent.getEnclosingWriter();
                bodyContent.writeOut(out);
                return SKIP_BODY;
            }
        } catch (IOException ioe) {
            System.err.println("error in doAfterBody " + ioe.getMessage());
            return SKIP_BODY;
        }
    }
}
