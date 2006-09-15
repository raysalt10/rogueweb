package com.aetrion.activerecord.fixture;

import com.aetrion.activerecord.models.Topic;

import java.util.Date;

/**
 * 
 */
public class Topics extends Fixture{

    public Topic first;
    public Topic second;

    public void initialize() {
        first = new Topic();
        first.setId(1);
        first.setTitle("Example");
        first.setAuthorName("Anthony");
        first.setAuthorEmailAddress("anthonyeden@gmail.com");
        first.setWrittenOn(new Date());
        first.setLastRead(new Date());
        first.setBonusTime(new Date());
        first.setContent("Have a nice day");
        first.setApproved(false);
        first.setRepliesCount(1);

        second = new Topic();
        second.setId(2);
        second.setTitle("The Second Topic of the day");
        second.setAuthorName("Mary");
        second.setWrittenOn(new Date());
        second.setContent("Have a nice day");
        second.setApproved(true);
        second.setRepliesCount(0);
        second.setParentId(first.getId());
    }

}
