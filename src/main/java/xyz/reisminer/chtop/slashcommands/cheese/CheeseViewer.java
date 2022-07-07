package xyz.reisminer.chtop.slashcommands.cheese;

public class CheeseViewer {
    public long getDiscordID() {
        return discordID;
    }

    public String getDbdName() {
        return dbdName;
    }

    public String getYtName() {
        return ytName;
    }

    public int getGamesLeft() {
        return gamesLeft;
    }

    public void setGamesLeft(int gamesLeft) {
        this.gamesLeft = gamesLeft;
    }

    private long discordID;
    private String dbdName;
    private String ytName;
    private int gamesLeft;

    /**
     * @param discordID ID of discord user
     * @param dbdName   dbd ign
     * @param ytName    yt channel name
     */
    public CheeseViewer(long discordID, String dbdName, String ytName) {
        this.discordID = discordID;
        this.dbdName = dbdName;
        this.ytName = ytName;
        gamesLeft = 2;
    }
}
