package com.codecool.enterprise.overcomplicated.model;

import java.util.ArrayList;

public class TictactoeGame {
    public TictactoeGame() {
        fields.add(getNullField());
        fields.add(getFirstField());
        fields.add(getSecondField());
        fields.add(getThirdField());
        fields.add(getFourthField());
        fields.add(getFifthField());
        fields.add(getSixthField());
        fields.add(getSeventhField());
        fields.add(getEightField());
    }

    public int getNullField() {
        return nullField;
    }

    public void setNullField(int nullField) {
        this.nullField = nullField;
        fields.set(0, nullField);
    }

    public int getFirstField() {
        return firstField;
    }

    public void setFirstField(int firstField) {
        this.firstField = firstField;
        fields.set(1, firstField);
    }

    public int getSecondField() {
        return secondField;
    }

    public void setSecondField(int secondField) {
        this.secondField = secondField;
        fields.set(2, secondField);
    }

    public int getThirdField() {
        return thirdField;
    }

    public void setThirdField(int thirdField) {
        this.thirdField = thirdField;
        fields.set(3, thirdField);
    }

    public int getFourthField() {
        return fourthField;
    }

    public void setFourthField(int fourthField) {
        this.fourthField = fourthField;
        fields.set(4, fourthField);
    }

    public int getFifthField() {
        return fifthField;
    }

    public void setFifthField(int fifthField) {
        this.fifthField = fifthField;
        fields.set(5, fifthField);
    }

    public int getSixthField() {
        return sixthField;
    }

    public void setSixthField(int sixthField) {
        this.sixthField = sixthField;
        fields.set(6, sixthField);
    }

    public int getSeventhField() {
        return seventhField;
    }

    public void setSeventhField(int seventhField) {
        this.seventhField = seventhField;
        fields.set(7, seventhField);
    }

    public int getEightField() {
        return eightField;
    }

    public void setEightField(int eightField) {
        this.eightField = eightField;
        fields.set(8, eightField);
    }

    public ArrayList<Integer> getFields() {
        return fields;
    }

    ArrayList<Integer> fields = new ArrayList<>();
    int nullField;
    int firstField;
    int secondField;
    int thirdField;
    int fourthField;
    int fifthField;
    int sixthField;
    int seventhField;
    int eightField;

    public void clearTable(){
        setNullField(0);
        setFirstField(0);
        setSecondField(0);
        setThirdField(0);
        setFourthField(0);
        setFifthField(0);
        setSixthField(0);
        setSeventhField(0);
        setEightField(0);
    }

    public String getTable(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fields.size(); i++){
            switch (fields.get(i)){
                case 0: stringBuilder.append("-");
                    break;
                case 1: stringBuilder.append("O");
                    break;
                case 2: stringBuilder.append("X");
            }
        }
        return stringBuilder.toString();
    }
}
