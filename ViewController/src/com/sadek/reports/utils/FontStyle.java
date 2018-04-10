package com.sadek.reports.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

import com.sadek.reports.BaseBean;

import java.io.IOException;

public class FontStyle extends BaseBean {
    private static Font fontDesc = null;
    int size;
    int style;
    BaseColor color;

    private String fontPath;

    public FontStyle() {
        setSize((int)new Font().getSize());
        setStyle(new Font().getStyle());
        setColor(new Font().getColor());
   }

    public FontStyle(int size) {
        setSize(size);
        setStyle(new Font().getStyle());
        setColor(new Font().getColor());
    }
    public FontStyle(BaseColor color) {
        setSize((int)new Font().getSize());
        setStyle(new Font().getStyle());
        setColor(color);
    }
    public FontStyle(int size, int style) {
        setSize(size);
        setStyle(style);
        setColor(new Font().getColor());
    }
    public FontStyle(int style, BaseColor color) {
        setSize(size);
        setStyle(style);
        setColor(color);
    }
    public FontStyle(int size, int style, BaseColor color) {
        setSize((int)new Font().getSize());
        setStyle(style);
        setColor(color);
    }


    public Font createFont() {


        try {
            fontDesc =
                    new Font(BaseFont.createFont(getArialFontPath(), BaseFont.IDENTITY_H, true), getSize(), getStyle(), getColor());
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontDesc;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getStyle() {
        return style;
    }

    public void setColor(BaseColor color) {
        this.color = color;
    }

    public BaseColor getColor() {
        return color;
    }
}
