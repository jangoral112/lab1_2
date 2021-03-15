/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.invoicing.Invoice.InvoiceBuilder;

public class BookKeeper {

    private final TaxFactory taxFactory;

    public BookKeeper(TaxFactory taxFactory) {
        this.taxFactory = taxFactory;
    }

    public Invoice issuance(InvoiceRequest invoiceRequest) {
        InvoiceBuilder invoiceBuilder = Invoice.builder()
                                               .setClientData(invoiceRequest.getClient())
                                               .setId(Id.generate());

        for (RequestItem item : invoiceRequest.getItems()) {
            Tax tax = taxFactory.createTaxForItem(item);
            InvoiceLine invoiceLine = new InvoiceLine(item.getProductData(), item.getQuantity(), item.getTotalCost(), tax);
            invoiceBuilder.addItem(invoiceLine);
        }

        return invoiceBuilder.build();
    }

}
