package com.example.doctorsdiabeticapp.Security;

import android.util.Base64;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class SecurityString {

    private final static String TAG = "Security";
    private final static String key = "passwordMessage";

    public SecurityString() {
    }

    public String encrypt(String value) {
        try {
            SecretKeySpec secretKeySpec = createKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException ex) {
            Log.e("Security", "NoSuchAlgorithmException: " + ex.toString());
        } catch (NoSuchPaddingException ex) {
            Log.e(TAG, " NoSuchPaddingException: " + ex.toString());
        } catch (InvalidKeyException ex) {
            Log.e(TAG, "InvalidKeyException: " + ex.toString());
        } catch (BadPaddingException ex) {
            Log.e(TAG, "BadPaddingException: " + ex.toString());
        } catch (IllegalBlockSizeException ex) {
            Log.e(TAG, "IllegalBlockSizeException: " + ex.toString());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.toString());
        }
        return "fail encrypt";

    }

    public String decrypt(String value) {
        try {
            SecretKeySpec secretKeySpec = createKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] original = Base64.decode(value, Base64.DEFAULT);
            byte[] decrypted = cipher.doFinal(original);
            return new String(decrypted);
        } catch (NoSuchAlgorithmException ex) {
            Log.e("Security", "NoSuchAlgorithmException: " + ex.toString());
        } catch (NoSuchPaddingException ex) {
            Log.e(TAG, " NoSuchPaddingException: " + ex.toString());
        } catch (InvalidKeyException ex) {
            Log.e(TAG, "InvalidKeyException: " + ex.toString());
        } catch (BadPaddingException ex) {
            Log.e(TAG, "BadPaddingException: " + ex.toString());
        } catch (IllegalBlockSizeException ex) {
            Log.e(TAG, "IllegalBlockSizeException: " + ex.toString());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.toString());
        }
        return "fail decrypt";
    }

    private SecretKeySpec createKey() {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
            messageDigest.update(bytes, 0, bytes.length);
            byte[] newKeys = messageDigest.digest();
            return new SecretKeySpec(newKeys, "AES");
        } catch (NoSuchAlgorithmException ex) {
            Log.e("Security", "NoSuchAlgorithmException: " + ex.toString());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.toString());
        }

        return null;
    }
}
