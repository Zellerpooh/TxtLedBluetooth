package com.example.txtledbluetooth.utils;

/**
 * Created by KomoriWu
 * on 2017-04-24.
 */

public class BleCommandUtils {
    //总体格式：GP$<指令数>$<指令1>;<指令2>;…;<指令n>$
    public static final String HEAD = "GP$1$";
    public static final String END = "$";
    //灯光部分
    public static final String LIGHT_MODEL = ",a,0:*";
    public static final String MOON_LIGHT = HEAD + "lmol" + LIGHT_MODEL + END;
    public static final String FIREWORK = HEAD + "lfwk" + LIGHT_MODEL + END;
    public static final String BLUE_SKIES = HEAD + "lhwl" + LIGHT_MODEL + END;
    public static final String RAINBOW = HEAD + "lrbw" + LIGHT_MODEL + END;
    public static final String PULSATE = HEAD + "lmsa" + LIGHT_MODEL + END;
    public static final String GLOW = HEAD + "lclg" + LIGHT_MODEL + END;
    public static final String MONOCHROME = HEAD + "lcbn" + LIGHT_MODEL + END;
    //其他设置
    public static final String LIGHT_SPEED = HEAD + "espd,hwl:"+ END;
    public static final String LIGHT_BRIGHT = HEAD + "elux,hwl:"+ END;
    public static final String OPEN = HEAD + "etof,all:1" + END;
    public static final String CLOSE = HEAD + "etof,all:0" + END;
    public static final String RESET = HEAD + "erst" + END;
    public static final String CLOSE_SOUND = HEAD + "esvt:0" + END;
    public static final String OPEN_SOUND = HEAD + "esvt:1" + END;
}
