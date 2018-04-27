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
import org.nuxeo.ecm.core.api.DocumentRef;

/**
 *
 */
public class ProductAdapter {
  protected final DocumentModel doc;

  //protected String titleXpath = "dc:title";
  //protected String descriptionXpath = "dc:description";
  protected String priceXpath = "hd_prod:price";
  protected String availableXpath = "hd_prod:available";
  protected String localDescriptionXpath = "hd_prod:description";
  protected String idProdXpath = "hd_prod:id_prod";
  protected String nameXpath = "hd_prod:name";
  protected String originFabXpath = "hd_prod:origin_fab";
  protected String sizeXpath = "hd_prod:size";


  public ProductAdapter(DocumentModel doc) {
    this.doc = doc;
  }

  // Basic methods
  //
  // Note that we voluntarily expose only a subset of the DocumentModel API in this adapter.
  // You may wish to complete it without exposing everything!
  // For instance to avoid letting people change the document state using your adapter,
  // because this would be handled through workflows / buttons / events in your application.
  //
  public void create() {
    CoreSession session = doc.getCoreSession();
    session.createDocument(doc);
  }

  public void save() {
    CoreSession session = doc.getCoreSession();
    //doc = session.saveDocument(doc);
    session.saveDocument(doc);
  }

  public DocumentRef getParentRef() {
    return doc.getParentRef();
  }

  // Technical properties retrieval
  public String getId() {
    return doc.getId();
  }

  public String getName() {
    return doc.getName();
  }

  public String getPath() {
    return doc.getPathAsString();
  }

  public String getState() {
    return doc.getCurrentLifeCycleState();
  }

  // Metadata get / set
  public String getTitle() {
    return doc.getTitle();
  }

  public Double getPrice() { return (double) doc.getPropertyValue(priceXpath);  }
  public void setPrice(double price) {     doc.setPropertyValue(priceXpath, price); }

  public boolean getAvailable() { return (boolean) doc.getPropertyValue(availableXpath);  }
  public void setAvailable(boolean available) {     doc.setPropertyValue(availableXpath, available); }

  public String getLocalDescription() { return (String) doc.getPropertyValue(localDescriptionXpath);  }
  public void setLocalDescription(String description) {     doc.setPropertyValue(localDescriptionXpath, description); }

  public int getIdProduct() { return (int) doc.getPropertyValue(idProdXpath);  }
  public void setIdProduct(int idProd) {     doc.setPropertyValue(idProdXpath, idProd); }

  public String getLocalName() { return (String) doc.getPropertyValue(nameXpath);  }
  public void setLocalName(String name) {     doc.setPropertyValue(nameXpath, name); }

  public String getOriginFab() { return (String) doc.getPropertyValue(originFabXpath);  }
  public void setOriginFab(String origin) {     doc.setPropertyValue(originFabXpath, origin); }

  public double getSize() { return (double) doc.getPropertyValue(sizeXpath);  }
  public void setSize(double size) {     doc.setPropertyValue(sizeXpath, size); }
}
