package com.example.dailycollection;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ItemData {
    private String category,date,mobile,uid,itemid;
    private int amount,todo;
   private Boolean star;
private long time;

    public ItemData(String category, String date, String mobile, String uid, String itemid, int amount, int todo, Boolean star, long time) {
        this.category = category;
        this.date = date;
        this.mobile = mobile;
        this.uid = uid;
        this.itemid = itemid;
        this.amount = amount;
        this.todo = todo;
        this.star = star;
        this.time = time;
    }
    public String getItemid() {
        return itemid;
    }
    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public ItemData() {
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getTodo() {
        return todo;
    }

    public void setTodo(int todo) {
        this.todo = todo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Boolean getStar() {
        return star;
    }

    public void setStar(Boolean star) {
        this.star = star;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("category", category);
        result.put("date", date);
        result.put("mobile", mobile);
        result.put("itemid", itemid);
        result.put("star", star);
        result.put("amount",amount);
        result.put("todo",todo);
        result.put("time",time);

        return result;
    }
}
