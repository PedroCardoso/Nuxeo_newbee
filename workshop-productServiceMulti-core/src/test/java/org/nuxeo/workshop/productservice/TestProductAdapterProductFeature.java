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

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.runtime.test.runner.*;
import org.nuxeo.workshop.productservice.ProductAdapter;

@RunWith(FeaturesRunner.class)
@Features(ProductServiceFeature.class)
//@Features(CoreFeature.class)
@Deploy({"org.nuxeo.workshop.productservice.workshop-productservice-core"})
@PartialDeploy(bundle = "studio.extensions.pcardoso-SANDBOX", extensions = { TargetExtensions.Automation.class })
public class TestProductAdapterProductFeature {
    @Inject
    CoreSession session;

    @Test
    public void shouldCallTheAdapter() {
        String doctype = "Product";
        String testTitle = "My Adapter Title";

        DocumentModel doc = session.createDocumentModel("/", "prod1Test", doctype);
        ProductAdapter adapter = doc.getAdapter(ProductAdapter.class);
        adapter.setLocalName("One name");
        adapter.create();
        // session.save() is only needed in the context of unit tests
        session.save();

        Assert.assertEquals("prod1Test", adapter.getTitle());
        Assert.assertNotNull("The adapter can't be used on the " + doctype + "Product", adapter);
        Assert.assertEquals("Document localName does not match when using the adapter", "One name", adapter.getLocalName());
    }
}
