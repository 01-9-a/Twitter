package twitter;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.endpoints.AdditionalParameters;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import io.github.redouane59.twitter.dto.tweet.TweetList;
import io.github.redouane59.twitter.dto.tweet.TweetV2;
import io.github.redouane59.twitter.dto.user.User;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

// TODO: write a description for this class
// TODO: complete all methods, irrespective of whether there is an explicit TODO or not
// TODO: write clear specs
// TODO: State the rep invariant and abstraction function
// TODO: what is the thread safety argument?
public class TwitterListener {

    TwitterClient twitter;
    Set<User> subscribedAll = new HashSet<>();
    Map<User, Set<String>> subscribedPattern = new HashMap<>();
    LocalDateTime lastFetch = OCT_1_2022;
    Map<User, List<TweetV2.TweetData>> memory = new HashMap<>();
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
        if(isValidUser(twitterUserName)){
            User user = twitter.getUserFromUserName(twitterUserName);
            if(subscribedAll.contains(user)){
                return false;
            }
            subscribedAll.add(user);
            return true;
        }
        throw new IllegalArgumentException();
    }

    private boolean isValidUser(String twitterUserName) {
        return twitter.getUserFromUserName(twitterUserName) != null;
    }


    // add a subscription for all tweets made by a specific
    // Twitter user that also match a given pattern
    // for simplicity, a match is an exact match of strings but
    // ignoring case
    public boolean addSubscription(String twitterUserName, String pattern) {
        if(isValidUser(twitterUserName)){
            User user = twitter.getUserFromUserName(twitterUserName);
            subscribedAll.remove(user);
            pattern = pattern.toLowerCase();
            if(subscribedPattern.containsKey(user)){
                if(subscribedPattern.get(user).contains(pattern)){
                    return false;
                }
                else{
                    subscribedPattern.get(user).add(pattern);
                    return true;
                }

            }
            else{
                Set<String> patterns = new HashSet<>();
                patterns.add(pattern);
                subscribedPattern.put(user, patterns);
                return true;
            }
        }
        throw new IllegalArgumentException();
    }

    // cancel a previous subscription
    // will also cancel subscriptions to specific patterns
    // from the twitter user
    public boolean cancelSubscription(String twitterUserName) {
        if(isValidUser(twitterUserName)){
            User user = twitter.getUserFromUserName(twitterUserName);
            return subscribedAll.remove(user) || subscribedPattern.remove(user, subscribedPattern.get(user));
        }
        throw new IllegalArgumentException();
    }

    // cancel a specific user-pattern subscription
    public boolean cancelSubscription(String twitterUserName, String pattern) {
        if(isValidUser(twitterUserName)){
            User user = twitter.getUserFromUserName(twitterUserName);
            pattern = pattern.toLowerCase();
            if(subscribedPattern.containsKey(user)){
                if(!subscribedPattern.get(user).contains(pattern)){
                    return false;
                }
                else if(subscribedPattern.get(user).contains(pattern) && subscribedPattern.get(user).size()==1){
                    subscribedPattern.remove(user);
                    return true;
                }
                else{
                    subscribedPattern.get(user).remove(pattern);
                    return true;
                }

            }
            else{
                return false;
            }
        }
        throw new IllegalArgumentException();
    }

    // get all subscribed tweets since the last tweet or
    // set of tweets was obtained
    public List<TweetV2.TweetData> getRecentTweets() {
        List<TweetV2.TweetData> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for(User u: subscribedAll){
            result.addAll(getTweetsByUser(u.getName(), lastFetch, now));
        }
        for(User u: subscribedPattern.keySet()){
            List<TweetV2.TweetData> lst = getTweetsByUser(u.getName(), lastFetch, now);
            for(TweetV2.TweetData t: lst){
                for(String s: subscribedPattern.get(u)){
                    if(t.getText().toLowerCase().contains(s)){
                        result.add(t);
                    }
                }
            }
        }
        lastFetch = now;
        return result;
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