package com.example.txtledbluetooth.bean;

/**
 * Created by KomoriWu
 * on 2017-04-22.
 */

public class Lighting {
    private String lightingName;
    private int lightingIcon;

    public Lighting(String lightingName, int lightingIcon) {
        this.lightingName = lightingName;
        this.lightingIcon = lightingIcon;
    }

    public String getLightingName() {
        return lightingName;
    }

    public void setLightingName(String lightingName) {
        this.lightingName = lightingName;
    }

    public int getLightingIcon() {
        return lightingIcon;
    }

    public void setLightingIcon(int lightingIcon) {
        this.lightingIcon = lightingIcon;
    }

}
