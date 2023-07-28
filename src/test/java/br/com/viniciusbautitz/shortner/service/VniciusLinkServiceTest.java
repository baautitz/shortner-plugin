package br.com.viniciusbautitz.shortner.service;

import br.com.viniciusbautitz.shortner.controller.LinkController;
import br.com.viniciusbautitz.shortner.model.Link;
import junit.framework.TestCase;

import java.util.List;

public class VniciusLinkServiceTest extends TestCase {

    LinkService linkService = new VniciusLinkService("21ec344eef804f9c9247695cbf2ba711");
    LinkController linkController = new LinkController(linkService);

    public void testGetAllLinks() {
        List<Link> links = null;
        try {
            links = linkController.getAllLinks();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertNotNull(links);
    }

    public void testCreateLink() {
        Link newTestLink;
        Link createdLink = null;
        try {
            newTestLink = new Link("teste", "teste.com", "Shortner Plugin", "Bautitz");
            createdLink = linkController.createLink(newTestLink);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertNotNull(createdLink);
        assertEquals("teste", createdLink.getName());
    }

    public void testGetLinkByName() {
        Link testLink = null;
        try {
            testLink = linkController.getLinkByName("teste");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertNotNull(testLink);
        assertEquals("teste", testLink.getName());
    }


    public void testDeleteByName() {
        boolean deleteLink = false;
        Link link = null;
        try {
            deleteLink = linkController.deleteByName("teste");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        try {
            link = linkController.getLinkByName("teste");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        assertTrue(deleteLink);
        assertNull(link);
    }
}