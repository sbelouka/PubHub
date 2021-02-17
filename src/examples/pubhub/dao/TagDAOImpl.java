package examples.pubhub.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

public class TagDAOImpl implements TagDAO {

	Connection connection = null;	// Our connection to the database
	PreparedStatement sqlStatement = null;	// We use prepared statements to help protect against SQL injection
	List<Tag> testList = new ArrayList<Tag>();
	List<Book> testBookList = new ArrayList<Book>();
	
	//----------------------------------------------
	
	@Override
	public boolean addTag(Tag tag)
	{
		try {
			connection = DAOUtilities.getConnection(); //establishes connection to pubhub
			String sql = "INSERT INTO tags (tagname) VALUES (?)";
			sqlStatement = connection.prepareStatement(sql);
			
			//sqlStatement.setInt(1, tag.getTagID());
			sqlStatement.setString(1, tag.getTagName());
			
			
			//checks to see if the above statement executed
			if (sqlStatement.executeUpdate() != 0)
			{
				//if it was successfully updated
				return true;
			}	
			else
				return false;
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
	public boolean addTag(String tagName)
	{
		try {
			connection = DAOUtilities.getConnection(); //establishes connection to pubhub
			String sql = "INSERT INTO tags (tagname) VALUES (?)";
			sqlStatement = connection.prepareStatement(sql);
			
			//sqlStatement.setInt(1, tag.getTagID());
			sqlStatement.setString(1, tagName);
			
			
			//checks to see if the above statement executed
			if (sqlStatement.executeUpdate() != 0)
			{
				//if it was successfully updated
				return true;
			}	
			else
				return false;
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
	
	public boolean removeTag(String tagName)
	{
		try {
			connection = DAOUtilities.getConnection(); //establishes connection to pubhub
			String sql = "DELETE FROM tags WHERE tagname = ?";
			sqlStatement = connection.prepareStatement(sql);
			
			sqlStatement.setString(1, tagName);
			
			
			//checks to see if the above statement executed
			if (sqlStatement.executeUpdate() != 0)
			{
				//if it was successfully updated
				return true;
			}	
			else
				return false;
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
	public boolean attachTagToBook(String tagName, String isbn_13)
	{
		
		try {
			connection = DAOUtilities.getConnection(); //establishes connection to pubhub
			String sql = "INSERT INTO bookstags VALUES (?,?)";
			sqlStatement = connection.prepareStatement(sql);
			 
			sqlStatement.setString(1, isbn_13);
			sqlStatement.setString(2, tagName);
			
			
			//checks to see if the above statement executed
			if (sqlStatement.executeUpdate() != 0)
			{
				//if it was successfully updated
				return true;
			}	
			else
				return false;
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
	public boolean removeTagRelationship(String tagName, String isbn_13)
	{
		
		try {
			connection = DAOUtilities.getConnection(); //establishes connection to pubhub
			String sql = "DELETE FROM bookstags WHERE isbn_13 = ? AND tagname = ?";
			sqlStatement = connection.prepareStatement(sql);
			
			sqlStatement.setString(1, isbn_13);
			sqlStatement.setString(2, tagName);
			
			
			//checks to see if the above statement executed
			if (sqlStatement.executeUpdate() != 0)
			{
				//if it was successfully updated
				return true;
			}	
			else
				return false;
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
	// ---------------------------------------------------------------------------
	
	public List<Tag> getAllTags()
	{
		List<Tag> tags = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();	// Get our database connection from the manager
			String sql = "SELECT * FROM Tags ORDER BY tagname";			// Our SQL query
			sqlStatement = connection.prepareStatement(sql);	// Creates the prepared statement from the query
			
			ResultSet resultSet = sqlStatement.executeQuery();			// Queries the database

			// So long as the ResultSet actually contains results...
			while (resultSet.next()) {
				// We need to populate a Tag object with info for each row from our query result
				Tag tag = new Tag();

				// Each variable in our Tag object maps to a column in a row from our results.
				tag.setTagName(resultSet.getString("tagname"));
				
				// Finally we add it to the list of Tag objects returned by this query.
				tags.add(tag);
				
			}
			
			resultSet.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed, 
			// or else we could wind up with a memory leak
			closeResources();
		}
		
		return tags;
	}
	
	public List<String> getAllRelationships()
	{
		List<String> relationships = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();	// Get our database connection from the manager
			String sql = "SELECT * FROM bookstags";			// Our SQL query
			sqlStatement = connection.prepareStatement(sql);	// Creates the prepared statement from the query
			
			ResultSet resultSet = sqlStatement.executeQuery();			// Queries the database

			// So long as the ResultSet actually contains results...
			while (resultSet.next()) {
				// We need to populate a Tag object with info for each row from our query result
				String currentRel = "";
				
				//needed in order to get the book title
				BookDAO bookDAO = new BookDAOImpl();
				Book book = bookDAO.getBookByISBN(resultSet.getString("isbn_13"));
				
				//placing the book title and tags together
				currentRel = "Book: " + book.getTitle() + " | Is linked with tag: " +
				resultSet.getString("tagname");
				
				relationships.add(currentRel);
				
			}
			
			resultSet.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed, 
			// or else we could wind up with a memory leak
			closeResources();
		}
		
		return relationships;
	}
	
	public List<Tag> getTagsOfBook(String isbn_13)
	{
		List<Tag> tags = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();	// Get our database connection from the manager
			String sql = "SELECT tagname FROM bookstags WHERE isbn_13=?";			// Our SQL query
			sqlStatement = connection.prepareStatement(sql);	// Creates the prepared statement from the query
			
			sqlStatement.setString(1, isbn_13);
			
			ResultSet resultSet = sqlStatement.executeQuery();			// Queries the database

			// So long as the ResultSet actually contains results...
			while (resultSet.next()) {
				// We need to populate a Tag object with info for each row from our query result
				Tag tag = new Tag();
				String tagName = resultSet.getString("tagname");
				
				// Each variable in our Tag object maps to a column in a row from our results.
				tag.setTagName(tagName);
				
				// Finally we add it to the list of Tag objects returned by this query.
				tags.add(tag);
				
			}
			
			resultSet.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed, 
			// or else we could wind up with a memory leak
			closeResources();
		}
		
		return tags;
	}
	
	public Tag getTag(String tagName)
	{
		Tag tag = new Tag();
		//tag = null;
		
		try {
			connection = DAOUtilities.getConnection();	// Get our database connection from the manager
			String sql = "SELECT * FROM tags WHERE tagname = ?";			// Our SQL query
			sqlStatement = connection.prepareStatement(sql);	// Creates the prepared statement from the query
			
			sqlStatement.setString(1, tagName);
			
			ResultSet resultSet = sqlStatement.executeQuery();
			
			//should only contain one element but you know
			if (resultSet.next()) {
				tag.setTagName(resultSet.getString("tagname"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed, 
			// or else we could wind up with a memory leak
			closeResources();
		}
		
		return tag;
	}
	
	//closes resources 
	private void closeResources() {
			try {
				if (sqlStatement != null)
					sqlStatement.close();
			} catch (SQLException e) {
				System.out.println("Could not close statement!");
				e.printStackTrace();
			}
			
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.out.println("Could not close connection!");
				e.printStackTrace();
			}
		}
}
