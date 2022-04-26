package org.prgms.gccoffee.model;

import static com.google.common.base.Preconditions.*;

import java.util.Objects;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

/**
 * VO 이메일 형태로 데이터를 받는다
 */
public class Email {
	private final String address;

	public Email(String address) {
		checkArgument(address != null, "address는 null이면 안됩니다.");
		checkArgument(address.length() >= 4 && address.length() <= 50, "address는 4자 이상 50자 이하로 작성하여야 합니다.");
		checkArgument(checkAddress(address), "Invalid Address");
		this.address = address;
	}

	// 만약 특정 문자열  ex 전화번호, 이메일 등을 입력 받고 싶을 때는 패턴을 사용하자
	private static boolean checkAddress(String address){
		return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address);
	}

	// VO는 equals와 hashcode를 재정의 해 주는 게 좋다.
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Email email = (Email)o;
		return Objects.equals(address, email.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address);
	}

	public String getAddress() {
		return address;
	}
}
