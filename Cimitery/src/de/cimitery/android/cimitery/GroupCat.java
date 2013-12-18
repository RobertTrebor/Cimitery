package de.cimitery.android.cimitery;

import java.util.ArrayList;
import java.util.List;


public class GroupCat {
	
	private long groupID;
	private String groupName;
	private List<Category> itemList = new ArrayList<Category>();
	
	
	
	public GroupCat(long id, String name) {
		this.groupID = id;
		this.groupName = name;
	}
	
	
	public long getGroupID() {
		return groupID;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public List<Category> getItemList() {		
		return itemList;
	}

	public void setItemList(List<Category> itemList) {
		this.itemList = itemList;
	}

}
