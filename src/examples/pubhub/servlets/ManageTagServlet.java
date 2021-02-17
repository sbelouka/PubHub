package examples.pubhub.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.model.Book;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class ManageTagServlet
 */
@WebServlet("/ManageTagServlet")
public class ManageTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageTagServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//makes it so that the manage tags jsp file is loaded
		
		TagDAO database = DAOUtilities.getTagDAO();
		
		List<Tag> tagList = database.getAllTags();
		request.setAttribute("tagNames", tagList);
			
		request.getRequestDispatcher("manageTags.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TagDAO database = DAOUtilities.getTagDAO();
		
		// ------------------------
		// Add Tag Button
		// ------------------------
		if(request.getParameter("tagAddName") != "" && request.getParameter("addTagSubmit") != null)
		{
			
			Tag tempTag = database.getTag(request.getParameter("tagAddName")); //looks for tag based on what the user typed in
			
			//request.getSession().setAttribute("message", "tag ");
			
			//checks if a tag with that same name has already been added
			if(tempTag.getTagName() == null) //if the tag equals the default tag id number, then it wasn't found
			{
				request.getSession().setAttribute("message", "Tag with that name has already been added");
				request.getSession().setAttribute("messageClass", "alert-danger");
				request.getRequestDispatcher("manageTags.jsp").forward(request, response);
			}
			else //create the new tag
			{
				tempTag = new Tag(request.getParameter("tagAddName"));
				
				//if the tag is added successfully...
				if(database.addTag(tempTag))
				{
					request.getSession().setAttribute("message", "Tag successfully added");
					request.getSession().setAttribute("messageClass", "alert-success");
					//this line keeps the tag from being added more than once
					response.sendRedirect(request.getContextPath() + "/ManageTags");
				}
				else
				{
					request.getSession().setAttribute("message", "There was a problem adding the tag");
					request.getSession().setAttribute("messageClass", "alert-danger");
					response.sendRedirect(request.getContextPath() + "/ManageTags");
				}
			}
		}
		else if (request.getParameter("tagAddName") == "" && request.getParameter("addTagSubmit") != null)
		{
			request.getSession().setAttribute("message", "Nothing was typed in the textbox");
			request.getSession().setAttribute("messageClass", "alert-danger");
		}
		
		// ------------------------
		// Remove Tag Button
		// ------------------------
		else if(request.getParameter("tagNames") != "null" && request.getParameter("removeTagSubmit") != null)
		{
			String tagName = request.getParameter("tagNames");
			
			//if the tag is removed successfully...
			if(database.removeTag(tagName))
			{
				request.getSession().setAttribute("message", "Tag successfully removed");
				request.getSession().setAttribute("messageClass", "alert-success");
				//this line keeps the tag from being removed more than once
				response.sendRedirect(request.getContextPath() + "/ManageTags");
			}
			else
			{
				request.getSession().setAttribute("message", "There was a problem removing the tag");
				request.getSession().setAttribute("messageClass", "alert-danger");
				response.sendRedirect(request.getContextPath() + "/ManageTags");
			}
		}
		//if the remove tag button is pressed without a tag being selected
		else if (request.getParameter("tagNames") == "null" && request.getParameter("addTagSubmit") != null)
		{
			request.getSession().setAttribute("message", "Nothing was selected");
			request.getSession().setAttribute("messageClass", "alert-danger");
		}
		
		// ------------------------
		// Apply Tag To Book Button
		// ------------------------
		else if(request.getParameter("tagNames") != "null" && request.getParameter("applyTagSubmit") != null)
		{
			String tagName = request.getParameter("tagNames");
			String bookISBN = request.getParameter("bookISBN");
			
			//if the tag is applied successfully...
			if(database.attachTagToBook(tagName, bookISBN))
			{
				request.getSession().setAttribute("message", "Tag successfully attached to " + bookISBN);
				request.getSession().setAttribute("messageClass", "alert-success");
				//this line keeps the tag from being removed more than once
				response.sendRedirect(request.getContextPath() + "/ManageTags");
			}
			else
			{
				request.getSession().setAttribute("message", "There was a problem attaching the tag");
				request.getSession().setAttribute("messageClass", "alert-danger");
				response.sendRedirect(request.getContextPath() + "/ManageTags");
			}
		}
		//if the apply tag button is pressed without a tag being selected
		else if (request.getParameter("tagNames") == "null" && request.getParameter("applyTagSubmit") != null)
		{
			request.getSession().setAttribute("message", "Nothing was selected");
			request.getSession().setAttribute("messageClass", "alert-danger");
		}
		
		// ------------------------
		// Remove Tag From Book Button
		// ------------------------
		else if(request.getParameter("tagNames") != "null" && request.getParameter("removeFromBookSubmit") != null)
		{
			String tagName = request.getParameter("tagNames");
			String bookISBN = request.getParameter("bookISBN");
					
			//if the tag is applied successfully...
			if(database.removeTagRelationship(tagName, bookISBN))
			{
				request.getSession().setAttribute("message", "Tag successfully removed from " + bookISBN);
				request.getSession().setAttribute("messageClass", "alert-success");
				//this line keeps the tag from being removed more than once
				response.sendRedirect(request.getContextPath() + "/ManageTags");
			}
			else
			{
				request.getSession().setAttribute("message", "There was a problem removing the relationship");
				request.getSession().setAttribute("messageClass", "alert-danger");
				response.sendRedirect(request.getContextPath() + "/ManageTags");
			}
		}
		//if the apply tag button is pressed without a tag being selected
		else if (request.getParameter("tagNames") == "null" && request.getParameter("removeFromBookSubmit") != null)
		{
			request.getSession().setAttribute("message", "Nothing was selected");
			request.getSession().setAttribute("messageClass", "alert-danger");
		}
		
	}
	

}
