/*
 * Copyright 2016 Microprofile.io
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
 */
package bg.jug.magman.resources;

import bg.jug.magman.domain.Article;
import bg.jug.magman.domain.Comment;
import bg.jug.magman.persistence.ArticleDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/")
@Produces("application/json")
@RequestScoped
public class ContentResource {

    @Inject
    private ArticleDAO articleDAO;

    @GET
    public Response getArticles() {
        List<Article> articles = articleDAO.getAllArticles();
        JsonArray articlesArray = buildArticlesJson(articles);
        return Response.ok(articlesArray).build();
    }

    @GET
    @Path("/{id}")
    public Response findArticleById(@PathParam("id") final Long articleId) {
        return articleDAO.findArticleById(articleId)
                .map(article -> Response.ok(article ).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/{authorName}")
    public Response findArticlesByAuthor(@PathParam("authorName") String authorName) {
        List<Article> articles = articleDAO.findArticlesByAuthor(authorName);
        JsonArray articlesArray = buildArticlesJson(articles);
        return Response.ok(articlesArray).build();

    }

    private JsonArray buildArticlesJson(List<Article> articles) {
        JsonArrayBuilder articlesArrayBuilder = Json.createArrayBuilder();
        articles.forEach(article -> articlesArrayBuilder.add(article.toJson()));
        return articlesArrayBuilder.build();
    }

    @POST
    @Consumes("application/json")
    public Response addArticle(String articleJson) {
        final Article article = Article.fromJson(articleJson);
        final Article created = articleDAO.addArticle(article);
        return Response.created(URI.create("/" + created.getId()))
                .entity(created.toJson())
                .build();
    }

    @PUT
    @Consumes("application/json")
    public Response updateArticle(String articleJson) {
        articleDAO.updateArticle(Article.fromJson(articleJson));
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteArticle(@PathParam("id") Long articleId) {
        articleDAO.deleteArticle(articleId);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/comment")
    @Consumes("application/json")
    public Response addCommentToArticle(@PathParam("id") Long articleId, String commentJson) {
        Comment comment = Comment.fromJson(commentJson);
        Comment created = articleDAO.addCommentToArticle(comment, articleId);
        return Response.created(URI.create("/" + created.getId()))
                .entity(created.toJson())
                .build();
    }

}
