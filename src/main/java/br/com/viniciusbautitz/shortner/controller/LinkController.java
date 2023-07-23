package br.com.viniciusbautitz.shortner.controller;

import br.com.viniciusbautitz.shortner.model.Link;
import br.com.viniciusbautitz.shortner.service.LinkService;

import java.util.List;

public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    public List<Link> getAllLinks() throws Exception {
        return linkService.getAllLinks();
    }


    public Link getLinkByName(String name) throws Exception {
        return linkService.getLinkByName(name);
    }

    public Link createLink(Link link) throws Exception {
        return linkService.createLink(link);
    }

    public Boolean deleteByName(String name) throws Exception {
        return linkService.deleteByName(name);
    }

}
