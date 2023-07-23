package br.com.viniciusbautitz.shortner.service;

import br.com.viniciusbautitz.shortner.model.Link;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class VniciusLinkService implements LinkService {

    private final String authorizationToken;
    private final OkHttpClient client;
    private final Gson gson;
    private final String baseUrl;

    public VniciusLinkService(String authorizationToken) {
        this.authorizationToken = authorizationToken;
        this.client = new OkHttpClient();
        this.gson = new Gson();
        this.baseUrl = "https://viniciusbautitz.com.br";
    }

    @Override
    public List<Link> getAllLinks() {
        Request request = new Request.Builder()
                .url(baseUrl + "/api/links/")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200) return null;
            if (response.body() == null) return null;

            String stringBody = response.body().string();
            Link[] links = gson.fromJson(stringBody, Link[].class);

            if (links == null) return null;

            return Arrays.asList(links);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Link getLinkByName(String name) {
        Request request = new Request.Builder()
                .url(baseUrl + "/api/links/" + name)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200) return null;
            if (response.body() == null) return null;

            String stringBody = response.body().string();

            return gson.fromJson(stringBody, Link.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Link createLink(Link link) {
        String linkJsonString = gson.toJson(link);

        Request request = new Request.Builder()
                .header("authorization", authorizationToken)
                .url(baseUrl + "/api/links/")
                .post(RequestBody.create(linkJsonString, MediaType.get("application/json; charset=utf-8")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 201 && response.body() != null) throw new RuntimeException("Não foi possível criar link: " + response.body().string());
            if (response.code() != 201) throw new RuntimeException("Não foi possível criar link");
            if (response.body() == null) return null;

            String stringBody = response.body().string();
            JsonObject jsonBody = gson.fromJson(stringBody, JsonObject.class);

            return gson.fromJson(jsonBody.get("content").getAsJsonObject(), Link.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean deleteByName(String name) {
        Request request = new Request.Builder()
                .header("authorization", authorizationToken)
                .url(baseUrl + "/api/links/" + name)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200 && response.body() != null) throw new RuntimeException("Não foi possível deletar link: " + response.body().string());
            if (response.code() != 200) throw new RuntimeException("Não foi deletar criar link");

            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
