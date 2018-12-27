package com.zyh.wanandroid.model;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * author : zyh
 * Date : 2018/12/27
 * Description :
 */
public class CollectResult {

    /**
     * data : {"curPage":1,"datas":[{"author":"rain9155","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"使用WanAndroid网站api，一款基于MVP + Rxjava2 + Dagger2 + Retrofit + Material Design的WanAndroid应用。","envelopePic":"http://wanandroid.com/blogimgs/02547877-4a5b-4873-b270-0ee06d4959d7.png","id":39776,"link":"http://www.wanandroid.com/blog/show/2457","niceDate":"17分钟前","origin":"","originId":7696,"publishTime":1545878019000,"title":"WanAndroid应用","userId":14587,"visible":0,"zan":0},{"author":"goodgleCoder","chapterId":45,"chapterName":"完整项目","courseId":14,"desc":"一个Material Design风格的论坛类项目。","envelopePic":"","id":39692,"link":"http://www.jianshu.com/p/7d90eab52ee5","niceDate":"18小时前","origin":"","originId":1164,"publishTime":1545813888000,"title":"一个值得学习的项目\u2014可用于毕业答辩","userId":14587,"visible":0,"zan":0},{"author":"Ruheng","chapterId":26,"chapterName":"基础UI控件","courseId":13,"desc":"详解Android图文混排实现。","envelopePic":"","id":39691,"link":"http://www.jianshu.com/p/6843f332c8df","niceDate":"18小时前","origin":"","originId":1165,"publishTime":1545813876000,"title":"Android图文混排实现方式详解","userId":14587,"visible":0,"zan":0},{"author":"goldze","chapterId":385,"chapterName":"架构","courseId":13,"desc":"基于MVVMHabit框架，结合阿里ARouter打造的一套Android-Databinding组件化开发方案。","envelopePic":"http://wanandroid.com/blogimgs/ebeabcb0-60c3-4d8b-a4e9-b1c0a620ac04.png","id":39475,"link":"http://www.wanandroid.com/blog/show/2461","niceDate":"2天前","origin":"","originId":7700,"publishTime":1545659034000,"title":"Android MVVM组件化架构方案","userId":14587,"visible":0,"zan":0}],"offset":0,"over":true,"pageCount":1,"size":20,"total":4}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;
    private int errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean {
        /**
         * curPage : 1
         * datas : [{"author":"rain9155","chapterId":294,"chapterName":"完整项目","courseId":13,"desc":"使用WanAndroid网站api，一款基于MVP + Rxjava2 + Dagger2 + Retrofit + Material Design的WanAndroid应用。","envelopePic":"http://wanandroid.com/blogimgs/02547877-4a5b-4873-b270-0ee06d4959d7.png","id":39776,"link":"http://www.wanandroid.com/blog/show/2457","niceDate":"17分钟前","origin":"","originId":7696,"publishTime":1545878019000,"title":"WanAndroid应用","userId":14587,"visible":0,"zan":0},{"author":"goodgleCoder","chapterId":45,"chapterName":"完整项目","courseId":14,"desc":"一个Material Design风格的论坛类项目。","envelopePic":"","id":39692,"link":"http://www.jianshu.com/p/7d90eab52ee5","niceDate":"18小时前","origin":"","originId":1164,"publishTime":1545813888000,"title":"一个值得学习的项目\u2014可用于毕业答辩","userId":14587,"visible":0,"zan":0},{"author":"Ruheng","chapterId":26,"chapterName":"基础UI控件","courseId":13,"desc":"详解Android图文混排实现。","envelopePic":"","id":39691,"link":"http://www.jianshu.com/p/6843f332c8df","niceDate":"18小时前","origin":"","originId":1165,"publishTime":1545813876000,"title":"Android图文混排实现方式详解","userId":14587,"visible":0,"zan":0},{"author":"goldze","chapterId":385,"chapterName":"架构","courseId":13,"desc":"基于MVVMHabit框架，结合阿里ARouter打造的一套Android-Databinding组件化开发方案。","envelopePic":"http://wanandroid.com/blogimgs/ebeabcb0-60c3-4d8b-a4e9-b1c0a620ac04.png","id":39475,"link":"http://www.wanandroid.com/blog/show/2461","niceDate":"2天前","origin":"","originId":7700,"publishTime":1545659034000,"title":"Android MVVM组件化架构方案","userId":14587,"visible":0,"zan":0}]
         * offset : 0
         * over : true
         * pageCount : 1
         * size : 20
         * total : 4
         */

        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<DatasBean> datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean implements MultiItemEntity {
            /**
             * author : rain9155
             * chapterId : 294
             * chapterName : 完整项目
             * courseId : 13
             * desc : 使用WanAndroid网站api，一款基于MVP + Rxjava2 + Dagger2 + Retrofit + Material Design的WanAndroid应用。
             * envelopePic : http://wanandroid.com/blogimgs/02547877-4a5b-4873-b270-0ee06d4959d7.png
             * id : 39776
             * link : http://www.wanandroid.com/blog/show/2457
             * niceDate : 17分钟前
             * origin :
             * originId : 7696
             * publishTime : 1545878019000
             * title : WanAndroid应用
             * userId : 14587
             * visible : 0
             * zan : 0
             */

            private String author;
            private int chapterId;
            private String chapterName;
            private int courseId;
            private String desc;
            private String envelopePic;
            private int id;
            private String link;
            private String niceDate;
            private String origin;
            private int originId;
            private long publishTime;
            private String title;
            private int userId;
            private int visible;
            private int zan;
            public static final int FIRST_TYPE = 1;
            public static final int SECOND_TYPE = 2;

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getChapterId() {
                return chapterId;
            }

            public void setChapterId(int chapterId) {
                this.chapterId = chapterId;
            }

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getEnvelopePic() {
                return envelopePic;
            }

            public void setEnvelopePic(String envelopePic) {
                this.envelopePic = envelopePic;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getNiceDate() {
                return niceDate;
            }

            public void setNiceDate(String niceDate) {
                this.niceDate = niceDate;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public int getOriginId() {
                return originId;
            }

            public void setOriginId(int originId) {
                this.originId = originId;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }

            @Override
            public int getItemType() {
                if (TextUtils.isEmpty(getEnvelopePic()))
                    return FIRST_TYPE;
                else
                    return SECOND_TYPE;
            }
        }
    }
}
