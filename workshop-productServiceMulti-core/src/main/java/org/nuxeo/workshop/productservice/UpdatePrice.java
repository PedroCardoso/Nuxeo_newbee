/*
 * (C) Copyright 2018 Nuxeo (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Pedro Cardoso
 */
package org.nuxeo.workshop.productservice;

import org.apache.commons.lang3.StringUtils;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.runtime.api.Framework;

/**
 *
 */
@Operation(id=UpdatePrice.ID, category=Constants.CAT_DOCUMENT, label="updatePrice", description="Describe here what your operation does.")
public class UpdatePrice {

    public static final String ID = "Document.UpdatePrice";

    @Context
    protected CoreSession session;

    @Param(name = "update multiplier", required = false)
    protected Double multiplier;

    @OperationMethod
    public DocumentModel run(DocumentModel input) throws NuxeoException {
        /*if (!(CONTRACT_TYPE.equals(input.getType()))) {
            throw new NuxeoException("Operation works only with "
                    + CONTRACT_TYPE + " document type.");
        }*/

        ProductService service = Framework.getService(ProductService.class);

        double price = service.calculatePrice(input);


        //Double price = (Double) input.getPropertyValue("hd_prod:price");

        input.setPropertyValue("hd_prod:price", price );

        return input;
    }

    @OperationMethod
    public DocumentModelList run(DocumentModelList docs) {
        docs.forEach(input -> {
            run(input);
        });
        return docs;

    }
}
