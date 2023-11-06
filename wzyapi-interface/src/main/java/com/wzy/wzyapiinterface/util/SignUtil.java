package com.wzy.wzyapiinterface.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-10-02 14:45
 **/
public class SignUtil {

 public static String getSign(String body, String secretKey){
  Digester md5 = new Digester(DigestAlgorithm.MD5);
  String content = body+"."+secretKey;
  String digest = md5.digestHex(content);
  return digest;
 }
}
