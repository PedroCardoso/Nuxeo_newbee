package org.nuxeo.workshop.productservice;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.adapter.DocumentAdapterFactory;

public class ProductAdapterFactory implements DocumentAdapterFactory {

    @Override
    public Object getAdapter(DocumentModel doc, Class<?> itf) {
        if ("Product".equals(doc.getType())){
            return new ProductAdapter(doc);
        }else{
            return null;
        }
    }
}
