package com.pn;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class MD4Test {

    public static void main(String[] args) throws InvalidCipherTextException {
        // 原始数据和密钥
        String plaintext = "Hello, World!";
        String key = "0123456789abcdef0123456789abcdef";

        // 将密钥转换为字节数组
        byte[] keyBytes = Hex.decode(key);

        // 生成初始化向量（IV）
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);

        // 初始化SM4加密器
        BlockCipher cipher = new SM4Engine();
        cipher = new CBCBlockCipher(cipher);
        PaddedBufferedBlockCipher paddedCipher = new PaddedBufferedBlockCipher(cipher);
        ParametersWithIV parameters = new ParametersWithIV(new KeyParameter(keyBytes), iv);
        paddedCipher.init(true, parameters);

        // 加密
        byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);
        byte[] ciphertextBytes = new byte[paddedCipher.getOutputSize(plaintextBytes.length)];
        int len = paddedCipher.processBytes(plaintextBytes, 0, plaintextBytes.length, ciphertextBytes, 0);
        paddedCipher.doFinal(ciphertextBytes, len);

        // 输出加密结果
        String ciphertext = new String(Hex.encode(ciphertextBytes));
        System.out.println("加密后的数据: " + ciphertext);

        // 解密
        paddedCipher.init(false, parameters);
        byte[] decryptedBytes = new byte[paddedCipher.getOutputSize(ciphertextBytes.length)];
        int decryptedLen = paddedCipher.processBytes(ciphertextBytes, 0, ciphertextBytes.length, decryptedBytes, 0);
        paddedCipher.doFinal(decryptedBytes, decryptedLen);

        // 输出解密结果
        String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);
        System.out.println("解密后的数据: " + decryptedText);
    }

    public static void main1(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());

        String input = "Hello, World!";

        // 创建MD4实例
        MessageDigest md4 = MessageDigest.getInstance("MD4", "BC");

        // 计算MD4哈希值
        byte[] hashBytes = md4.digest(input.getBytes());

        // 将字节数组转换为十六进制字符串
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        String md4Hash = sb.toString();

        System.out.println("MD4哈希值: " + md4Hash);


    }
}
