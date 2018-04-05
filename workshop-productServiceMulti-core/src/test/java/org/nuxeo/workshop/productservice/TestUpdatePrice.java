package org.nuxeo.workshop.productservice;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.*;

@RunWith(FeaturesRunner.class)
@Features(AutomationFeature.class)
@RepositoryConfig(init = DefaultRepositoryInit.class, cleanup = Granularity.METHOD)
@Deploy("org.nuxeo.workshop.productservice.workshop-productservice-core")
@PartialDeploy(bundle = "studio.extensions.pcardoso-SANDBOX", extensions = { TargetExtensions.Automation.class })

public class TestUpdatePrice {

    protected DocumentModel src;

    @Inject
    protected CoreSession session;

    @Inject
    protected AutomationService automationService;

    @Test
    public void shouldCallTheOperation() throws OperationException {

        src = session.createDocumentModel("/", "prod1", "Product");
        src.setPropertyValue("hd_prod:price", 10.0);
        src = session.createDocument(src);
        session.save();

        OperationContext ctx = new OperationContext(session);
        ctx.setInput(src);

        DocumentModel doc = (DocumentModel) automationService.run(ctx, UpdatePrice.ID);
        assertEquals(doc.getPropertyValue("hd_prod:price"), 105.0);
    }

}
