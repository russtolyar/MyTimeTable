package java.com.solvd.timetable.domain;

public class Room {

    private Long id;
    private String number;
    private RoomType roomType;
    private boolean firstLessonFlag = true;
    private boolean secondLessonFlag = true;
    private boolean thirdLessonFlag = true;
    private boolean fourthLessonFlag = true;
    private boolean fifthLessonFlag = true;
    private boolean sixthLessonFlag = true;
    private boolean seventhLessonFlag = true;

    public Room() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
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
        return "Cabinet #" + number;
    }
}
