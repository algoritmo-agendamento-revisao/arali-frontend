package br.com.arali.app.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Table(name = "studies")
@Entity(name = "studies")
public class Study {
    @Id
    @GeneratedValue
    private Long id;
    private boolean completed;
    @ManyToOne
    @JoinColumn(name = "student_fk")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "card_fk")
    private Card card;
    private Date currentDate;
    private boolean isRight;
    private Date lastRepetition;
    private Date nextRepetition;
    private Integer timeForResolution;
    private Integer numberOfRepetitions;

    public static Date getDateOfLastStudy(List<Study> st) {
        Date date = null;
        for(Study study : st){
            if(date == null || study.getCurrentDate().getTime() < date.getTime()){
                date = study.getCurrentDate();
            }
        }
        return date;
    }

    public Student getStudent() {
        return student;
    }

    public Study setStudent(Student student) {
        this.student = student;
        return this;
    }

    public Card getCard() {
        return card;
    }

    public Study setCard(Card card) {
        this.card = card;
        return this;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public Study setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
        return this;
    }

    public boolean isRight() {
        return isRight;
    }

    public Study setRight(boolean right) {
        isRight = right;
        return this;
    }

    public Date getLastRepetition() {
        return lastRepetition;
    }

    public Study setLastRepetition(Date lastRepetition) {
        this.lastRepetition = lastRepetition;
        return this;
    }

    public Date getNextRepetition() {
        return nextRepetition;
    }

    public Study setNextRepetition(Date nextRepetition) {
        this.nextRepetition = nextRepetition;
        return this;
    }

    public Integer getTimeForResolution() {
        return timeForResolution;
    }

    public Study setTimeForResolution(Integer timeForResolution) {
        this.timeForResolution = timeForResolution;
        return this;
    }

    public Integer getNumberOfRepetitions() {
        return numberOfRepetitions;
    }

    public Study setNumberOfRepetitions(Integer numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
        return this;
    }

    public Integer getInterval(){
        Calendar d1 = Calendar.getInstance();
        d1.setTime(this.nextRepetition);

        Calendar d2 = Calendar.getInstance();
        d2.setTime(this.currentDate);

        Long nextRepetition = d1.getTimeInMillis();
        Long currentDate    = d2.getTimeInMillis();

        return (int) Math.floor((nextRepetition - currentDate) / (24 * 60 * 60 * 1000));
    }


    public void setId(Long aLong) {
        this.id = aLong;
    }
}