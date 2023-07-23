package br.com.viniciusbautitz.shortner.service;

import br.com.viniciusbautitz.shortner.controller.LinkController;
import br.com.viniciusbautitz.shortner.model.Link;
import junit.framework.TestCase;

import java.util.List;

public class VniciusLinkServiceTest extends TestCase {

    LinkService linkService = new VniciusLinkService("21ec344eef804f9c9247695cbf2ba711");
    LinkController linkController = new LinkController(linkService);

    public void testGetAllLinks() {
        try {
            List<Link> links = linkController.getAllLinks();
            assertNotNull(links);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void testCreateLink() {
        try {
            Link newTestLink = new Link("teste", "teste.com", "Shortner Plugin", "Bautitz");
            Link createdLink = linkController.createLink(newTestLink);
            assertEquals("teste", createdLink.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void testGetLinkByName() {
        try {
            Link testLink = linkController.getLinkByName("teste");
            assertEquals("teste", testLink.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void testDeleteByName() {
        try {
            boolean deleteLink = linkController.deleteByName("teste");
            assertTrue(deleteLink);
            assertNull(linkController.getLinkByName("teste"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}