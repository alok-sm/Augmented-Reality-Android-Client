package com.example.surface_view;

import android.os.AsyncTask;

public class AsyncPost extends AsyncTask<Void, Void, String> 
{
	public boolean done = false;
	public PostImage post = null;
	public AsyncPost(String url, String filePath)
	{
		post = new PostImage(url, filePath);
	}
	@Override
	protected String doInBackground(Void... params) 
	{
		String result = post.callPost();
		done = true;
		return result;
	}
}
