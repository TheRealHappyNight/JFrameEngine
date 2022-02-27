package snake.internal.resourceloading;

public enum Resources {
    HEADUP("headUp", "Snake/PlayerModel/HeadUp.png"),
    HEADDOWN("headDown", "Snake/PlayerModel/HeadDown.png"),
    HEADLEFT("headLeft", "Snake/PlayerModel/HeadLeft.png"),
    HEADRIGHT("headRight", "Snake/PlayerModel/HeadRight.png"),
    BODYHORIZONTAL("bodyHor", "Snake/PlayerModel/BodyHorizontal.png"),
    BODYVERTICAL("bodyVer", "Snake/PlayerModel/BodyVertical.png"),
    PICKUP("collectible", "Snake/Environment/Pickup.png"),
    FENCELEFTRIGHTMID("fenceMid", "Snake/Environment/FenceLeftRightMid.png"),
    FENCELEFT("fenceLeft", "Snake/Environment/FenceLeft.png"),
    FENCERIGHT("fenceRight", "Snake/Environment/FenceRight.png"),
    FENCESINGLE("fenceSingle", "Snake/Environment/FenceSingle.png"),
    ROAD("road", "Snake/Environment/Road.png"),
    FENCEDOWN("fenceUp", "Snake/Environment/FenceUp.png"),
    FENCEUP("fenceDown", "Snake/Environment/FenceDown.png"),
    FENCEUPDOWNMID("fenceUpDown", "Snake/Environment/FenceUpDownMid.png"),
    CORNERUPLEFT("CornerUpLeft", "Snake/Environment/CornerUpLeft.png"),
    CORNERUPRIGHT("CornerUpRight", "Snake/Environment/CornerUpRight.png"),
    CORNERDOWNLEFT("CornerDownLeft", "Snake/Environment/CornerDownLeft.png"),
    CORNERDOWNRIGHT("CornerDownRight", "Snake/Environment/CornerDownRight.png");

    private final String key;
    private final String filePath;

    //File Path should have the ROOT in resources folder
    Resources(String key, String filePath) {
        this.key = key;
        this.filePath = filePath;
    }

    public String getKey() {
        return key;
    }

    public String getFilePath() {
        return filePath;
    }
}
