package com.socialmediaraiser.twitter.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.socialmediaraiser.twitter.IUser;
import com.socialmediaraiser.twitter.dto.tweet.ITweet;
import com.socialmediaraiser.twitter.helpers.ConverterHelper;
import com.socialmediaraiser.twitter.dto.tweet.TweetDTOv2;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * @version labs
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOv2 implements IUser {
    private static final Logger LOGGER = Logger.getLogger(UserDTOv2.class.getName());

    private UserData[] data;
    private UserData.Includes includes;

    @Data
    public static class UserData implements IUser{
        private String id;
        @JsonProperty("created_at")
        private String createdAt;
        @JsonProperty("username")
        private String name;
        private String location;
        private String url;
        private boolean verified;
        @JsonProperty("profile_image_url")
        private String profileImageUrl;
        private UserStatsDTO stats;
        @JsonProperty("most_recent_tweet_id")
        private String mostRecentTweetId;
        @JsonProperty("pinned_tweet_id")
        private String pinnedTweetId;
        private String description;
        private String lang;
        private boolean isProtectedAccount;

        @Data
        public static class Includes{
            private TweetDTOv2.TweetData[] tweets; // @TODO problem here
        }

        @Override
        public Date getDateOfCreation() {
            return ConverterHelper.getDateFromTwitterString(this.createdAt);
        }

        @Override
        public int getFollowersCount() {
            return this.stats.getFollowersCount();
        }

        @Override
        public int getFollowingCount() {
            return this.stats.getFollowingCount();
        }

        @Override
        public int getTweetCount() {
            return this.stats.getTweetCount();
        }

        @Override
        public List<ITweet> getMostRecentTweet() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Date getLastUpdate() {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public String getId() {
        return this.data[0].getId();
    }

    @Override
    public String getName() {
        return this.data[0].getName();
    }

    @Override
    public String getLocation() {
        return this.data[0].getLocation();
    }

    @Override
    public String getDescription() {
        return this.data[0].getDescription();
    }

    @Override
    public Date getDateOfCreation() {
        return ConverterHelper.getDateFromTwitterDateV2(this.data[0].getCreatedAt());
    }

    @Override
    public Date getLastUpdate() {
        if(this.includes.getTweets().length>0){
            return this.includes.getTweets()[0].getCreatedAt();
        } else{
            return null;
        }
    }

    @Override
    public List<ITweet> getMostRecentTweet(){
        return Arrays.asList(this.includes.getTweets());
    }


    @Override
    public int getFollowersCount() {
        return this.data[0].getStats().getFollowersCount();
    }

    @Override
    public int getFollowingCount() {
        return this.data[0].getStats().getFollowingCount();
    }

    @Override
    public int getTweetCount() {
        return this.data[0].getStats().getTweetCount();
    }

    @Override
    public String getLang() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isProtectedAccount() {
        throw new UnsupportedOperationException();
    }

}
