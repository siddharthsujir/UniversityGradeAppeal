package com.cse564hateoas.appealing.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.cse564hateoas.appealing.activities.RemoveAppealActivity;
import com.cse564hateoas.appealing.activities.CreateAppealActivity;
import com.cse564hateoas.appealing.activities.InvalidAppealException;
import com.cse564hateoas.appealing.activities.NoSuchAppealException;
import com.cse564hateoas.appealing.activities.AppealDeletionException;
import com.cse564hateoas.appealing.activities.ReadAppealActivity;
import com.cse564hateoas.appealing.activities.UpdateException;
import com.cse564hateoas.appealing.activities.UpdateAppealActivity;
import com.cse564hateoas.appealing.representations.AppealRepresentation;
import com.cse564hateoas.appealing.representations.AppealUri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/appeal")
public class AppealResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealResource.class);

    private @Context UriInfo uriInfo;

    public AppealResource() {
        LOG.info("AppealResource constructor");
    }

    /**
     * Used in test cases only to allow the injection of a mock UriInfo.
     * 
     * @param uriInfo
     */
    public AppealResource(UriInfo uriInfo) {
        LOG.info("AppealResource constructor with mock uriInfo {}", uriInfo);
        this.uriInfo = uriInfo;  
    }
    
    @GET
    @Path("/{appealId}")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response getAppeal() {
        LOG.info("Retrieving an Appeal Resource");
        
        Response response;
        
        try {
            LOG.info("Inside GET Method");
            AppealRepresentation responseRepresentation = new ReadAppealActivity().retrieveByUri(new AppealUri(uriInfo.getRequestUri()));
           LOG.info("2nd");
            response = Response.ok().entity(responseRepresentation).build();
        } catch(NoSuchAppealException nsoe) {
            LOG.debug("No such appeal");
            System.out.println("Helle nsoe");
            response = Response.status(Status.NOT_FOUND).build();
        } catch (Exception ex) {
            LOG.debug("Something went wrong retriveing the Appeal");
            System.out.println("Helle ex");
            response = Response.serverError().build();
        }
        
        LOG.debug("Retrieved the appeal resource", response);
        
        return response;
    }
   /* 
    @PUT
    @Path("/{appealId}")
    @Consumes("application/vnd.cse564-appeals+xml")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response updateAppeal(String appealRepresentation) {
        LOG.info("updating an appeal Resource");
        
        Response response;
        
        try {
            System.out.println("Hello");
            System.out.println("Inside Appeal Resource Update Appeal method "+AppealRepresentation.fromXmlString(appealRepresentation).getAppeal().getStatus());
            AppealRepresentation responseRepresentation = new UpdateAppealActivity().update(AppealRepresentation.fromXmlString(appealRepresentation).getAppeal(), new AppealUri(uriInfo.getRequestUri()));
            response = Response.ok().entity(responseRepresentation).build();
        } catch (InvalidAppealException ioe) {
            LOG.debug("Invalid Appeal - Problem with the appealrepresentation {}", appealRepresentation);
            response = Response.status(Status.BAD_REQUEST).build();
        } catch (Exception ex) {
            LOG.debug("Someting went wrong updating the appeal resource");
            response = Response.serverError().build();
        }
        
        LOG.debug("Resulting response for creating the appeal resource is {}", response);
        
        return response;
    }
    */
    
    @POST
    @Consumes("application/vnd.cse564-appeals+xml")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response createAppeal(AppealRepresentation appealRepresentation) {
        LOG.info("Creating an Appeal Resource");
        
        Response response;
        
        try {
            System.out.println("Inside appeal Resout "+appealRepresentation);
            AppealRepresentation responseRepresentation = new CreateAppealActivity().create(appealRepresentation.getAppeal(), new AppealUri(uriInfo.getRequestUri()));//create(AppealRepresentation.fromXmlString(appealRepresentation).getAppeal(), new AppealUri(uriInfo.getRequestUri()));
            response = Response.created(responseRepresentation.getUpdateLink().getUri()).entity(responseRepresentation).build();
        } catch (InvalidAppealException ioe) {
            LOG.debug("Invalid Appeal - Problem with the orderrepresentation {}", appealRepresentation);
            response = Response.status(Status.BAD_REQUEST).build();
        } catch (Exception ex) {
            LOG.debug("Someting went wrong creating the appeal resource");
            response = Response.serverError().build();
        }
        
        LOG.debug("Resulting response for creating the appeal resource is {}", response);
        
        return response;
    }
    
    

    @DELETE
    @Path("/{appealId}")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response removeAppeal() {
        LOG.info("Removing an Appeal Reource");
        
        Response response;
        
        try {
            AppealRepresentation removedAppeal = new RemoveAppealActivity().delete(new AppealUri(uriInfo.getRequestUri()));
            response = Response.ok().entity(removedAppeal).build();
        } catch (NoSuchAppealException nsoe) {
            LOG.debug("No such appeal resource to delete");
            response = Response.status(Status.NOT_FOUND).build();
        } catch(AppealDeletionException ode) {
            LOG.debug("Problem deleting appeal resource");
            response = Response.status(405).header("Allow", "GET").build();
        } catch (Exception ex) {
            LOG.debug("Something went wrong deleting the appeal resource");
            response = Response.serverError().build();
        }
        
        LOG.debug("Resulting response for deleting the appeal resource is {}", response);
        
        return response;
    }

    @POST
    @Path("/{appealId}")
    @Consumes("application/vnd.cse564-appeals+xml")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response updateAppeal(String appealRepresentation) {
        LOG.info("Updating an Appeal Resource");
        
        Response response;
        
        try {
            AppealRepresentation responseRepresentation = new UpdateAppealActivity().update(AppealRepresentation.fromXmlString(appealRepresentation).getAppeal(), new AppealUri(uriInfo.getRequestUri()));
            response = Response.ok().entity(responseRepresentation).build();
        } catch (InvalidAppealException ioe) {
            LOG.debug("Invalid appeal in the XML representation {}", appealRepresentation);
            response = Response.status(Status.BAD_REQUEST).build();
        } catch (NoSuchAppealException nsoe) {
            LOG.debug("No such appeal resource to update");
            response = Response.status(Status.NOT_FOUND).build();
        } catch(UpdateException ue) {
            LOG.debug("Problem updating the appeal resource");
            response = Response.status(Status.CONFLICT).build();
        } catch (Exception ex) {
            LOG.debug("Something went wrong updating the appeal resource");
            response = Response.serverError().build();
        } 
        
        LOG.debug("Resulting response for updating the appeal resource is {}", response);
        
        return response;
     }
}
