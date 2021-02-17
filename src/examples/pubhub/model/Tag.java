package examples.pubhub.model;

public class Tag {

	private String tagName;
	
	public Tag()
	{
		tagName = "???";
	}
	
	public Tag(String tagName)
	{
		this.tagName = tagName;
	}
	
	public String getTagName() {
		return tagName;
	}
	
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
}
