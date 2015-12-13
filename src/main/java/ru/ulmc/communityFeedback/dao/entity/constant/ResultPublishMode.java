package ru.ulmc.communityFeedback.dao.entity.constant;

/**
 * Created by 45 on 13.12.2015.
 */
public enum ResultPublishMode {
    AFTER_CLOSE(1), AFTER_VOTE(2), PUBLIC(3), PRIVATE(0);
    int code;

    ResultPublishMode(int code) {
        this.code = code;
    }
}
