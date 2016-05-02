package com.cse564hateoas.appealing.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.cse564hateoas.appealing.activities.InvalidEmailException;
import com.cse564hateoas.appealing.activities.NoSuchAppealException;
import com.cse564hateoas.appealing.activities.EmailActivity;
import com.cse564hateoas.appealing.activities.UpdateException;
import com.cse564hateoas.appealing.model.Identifier;
import com.cse564hateoas.appealing.representations.Link;
import com.cse564hateoas.appealing.representations.EmailRepresentation;
import com.cse564hateoas.appealing.representations.Representation;
import com.cse564hateoas.appealing.representations.AppealUri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/email/{emailId}")
public class EmailResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(EmailResource.class);
    
    private @Context UriInfo uriInfo;
    
    public EmailResource(){
        LOG.info("Email Resource Constructor");
    }
    
    /**
     * Used in test cases only to allow the injection of a mock UriInfo.
     * @param uriInfo
     */
    public EmailResource(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @PUT
    @Consumes("application/vnd.cse564-appeals+xml")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response send(EmailRepresentation emailRepresentation) {
        LOG.info("Creating a new email");
        
        Response response;
        
        try {
            response = Response.created(uriInfo.getRequestUri()).entity(new EmailActivity().pay(emailRepresentation.getEmail(), 
                            new AppealUri(uriInfo.getRequestUri()))).build();
        } catch(NoSuchAppealException nsoe) {
            LOG.debug("No appeal for email {}", emailRepresentation);
            response = Response.status(Status.NOT_FOUND).build();
        } catch(UpdateException ue) {
            LOG.debug("Invalid update to email {}", emailRepresentation);
            Identifier identifier = new AppealUri(uriInfo.getRequestUri()).getId();
            Link link = new Link(Representation.SELF_REL_VALUE, new AppealUri(uriInfo.getBaseUri().toString() + "appeal/" + identifier));
            response = Response.status(Status.FORBIDDEN).entity(link).build();
        } catch(InvalidEmailException ipe) {
            LOG.debug("Invalid email for appeal");
            response = Response.status(Status.BAD_REQUEST).build();
        } catch(Exception e) {
            LOG.debug("Someting when wrong with processing email");
            response = Response.serverError().build();
        }
        
        LOG.debug("Created the new email activity {}", response);
        
        return response;
    }
}
