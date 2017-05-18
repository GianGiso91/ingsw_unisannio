package unisannio.ingsoft.bbm.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "breweryApi",
        version = "v1",
        resource = "brewery",
        namespace = @ApiNamespace(
                ownerDomain = "backend.bbm.ingsoft.unisannio",
                ownerName = "backend.bbm.ingsoft.unisannio",
                packagePath = ""
        )
)
public class BreweryEndpoint {

    private static final Logger logger = Logger.getLogger(BreweryEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Brewery.class);
    }

    /**
     * Returns the {@link Brewery} with the corresponding ID.
     *
     * @param idbrewery the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Brewery} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "brewery/{idbrewery}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Brewery get(@Named("idbrewery") String idbrewery) throws NotFoundException {
        logger.info("Getting Brewery with ID: " + idbrewery);
        Brewery brewery = ofy().load().type(Brewery.class).id(idbrewery).now();
        if (brewery == null) {
            throw new NotFoundException("Could not find Brewery with ID: " + idbrewery);
        }
        return brewery;
    }

    /**
     * Inserts a new {@code Brewery}.
     */
    @ApiMethod(
            name = "insert",
            path = "brewery",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Brewery insert(Brewery brewery) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that brewery.idbrewery has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(brewery).now();
        logger.info("Created Brewery with ID: " + brewery.getIdbrewery());

        return ofy().load().entity(brewery).now();
    }

    /**
     * Updates an existing {@code Brewery}.
     *
     * @param idbrewery the ID of the entity to be updated
     * @param brewery   the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code idbrewery} does not correspond to an existing
     *                           {@code Brewery}
     */
    @ApiMethod(
            name = "update",
            path = "brewery/{idbrewery}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Brewery update(@Named("idbrewery") String idbrewery, Brewery brewery) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(idbrewery);
        ofy().save().entity(brewery).now();
        logger.info("Updated Brewery: " + brewery);
        return ofy().load().entity(brewery).now();
    }

    /**
     * Deletes the specified {@code Brewery}.
     *
     * @param idbrewery the ID of the entity to delete
     * @throws NotFoundException if the {@code idbrewery} does not correspond to an existing
     *                           {@code Brewery}
     */
    @ApiMethod(
            name = "remove",
            path = "brewery/{idbrewery}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("idbrewery") String idbrewery) throws NotFoundException {
        checkExists(idbrewery);
        ofy().delete().type(Brewery.class).id(idbrewery).now();
        logger.info("Deleted Brewery with ID: " + idbrewery);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "brewery",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Brewery> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Brewery> query = ofy().load().type(Brewery.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Brewery> queryIterator = query.iterator();
        List<Brewery> breweryList = new ArrayList<Brewery>(limit);
        while (queryIterator.hasNext()) {
            breweryList.add(queryIterator.next());
        }
        return CollectionResponse.<Brewery>builder().setItems(breweryList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String idbrewery) throws NotFoundException {
        try {
            ofy().load().type(Brewery.class).id(idbrewery).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Brewery with ID: " + idbrewery);
        }
    }
}