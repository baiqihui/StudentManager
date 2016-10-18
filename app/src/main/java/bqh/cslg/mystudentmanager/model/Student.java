package bqh.cslg.mystudentmanager.model;

import java.io.Serializable;

/**
 * Created by baiqihui on 2016/10/13.
 */

public class Student implements Serializable{
    String number;
    String name;
    String gender;
    String birth;
    String native_place;
    String specialty;
    String phone;

    public Student() {
    }

    public Student(String number, String name, String gender, String birth, String native_place, String specialty, String phone) {
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.native_place = native_place;
        this.specialty = specialty;
        this.phone = phone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getNative_place() {
        return native_place;
    }

    public void setNative_place(String native_place) {
        this.native_place = native_place;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
