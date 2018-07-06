package hr.fer.zemris.java.hw17.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class offers methods for Gallery web app which provide 
 * app with specific data in JSON format.
 * @author Josip Trbuscic
 *
 */
@Path("/galerija")
public class Gallery {
	
	@Context
	private ServletContext context;
	/**
	 * Path to the file containing names, descriptions and tags of all pictures
	 */
	private static final String DESCRIPTOR_PATH = "/WEB-INF/opisnik.txt";
	
	/**
	 * Returns tags of all pictures 
	 * @return collection of tags in JSON format
	 */
	@Path("/getTags")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTags() {
		
		Set<String> tagsSet = new TreeSet<>();
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(context.getRealPath(DESCRIPTOR_PATH)));
			
		} catch (IOException ignorable) {
		}
		
		for(int i = 2; i < lines.size(); i+=3) {
			String[] tags = lines.get(i).split(",");
			for(String tag : tags) {
				tagsSet.add(tag.trim());
			}
			
		}
		
		JSONObject tagsJson = new JSONObject();
		tagsJson.put("tags", tagsSet);
		
		return Response.status(Status.OK).entity(tagsJson.toString()).build();
	}
	
	/**
	 * Returns list of names of pictures which are tagged 
	 * with specified tag
	 * @param requestedTag - tag used to filter pictures
	 * @return  list of names of pictures in JSON format
	 */
	@Path("/thumbnailList/{requestedTag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPictureList(@PathParam("requestedTag") String requestedTag) {
		
		List<String> pictures = new ArrayList<>();
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(context.getRealPath(DESCRIPTOR_PATH)));
		} catch (IOException ignorable) {
		}
		
		for(int i = 2; i < lines.size(); i+=3) {
			String[] tags = lines.get(i).split(",");
			
			for(String tag : tags) {
				if(tag.trim().equals(requestedTag)) {
					pictures.add(lines.get(i-2));
				}
			}
		}
		
		JSONObject picturesJson = new JSONObject();
		picturesJson.put("thumbs", pictures);
		
		return Response.status(Status.OK).entity(picturesJson.toString()).build();
	}
	
	/**
	 * Returns tags and description of specific picture
	 * @param requestedName - name of the picture
	 * @return tags and description of specific picture in JSON format
	 */
	@Path("/pictureInfo/{requestedName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPictureInfo(@PathParam("requestedName") String requestedName) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(context.getRealPath(DESCRIPTOR_PATH)));
		} catch (IOException ignorable) {
		}
		
		JSONObject result = new JSONObject();
		for(int i = 0; i < lines.size(); i+=3) {
			String name = lines.get(i);
			
			if(name.equals(requestedName)) {
				result.put("description", lines.get(i+1));
				JSONArray tagsJSON = new JSONArray();
				
				String[] tags = lines.get(i+2).split(",");
				
				for(String tag : tags) {
					tagsJSON.put(tag.trim());
				}
				
				result.put("tags", tagsJSON);
				
			}
		}
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}
	
}
