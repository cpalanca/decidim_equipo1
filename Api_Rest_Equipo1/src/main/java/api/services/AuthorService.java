package api.services;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
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
import api.domain.Author;


@Path("/author")
@Consumes(value= MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class AuthorService {
	
	@GET
	public Response findAllAuthors() throws SQLException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		Connection conn = Utils.connectDB();
		
		try {
            Statement miStatement = conn.createStatement();
            String sql = "Select * from authors";
            ResultSet miResultSet = miStatement.executeQuery(sql);
            
            while(miResultSet.next()) {
            	Author obj = new Author();
            	obj.setId(miResultSet.getInt("id"));
            	obj.setNickname(miResultSet.getString("nickname"));
            	authors.add(obj);
            }
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		//connect();
		return Response.ok(authors).build();
	}
	
	@GET
	@Path("{authorId}")
	public Response findAuthor( @PathParam("authorId") int authorId) throws ClassNotFoundException {
		Connection conn = Utils.connectDB();
		Author obj = new Author();
		try {
            Statement miStatement = conn.createStatement();
            String sql = "Select * from authors where id="+authorId;
            ResultSet miResultSet = miStatement.executeQuery(sql);
            
            while(miResultSet.next()) {
            	obj.setId(miResultSet.getInt("id"));
            	obj.setNickname(miResultSet.getString("nickname"));
            }
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

		//connect();
		return Response.ok(obj).build();
	}
	
	
	@DELETE
	@Path("{authorId}")
	public Response deleteAuthor( @PathParam("authorId") int authorId) throws ClassNotFoundException {
		Connection conn = Utils.connectDB();
		try {
            Statement miStatement = conn.createStatement();
            String sql = "Delete from authors where id="+authorId;
            System.out.println(sql);
            miStatement.executeUpdate(sql);
            miStatement.close();
            conn.close();
        } catch (Exception e) {
        	return Response.status(Status.BAD_REQUEST).entity("Author not found").build();
        }
		return Response.status(Status.OK).entity("Author eliminado").build();
	}
	
	@POST
	public Response createAuthor(Author authorRequest) throws ClassNotFoundException{
		Connection conn = Utils.connectDB();
			    
		    try {
		    	String sql = "INSERT INTO authors (id, nickname) VALUES (?,?)";
	    		PreparedStatement ps= conn.prepareStatement(sql);
	    		ps.setInt(1, authorRequest.getId());
	    		ps.setString(2, authorRequest.getNickname());
	    		ps.executeUpdate();
	    		ps.close();
	    		conn.close();
		    } catch (SQLException e) {
		    	return Response.status(Status.BAD_REQUEST).entity("Compruebe los datos").build();
		    }
	    return Response.status(Status.OK).entity("Author guardado correctamente").build();
		
	}


	@PUT
	@Path("{authorId}")
	public Response editAuthor(@PathParam("authorId") int authorId, Author author) throws ClassNotFoundException {
		Connection con = null;
		Class.forName("org.sqlite.JDBC");
		int rs;
		System.out.println(author.toString());
		try {
			con = DriverManager.getConnection("jdbc:sqlite:/home/tsi/decidim_equipo1.db");
			Statement miStatement = con.createStatement();
			String sql = "UPDATE authors SET id ="+author.getId()+", nickname = '"+author.getNickname()+"' where id="+authorId;
			System.out.println(sql);
			rs=miStatement.executeUpdate(sql);
			System.out.print(rs);
			miStatement.close();
            con.close();
		}catch(SQLException e) {
			return Response.status(Status.BAD_REQUEST).entity("Revisa los datos").build();
		}		
		
		return Response.status(Status.OK).entity("Actualizado correctamente").build();
		
	}
	
	
	@HEAD
	public Response pingAuthorsService() {
		return Response.noContent().header("running", true).build();
	}
	
}