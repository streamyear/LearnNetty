package com.streamyear.netty7.version1;

public class UserInfo {
	private int age;
	private String name;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserInfo{" +
				"age=" + age +
				", name='" + name + '\'' +
				'}';
	}
}
