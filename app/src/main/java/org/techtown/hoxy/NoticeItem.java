package org.techtown.hoxy;


import android.graphics.Bitmap;

public class NoticeItem {
        String notice;

        public NoticeItem(String notice) {
                this.notice = notice;
        }
        public NoticeItem(){
                this.notice = "";
        }

        public String getNotice() {
                return notice;
        }

        public void setNotice(String notice) {
                this.notice = notice;
        }
}

