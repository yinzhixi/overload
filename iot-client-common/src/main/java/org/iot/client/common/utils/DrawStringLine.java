package org.iot.client.common.utils;

public class DrawStringLine{
    private int fontX = 10,fontY = 120,fontSize = 52,lineSpace=20;
    private String[] lines;
    public DrawStringLine() {}
    public DrawStringLine(int fontX,int fontY,int fontSize,int lineSpace,String[] lines) {
        this.fontX = fontX;
        this.fontY = fontY;
        this.fontSize = fontSize;
        this.lineSpace = lineSpace;
        this.lines = lines;
    }
    public int getFontX() {
        return fontX;
    }
    public void setFontX(int fontX) {
        this.fontX = fontX;
    }
    public int getFontY() {
        return fontY;
    }
    public void setFontY(int fontY) {
        this.fontY = fontY;
    }
    public int getFontSize() {
        return fontSize;
    }
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    public int getLineSpace() {
        return lineSpace;
    }
    public void setLineSpace(int lineSpace) {
        this.lineSpace = lineSpace;
    }
    public String[] getLines() {
        return lines;
    }
    public void setLines(String[] lines) {
        this.lines = lines;
    }
}
