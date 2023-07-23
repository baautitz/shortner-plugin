package br.com.viniciusbautitz.shortner.service;

import br.com.viniciusbautitz.shortner.model.Link;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public interface LinkService {

    @Nullable
    List<Link> getAllLinks() throws Exception;

    @Nullable
    Link getLinkByName(String name) throws Exception;

    @Nullable
    Link createLink(Link link) throws Exception;

    Boolean deleteByName(String name) throws Exception;

}
