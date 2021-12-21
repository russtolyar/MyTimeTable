package java.com.solvd.timetable.domain;

import java.util.List;

public class Teacher {

    private Long id;
    private String firstName;
    private String lastName;
    private List<Subject> subjects;
    private boolean firstLessonFlag = true;
    private boolean secondLessonFlag = true;
    private boolean thirdLessonFlag = true;
    private boolean fourthLessonFlag = true;
    private boolean fifthLessonFlag = true;
    private boolean sixthLessonFlag = true;
    private boolean seventhLessonFlag = true;

    public Teacher() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public boolean isFirstLessonFlag() {
        return firstLessonFlag;
    }

    public void setFirstLessonFlag(boolean firstLessonFlag) {
        this.firstLessonFlag = firstLessonFlag;
    }

    public boolean isSecondLessonFlag() {
        return secondLessonFlag;
    }

    public void setSecondLessonFlag(boolean secondLessonFlag) {
        this.secondLessonFlag = secondLessonFlag;
    }

    public boolean isThirdLessonFlag() {
        return thirdLessonFlag;
    }

    public void setThirdLessonFlag(boolean thirdLessonFlag) {
        this.thirdLessonFlag = thirdLessonFlag;
    }

    public boolean isFourthLessonFlag() {
        return fourthLessonFlag;
    }

    public void setFourthLessonFlag(boolean fourthLessonFlag) {
        this.fourthLessonFlag = fourthLessonFlag;
    }

    public boolean isFifthLessonFlag() {
        return fifthLessonFlag;
    }

    public void setFifthLessonFlag(boolean fifthLessonFlag) {
        this.fifthLessonFlag = fifthLessonFlag;
    }

    public boolean isSixthLessonFlag() {
        return sixthLessonFlag;
    }

    public void setSixthLessonFlag(boolean sixthLessonFlag) {
        this.sixthLessonFlag = sixthLessonFlag;
    }

    public boolean isSeventhLessonFlag() {
        return seventhLessonFlag;
    }

    public void setSeventhLessonFlag(boolean seventhLessonFlag) {
        this.seventhLessonFlag = seventhLessonFlag;
    }

    @Override
    public String toString() {
        return " " + firstName +
                " " + lastName;
    }
}
