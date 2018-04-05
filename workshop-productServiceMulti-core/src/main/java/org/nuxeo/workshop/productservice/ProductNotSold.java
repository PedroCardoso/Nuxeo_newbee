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
