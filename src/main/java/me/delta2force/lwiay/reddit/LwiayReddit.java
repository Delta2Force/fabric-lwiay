package me.delta2force.lwiay.reddit;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import me.delta2force.lwiay.reddit.structure.Post;
import me.delta2force.lwiay.reddit.structure.PostListing;
import me.delta2force.lwiay.reddit.structure.Response;

public class LwiayReddit {
	private Response redditResponse;
	
	public LwiayReddit() {
		System.out.println("Getting data for LWIAY...");
		System.setProperty("http.agent", "minecraft:lwiayclient:1.0 (by u/DeltaTwoForce)");
		Gson gson = new Gson();
		URL url = null;
		try {
			url = new URL("https://www.reddit.com/r/pewdiepiesubmissions.json?sort=top&t=week");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		URLConnection urlcon = null;
		
		try {
			urlcon = url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		urlcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		try {
			urlcon.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			redditResponse = gson.fromJson(new InputStreamReader(urlcon.getInputStream()), Response.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Post getPost(int index) {
		return redditResponse.data.children[index].data;
	}
	
	public Post[] getPosts() {
		ArrayList<Post> posts = new ArrayList<>();
		for(PostListing pl : redditResponse.data.children) {
			posts.add(pl.data);
		}
		return posts.toArray(new Post[] {});
	}
}
