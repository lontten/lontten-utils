package com.lontten.util.json;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class DemoUser {
    private Long id;
    private Integer age;
    private String name;

    private Boolean bool;


    private UUID uuid;

    private LocalDate date;
    private LocalDateTime dateTime;
    private LocalTime time;

    public DemoUser() {
    }

    public DemoUser(Long id, Integer age, String name, Boolean bool, UUID uuid, LocalDate date, LocalDateTime dateTime, LocalTime time) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.bool = bool;
        this.uuid = uuid;
        this.date = date;
        this.dateTime = dateTime;
        this.time = time;
    }

    @Override
    public String toString() {
        return "DemoUser{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", aBoolean=" + bool +
                ", uuid=" + uuid +
                ", date=" + date +
                ", dateTime=" + dateTime +
                ", time=" + time +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getBool() {
        return bool;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
