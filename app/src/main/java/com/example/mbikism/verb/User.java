package com.example.mbikism.verb;
public class User {

    public String birthday;
    public String firstName;
    public String lastName;
    public String userID;
    public String category;
    public String email;
    public String orgName;
    public String about;
    public int quizScore;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User (String userID, String firstName, String lastName, String category, String email, String birthday, int quizScore) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this. category = category;
        this.email = email;
        this.birthday = birthday;
        this.quizScore = quizScore;
    }

    public User (String userID, String firstName, String lastName, String category, String email, String orgName, String about, int quizScore) {
        this.userID = userID;
        this.orgName = orgName;
        this. category = category;
        this.email = email;
        this.about = about;
        this.quizScore = quizScore;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

