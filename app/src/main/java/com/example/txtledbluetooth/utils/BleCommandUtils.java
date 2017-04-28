package com.example.txtledbluetooth.utils;

/**
 * Created by KomoriWu
 * on 2017-04-24.
 */

public class BleCommandUtils {
    //总体格式：GP$<指令数>$<指令1>;<指令2>;…;<指令n>$
    public static final String HEAD = "GP$1$";
    public static final String END = "$";
    //指令代号
    public static final String MOON_LIGHT = "mol";
    public static final String FIREWORK = "fwk";
    public static final String BLUE_SKIES = "hwl";
    public static final String RAINBOW = "rbw";
    public static final String PULSATE = "msa";
    public static final String GLOW = "clg";
    public static final String MONOCHROME = "cbn";
    //灯光部分
    public static final String LIGHT_MODEL0 = ",a,0:*";
    public static final String LIGHT_MODEL1 = ",a,1:*";
    public static final String LIGHT_MODEL2 = ",a,2:*";
    public static final String LIGHT_MODEL3 = ",a,3:*";
    public static final String LIGHT_UPDATE_COLOR0 = ",s,0:";
    public static final String LIGHT_UPDATE_COLOR1 = ",s,1:";
    public static final String LIGHT_UPDATE_COLOR2 = ",s,2:";
    public static final String MOON_LIGHT_COMMAND = HEAD + "l" + MOON_LIGHT + LIGHT_MODEL0 + END;
    public static final String FIREWORK_COMMAND = HEAD + "l" + FIREWORK + LIGHT_MODEL0 + END;
    public static final String BLUE_SKIES_COMMAND = HEAD + "l" + BLUE_SKIES + LIGHT_MODEL0 + END;
    public static final String RAINBOW_COMMAND = HEAD + "l" + RAINBOW + LIGHT_MODEL0 + END;
    public static final String PULSATE_COMMAND = HEAD + "l" + PULSATE + LIGHT_MODEL0 + END;
    public static final String GLOW_COMMAND = HEAD + "l" + GLOW + LIGHT_MODEL0 + END;
    public static final String MONOCHROME_COMMAND = HEAD + "l" + MONOCHROME + LIGHT_MODEL0 + END;

    //其他设置
    public static final String LIGHT_SPEED = "espd,";
    public static final String LIGHT_BRIGHT = "elux,";
    public static final String OPEN = HEAD + "etof,all:1" + END;
    public static final String CLOSE = HEAD + "etof,all:0" + END;
    public static final String RESET = HEAD + "erst" + END;
    public static final String CLOSE_SOUND = HEAD + "esvt:0" + END;
    public static final String OPEN_SOUND = HEAD + "esvt:1" + END;

    //灯光速度
    public static String getLightSpeedCommand(String lightNo, String speedHex) {
        return HEAD + LIGHT_SPEED + lightNo + ":" + speedHex + END;
    }

    //灯光亮度
    public static String getLightBrightCommand(String lightNo, String brightHex) {
        return HEAD + LIGHT_BRIGHT + lightNo + ":" + brightHex + END;
    }

    public static String getLightNo(int position) {
        String lightNo = "";
        switch (position) {
            case 0:
                lightNo = MOON_LIGHT;
                break;
            case 1:
                lightNo = FIREWORK;
                break;
            case 2:
                lightNo = BLUE_SKIES;
                break;
            case 3:
                lightNo = RAINBOW;
                break;
            case 4:
                lightNo = PULSATE;
                break;
            case 5:
                lightNo = GLOW;
                break;
            case 6:
                lightNo = MONOCHROME;
                break;
        }
        return lightNo;
    }

    public static String getInitCommandByType(String lightNo, int position) {
        String command = LIGHT_MODEL0;
        switch (position) {
            case 0:
                command = LIGHT_MODEL0;
                break;
            case 1:
                command = LIGHT_MODEL1;
                break;
            case 2:
                command = LIGHT_MODEL2;
                break;
            case 3:
                command = LIGHT_MODEL3;
                break;
        }
        return HEAD + "l" + lightNo + command + END;
    }

    public static String updateLightColor(String lightNo,int position, String color) {
        String command = LIGHT_UPDATE_COLOR0;
        switch (position) {
            case 0:
                command = LIGHT_UPDATE_COLOR0;
                break;
            case 1:
                command = LIGHT_UPDATE_COLOR1;
                break;
            case 2:
                command = LIGHT_UPDATE_COLOR2;
                break;
        }
        return HEAD + "l" + lightNo + command + "#" + color + END;
    }
}
