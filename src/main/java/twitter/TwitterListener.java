package twitter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.endpoints.AdditionalParameters;
import io.github.redouane59.twitter.dto.tweet.TweetList;
import io.github.redouane59.twitter.dto.tweet.TweetV2;
import io.github.redouane59.twitter.dto.user.User;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// TODO: write a description for this class
// TODO: complete all methods, irrespective of whether there is an explicit TODO or not
// TODO: write clear specs
// TODO: State the rep invariant and abstraction function
// TODO: what is the thread safety argument?
public class TwitterListener {

    TwitterClient twitter;
    private static final LocalDateTime OCT_1_2022 = LocalDateTime.parse("2022-10-01T00:00:00");

    // create a new instance of TwitterListener
    // the credentialsFile is a JSON file that
    // contains the API access keys
    // consider placing this file in the
    // 'secret' directory but the constructor
    // should work with any path
    public TwitterListener(File credentialsFile) {
        twitter = new TwitterClient(TwitterClient.getAuthentication(credentialsFile));
        // ... add other elements ...
    }

    // add a subscription for all tweets made by a specific
    // Twitter user
    public boolean addSubscription(String twitterUserName) {
        return false;
    }

    private boolean isValidUser(String twitterUserName) {
        return false;
    }


    // add a subscription for all tweets made by a specific
    // Twitter user that also match a given pattern
    // for simplicity, a match is an exact match of strings but
    // ignoring case
    public boolean addSubscription(String twitterUserName, String pattern) {
        return true;
    }

    // cancel a previous subscription
    // will also cancel subscriptions to specific patterns
    // from the twitter user
    public boolean cancelSubscription(String twitterUserName) {
        return true;
    }

    // cancel a specific user-pattern subscription
    public boolean cancelSubscription(String twitterUserName, String pattern) {
        return false;
    }

    // get all subscribed tweets since the last tweet or
    // set of tweets was obtained
    public List<TweetV2.TweetData> getRecentTweets() {
        return new ArrayList<>();
    }

    // get all the tweets made by a user
    // within a time range.
    // method has been implemented to help you.
    public List<TweetV2.TweetData> getTweetsByUser(String twitterUserName,
                                                   LocalDateTime startTime,
                                                   LocalDateTime endTime) {
        User twUser = twitter.getUserFromUserName(twitterUserName);
        if (twUser == null) {
            throw new IllegalArgumentException();
        }
        TweetList twList = twitter.getUserTimeline(twUser.getId(), AdditionalParameters.builder().startTime(startTime).endTime(endTime).build());
        return twList.getData();
    }

}
