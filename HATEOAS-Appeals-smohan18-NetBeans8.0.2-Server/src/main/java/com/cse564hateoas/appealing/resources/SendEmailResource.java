package com.cse564hateoas.appealing.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.cse564hateoas.appealing.activities.CompleteAppealActivity;
import com.cse564hateoas.appealing.activities.NoSuchAppealException;
import com.cse564hateoas.appealing.activities.AppealAlreadyEvaluatedException;
import com.cse564hateoas.appealing.activities.AppealNotSentException;
import com.cse564hateoas.appealing.activities.ReadEmailActivity;
import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.representations.AppealRepresentation;
import com.cse564hateoas.appealing.representations.SendMailRepresentation;
import com.cse564hateoas.appealing.representations.AppealUri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/receipt")
public class SendEmailResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(SendEmailResource.class);

    private @Context
    UriInfo uriInfo;

    public SendEmailResource() {
        LOG.info("Receipt Resource constructor");
    }

    /**
     * Used in test cases only to allow the injection of a mock UriInfo.
     * 
     * @param uriInfo
     */
    public SendEmailResource(UriInfo uriInfo) {
        this.uriInfo = uriInfo;

    }

    @GET
    @Path("/{appealId}")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response getReceipt() {
        LOG.info("Retrieving a  Receipt Resource");
        
        Response response;
        
        try {
            SendMailRepresentation responseRepresentation = new ReadEmailActivity().read(new AppealUri(uriInfo.getRequestUri()));
            response = Response.ok().entity(responseRepresentation).build();
        } catch (AppealAlreadyEvaluatedException oce) {
            LOG.debug("Order already completed");
            response = Response.status(Status.NO_CONTENT).build();
        } catch (AppealNotSentException onpe) {
            LOG.debug("Order not paid");
            response = Response.status(Status.NOT_FOUND).build();
        } catch (NoSuchAppealException nsoe) {
            LOG.debug("No such appeal");
            response = Response.status(Status.NOT_FOUND).build();
        }
        
        LOG.debug("The responce for the retrieve receipt request is {}", response);
        
        return response;
    }
    
    @DELETE
    @Path("/{appealId}")
    public Response completeAppeal(@PathParam("appealId")String identifier) {
        LOG.info("Retrieving a  Receipt Resource");
        
        Response response;
        
        try {
            AppealRepresentation finalizedOrderRepresentation = new CompleteAppealActivity().completeAppeal(new Identifier(identifier));
            response = Response.ok().entity(finalizedOrderRepresentation).build();
        } catch (AppealAlreadyEvaluatedException oce) {
            LOG.debug("Appeal already completed");
            response = Response.status(Status.NO_CONTENT).build();
        } catch (NoSuchAppealException nsoe) {
            LOG.debug("No such appeal");
            response = Response.status(Status.NOT_FOUND).build();
        } catch (AppealNotSentException onpe) {
            LOG.debug("Appeal not paid ");
            response = Response.status(Status.CONFLICT).build();
        }
        
        LOG.debug("The response for the delete receipt request is {}", response);
        
        return response;
    }
}
