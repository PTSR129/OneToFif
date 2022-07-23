package com.example.onetofif;

import java.util.Random;

public class gameEngine {
    static final int gameMapWidth = 5;
    static final int gameMapHeight = 5;
    static final int gameMapSize = gameMapWidth * gameMapHeight;
    static final int gameGoal = 50;
    static int[][] gameMap = new int[gameMapWidth][gameMapHeight];
    static int location, width, height;
    static int currentNumber;

    static void init() {//gameMap 초기화
        initCurrentNumber();
        for (int i = 0; i < gameMapWidth; i++)
            for (int j = 0; j < gameMapHeight; j++) {
                gameMap[i][j] = 0;
            }
    }
    static void GenerateRandomMap(int[][] gameMap){
        //gameMap에 1부터 25까지 랜덤하게 넣음
        //배열을 섞거나, 1부터 차례대로 랜덤하게 넣으면 될듯
        //1차원 배열이 훨 간단했을텐데....................
        Random random = new Random();
        for (int i = 0; i < gameMapWidth * gameMapHeight; i++) {
            location = random.nextInt(gameMapHeight * gameMapWidth);
            width = location / 5;
            height = location % 5;
            if (gameMap[width][height] == 0)
                gameMap[width][height] = i + 1;
            else {
                while (gameMap[width][height] != 0) {
                    //getNextPoint 함수로 만들 것
                    width = width - 1;
                    if (width < 0) {
                        width = gameMapWidth - 1;
                        height = height - 1;
                    }
                    if (height < 0) {
                        height = gameMapHeight - 1;
                    }
                }
                gameMap[width][height] = i + 1;
            }
        }
        //gameMap 초기화 끝
    }

    static int[][] getGameMap() {
        GenerateRandomMap(gameMap);
        return gameMap;
    }

    static void initCurrentNumber() {
        //현재 클릭할 번호 초기화
        currentNumber = 1;
    }

    static int getCurrentNumber() {
        //현재 클릭할 번호를 return
        return currentNumber;
    }

    static String getNextBtnText() {
        //버튼이 클릭 되었다면, 버튼에 다음 나올 숫자를 return
        if(gameGoal >= currentNumber + gameMapSize) return Integer.toString(currentNumber + gameMapSize);
        else return "";
    }

    static void increaseCurrentNumber() {
        currentNumber += 1;//다음 클릭할 번호를 저장
    }
    static boolean lastCurrentNumber(){
        return (currentNumber == gameGoal);
    }
    static int getGoalNumber() {return gameGoal;}
}