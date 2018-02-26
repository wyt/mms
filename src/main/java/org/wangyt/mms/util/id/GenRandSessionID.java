package org.wangyt.mms.util.id;

import java.security.SecureRandom;

/**
 * generate a random session ID
 * 
 * @author WANG YONG TAO
 *
 */
public class GenRandSessionID {

	private static final SecureRandom PRNG = new SecureRandom();

	public static String gen() {
		byte[] sid = new byte[32];
		PRNG.nextBytes(sid);
		String sessionID = Hex.encode(sid);

		return sessionID;
	}

	public static void main(String[] args) {
		System.out.println("generate a random session ID:\r\n" + gen());
	}
}
