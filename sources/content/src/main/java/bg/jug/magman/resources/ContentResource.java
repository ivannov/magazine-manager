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
import bg.jug.magman.persistence.ArticleDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ContentResource {


    @Inject
    private ArticleDAO articleDAO;

    @POST
    public Response addArticle(String articleJson) {
        final Article article = Article.fromJson(articleJson);
        final Article created = articleDAO.addArticle(article);
        return Response.created(URI.create("/" + created.getId()))
                .entity(created.toJson())
                .build();
    }
}
