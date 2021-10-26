package com.atr.lowadventure;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class Textures {
    public HashMap<String, Bitmap> textures;
    public HashMap<String, byte[]> texturesNew;
    public Context context;
    public GameScreen gm;
    public int buttonSize;
    public final int textureSize=16;
    public boolean oldMethod=true;
    public BitmapFactory.Options options;

    public Textures(GameScreen gm){
        this.context=gm.context;
        this.gm=gm;
        buttonSize=gm.buttonSize;
        options = new BitmapFactory.Options();
        options.inScaled = false;
    }

    public void init(){

        buttonSize=gm.buttonSize;
        textures = new HashMap<String, Bitmap>();
        texturesNew = new HashMap<String, byte[]>();

        put("barrel1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.barrel1_16px, options), textureSize,textureSize+textureSize/2, false));

        put("water1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.water1_16px, options), textureSize,textureSize, false));

        put("radio", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.radio_16px, options), textureSize,textureSize, false));

        put("jug1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.jug1_16px, options), textureSize,textureSize+textureSize/2, false));

        put("grenade1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.grenade1_16px, options), textureSize,textureSize, false));

        put("flashlight", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.flashlight_16px, options), textureSize,textureSize, false));

        put("cup", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cup_16px, options), textureSize,textureSize, false));

        put("cookies", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cookies_16px, options), textureSize,textureSize, false));

        put("chatbutton", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.chatbutton_16px, options), buttonSize, buttonSize, false));

        put("tablebackground", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tablebackground_16px, options), buttonSize*2, buttonSize*2, false));

        put("dialogbackground", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.background_16px, options), buttonSize*2, buttonSize*2, false));

        put("palletebutton", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pallete_16px, options), buttonSize, buttonSize, false));

        put("gasbutton", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gasbutton_16px, options), buttonSize, buttonSize, false));

        put("brakebutton", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brakebutton_16px, options), buttonSize, buttonSize, false));

        put("ridebutton", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ridebutton_16px, options), buttonSize, buttonSize, false));

        put("car1r5", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car1r5_16px, options), textureSize*3, textureSize*2, false));

        put("car1l5",flipBitmap(textures.get("car1r5")));

        put("car1r4", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car1r4_16px, options), textureSize*3, textureSize*2, false));

        put("car1l4",flipBitmap(textures.get("car1r4")));

        put("car1r3", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car1r3_16px, options), textureSize*3, textureSize*2, false));

        put("car1l3",flipBitmap(textures.get("car1r3")));

        put("car1r2", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car1r2_16px, options), textureSize*3, textureSize*2, false));

        put("car1l2",flipBitmap(textures.get("car1r2")));

        put("car1r1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car1r1_16px, options), textureSize*3, textureSize*2, false));

        put("car1l1",flipBitmap(textures.get("car1r1")));

        put("cone1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cone1_16px, options), textureSize, textureSize, false));

        put("sofa1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sofa1_16px, options), textureSize*2, textureSize+textureSize/2, false));

        put("tree2cutdown", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tree2cutdown_16px, options), textureSize, textureSize, false));

        put("brickwallbevelru", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwallbevelru_16px, options), textureSize, textureSize*3, false));

        put("brickwallbevellu", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwallbevellu_16px, options), textureSize, textureSize*3, false));

        put("brickwallbevelld", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwallbevelld_16px, options), textureSize, textureSize*3, false));

        put("brickwallbevelrd", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwallbevelrd_16px, options), textureSize, textureSize*3, false));

        put("brickwallbeveld", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwallbeveld_16px, options), textureSize, textureSize*3, false));

        put("brickwallbevelu", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwallbevelu_16px, options), textureSize, textureSize*3, false));

        put("brickwallbevell", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwallbevell_16px, options), textureSize, textureSize*3, false));

        put("brickwallbevelr", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwallbevelr_16px, options), textureSize, textureSize*3, false));

        put("asphalt8", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt8_16px, options), textureSize, textureSize, false));

        put("asphalt9", rotateBitmap(textures.get("asphalt8"), 90));

        put("asphalt10", rotateBitmap(textures.get("asphalt8"), 180));

        put("asphalt11", rotateBitmap(textures.get("asphalt8"), 270));

        put("asphalt6", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt6_16px, options), textureSize, textureSize, false));

        put("asphalt12", rotateBitmap(textures.get("asphalt6"), 90));

        put("asphalt13", rotateBitmap(textures.get("asphalt6"), 270));

        put("asphalt7", rotateBitmap(textures.get("asphalt6"), 180));

        put("asphalt5", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt5_16px, options), textureSize, textureSize, false));

        put("asphalt14", rotateBitmap(textures.get("asphalt5"), 90));

        put("asphalt4", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt4_16px, options), textureSize, textureSize, false));

        put("asphalt15", rotateBitmap(textures.get("asphalt4"), 90));

        put("asphalt2", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt2_16px, options), textureSize, textureSize, false));

        put("asphalt3", flipBitmap(textures.get("asphalt2")));

        put("asphalt16", rotateBitmap(textures.get("asphalt3"), 90));

        put("asphalt17", rotateBitmap(textures.get("asphalt3"), 270));

        put("asphalt18", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt18_16px, options), textureSize, textureSize, false));

        put("asphalt28", rotateBitmap(textures.get("asphalt18"), 90));

        put("asphalt29", rotateBitmap(textures.get("asphalt18"), 180));

        put("asphalt30", rotateBitmap(textures.get("asphalt18"), 270));

        put("asphalt19", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt19_16px, options), textureSize, textureSize, false));

        put("asphalt31", rotateBitmap(textures.get("asphalt19"), 90));

        put("asphalt20", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt20_16px, options), textureSize, textureSize, false));

        put("asphalt34", rotateBitmap(textures.get("asphalt20"), 90));

        put("asphalt35", rotateBitmap(textures.get("asphalt20"), 180));

        put("asphalt36", rotateBitmap(textures.get("asphalt20"), 270));

        put("asphalt21", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt21_16px, options), textureSize, textureSize, false));

        put("asphalt37", rotateBitmap(textures.get("asphalt21"), 90));

        put("asphalt38", rotateBitmap(textures.get("asphalt21"), 180));

        put("asphalt39", rotateBitmap(textures.get("asphalt21"), 270));

        put("asphalt22", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt22_16px, options), textureSize, textureSize, false));

        put("asphalt40", rotateBitmap(textures.get("asphalt22"), 90));

        put("asphalt41", rotateBitmap(textures.get("asphalt22"), 180));

        put("asphalt42", rotateBitmap(textures.get("asphalt22"), 270));

        put("asphalt23", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt23_16px, options), textureSize, textureSize, false));

        put("asphalt24", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt24_16px, options), textureSize, textureSize, false));

        put("asphalt43", rotateBitmap(textures.get("asphalt24"), 90));

        put("asphalt44", rotateBitmap(textures.get("asphalt24"), 180));

        put("asphalt45", rotateBitmap(textures.get("asphalt24"), 270));

        put("asphalt25", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt25_16px, options), textureSize, textureSize, false));

        put("asphalt46", rotateBitmap(textures.get("asphalt25"), 90));

        put("asphalt47", rotateBitmap(textures.get("asphalt25"), 180));

        put("asphalt48", rotateBitmap(textures.get("asphalt25"), 270));

        put("asphalt26", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt26_16px, options), textureSize, textureSize, false));

        put("asphalt49", rotateBitmap(textures.get("asphalt26"), 90));

        put("asphalt50", rotateBitmap(textures.get("asphalt26"), 180));

        put("asphalt51", rotateBitmap(textures.get("asphalt26"), 270));

        put("asphalt27", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt27_16px, options), textureSize, textureSize, false));

        put("asphalt52", rotateBitmap(textures.get("asphalt27"), 90));

        put("asphalt53", rotateBitmap(textures.get("asphalt27"), 180));

        put("asphalt54", rotateBitmap(textures.get("asphalt27"), 270));

        put("asphalt", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.asphalt_16px, options), textureSize, textureSize, false));

        put("metalsheet", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ironsheet_16px, options), textureSize, textureSize, false));

        put("buttonright", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.buttonright_16px, options), buttonSize, buttonSize, false));

        put("buttonleft", flipBitmap(textures.get("buttonright")));

        put("buttonrotate", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.buttonrotate_16px, options), buttonSize, buttonSize, false));

        put("chair1d", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.chair1d_16px, options), textureSize, textureSize+textureSize/2, false));

        put("chair1u", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.chair1u_16px, options), textureSize, textureSize+textureSize/2, false));

        put("chair1l", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.chair1l_16px, options), textureSize, textureSize+textureSize/2, false));

        put("chair1r", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.chair1_16px, options), textureSize, textureSize+textureSize/2, false));

        put("table1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.table1_16px, options), textureSize*2, textureSize+textureSize/2, false));

        put("brickwallwindow5", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwallwindow5_16px, options), textureSize, textureSize*3, false));

        put("brickwallwindow4", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwallwindow4_16px, options), textureSize, textureSize*3, false));

        put("brickwallwindow3", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwallwindow3_16px, options), textureSize, textureSize*3, false));

        put("brickwallwindow1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwallwindow1_16px, options), textureSize, textureSize*3, false));

        put("brickwallwindow2", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwallwindow2_16px, options), textureSize, textureSize*3, false));

        put("nekohatu", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nekohatu_16px, options), textureSize*3/2,textureSize+textureSize*3/2, false));

        put("nekohatd", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nekohatd_16px, options), textureSize*3/2, textureSize+textureSize*3/2, false));

        put("nekohatr", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nekohatr_16px, options), textureSize*3/2, textureSize+textureSize*3/2, false));

        put("nekohatl", flipBitmap(textures.get("nekohatr")));

        put("nekohairu", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nekohairu_16px, options), textureSize*3/2,textureSize+textureSize*3/2, false));

        put("nekohaird", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nekohaird_16px, options), textureSize*3/2, textureSize+textureSize*3/2, false));

        put("nekohairr", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nekohairr_16px, options), textureSize*3/2, textureSize+textureSize*3/2, false));

        put("nekohairl", flipBitmap(textures.get("nekohairr")));

        put("tree2", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tree2_16px, options), textureSize*2, textureSize*2, false));

        put("wood2", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wood2_16px, options), textureSize, textureSize, false));

        put("warderobeblock", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.warderobeblock_16px, options), textureSize, textureSize*2, false));

        put("guardblockbad", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.guardblockbad_16px, options), textureSize*3/2, textureSize*3/2, false));

        put("guardblock", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.guardblock_16px, options), textureSize*3/2, textureSize*3/2, false));

        put("scannerblockbad", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.scannerblockbad_16px, options), textureSize*3, textureSize, false));

        put("scannerblock", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.scannerblock_16px, options), textureSize*3, textureSize, false));

        put("profileblock", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.profileblock_16px, options), textureSize, textureSize*2, false));

        put("settingsblock", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.settingsblock_16px, options), textureSize, textureSize, false));

        put("brickwall", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brickwall_16px, options), textureSize, textureSize*3, false));

        put("mapblockbad", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mapblockbad_16px, options), textureSize, textureSize, false));

        put("mapblock", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mapblock_16px, options), textureSize, textureSize, false));

        put("idblockbad", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.idblockbad_16px, options), textureSize, textureSize, false));

        put("idblock", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.idblock_16px, options), textureSize, textureSize, false));

        put("construction", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.construction_16px, options), textureSize, textureSize, false));

        put("compassblock", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.compassblock_16px, options), textureSize, textureSize, false));

        put("teleportbad", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.teleportbad_16px, options), textureSize, textureSize, false));

        put("iron", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.iron_16px, options), textureSize, textureSize, false));

        put("brick", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brick_16px, options), textureSize, textureSize, false));

        put("teleport", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.teleport_16px, options), textureSize, textureSize, false));

        put("logblock", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.logblock_16px, options), textureSize, textureSize, false));

        put("sand", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sand_16px, options), textureSize, textureSize, false));

        put("wood", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wood_16px, options), textureSize, textureSize, false));

        put("stone", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.stone_16px, options), textureSize, textureSize, false));

        put("ceramics", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ceramics_16px, options), textureSize, textureSize, false));

        put("pu1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pu1_16px, options), textureSize, textureSize*2, false));

        put("pu2", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pu2_16px, options), textureSize, textureSize*2, false));

        put("pu3", flipBitmap(textures.get("pu2")));

        put("pd1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pd1_16px, options), textureSize, textureSize*2, false));

        put("pd2", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pd2_16px, options), textureSize, textureSize*2, false));

        put("pd3", flipBitmap(textures.get("pd2")));

        put("pr1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pr1_16px, options), textureSize, textureSize*2, false));

        put("pr2", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pr2_16px, options), textureSize, textureSize*2, false));

        put("pr3", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pr3_16px, options), textureSize, textureSize*2, false));

        put("pl1", flipBitmap(textures.get("pr1")));

        put("pl2", flipBitmap(textures.get("pr2")));

        put("pl3", flipBitmap(textures.get("pr3")));

        put("atr", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.atrlogo_16px, options), buttonSize, buttonSize, false));

        put("table", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.table_16px, options), textureSize, textureSize, false));

        put("plusbutton", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.plusbutton_16px, options), buttonSize, buttonSize, false));

        put("minusbutton", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.minusbutton_16px, options), buttonSize, buttonSize, false));

        put("grass1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.grass1_16px, options), textureSize, textureSize, false));

        put("tree1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tree1_16px, options), textureSize, textureSize*2, false));

        put("flower1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.flower1_16px, options), textureSize, textureSize, false));

        put("water", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.water_16px, options), textureSize, textureSize, false));

        put("copybutton", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.copybutton_16px, options), buttonSize, buttonSize, false));

        put("button2", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button2_16px, options), buttonSize, buttonSize, false));

        put("button1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button1_16px, options), buttonSize, buttonSize, false));

        put("indicator1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.indicator1_16px, options), textureSize, textureSize, false));

        put("indicator3", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.indicator3_16px, options), textureSize, textureSize, false));

        put("inventory1", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.inventory1_16px, options), buttonSize, buttonSize, false));

        put("inventory2", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.inventory2_16px, options), buttonSize, buttonSize, false));

        put("grass", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.grass_16px, options), textureSize, textureSize, false));

        put("dirt", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dirt_16px, options), textureSize, textureSize, false));

        put("barrier", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.barrier_16px, options), textureSize, textureSize, false));

        put("showel", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.indicator2_16px, options), textureSize, textureSize, false));

        put("map", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_16px, options), buttonSize, buttonSize, false));

        put("inventory", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.inventory_16px, options), buttonSize, buttonSize, false));

        put("pause", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pause_16px, options), buttonSize, buttonSize, false));

        put("usebutton", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.usebutton_16px, options), buttonSize, buttonSize, false));

        put("circlebutton", Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.circlebutton_16px, options), buttonSize, buttonSize, false));


    }

    public void put(String name, Bitmap texture){
        if(oldMethod) {
            textures.put(name, texture);
        } else {
            texturesNew.put(name, toByte(texture));
        }
    }

    public Bitmap get(String name){
        if(oldMethod) {
            return textures.get(name);
        } else {
            return toBitmap(texturesNew.get(name));
        }
    }

    public boolean containsKey(Object name){
        if(oldMethod) {
            return textures.containsKey(name);
        } else {
            return texturesNew.containsKey(name);
        }
    }

    private byte[] toByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,stream);
      return stream.toByteArray();
    }

    private Bitmap toBitmap(byte[] bytes){

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public Bitmap rotateBitmap(Bitmap source, int angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public Resources getResources(){
        return context.getResources();
    }

    Bitmap flipBitmap(Bitmap src)
    {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return dst;
    }
}
