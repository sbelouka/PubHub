package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;

public interface TagDAO {

	public boolean addTag(Tag tag);
	public boolean addTag(String tagName);
	
	public boolean removeTag(String name);
	
	public boolean attachTagToBook(String tagName, String isbn_13);
	
	public boolean removeTagRelationship(String tagName, String isbn_13);
	
	// -------------
	
	public List<Tag> getAllTags();
	
	public List<String> getAllRelationships();
	
	public List<Tag> getTagsOfBook(String bookISBN);
	
	public Tag getTag(String tagName);
}
