import dmzjapiexplore.DmzjParamFinder;
import dmzjapiexplore.DmzjRSADecrypt;
import dmzjapiexplore.RawRespSamples;

public class Main {

//    private static String resp = "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";


    public static void main(String[] args) {
//        Huffman.INSTANCE.run();
//        ClassesTest.INSTANCE.run();
//        DmzjRSADecrypt.run(RawRespSamples.NOVEL_DETAIL, "/Users/zzy/Downloads/dmzj_resp.bin");
//        DmzjRSADecrypt.run(RawRespSamples.COMIC_DETAIL, "/Users/zzy/Downloads/dmzj_resp_comic_detail.bin");
        DmzjRSADecrypt.run(RawRespSamples.COMIC_CHAPTER, "/Users/zzy/Downloads/dmzj_resp_comic_chapter.bin");
//        NovelApiHelper.run();
//        DmzjParamFinder.run(DmzjParamFinder.ParamType._ID);
    }

}
