package com.testing.center.web.common.cache;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.URL;
import java.util.List;


/**
 * ehcache 帮助类
 *
 */
public class EhcacheHelper {

    static Logger logger = LoggerFactory.getLogger(EhcacheHelper.class);

    private EhcacheHelper() {
    }

    static {
        ClassLoader standardClassloader = Thread.currentThread().getContextClassLoader();
        URL url = null;
        if (standardClassloader != null) {
            url = standardClassloader.getResource("/ehcache/ehcache.xml");
        }

        if (url == null) {
            url = EhcacheHelper.class.getResource("/ehcache/ehcache.xml");
        }
       
        cm = CacheManager.newInstance(url);
    }


    private static  CacheManager cm ;
    static private Cache job_cache = cm.getCache(CacheKeyConstant.EHCACHE_JOB_LIST);

    static public void addjob(String k) {
        Element e = new Element(k, k);
        job_cache.put(e);
    }

    static public void removeJob(String k) {
        job_cache.remove(k);
    }

    static public boolean checkkey(String k) {
        return job_cache.isKeyInCache(k);
    }

    /**
     * 获取缓存
     * @param cacheName
     * @param key
     * @return
     */
    static Object getCacheObject(String cacheName, String key) {
        Element e = cm.getCache(cacheName).get(key);

        Object returnObj = null;
        if (e != null) {
            returnObj = e.getObjectValue();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("getCacheObject()...key:" + key + ",value:" + returnObj);
        }

        return returnObj;
    }

    /**
     * 获取所有key列表集合
     * 请慎用
     * @param cacheName
     * @return
     */
    static public  List<String> getCacheKeys(String cacheName) {

        return cm.getCache(cacheName).getKeys();
    }

    static public void setCacheObject(String cacheName, String key, Object value) {

        Element e = new Element(key, value);
        cm.getCache(cacheName).put(e);
    }

    static public void removeAll(String cacheName) {
        cm.getCache(cacheName).removeAll();
    }

    static public <T> T getCacheAndSet(String cacheName, String key,
                                       CacheCaller<T> caller) {

        if (logger.isDebugEnabled()) {
            logger.debug("getCacheAndSet()...key:" + key);
        }
        // 返回空值
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(cacheName)) {
            return null;
        }

        /*Object returnObj = EhcacheHelper.getCacheObject(cacheName, key);

        // 缓存存在数据，直接返回
        if (null != returnObj) {
            // 如果缓存数据为空对象，返回null
            if (returnObj instanceof EmptyCacheObject) {
                return null;
            }
            return (T) returnObj;
        }
        // 已经有任务在执行 , 最多获取缓存10次，有获取到数据直接返回，都没有获取到数据直接落地到数据库查询
        if (EhcacheHelper.checkkey(cacheName + "_" + key)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("getAndSet() InterruptedException error:" + e.getMessage());
            }
            returnObj = getCacheObject(cacheName, key);
            if (null != returnObj) {
                // 如果缓存数据为空对象，返回null
                if (returnObj instanceof EmptyCacheObject) {
                    return null;
                }
            }
            return (T) returnObj;

        } else { //没有任务在执行,调用回调接口
            EhcacheHelper.addjob(cacheName + "_" + key);
            try {
                T o = caller.getData();
                // 数据为空的话往缓存里保存空对象，防止缓存被击穿
                if (null != o) {
                    setCacheObject(cacheName, key, o);
                    return o;
                } else {
                    setCacheObject(cacheName, key, EmptyCacheObject.instance);
                    return null;
                }

            } catch (Exception e) {
                logger.error("getAndSet() error:" + e.getMessage(), e);
            } finally {
                EhcacheHelper.removeJob(cacheName + "_" + key);
            }
        }*/
        return caller.getData();
    }

    /**
     * ehcache 缓存key
     */
    public static  final class     CacheKeyConstant {

        public static final String EHCACHE_JOB_LIST = "_ehcache_job_list";
        
        public static final String EHCACHE_INDEX_LISTCHANNELXMBDBYBDTYPE = "_ehcache_index_listChannelXmBdByBdType";
        public static final String EHCACHE_INDEX_LISTCHANNELXMBDBOOKS = "_ehcache_index_listChannelXmBdBooks";
        public static final String EHCACHE_INDEX_LISTREADOTHERBOOKS = "_ehcache_index_listReadOtherBooks";
        public static final String EHCACHE_INDEX_GETBOOKINFOBYID = "_ehcache_index_getBookInfoById";
        public static final String EHCACHE_INDEX_GETCATEGORY = "_ehcache_index_getCategory";
        public static final String EHCACHE_INDEX_LISTFREEADRELEASEBYCLIENT = "_ehcache_index_listFreeAdReleaseByClient";
        public static final String EHCACHE_INDEX_LISTFREEAD = "_ehcache_index_listFreeAd";
        public static final String EHCACHE_INDEX_LISTFREEADVERTISING = "_ehcache_index_listFreeAdvertising";
        public static final String EHCACHE_INDEX_GETFREEINDEXDATA = "_ehcache_index_getFreeIndexData";
        /**
         * V450 版本新增
         */
        public static final String EHCACHE_INDEX_GETFREEINDEXDATA_APPNAME = "_ehcache_index_getFreeIndexData_appname";
        public static final String EHCACHE_INDEX_LISTPAGECHANNELXMBDBOOKS = "_ehcache_index_listPageChannelXmBdBooks";
        public static final String EHCACHE_INDEX_LISTPAGECHANNELYSBDBOOKS = "_ehcache_index_listPageChannelYsBdBooks";//精品页原生榜单图书
        public static final String EHCACHE_INDEX_GETTAGINFOBYID = "_ehcache_index_getTagInfoById";
        public static final String EHCACHE_INDEX_FINDFREEADBYID = "_ehcache_index_findFreeAdById";
        public static final String EHCACHE_INDEX_LISTBOOKSBYAUTHORNAME = "_ehcache_index_listBooksByAuthorName";
        public static final String EHCACHE_INDEX_LISTPAGEBOOKSBYAUTHORNAME = "_ehcache_index_listPageBooksByAuthorName";
        public static final String EHCACHE_INDEX_GETPVCOUNTBYBOOKID = "_ehcache_index_getPvCountByBookId";
        public static final String EHCACHE_INDEX_GETMAIDUANBYBOOKID = "_ehcache_index_getMaiDuanByBookId";
        public static final String EHCACHE_INDEX_GETTHIRDCATEBYSECID = "_ehcache_index_getThirdCateBySecId";

        public static final String EHCACHE_INDEX__EHCACHE_LISTEXCHANGEBOOK = "_ehcache_listExChangeBook";

        public static final String EHCACHE_CHANNEL_FINDCHANNELLIBIDBYCHANNELID = "_ehcache_channel_findChannelLibIdByChannelId";

        public static final String EHCACHE_INTERFACE_LISTBOOKVOLUMES = "_ehcache_interface_listBookVolumes";
        public static final String EHCACHE_INTERFACE_GETCHAPTERCONTENT = "_ehcache_interface_getChapterContent";

        public static final String EHCACHE_INDEX_LISTLBSUSERBOOKBYUID= "_ehcache_interface_listLbsUserBookByUid";

        public static final String EHCACHE_INDEX_LISTSEARCHKEYWORDS= "_ehcache_interface_listSearchKeyWords";//搜索关键词

        public static final String EHCACHE_INDEX_SEARCHKEYWORDS= "_ehcache_interface_searchKeyWords";//搜索联想词

        public static final String EHCACHE_INDEX_SEARCHAUTHORBYKEYWORDS= "_ehcache_interface_searchAuthorByKeyWords";//搜索联想词
        public static final String EHCACHE_INDEX_SEARCHBOOKBYKEYWORDS= "_ehcache_interface_searchBookByKeyWords";//搜索联想词
        public static final String EHCACHE_LIVEPROPETY= "_ehcache_interface_livePropety";//直播开关属性 add by luozi
        public static final String EHCACHE_BOOKCHAPTER_INFO= "_ehcache_interface_BookChapterInfo";//书籍章节基本信息 add by luozi
        public static final String EHCACHE_BOOKDETAILRESULT= "_ehcache_bookDetailResult";//书籍详情信息 add by luozi
        public static final String EHCACHE_EVERYONELOOKBOOKLIST= "_ehcache_everyoneLookBookList";//大家都在看的书籍列表 add by luozi


    }
}
