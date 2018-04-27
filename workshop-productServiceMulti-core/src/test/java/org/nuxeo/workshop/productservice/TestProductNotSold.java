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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.collections.api.CollectionManager;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventProducer;
import org.nuxeo.ecm.core.event.EventService;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;
import org.nuxeo.ecm.core.event.impl.EventListenerDescriptor;
import org.nuxeo.ecm.platform.test.PlatformFeature;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.runner.*;

import javax.inject.Inject;


@RunWith(FeaturesRunner.class)
@Features({ PlatformFeature.class })
@Deploy({"org.nuxeo.workshop.productservice.workshop-productservice-core"})
@PartialDeploy(bundle = "studio.extensions.pcardoso-SANDBOX", extensions = { TargetExtensions.Automation.class })

public class TestProductNotSold {

    protected final List<String> events = Arrays.asList("productnotsoldevent");

    @Inject
    protected EventService s;

    @Inject
    protected CoreSession session;

    //@Inject
    //protected CollectionManager collectionMng;


    @Test
    public void listenerRegistration() {
        EventListenerDescriptor listener = s.getEventListener("productnotsold");
        assertNotNull(listener);
        assertTrue(events.stream().allMatch(listener::acceptEvent));
    }

    @Test
    public void testEventNotSold() {
        EventProducer eventProducer = Framework.getService(EventProducer.class);

        DocumentModel parentFolder = session.createDocumentModel("/", "products", "Folder");
        parentFolder = session.createDocument(parentFolder);
        session.save();

        DocumentModel doc = session.createDocumentModel(parentFolder.getPathAsString(), "newProduct", "Product");
        doc = session.createDocument(doc);
        session.save();


        //DocumentModel vis1 = session.createDocumentModel("/", "prod1Vis1", "visual");
        //collectionMng.addToCollection(doc, vis1, session);

        DocumentEventContext ctx = new DocumentEventContext(session, session.getPrincipal(), doc);
        ctx.setProperty("myprop", "something");
        Event event = ctx.newEvent("productnotsoldevent");
        eventProducer.fireEvent(event);

        DocumentModel trashFolder = session.getChild(parentFolder.getRef(), "Trash");

        assertNotNull(trashFolder);
    }
}
