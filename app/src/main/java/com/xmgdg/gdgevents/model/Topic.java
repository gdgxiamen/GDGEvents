package com.xmgdg.gdgevents.model;


import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import com.xmgdg.gdgevents.MainActivity;
import com.xmgdg.gdgevents.R;

/**
 * Created by ye on 15/5/17.
 */
public class Topic implements Parcelable {

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

	public String getSummary() {
		Activity activity = MainActivity.activity;
		String summary = "";
		summary += title + System.getProperty("line.separator");
		summary += activity.getString(R.string.starttime) + start.replace("+0800", "") +
				System.getProperty("line" + ".separator");
		summary += activity.getString(R.string.location) + location + System
				.getProperty("line" + ".separator");
		summary += activity.getString(R.string.description) + description;
		return summary;
	}

	public static final Parcelable.Creator<Topic> CREATOR = new Parcelable.Creator<Topic>() {

		@Override
		public Topic createFromParcel(Parcel source) {
			return new Topic(source);
		}

		@Override
		public Topic[] newArray(int size) {
			return new Topic[0];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	public Topic() {
	}

	private Topic(Parcel in) {
		group = in.readString();
		description = in.readString();
		start = in.readString();
		timezoneName = in.readString();
		participantsCount = in.readString();
		title = in.readString();
		iconUrl = in.readString();
		link = in.readString();
		location = in.readString();
		end = in.readString();
		id = in.readString();
		temporalRelation = in.readString();
		gPlusEventLink = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(group);
		dest.writeString(description);
		dest.writeString(start);
		dest.writeString(timezoneName);
		dest.writeString(participantsCount);
		dest.writeString(title);
		dest.writeString(iconUrl);
		dest.writeString(link);
		dest.writeString(location);
		dest.writeString(end);
		dest.writeString(id);
		dest.writeString(temporalRelation);
		dest.writeString(gPlusEventLink);
	}


}
