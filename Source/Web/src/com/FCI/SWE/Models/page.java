package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Page {
	public String ID;
	public String user_ID;
	public String page_ID;
	public String page_name;
	public String page_owner;
	public String user_name;
	public String post_ID;
	public String feelings;
	public String content;
	public String numoflikes;
	public JSONArray seen = new JSONArray();
	public String security;
	public String notes;
	public String category;
	public JSONArray Likes = new JSONArray();

	/**
	 * create new post in a page
	 * This function will store post in data store
	 * 
	 * @param page_id
	 *            provided page id
	 * @param user_id
	 *            provided user id
	 * @param user_name
	 *            provided user name          
	 * @param feeling
	 *            provided user feeling
	 * @param content
	 *            provided post content
	 * @return Status String
	 */         
	public String newpagepost(String page_id,String user_id, String user_name, String feeling,
			String content) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("page", list.size() + 2);
		employee.setProperty("feeling", feeling);
		employee.setProperty("page_id", page_id);
		employee.setProperty("user_name", user_name);
		employee.setProperty("user_id", user_id);
		employee.setProperty("content", content);
		employee.setProperty("seen", seen.toJSONString());
		employee.setProperty("Likes", Likes.toJSONString());
		datastore.put(employee);
		
		  Hashtag H = new Hashtag(); List hash = gethashes(content);
		  
		 for(int i=0;i<hash.size();i++){ H.AddHashtag(hash.get(i).toString(),
		  String.valueOf(list.size()+1));
		  
		  }
		 
		return "page";

	}
	/**
	 * put hashtags in a list
	 * @param token
	 *            provided post to get as hashtag
	 * @return Status List
	 */

	public List gethashes(String token) {

		List<String> hashtags = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(token);

		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			if (token.startsWith("@")) {
				if (!hashtags.contains(token))
					hashtags.add(token);

			}
		}

		for (int i = 0; i < hashtags.size(); i++) {
			System.out.println(hashtags.get(i));

		}

		return hashtags;

	}
	
	/**
	 * View posts
	 * This function will get posts from data store
	 * 
	 * @param page_id
	 *            provided page id
	 * @return Status ArrayList
	 */
	public  ArrayList ViewPosts(String pageid){
		
		ArrayList<Page> posts = new ArrayList();
		
		DatastoreService datastore  = DatastoreServiceFactory
				.getDatastoreService();
		Query q = new Query("page");
		PreparedQuery pq = datastore.prepare(q);
		for (Entity entity : pq.asIterable()) {
			System.out.print("enter\n");
			if(entity.getProperty("page_id").toString().equals(pageid)){      
			//	System.out.print("l\n");
				Page p = new Page();
				p.setID(Long.toString(entity.getKey().getId()));
				p.setUser_name(entity.getProperty("user_name").toString());
				p.setUser_ID(entity.getProperty("user_id").toString());
				p.setPage_ID(entity.getProperty("page_id").toString());
				p.setContent(entity.getProperty("content").toString());
				p.setFeelings(entity.getProperty("feeling").toString());
				
				posts.add(p);
			
				}
		
		}
		
		System.out.print("pot "+posts.size());
		return posts;
	}
 
	/**
	 * Like Post
	 * 
	 * @param postid
	 *            provided post id
	 * @param userid
	 *            provided user id         
	 * @return Status Boolean
	 */
	public Boolean LikePost(String postid, String userid) throws ParseException {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query q = new Query("page");
		PreparedQuery pq = datastore.prepare(q);

		for (Entity entity : pq.asIterable()) {

			if (Long.toString(entity.getKey().getId()).equals(postid)) {

				JSONParser parser = new JSONParser();
				Object obj = parser.parse(entity.getProperty("Likes")
						.toString());
				JSONArray array = (JSONArray) obj;
				if (array.contains(userid)) {
					return true;
				} else {
					array.add(userid);
					entity.setProperty("Likes", array.toJSONString());
					datastore.put(entity);
					return true;
				}

			}
		}

		return false;

	}

	/**
	 * create new page
	 * This function will store page in data store
	 * 
	 * @param owner
	 *            provided page admin
	 * @param name
	 *            provided name
	 * @param category
	 *            provided page category
	 * @return Status String
	 */
	public String newpage(String owner,String name, String cateagory) {
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query q = new Query("Pages");
		PreparedQuery pq = datastore.prepare(q);

		
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("Pages", list.size() + 2);
		
		employee.setProperty("page_owner",owner);
		employee.setProperty("page_name",name);
		employee.setProperty("cateagory",cateagory);
		employee.setProperty("numoflikes","0");
		datastore.put(employee);
		return "done";
	}
	
	/**
	 *  Search for a specific page
	 *  
	 * This function will get pages from data store
	 * 
	 * @param name
	 *            provided user name
	 * @param type
	 *            provided type
	 * @return Status ArrayList
	 */
	public ArrayList PageSearch(String name,String type){
		ArrayList<Page> pages = new ArrayList();
		// type = page_name or page_owner
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query q = new Query("Pages");
		PreparedQuery pq = datastore.prepare(q);
		for(Entity entity : pq.asIterable()){
			if(entity.getProperty(type).equals(name)){
				Page p = new Page();
				
				p.setPage_ID(Long.toString(entity.getKey().getId()));
		
				p.setPage_name(entity.getProperty("page_name").toString());
				p.setPage_owner(entity.getProperty("page_owner").toString());
				p.setCategory(entity.getProperty("cateagory").toString());
				p.setNumoflikes(entity.getProperty("numoflikes").toString());
				pages.add(p);
			}
		}
		
		return pages;
	}
	
	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(String user_ID) {
		this.user_ID = user_ID;
	}

	public String getPage_ID() {
		return page_ID;
	}

	public void setPage_ID(String page_ID) {
		this.page_ID = page_ID;
	}
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPost_ID() {
		return post_ID;
	}

	public void setPost_ID(String post_ID) {
		this.post_ID = post_ID;
	}

	public String getFeelings() {
		return feelings;
	}

	public void setFeelings(String feelings) {
		this.feelings = feelings;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public JSONArray getType() {
		return seen;
	}

	public void setType(JSONArray type) {
		this.seen = type;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPage_name() {
		return page_name;
	}

	public void setPage_name(String page_name) {
		this.page_name = page_name;
	}
	

	public String getPage_owner() {
		return page_owner;
	}

	public void setPage_owner(String page_owner) {
		this.page_owner = page_owner;
	}

	public String getNumoflikes() {
		return numoflikes;
	}

	public void setNumoflikes(String numoflikes) {
		this.numoflikes = numoflikes;
	}
}

	