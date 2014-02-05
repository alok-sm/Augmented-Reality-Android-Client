package com.example.surface_view;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class PostImage 
{
	String url, filePath;
	public PostImage(String _url, String _filePath)
	{
		url = _url;
		filePath = _filePath;
	}
	public String callPost()
    {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		MultipartEntityBuilder build = MultipartEntityBuilder.create();
		File file = new File(filePath);
		build.addPart("pic", new FileBody(file));
		HttpEntity ent = build.build();
		post.setEntity(ent);
		HttpResponse resp = null;
		String result="err";
		try
		{
			resp = client.execute(post);
			HttpEntity resEnt = resp.getEntity();
			result = EntityUtils.toString(resEnt);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
    }
}
