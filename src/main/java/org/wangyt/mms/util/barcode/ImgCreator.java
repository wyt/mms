// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.


package org.wangyt.mms.util.barcode;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImgCreator
{

    private Image img;
    public Graphics g;

    public ImgCreator()
    {
    }

    public Image getImage(int i, int j)
    {
        int k = j <= i ? i : j;
        img = new BufferedImage(k, k, 13);
        g = ((BufferedImage)img).createGraphics();
        return img;
    }

    public Graphics getGraphics()
    {
        return g;
    }
}
