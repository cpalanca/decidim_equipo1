package api.services;


import java.util.ArrayList;
import java.text.ParseException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import api.Utils;
import api.domain.Comment;

@Path("/comment")
@Consumes(value= MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class CommentService {
	
	@GET
	public Response findAllComments() throws SQLException, ClassNotFoundException, ParseException {
		List<Comment> comments = new ArrayList<>();
		Connection conn = Utils.connectDB();
		
		try {
            Statement miStatement = conn.createStatement();
            String sql = "Select * from comments order by created_at desc";
            ResultSet miResultSet = miStatement.executeQuery(sql);
            
            while(miResultSet.next()) {
            	Comment obj = new Comment();
            	obj.setId(miResultSet.getInt("id"));
            	obj.setBody(miResultSet.getString("body"));
            	obj.setId_authors(miResultSet.getInt("id_authors"));
            	obj.setId_comments(miResultSet.getString("id_comments"));
            	obj.setDown_votes(miResultSet.getInt("down_votes"));
            	obj.setUp_votes(miResultSet.getInt("up_votes"));
            	obj.setReply_to(miResultSet.getString("reply_to"));
            	obj.setReply_level(miResultSet.getInt("reply_level"));
            	obj.setCreated_at(miResultSet.getString("created_at"));
            	comments.add(obj);
            }
            
            
        } catch (SQLException e) {
            return Response.status(Status.BAD_REQUEST).entity(false).build();
        }
		
		return Response.status(Status.OK).entity(comments).build();
	}
	
	@GET
	@Path("{commentId}")
	public Response findComment( @PathParam("commentId") int commentId) throws ClassNotFoundException, ParseException {
		Connection conn = Utils.connectDB();
		Comment obj = new Comment();
		try {
            Statement miStatement = conn.createStatement();
            String sql = "Select * from comments where id="+commentId;
            ResultSet miResultSet = miStatement.executeQuery(sql);
            
            while(miResultSet.next()) {
            	obj.setId(miResultSet.getInt("id"));
            	obj.setBody(miResultSet.getString("body"));
            	obj.setId_authors(miResultSet.getInt("id_authors"));
            	obj.setId_comments(miResultSet.getString("id_comments"));
            	obj.setCreated_at(miResultSet.getString("created_at"));
            	obj.setDown_votes(miResultSet.getInt("down_votes"));
            	obj.setUp_votes(miResultSet.getInt("up_votes"));
            	obj.setReply_to(miResultSet.getString("reply_to"));
            	obj.setReply_level(miResultSet.getInt("reply_level"));
            	
            }

            
        } catch (SQLException e) {
            return Response.status(Status.BAD_REQUEST).entity(false).build();
        }

		return Response.status(Status.OK).entity(obj).build();
	}
	
	@DELETE
	@Path("{commentId}")
	public Response deleteComment( @PathParam("commentId") int commentId) throws ClassNotFoundException {
		Connection conn = Utils.connectDB();
		int rs;
		try {
            Statement miStatement = conn.createStatement();
            String sql = "Delete from comments where id="+commentId;
            System.out.println(sql);
            rs=miStatement.executeUpdate(sql);
            miStatement.close();
            conn.close();
        } catch (Exception e) {
        	return Response.status(Status.BAD_REQUEST).entity(false).build();
        }
		if(rs==0)
			return Response.status(Status.BAD_REQUEST).entity(false).build();
		return Response.status(Status.OK).entity(true).build();
	}
	
	@POST
	public Response createAuthor(Comment objRequest) throws ClassNotFoundException{
		Connection conn = Utils.connectDB();
			    
		    try {
		    	String sql = "INSERT INTO comments(id, id_authors, body, id_comments, created_at, down_votes, up_votes, reply_to, reply_level) VALUES (?,?,?,?,?,?,?,?,?)";
	    		PreparedStatement ps= conn.prepareStatement(sql);
	    		ps.setInt(1, objRequest.getId());
	    		ps.setInt(2, objRequest.getId_authors());
	    		ps.setString(3, objRequest.getBody());
	    		ps.setString(4, objRequest.getId_comments());
	    		ps.setString(5, objRequest.getCreated_at());
	    		ps.setInt(6, objRequest.getDown_votes());
	    		ps.setInt(7, objRequest.getUp_votes());
	    		ps.setString(8, objRequest.getReply_to());
	    		ps.setInt(9, objRequest.getReply_level());  
	    		ps.executeUpdate();
	    		ps.close();
	    		conn.close();
		    } catch (SQLException e) {
		    	return Response.status(Status.BAD_REQUEST).entity(false).build();
		    }
	    return Response.status(Status.OK).entity(true).build();
		
	}


	@PUT
	@Path("{commentId}")
	public Response updateComment(@PathParam("commentId") int commentId, Comment objRequest) throws ClassNotFoundException {
		Connection conn = Utils.connectDB();
			    
		    try {
		    	String sql = "UPDATE comments SET id_authors = ?, body = ?, id_comments = ?, created_at = ?, down_votes = ?, up_votes = ?, reply_to = ?, reply_level = ? WHERE id = ?";
	    		PreparedStatement ps= conn.prepareStatement(sql);
	    		ps.setInt(1, objRequest.getId_authors());
	    		ps.setString(2, objRequest.getBody());
	    		ps.setString(3, objRequest.getId_comments());
	    		ps.setString(4, objRequest.getCreated_at());
	    		ps.setInt(5, objRequest.getDown_votes());
	    		ps.setInt(6, objRequest.getUp_votes());
	    		ps.setString(7, objRequest.getReply_to());
	    		ps.setInt(8, objRequest.getReply_level());  
	    		ps.setInt(9, commentId);
	    		ps.executeUpdate();
	    		ps.close();
	    		conn.close();
		    } catch (SQLException e) {
		    	return Response.status(Status.BAD_REQUEST).entity(false).build();
		    }
	    return Response.status(Status.OK).entity(true).build();
	}
	
	
	@HEAD
	public Response pingCommentsService() {
		return Response.noContent().header("running", true).build();
	}

}
