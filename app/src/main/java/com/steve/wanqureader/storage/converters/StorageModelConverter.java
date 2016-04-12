package com.steve.wanqureader.storage.converters;

import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.network.model.Tag;
import com.steve.wanqureader.storage.model.StarredPost;
import com.steve.wanqureader.storage.model.StarredTag;

/**
 * Created by steve on 4/9/16.
 */
public class StorageModelConverter {
    public static StarredPost convertToStoragePostModel(Post post) {
        return new StarredPost(
                System.currentTimeMillis() * 1000,
                post.getCreationDate(),
                post.getIssue(),
                post.getPostNo(),
                post.getReadTimeMinutes(),
                post.getReadableArticle(),
                post.getReadableTitle(),
                post.getTitle(),
                post.getReadableSummary(),
                post.getSummary(),
                post.getSlug(),
                post.getUrl(),
                post.getUrlDomain());
    }

    public static StarredTag convertToStorageTagModel(Tag tag, StarredPost post) {
        return new StarredTag(tag.getTag(), post);
    }
}
