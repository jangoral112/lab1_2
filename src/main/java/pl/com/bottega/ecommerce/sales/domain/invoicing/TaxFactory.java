package pl.com.bottega.ecommerce.sales.domain.invoicing;

public interface TaxFactory {

    public Tax createTaxForItem(RequestItem requestItem);

}
