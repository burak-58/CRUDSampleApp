package io.antmedia.rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import io.antmedia.db.CRUDMongoStore;
import io.antmedia.db.types.CRUDUser;
import io.antmedia.plugin.CRUDMain;
import io.antmedia.rest.model.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Component
@Path("/v1/sample")
public class CRUDService {

	@Context
	protected ServletContext servletContext;
	
	@GET
	@Path("/user/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CRUDUser get(@PathParam("name") String name) 
	{
		CRUDMongoStore store = getCRUDDatastore();
		
		return store.getUser(name);
	}
	
	
	@POST
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Result create(CRUDUser user) 
	{
		CRUDMongoStore store = getCRUDDatastore();
		boolean success = store.saveUser(user);
		
		return new Result(success, success?"it is stored":"it isn't stored");
	}
	
	@PUT
	@Path("/user/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Result create(@PathParam("name") String name, CRUDUser user) 
	{
		CRUDMongoStore store = getCRUDDatastore();
		boolean success = store.updateUser(name, user);
		
		return new Result(success, success?"it is updated":"it isn't updated");
	}

	@DELETE
	@Path("/user/{name}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Result deleteUser(@PathParam("name") String name) {
		CRUDMongoStore store = getCRUDDatastore();
		boolean success = store.deleteUser(name);
		
		return new Result(success, success?"it is delete":"it isn't deleted");		
	}

	
	public CRUDMongoStore getCRUDDatastore() {
		ApplicationContext appCtx = (ApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		CRUDMain crud = (CRUDMain) appCtx.getBean(CRUDMain.BEAN_NAME);
		return crud.getStore();
	}
}
