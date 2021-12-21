package java.com.solvd.timetable.domain;

public class Subject {

    private Long id;
    private String name;
    private RoomType roomTypeNeeded;
    private boolean gradeFlag = true;
    private boolean firstLessonFlag = true;
    private boolean secondLessonFlag = true;
    private boolean thirdLessonFlag = true;
    private boolean fourthLessonFlag = true;
    private boolean fifthLessonFlag = true;
    private boolean sixthLessonFlag = true;
    private boolean seventhLessonFlag = true;

    public Subject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomType getRoomTypeNeeded() {
        return roomTypeNeeded;
    }

    public void setRoomTypeNeeded(RoomType roomTypeNeeded) {
        this.roomTypeNeeded = roomTypeNeeded;
    }

    public boolean isGradeFlag() {
        return gradeFlag;
    }

    public void setGradeFlag(boolean gradeFlag) {
        this.gradeFlag = gradeFlag;
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
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }

        Subject subject = (Subject) object;
        return this.getId().equals(subject.getId()) && this.getRoomTypeNeeded().name().equals(subject.getRoomTypeNeeded().name())
                && (this.getName() != null && this.getName().equals(subject.getName()));
    }

    @Override
    public String toString() {
        return " " + name;
    }
}
