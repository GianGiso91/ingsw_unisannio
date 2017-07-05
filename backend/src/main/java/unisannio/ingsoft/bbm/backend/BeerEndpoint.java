package unisannio.ingsoft.bbm.backend;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.cmd.QueryKeys;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.inject.Named;

@Api(
    name = "beerApi",
    version = "v1",
    resource = "beer",
    namespace = @ApiNamespace(
      ownerDomain = "backend.bbm.ingsoft.unisannio",
      ownerName = "backend.bbm.ingsoft.unisannio",
      packagePath = ""
    )
)

public class BeerEndpoint {

  private static final Logger LOGGER = Logger.getLogger(BeerEndpoint.class.getName());
  private static final int DEFAULT_LIST_LIMIT = 50;

  static {
    ObjectifyService.register(Beer.class);
  }

  /**
   * Returns the {@link Beer} with the corresponding ID.
   *
   * @param idbeer the ID of the entity to be retrieved
   * @return the entity with the corresponding ID
   * @throws NotFoundException if there is no {@code Beer} with the provided ID.
   */
  @ApiMethod(
      name = "get",
      path = "beer/{idbeer}",
      httpMethod = ApiMethod.HttpMethod.GET
  )
  public Beer get(@Named("idbeer") String idbeer) throws NotFoundException {
    if (LOGGER.isLoggable(Level.INFO)) {
      LOGGER.info("Getting Beer with ID: " + idbeer);
    }
    Beer beer = ofy().load().type(Beer.class).id(idbeer).now();
    if (beer == null) {
      throw new NotFoundException("Could not find Beer with ID: " + idbeer);
    }
    return beer;
  }

  /**
   * Inserts a new {@code Beer}.
   */
  @ApiMethod(
      name = "insert",
      path = "beer",
      httpMethod = ApiMethod.HttpMethod.POST
  )
  public Beer insert(Beer beer) {
    ofy().save().entity(beer).now();
    if (LOGGER.isLoggable(Level.INFO)) {
      LOGGER.info("Created Beer with ID: " + beer.getIdbeer());
    }
    return ofy().load().entity(beer).now();
  }

  /**
   * Updates an existing {@code Beer}.
   *
   * @param idbeer the ID of the entity to be updated
   * @param beer   the desired state of the entity
   * @return the updated version of the entity
   * @throws NotFoundException if the {@code idbeer} does not correspond to an existing {@code Beer}
   */
  @ApiMethod(
      name = "update",
      path = "beer/{idbeer}",
      httpMethod = ApiMethod.HttpMethod.PUT
  )
  public Beer update(@Named("idbeer") String idbeer, Beer beer) throws NotFoundException {
    checkExists(idbeer);
    ofy().save().entity(beer).now();
    if (LOGGER.isLoggable(Level.INFO)) {
      LOGGER.info("Updated Beer: " + beer);
    }
    return ofy().load().entity(beer).now();
  }

  /**
   * Deletes the specified {@code Beer}.
   *
   * @param idbeer the ID of the entity to delete
   * @throws NotFoundException if the {@code idbeer} does not correspond to an existing {@code Beer}
   */
  @ApiMethod(
      name = "remove",
      path = "beer/{idbeer}",
      httpMethod = ApiMethod.HttpMethod.DELETE
  )
  public void remove(@Named("idbeer") String idbeer) throws NotFoundException {
    checkExists(idbeer);
    ofy().delete().type(Beer.class).id(idbeer).now();
    if (LOGGER.isLoggable(Level.INFO)) {
      LOGGER.info("Deleted Beer with ID: " + idbeer);
    }
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
      path = "beer",
      httpMethod = ApiMethod.HttpMethod.GET
  )
  public CollectionResponse<Beer> list(@Nullable @Named("cursor") String cursor,
                                       @Nullable @Named("limit") Integer limit) { //NOPMD
    limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
    Query<Beer> query = ofy().load().type(Beer.class).limit(limit);
    if (cursor != null) {
      query = query.startAt(Cursor.fromWebSafeString(cursor));
    }
    QueryResultIterator<Beer> queryIterator = query.iterator();
    List<Beer> beerList = new ArrayList<Beer>(limit);
    while (queryIterator.hasNext()) {
      beerList.add(queryIterator.next());
    }
    return CollectionResponse.<Beer>builder().setItems(beerList)
      .setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
  }


  /**
   * List all entities' ids.
   *
   * @param cursor used for pagination to determine which page to return
   * @param limit  the maximum number of entries to return
   * @return a response that encapsulates the result list and the next page token/cursor
   */
  @ApiMethod(
      name = "listId",
      path = "beerids",
      httpMethod = ApiMethod.HttpMethod.GET
  )
  public CollectionResponse<String> listId(@Nullable @Named("cursor") String cursor,
                                           @Nullable @Named("limit") Integer limit) { //NOPMD
    limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
    QueryKeys<Beer> query = ofy().load().type(Beer.class).limit(limit).keys();
    QueryResultIterator<Key<Beer>> queryIterator = query.iterator();
    List<String> beerIdList = new ArrayList<String>(limit);
    while (queryIterator.hasNext()) {
      beerIdList.add(queryIterator.next().getName());
    }
    return CollectionResponse.<String>builder().setItems(beerIdList)
      .setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
  }

  private void checkExists(String idbeer) throws NotFoundException {
    try {
      ofy().load().type(Beer.class).id(idbeer).safe();
    } catch (com.googlecode.objectify.NotFoundException e) {
      throw new NotFoundException("Could not find Beer with ID: " + idbeer); //NOPMD
    }
  }
}