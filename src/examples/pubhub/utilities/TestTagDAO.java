package examples.pubhub.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.dao.TagDAOImpl;
import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.BookDAOImpl;

public class TestTagDAO {

	public static void main(String[] args)
	{
		TagDAO tagDAO = new TagDAOImpl();
		BookDAO bookDAO = new BookDAOImpl();
		
		List<Tag> tagList = tagDAO.getAllTags();
		
		//displays all of the tags that currently exist
		for (int i = 0; i < tagList.size(); i++)
		{
			System.out.println("Tag name: " + tagList.get(i).getTagName());
		}
		
		// --------------------
		//testing different functions of the tagDAOImpl class
		// --------------------
		
		//adding different tags
		//Tag tag1 = new Tag("Action");
		//Tag tag2 = new Tag("Adventure");
				
		//tagDAO.addTag(tag1);
		//tagDAO.addTag(tag2);
		
		//removing tags
		//tagDAO.removeTag("Adventure");
		
		//attaching tag to book;
		//tagDAO.attachTagToBook("Horror", "1111111111169");
		//tagDAO.removeTagRelationship("Fiction", "1111111111169");
		
		//printing out all relationships
		List<String> tempRelList = tagDAO.getAllRelationships();
		
		for(int j = 0; j < tempRelList.size(); j++)
		{
			System.out.println(tempRelList.get(j));
		}
		
		//printing out all tags related to a given books isbn number
		List<Tag> tempTagList = tagDAO.getTagsOfBook("1111111113451");
		
		for(int k = 0; k < tempTagList.size(); k++)
		{
			System.out.println(tempTagList.get(k).getTagName());
		}
		
	}
}
