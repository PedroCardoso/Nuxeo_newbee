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


import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;

import org.nuxeo.ecm.core.event.impl.DocumentEventContext;

public class ProductNotSold implements EventListener {

    private static final String TRASH_NAME = "Trash";

    @Override
    public void handleEvent(Event event) {
        EventContext ctx = event.getContext();
        if (!(ctx instanceof DocumentEventContext)) {
          return;
        }

        DocumentEventContext docCtx = (DocumentEventContext) ctx;
        DocumentModel doc = docCtx.getSourceDocument();
        if (doc == null) {
            return;
        }
        // Add some logic starting from here.
        CoreSession coreSession = event.getContext().getCoreSession();
        DocumentModel docFolder = coreSession.getDocument(doc.getParentRef());

        DocumentModel trash;

        if (!coreSession.hasChild(docFolder.getRef(), TRASH_NAME)) {
            trash = coreSession.createDocumentModel(docFolder.getPathAsString(), TRASH_NAME, "Folder");
            trash = coreSession.createDocument(trash);

            trash = coreSession.saveDocument(trash);
        }
        else {
            trash = coreSession.getChild(docFolder.getRef(), TRASH_NAME);
        }
        coreSession.move(doc.getRef(), trash.getRef(), doc.getName());
        coreSession.save();


    }
}
