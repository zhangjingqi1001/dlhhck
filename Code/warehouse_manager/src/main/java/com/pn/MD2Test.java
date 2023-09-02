package com.pn;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;

import java.security.KeyPair;
import java.util.Base64;

public class MD2Test {

    public static void main(String[] args) {
//      生成用于非对称加密的公钥和私钥，仅用于非对称加密
//      将公钥和私钥相关联，以便在加密、解密、签名和验证等操作中使用
//      参数"SM2"表示使用SM2（椭圆曲线密码学）算法生成密钥对
        KeyPair pair = SecureUtil.generateKeyPair("SM2");

        System.out.println("私钥对象 ； "+ pair.getPrivate());
        System.out.println("公钥对象 ； "+ pair.getPublic());

//      密钥对通常用于非对称加密算法
//      公钥用于加密数据
//      调用getEncoded()方法，可以获取私钥的字节数组表示形式，使用特定编码规则将私钥转换为字节数组
        byte[] privateKey = pair.getPrivate().getEncoded();
//      而私钥用于解密数据或签名
        byte[] publicKey = pair.getPublic().getEncoded();


//      私钥 - encodeToString将字节数组转换为Base64编码格式的字符串
        String privateKeyStr= Base64.getEncoder().encodeToString(privateKey);
        System.out.println("私钥："+privateKeyStr);

//      公钥
        String publicKeyStr=Base64.getEncoder().encodeToString(publicKey);
        System.out.println("公钥："+publicKeyStr);


//      加密
        // 公钥加密，私钥解密
        SM2 sm2 = SmUtil.sm2(null, publicKey);
        String content = "我是Hanley.";
        String encryptStr = sm2.encryptBcd(content, KeyType.PublicKey);
        System.out.println(encryptStr);


//      公钥加密，私钥解密
        SM2 sm22 = SmUtil.sm2(privateKeyStr, null);
        String decryptStr = StrUtil.utf8Str(sm22.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        System.out.println("解密后数据 "+decryptStr);

    }
}
