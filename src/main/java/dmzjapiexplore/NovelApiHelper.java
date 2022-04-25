package dmzjapiexplore;




public class NovelApiHelper {
    public static final String NOVEL_KEY = "IBAAKCAQEAsUAdKtXNt8cdrcTXLsaFKj9bSK1nEOAROGn2KJXlEVekcPssKUxSN8dsfba51kmHM";
    public static String APP_DOMAIN_NAME = "muwai.com";
    public static final String NOVEL_URL = ("http://jurisdiction." + APP_DOMAIN_NAME + "");

    public static void run(){
        String url = NovelApiHelper.getFullUrl(NovelApiHelper.getChapterUrl("11615", "117485"), "test");
        System.out.println("Full Url: " + url);
    }

    public static String getChapterUrl(String volumeId, String chapterId){
        return NOVEL_URL + "/lnovel/" + volumeId + "_" + chapterId + ".txt";
    }

    public static String getFullUrl( String url, final String volName) {
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        String replace = url.replace(NOVEL_URL, "");
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("?t=");
        sb.append(currentTimeMillis);
        sb.append("&k=");
        sb.append(MD5.MD5Encode(NOVEL_KEY + replace + currentTimeMillis).toLowerCase());
        return sb.toString();
    }
}
