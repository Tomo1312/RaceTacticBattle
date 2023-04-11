package com.racetacticbattle.game.Models;

public class SearchingRoomPlayer {
    String opponentPlayerUId, ownUId;

    public SearchingRoomPlayer() {
    }

    public SearchingRoomPlayer(String ownUId) {
        this.opponentPlayerUId = "none";
        this.ownUId = ownUId;
    }

    public SearchingRoomPlayer(String ownUId, String opponentPlayerUId) {
        this.opponentPlayerUId = opponentPlayerUId;
        this.ownUId = ownUId;
    }

    public SearchingRoomPlayer(SearchingRoomPlayer opponentPlayerTmp) {
    }

    public String getOpponentPlayerUId() {
        return opponentPlayerUId;
    }

    public void setOpponentPlayerUId(String opponentPlayerUId) {
        this.opponentPlayerUId = opponentPlayerUId;
    }

    public String getOwnUId() {
        return ownUId;
    }

    public void setOwnUId(String ownUId) {
        this.ownUId = ownUId;
    }
}
