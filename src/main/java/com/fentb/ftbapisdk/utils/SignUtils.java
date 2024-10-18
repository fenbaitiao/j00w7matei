package com.fentb.ftbapisdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签名工具
 *

 */
public class SignUtils {
    /**
     * 生成签名
     * @param salt
     * @param secretKey
     * @return
     */
    public static String genSign(String salt, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = salt + "." + secretKey;
        return md5.digestHex(content);
    }
}
