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
import api.domain.Proposal;

@Path("/proposal")
@Consumes(value= MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class ProposalService {
	
	@GET
	public Response findAllProposals() throws SQLException, ClassNotFoundException, ParseException {
		List<Proposal> proposals = new ArrayList<>();
		Connection conn = Utils.connectDB();
		
		try {
            Statement miStatement = conn.createStatement();
            String sql = "Select * from proposal";
            ResultSet miResultSet = miStatement.executeQuery(sql);

            while(miResultSet.next()) {
            	Proposal obj = new Proposal();
            	obj.setId(miResultSet.getInt("id"));
            	obj.setTitle(miResultSet.getString("title"));
            	obj.setId_authors(miResultSet.getInt("id_authors"));
            	obj.setId_comments(miResultSet.getString("id_comments"));
            	obj.setN_endorsement(miResultSet.getInt("n_endorsement"));
            	obj.setCreated_at(miResultSet.getString("created_at"));
            	obj.setPublished_at(miResultSet.getString("published_at"));
            	proposals.add(obj);
            }
            
            
        } catch (SQLException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getStackTrace()).build();
        }
		
		return Response.status(Status.OK).entity(proposals).build();
	}
	
	@GET
	@Path("{proposalId}")
	public Response findProposal( @PathParam("proposalId") int proposalId) throws ClassNotFoundException, ParseException {
		Connection conn = Utils.connectDB();
		Proposal obj = new Proposal();
		try {
            Statement miStatement = conn.createStatement();
            String sql = "Select * from proposal where id="+proposalId;
            ResultSet miResultSet = miStatement.executeQuery(sql);
            
            while(miResultSet.next()) {
            	obj.setId(miResultSet.getInt("id"));
            	obj.setTitle(miResultSet.getString("title"));
            	obj.setId_authors(miResultSet.getInt("id_authors"));
            	obj.setId_comments(miResultSet.getString("id_comments"));
            	obj.setN_endorsement(miResultSet.getInt("n_endorsement"));
            	obj.setCreated_at(miResultSet.getString("created_at"));
            	obj.setPublished_at(miResultSet.getString("published_at"));
            	
            }

            
        } catch (SQLException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

		return Response.status(Status.OK).entity(obj).build();
	}
	
	@DELETE
	@Path("{proposalId}")
	public Response deleteProposal( @PathParam("proposalId") int proposalId) throws ClassNotFoundException {
		Connection conn = Utils.connectDB();
		int rs;
		try {
            Statement miStatement = conn.createStatement();
            String sql = "Delete from proposal where id="+proposalId;
            System.out.println(sql);
            rs=miStatement.executeUpdate(sql);
            miStatement.close();
            conn.close();
        } catch (Exception e) {
        	return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
		if(rs==0)
			return Response.status(Status.OK).entity("La propuesta no existe").build();
		return Response.status(Status.OK).entity("Proposal eliminada").build();
	}
	
	@POST
	public Response createProposal(Proposal objRequest) throws ClassNotFoundException{
		Connection conn = Utils.connectDB();
			    
		    try {
		    	String sql = "INSERT INTO proposal(id, title, id_authors, id_comments, n_endorsement, created_at, published_at) VALUES(?,?,?,?,?,?,?)";
	    		PreparedStatement ps= conn.prepareStatement(sql);
	    		ps.setInt(1, objRequest.getId());
	    		ps.setString(2, objRequest.getTitle());
	    		ps.setInt(3, objRequest.getId_authors());
	    		ps.setString(4, objRequest.getId_comments());
	    		ps.setInt(5, objRequest.getN_endorsement());
	    		ps.setString(6, objRequest.getCreated_at());
	    		ps.setString(7, objRequest.getPublished_at());  
	    		ps.executeUpdate();
	    		ps.close();
	    		conn.close();
		    } catch (SQLException e) {
		    	return Response.status(Status.BAD_REQUEST).entity("Compruebe los datos").build();
		    }
	    return Response.status(Status.OK).entity("Commentario guardado correctamente").build();
		
	}
	
	@PUT
	@Path("{proposalId}")
	public Response updateComment(@PathParam("proposalId") int proposalId, Proposal objRequest) throws ClassNotFoundException {
		Connection conn = Utils.connectDB();
		System.out.println("p1");	    
		    try {
		    	String sql = "UPDATE proposal SET title = ?, id_authors = ?, id_comments = ?, n_endorsement = ?, created_at = ?, published_at = ? WHERE id = ?";
	    		PreparedStatement ps= conn.prepareStatement(sql);
	    		System.out.println("p2");	
	    		ps.setString(1, objRequest.getTitle());
	    		ps.setInt(2, objRequest.getId_authors());
	    		ps.setString(3, objRequest.getId_comments());
	    		ps.setInt(4, objRequest.getN_endorsement());
	    		ps.setString(5, objRequest.getCreated_at());
	    		ps.setString(6, objRequest.getPublished_at());  
	    		ps.setInt(7, proposalId);
	    		System.out.println("p3");	
	    		System.out.println(ps);
	    		ps.executeUpdate();
	    		ps.close();
	    		conn.close();
		    } catch (SQLException e) {
		    	return Response.status(Status.BAD_REQUEST).entity("Compruebe los datos").build();
		    }
	    return Response.status(Status.OK).entity("Propuesta actualizada correctamente").build();
	}
	
	
	@HEAD
	public Response pingCommentsService() {
		return Response.noContent().header("running", true).build();
	}

}
