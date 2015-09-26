package com.xmgdg.gdgevents.Tools;


/**
 * Created by Yulan on 2015/5/21.
 * 状态值
 */
public class AppStat {
    //活动上下文菜单ID对应名称
    public static class MainEventsContextMenuID {
        public static final int share = 1;
        public static final int addToCalendar = 2;
        public static final int openInGooglePlus = 3;
    }

    //数据库目前版本
    public static class DataBaseVersion {
        public static final int nowVersion = 1;
    }

    //数据库列号
    public static class DataBaseColumnIndex {
        public static final int id = 0;
        public static final int groupId = 1;
        public static final int description = 2;
        public static final int start = 3;
        public static final int timezoneName = 4;
        public static final int participantsCount = 5;
        public static final int title = 6;
        public static final int iconUrl = 7;
        public static final int link = 8;
        public static final int location = 9;
        public static final int end = 10;
        public static final int temporalRelation = 11;
        public static final int gPlusEventLink = 12;
    }

    //Preferences
    public static class Preferences {
        public static final String FileName = "AppStat";
        public static final String InitPosition = "InitPosition";
    }

}
