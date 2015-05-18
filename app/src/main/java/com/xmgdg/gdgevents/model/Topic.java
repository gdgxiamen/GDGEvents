package com.xmgdg.gdgevents.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ye on 15/5/17.
 *
 */
public class Topic {

    private String group;
    private String description;
    private String start;
    private String timezoneName;
    private String participantsCount;
    private String title;
    private String iconUrl;
    private String link;
    private String location;
    private String end;
    private String id;
    private String temporalRelation;
    private String gPlusEventLink;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getTimezoneName() {
        return timezoneName;
    }

    public void setTimezoneName(String timezoneName) {
        this.timezoneName = timezoneName;
    }

    public String getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(String participantsCount) {
        this.participantsCount = participantsCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemporalRelation() {
        return temporalRelation;
    }

    public void setTemporalRelation(String temporalRelation) {
        this.temporalRelation = temporalRelation;
    }

    public String getgPlusEventLink() {
        return gPlusEventLink;
    }

    public void setgPlusEventLink(String gPlusEventLink) {
        this.gPlusEventLink = gPlusEventLink;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "group='" + group + '\'' +
                ", description='" + description + '\'' +
                ", start='" + start + '\'' +
                ", timezoneName='" + timezoneName + '\'' +
                ", participantsCount='" + participantsCount + '\'' +
                ", title='" + title + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", link='" + link + '\'' +
                ", location='" + location + '\'' +
                ", end='" + end + '\'' +
                ", id='" + id + '\'' +
                ", temporalRelation='" + temporalRelation + '\'' +
                ", gPlusEventLink='" + gPlusEventLink + '\'' +
                '}';
    }


//    public static Topic fromJson(JSONObject json) throws JSONException {
//        Topic topic = new Topic();
//        topic.group = json.getString("group");
//        topic.description = json.getString("description");
//        topic.start = json.getString("start");
//        topic.timezoneName = json.getString("timezoneName");
//        topic.participantsCount = json.getString("participantsCount");
//        topic.title = json.getString("title");
//        topic.iconUrl = json.getString("iconUrl");
//        topic.link = json.getString("link");
//        topic.location = json.getString("location");
//        topic.end = json.getString("end");
//        topic.id = json.getString("id");
//        topic.temporalRelation = json.getString("temporalRelation");
//        topic.gPlusEventLink = json.getString("gPlusEventLink");
//
//        return topic;
//    }
}
