package com.kamilachyla.guimodel;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.nio.Buffer;
import java.util.function.Function;

public class ImageProperty extends Property<BufferedImage> implements Observer {

    private final NumericProperty wProp;
    private final NumericProperty hProp;
    private final StringProperty fNameProp;

    public ImageProperty(String name, NumericProperty width, NumericProperty height, StringProperty fNameProp) {
        super(name, null);

        this.wProp = width;
        this.hProp = height;
        this.fNameProp = fNameProp;

        this.wProp.addListener(this);
        this.hProp.addListener(this);
        this.fNameProp.addListener(this);
    }

    @Override
    public void changed() {
        super.changed();
        //setValue(new BufferedImage(wProp.getValue().intValue(), hProp.getValue().intValue(), BufferedImage.TYPE_INT_ARGB));
    }

    public int getWidth() {
        return wProp.getValue().intValue();
    }

    public int getHeight() {
        return hProp.getValue().intValue();
    }

    @Override
    public Function<BufferedImage, String> getDebugFn() {
        return (BufferedImage ib) -> ib == null ? "<null>" : String.format("Image [%sx%s]", ib.getWidth(), ib.getHeight());
    }

    private String getFileName() {
        return fNameProp.getValue();
    }
}
