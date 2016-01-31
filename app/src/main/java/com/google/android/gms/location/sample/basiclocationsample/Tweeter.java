package com.google.android.gms.location.sample.basiclocationsample;

import java.io.IOException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created by alantran on 1/30/16.
 */
public class Tweeter {

    private final static String CONSUMER_KEY = "wJkvYkDUTvfKzfAnR9CNl2hre";
    private final static String CONSUMER_KEY_SECRET = "Dyp5R9syUtsSdRg6M2RC237lulpalb74zxHczAVcOOgsjLoDEf";

    Twitter twitter;
    RequestToken requestToken;

    public Tweeter(){
    }

    public String getRequestURL() throws TwitterException, IOException {
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
        requestToken = twitter.getOAuthRequestToken();
        return requestToken.getAuthorizationURL();
    }

    public void tweet(String text, String pin) throws TwitterException{
        AccessToken accessToken = null;
        while (null == accessToken) {
            try{
                accessToken = twitter.getOAuthAccessToken(requestToken, pin);
            } catch (TwitterException te) {
                System.out.println("Failed to get access token, caused by: "+ te.getMessage());
                System.out.println("Retry input PIN");
            }
        }
        System.out.println("Access Token: " + accessToken.getToken());
        System.out.println("Access Token Secret: "+ accessToken.getTokenSecret());

        twitter.updateStatus(text);
    }
}
