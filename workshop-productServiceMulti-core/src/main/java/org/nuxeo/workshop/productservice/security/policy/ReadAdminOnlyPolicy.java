package org.nuxeo.workshop.productservice.security.policy;

import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.core.api.security.ACP;
import org.nuxeo.ecm.core.api.security.Access;
import org.nuxeo.ecm.core.model.Document;
import org.nuxeo.ecm.core.security.AbstractSecurityPolicy;
import org.nuxeo.ecm.core.security.SecurityPolicy;

import java.security.Principal;

public class ReadAdminOnlyPolicy extends AbstractSecurityPolicy implements SecurityPolicy {

    @Override
    public Access checkPermission(Document doc, ACP mergedAcp,
                                  Principal principal, String permission,
                                  String[] resolvedPermissions, String[] additionalPrincipals) {
        if (!(principal instanceof NuxeoPrincipal)) {
            return Access.DENY;
        }

        // Note that doc is NOT a DocumentModel
        if (doc.getType().getName().equals("File")) {
            return Access.DENY;
        }

        NuxeoPrincipal nuxeoPrincipal = (NuxeoPrincipal) principal;
        if (nuxeoPrincipal.getAllGroups().contains("Administrators")) {
            return Access.GRANT;
        }

        return Access.UNKNOWN;
    }
}
