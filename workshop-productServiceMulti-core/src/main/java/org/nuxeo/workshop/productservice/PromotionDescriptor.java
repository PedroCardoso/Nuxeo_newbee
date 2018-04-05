package org.nuxeo.workshop.productservice;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XNodeList;
import org.nuxeo.common.xmap.annotation.XObject;

@XObject("promotion")
public class PromotionDescriptor {

    @XNode("@id")
    public String promotionsId;

    @XNode("discount")
    public double discount;

    @XNodeList(value = "product@id", type = String[].class, componentType = String.class)
    public String[]productClass;

}
