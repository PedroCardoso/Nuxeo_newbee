package org.nuxeo.workshop.productservice;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;

public interface ProductService {
    /** Add some methods here. **/
    public double calculatePrice(DocumentModel product);
}
