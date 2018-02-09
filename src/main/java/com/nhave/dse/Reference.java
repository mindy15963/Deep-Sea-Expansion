package com.nhave.dse;

public class Reference
{
    public static final String NHCORE_VERSION = "4.0.1";
    
	public static final String MODID = "dse";
    public static final String NAME = "Deep Sea Expansion";
    public static final String VERSION = "A3";
    public static final String DEPENDENCIES = 
    		"required-after:nhc@[" + NHCORE_VERSION + ",)";
    
    public static final String GUIFACTORY = "com.nhave.dse.client.gui.ModGuiFactory";
    public static final String CLIENT_PROXY = "com.nhave.dse.proxy.ClientProxy";
    public static final String COMMON_PROXY = "com.nhave.dse.proxy.CommonProxy";
}