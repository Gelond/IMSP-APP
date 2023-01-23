package uac.imsp.clockingapp;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class FingerprintHelper {
	private static final String KEY_NAME = "fingerprint_key";
	private KeyStore keyStore;
	private final String KEY_TYPE="AndroidKeyStore";

	public FingerprintHelper() {
		try {


			keyStore = KeyStore.getInstance(KEY_TYPE);
			keyStore.load(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateKey() {
		try {

			if (keyStore.containsAlias(KEY_NAME)) {
				// key is already present in the Keystore
				return;
			}
				// key is not present in the Keystore

			KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(KEY_NAME,
					KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
					.setBlockModes(KeyProperties.BLOCK_MODE_CBC)
					.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);

			KeyGenerator keyGenerator = KeyGenerator.getInstance(
					KeyProperties.KEY_ALGORITHM_AES, KEY_TYPE);
			keyGenerator.init(builder.build());
			SecretKey key = keyGenerator.generateKey();
			Certificate[] chain = new Certificate[0]; //create an empty certificate chain
			keyStore.setKeyEntry(KEY_NAME, key, null, chain);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] encrypt(byte[] data) {
		try {
			Key key = keyStore.getKey(KEY_NAME, null);
			Cipher cipher = Cipher.getInstance
					("AES/CBC/PKCS7Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] decrypt(byte[] data) {
		try {
			Key key = keyStore.getKey(KEY_NAME, null);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean compareData(byte[] original, byte[] live) {
		/*byte[] decrypted = decrypt(original);
		return Arrays.equals(decrypted, live);*/
		byte [] encrypted=encrypt(live);
		return Arrays.equals(encrypted, original);
	}
}
