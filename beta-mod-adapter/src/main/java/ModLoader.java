import java.lang.annotation.Annotation;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.FileNotFoundException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.Random;
import org.lwjgl.input.Keyboard;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.logging.Handler;
import java.util.logging.Formatter;
import java.util.logging.SimpleFormatter;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Properties;
import java.util.LinkedList;
import java.lang.reflect.Method;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.io.File;
import java.util.Map;
import java.util.List;

// 
// Decompiled by Procyon v0.5.36
// 

public final class ModLoader {
    private static final List animList;
    private static final Map<Integer, BaseMod> blockModels;
    private static final Map<Integer, Boolean> blockSpecialInv;
    private static final File cfgdir;
    private static final File cfgfile;
    public static Level cfgLoggingLevel;
    private static Map<String, Class<? extends sn>> classMap;
    private static long clock;
    public static final boolean DEBUG = false;
    private static Field field_animList;
    private static Field field_armorList;
    private static Field field_blockList;
    private static Field field_modifiers;
    private static Field field_TileEntityRenderers;
    private static boolean hasInit;
    private static int highestEntityId;
    private static final Map<BaseMod, Boolean> inGameHooks;
    private static final Map<BaseMod, Boolean> inGUIHooks;
    private static Minecraft instance;
    private static int itemSpriteIndex;
    private static int itemSpritesLeft;
    private static final Map<BaseMod, Map<qb, boolean[]>> keyList;
    private static final File logfile;
    private static final Logger logger;
    private static FileHandler logHandler;
    private static Method method_RegisterEntityID;
    private static Method method_RegisterTileEntity;
    private static final File modDir;
    private static final LinkedList<BaseMod> modList;
    private static int nextBlockModelID;
    private static final Map<Integer, Map<String, Integer>> overrides;
    public static final Properties props;
    private static kd[] standardBiomes;
    private static int terrainSpriteIndex;
    private static int terrainSpritesLeft;
    private static String texPack;
    private static boolean texturesAdded;
    private static final boolean[] usedItemSprites;
    private static final boolean[] usedTerrainSprites;
    public static final String VERSION = "ModLoader Beta 1.7.3";
    
    static {
        animList = new LinkedList<aw>();
        blockModels = new HashMap<Integer, BaseMod>();
        blockSpecialInv = new HashMap<Integer, Boolean>();
        cfgdir = new File(Minecraft.b(), "/config/");
        cfgfile = new File(ModLoader.cfgdir, "ModLoader.cfg");
        ModLoader.cfgLoggingLevel = Level.FINER;
        ModLoader.classMap = null;
        ModLoader.clock = 0L;
        ModLoader.field_animList = null;
        ModLoader.field_armorList = null;
        ModLoader.field_blockList = null;
        ModLoader.field_modifiers = null;
        ModLoader.field_TileEntityRenderers = null;
        ModLoader.hasInit = false;
        ModLoader.highestEntityId = 3000;
        inGameHooks = new HashMap<BaseMod, Boolean>();
        inGUIHooks = new HashMap<BaseMod, Boolean>();
        ModLoader.instance = null;
        ModLoader.itemSpriteIndex = 0;
        ModLoader.itemSpritesLeft = 0;
        keyList = new HashMap<BaseMod, Map<qb, boolean[]>>();
        logfile = new File(Minecraft.b(), "ModLoader.txt");
        logger = Logger.getLogger("ModLoader");
        ModLoader.logHandler = null;
        ModLoader.method_RegisterEntityID = null;
        ModLoader.method_RegisterTileEntity = null;
        modDir = new File(Minecraft.b(), "/mods/");
        modList = new LinkedList<BaseMod>();
        ModLoader.nextBlockModelID = 1000;
        overrides = new HashMap<Integer, Map<String, Integer>>();
        props = new Properties();
        ModLoader.terrainSpriteIndex = 0;
        ModLoader.terrainSpritesLeft = 0;
        ModLoader.texPack = null;
        ModLoader.texturesAdded = false;
        usedItemSprites = new boolean[256];
        usedTerrainSprites = new boolean[256];
    }
    
    public static void AddAchievementDesc(final ny achievement, final String name, final String description) {
        try {
            if (achievement.f.contains(".")) {
                final String[] split = achievement.f.split("\\.");
                if (split.length == 2) {
                    final String key = split[1];
                    AddLocalization("achievement." + key, name);
                    AddLocalization("achievement." + key + ".desc", description);
                    setPrivateValue((Class<? super ny>)vr.class, achievement, 1, nh.a().a("achievement." + key));
                    setPrivateValue(ny.class, achievement, 3, nh.a().a("achievement." + key + ".desc"));
                }
                else {
                    setPrivateValue((Class<? super ny>)vr.class, achievement, 1, name);
                    setPrivateValue(ny.class, achievement, 3, description);
                }
            }
            else {
                setPrivateValue((Class<? super ny>)vr.class, achievement, 1, name);
                setPrivateValue(ny.class, achievement, 3, description);
            }
        }
        catch (IllegalArgumentException e) {
            ModLoader.logger.throwing("ModLoader", "AddAchievementDesc", e);
            ThrowException(e);
        }
        catch (SecurityException e2) {
            ModLoader.logger.throwing("ModLoader", "AddAchievementDesc", e2);
            ThrowException(e2);
        }
        catch (NoSuchFieldException e3) {
            ModLoader.logger.throwing("ModLoader", "AddAchievementDesc", e3);
            ThrowException(e3);
        }
    }
    
    public static int AddAllFuel(final int id) {
        ModLoader.logger.finest("Finding fuel for " + id);
        int result = 0;
        for (Iterator<BaseMod> iter = ModLoader.modList.iterator(); iter.hasNext() && result == 0; result = iter.next().AddFuel(id)) {}
        if (result != 0) {
            ModLoader.logger.finest("Returned " + result);
        }
        return result;
    }
    
    public static void AddAllRenderers(final Map<Class<? extends sn>, bw> o) {
        if (!ModLoader.hasInit) {
            init();
            ModLoader.logger.fine("Initialized");
        }
        for (final BaseMod mod : ModLoader.modList) {
            mod.AddRenderer(o);
        }
    }
    
    public static void addAnimation(final aw anim) {
        ModLoader.logger.finest("Adding animation " + anim.toString());
        for (final aw oldAnim : ModLoader.animList) {
            if (oldAnim.f == anim.f && oldAnim.b == anim.b) {
                ModLoader.animList.remove(anim);
                break;
            }
        }
        ModLoader.animList.add(anim);
    }
    
    public static int AddArmor(final String armor) {
        try {
            final String[] existingArmor = (String[])ModLoader.field_armorList.get(null);
            final List<String> existingArmorList = Arrays.asList(existingArmor);
            final List<String> combinedList = new ArrayList<String>();
            combinedList.addAll(existingArmorList);
            if (!combinedList.contains(armor)) {
                combinedList.add(armor);
            }
            final int index = combinedList.indexOf(armor);
            ModLoader.field_armorList.set(null, combinedList.toArray(new String[0]));
            return index;
        }
        catch (IllegalArgumentException e) {
            ModLoader.logger.throwing("ModLoader", "AddArmor", e);
            ThrowException("An impossible error has occured!", e);
        }
        catch (IllegalAccessException e2) {
            ModLoader.logger.throwing("ModLoader", "AddArmor", e2);
            ThrowException("An impossible error has occured!", e2);
        }
        return -1;
    }
    
    public static void AddLocalization(final String key, final String value) {
        Properties props = null;
        try {
            props = getPrivateValue(nh.class, nh.a(), 1);
        }
        catch (SecurityException e) {
            ModLoader.logger.throwing("ModLoader", "AddLocalization", e);
            ThrowException(e);
        }
        catch (NoSuchFieldException e2) {
            ModLoader.logger.throwing("ModLoader", "AddLocalization", e2);
            ThrowException(e2);
        }
        if (props != null) {
            props.put(key, value);
        }
    }
    
    private static void addMod(final ClassLoader loader, final String filename) {
        try {
            String name = filename.split("\\.")[0];
            if (name.contains("$")) {
                return;
            }
            if (ModLoader.props.containsKey(name) && (ModLoader.props.getProperty(name).equalsIgnoreCase("no") || ModLoader.props.getProperty(name).equalsIgnoreCase("off"))) {
                return;
            }
            final Package pack = ModLoader.class.getPackage();
            if (pack != null) {
                name = String.valueOf(pack.getName()) + "." + name;
            }
            final Class<?> instclass = loader.loadClass(name);
            if (!BaseMod.class.isAssignableFrom(instclass)) {
                return;
            }
            setupProperties((Class<? extends BaseMod>)instclass);
            final BaseMod mod = (BaseMod)instclass.newInstance();
            if (mod != null) {
                ModLoader.modList.add(mod);
                ModLoader.logger.fine("Mod Loaded: \"" + mod.toString() + "\" from " + filename);
                System.out.println("Mod Loaded: " + mod.toString());
            }
        }
        catch (Throwable e) {
            ModLoader.logger.fine("Failed to load mod from \"" + filename + "\"");
            System.out.println("Failed to load mod from \"" + filename + "\"");
            ModLoader.logger.throwing("ModLoader", "addMod", e);
            ThrowException(e);
        }
    }
    
    public static void AddName(final Object instance, final String name) {
        String tag = null;
        if (instance instanceof gm) {
            final gm item = (gm)instance;
            if (item.a() != null) {
                tag = String.valueOf(item.a()) + ".name";
            }
        }
        else if (instance instanceof uu) {
            final uu block = (uu)instance;
            if (block.o() != null) {
                tag = String.valueOf(block.o()) + ".name";
            }
        }
        else if (instance instanceof iz) {
            final iz stack = (iz)instance;
            if (stack.l() != null) {
                tag = String.valueOf(stack.l()) + ".name";
            }
        }
        else {
            final Exception e = new Exception(String.valueOf(instance.getClass().getName()) + " cannot have name attached to it!");
            ModLoader.logger.throwing("ModLoader", "AddName", e);
            ThrowException(e);
        }
        if (tag != null) {
            AddLocalization(tag, name);
        }
        else {
            final Exception e = new Exception(instance + " is missing name tag!");
            ModLoader.logger.throwing("ModLoader", "AddName", e);
            ThrowException(e);
        }
    }
    
    public static int addOverride(final String fileToOverride, final String fileToAdd) {
        try {
            final int i = getUniqueSpriteIndex(fileToOverride);
            addOverride(fileToOverride, fileToAdd, i);
            return i;
        }
        catch (Throwable e) {
            ModLoader.logger.throwing("ModLoader", "addOverride", e);
            ThrowException(e);
            throw new RuntimeException(e);
        }
    }
    
    public static void addOverride(final String path, final String overlayPath, final int index) {
        int dst = -1;
        int left = 0;
        if (path.equals("/terrain.png")) {
            dst = 0;
            left = ModLoader.terrainSpritesLeft;
        }
        else {
            if (!path.equals("/gui/items.png")) {
                return;
            }
            dst = 1;
            left = ModLoader.itemSpritesLeft;
        }
        System.out.println("Overriding " + path + " with " + overlayPath + " @ " + index + ". " + left + " left.");
        ModLoader.logger.finer("addOverride(" + path + "," + overlayPath + "," + index + "). " + left + " left.");
        Map<String, Integer> overlays = ModLoader.overrides.get(dst);
        if (overlays == null) {
            overlays = new HashMap<String, Integer>();
            ModLoader.overrides.put(dst, overlays);
        }
        overlays.put(overlayPath, index);
    }
    
    public static void AddRecipe(final iz output, final Object... params) {
        hk.a().a(output, params);
    }
    
    public static void AddShapelessRecipe(final iz output, final Object... params) {
        hk.a().b(output, params);
    }
    
    public static void AddSmelting(final int input, final iz output) {
        ey.a().a(input, output);
    }
    
    public static void AddSpawn(final Class<? extends ls> entityClass, final int weightedProb, final lk spawnList) {
        AddSpawn(entityClass, weightedProb, spawnList, (kd[])null);
    }
    
    public static void AddSpawn(final Class<? extends ls> entityClass, final int weightedProb, final lk spawnList, kd... biomes) {
        if (entityClass == null) {
            throw new IllegalArgumentException("entityClass cannot be null");
        }
        if (spawnList == null) {
            throw new IllegalArgumentException("spawnList cannot be null");
        }
        if (biomes == null) {
            biomes = ModLoader.standardBiomes;
        }
        for (int i = 0; i < biomes.length; ++i) {
            final List<bj> list = (List<bj>)biomes[i].a(spawnList);
            if (list != null) {
                boolean exists = false;
                for (final bj entry : list) {
                    if (entry.a == entityClass) {
                        entry.b = weightedProb;
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    list.add(new bj((Class)entityClass, weightedProb));
                }
            }
        }
    }
    
    public static void AddSpawn(final String entityName, final int weightedProb, final lk spawnList) {
        AddSpawn(entityName, weightedProb, spawnList, (kd[])null);
    }
    
    public static void AddSpawn(final String entityName, final int weightedProb, final lk spawnList, final kd... biomes) {
        final Class<? extends sn> entityClass = ModLoader.classMap.get(entityName);
        if (entityClass != null && ls.class.isAssignableFrom(entityClass)) {
            AddSpawn((Class<? extends ls>)entityClass, weightedProb, spawnList, biomes);
        }
    }
    
    public static boolean DispenseEntity(final fd world, final double x, final double y, final double z, final int xVel, final int zVel, final iz item) {
        boolean result = false;
        for (Iterator<BaseMod> iter = ModLoader.modList.iterator(); iter.hasNext() && !result; result = iter.next().DispenseEntity(world, x, y, z, xVel, zVel, item)) {}
        return result;
    }
    
    public static List<BaseMod> getLoadedMods() {
        return Collections.unmodifiableList((List<? extends BaseMod>)ModLoader.modList);
    }
    
    public static Logger getLogger() {
        return ModLoader.logger;
    }
    
    public static Minecraft getMinecraftInstance() {
        if (ModLoader.instance == null) {
            try {
                final ThreadGroup group = Thread.currentThread().getThreadGroup();
                final int count = group.activeCount();
                final Thread[] threads = new Thread[count];
                group.enumerate(threads);
                for (int i = 0; i < threads.length; ++i) {
                    if (threads[i].getName().equals("Minecraft main thread")) {
                        ModLoader.instance = getPrivateValue(Thread.class, threads[i], "target");
                        break;
                    }
                }
            }
            catch (SecurityException e) {
                ModLoader.logger.throwing("ModLoader", "getMinecraftInstance", e);
                throw new RuntimeException(e);
            }
            catch (NoSuchFieldException e2) {
                ModLoader.logger.throwing("ModLoader", "getMinecraftInstance", e2);
                throw new RuntimeException(e2);
            }
        }
        return ModLoader.instance;
    }
    
    public static <T, E> T getPrivateValue(final Class<? super E> instanceclass, final E instance, final int fieldindex) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
        try {
            final Field f = instanceclass.getDeclaredFields()[fieldindex];
            f.setAccessible(true);
            return (T)f.get(instance);
        }
        catch (IllegalAccessException e) {
            ModLoader.logger.throwing("ModLoader", "getPrivateValue", e);
            ThrowException("An impossible error has occured!", e);
            return null;
        }
    }
    
    public static <T, E> T getPrivateValue(final Class<? super E> instanceclass, final E instance, final String field) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
        try {
            final Field f = instanceclass.getDeclaredField(field);
            f.setAccessible(true);
            return (T)f.get(instance);
        }
        catch (IllegalAccessException e) {
            ModLoader.logger.throwing("ModLoader", "getPrivateValue", e);
            ThrowException("An impossible error has occured!", e);
            return null;
        }
    }
    
    public static int getUniqueBlockModelID(final BaseMod mod, final boolean full3DItem) {
        final int id = ModLoader.nextBlockModelID++;
        ModLoader.blockModels.put(id, mod);
        ModLoader.blockSpecialInv.put(id, full3DItem);
        return id;
    }
    
    public static int getUniqueEntityId() {
        return ModLoader.highestEntityId++;
    }
    
    private static int getUniqueItemSpriteIndex() {
        while (ModLoader.itemSpriteIndex < ModLoader.usedItemSprites.length) {
            if (!ModLoader.usedItemSprites[ModLoader.itemSpriteIndex]) {
                ModLoader.usedItemSprites[ModLoader.itemSpriteIndex] = true;
                --ModLoader.itemSpritesLeft;
                return ModLoader.itemSpriteIndex++;
            }
            ++ModLoader.itemSpriteIndex;
        }
        final Exception e = new Exception("No more empty item sprite indices left!");
        ModLoader.logger.throwing("ModLoader", "getUniqueItemSpriteIndex", e);
        ThrowException(e);
        return 0;
    }
    
    public static int getUniqueSpriteIndex(final String path) {
        if (path.equals("/gui/items.png")) {
            return getUniqueItemSpriteIndex();
        }
        if (path.equals("/terrain.png")) {
            return getUniqueTerrainSpriteIndex();
        }
        final Exception e = new Exception("No registry for this texture: " + path);
        ModLoader.logger.throwing("ModLoader", "getUniqueItemSpriteIndex", e);
        ThrowException(e);
        return 0;
    }
    
    private static int getUniqueTerrainSpriteIndex() {
        while (ModLoader.terrainSpriteIndex < ModLoader.usedTerrainSprites.length) {
            if (!ModLoader.usedTerrainSprites[ModLoader.terrainSpriteIndex]) {
                ModLoader.usedTerrainSprites[ModLoader.terrainSpriteIndex] = true;
                --ModLoader.terrainSpritesLeft;
                return ModLoader.terrainSpriteIndex++;
            }
            ++ModLoader.terrainSpriteIndex;
        }
        final Exception e = new Exception("No more empty terrain sprite indices left!");
        ModLoader.logger.throwing("ModLoader", "getUniqueItemSpriteIndex", e);
        ThrowException(e);
        return 0;
    }
    
    private static void init() {
        ModLoader.hasInit = true;
        final String usedItemSpritesString = "1111111111111111111111111111111111111101111111011111111111111001111111111111111111111111111011111111100110000011111110000000001111111001100000110000000100000011000000010000001100000000000000110000000000000000000000000000000000000000000000001100000000000000";
        final String usedTerrainSpritesString = "1111111111111111111111111111110111111111111111111111110111111111111111111111000111111011111111111111001111111110111111111111100011111111000010001111011110000000111111000000000011111100000000001111000000000111111000000000001101000000000001111111111111000011";
        for (int i = 0; i < 256; ++i) {
            if (!(ModLoader.usedItemSprites[i] = (usedItemSpritesString.charAt(i) == '1'))) {
                ++ModLoader.itemSpritesLeft;
            }
            if (!(ModLoader.usedTerrainSprites[i] = (usedTerrainSpritesString.charAt(i) == '1'))) {
                ++ModLoader.terrainSpritesLeft;
            }
        }
        try {
            ModLoader.instance = getPrivateValue((Class<? super Object>)Minecraft.class, (Object)null, 1);
            ModLoader.instance.t = new EntityRendererProxy(ModLoader.instance);
            ModLoader.classMap = getPrivateValue((Class<? super Object>)jc.class, (Object)null, 0);
            (ModLoader.field_modifiers = Field.class.getDeclaredField("modifiers")).setAccessible(true);
            (ModLoader.field_blockList = gr.class.getDeclaredFields()[0]).setAccessible(true);
            (ModLoader.field_TileEntityRenderers = ll.class.getDeclaredFields()[0]).setAccessible(true);
            ModLoader.field_armorList = ds.class.getDeclaredFields()[3];
            ModLoader.field_modifiers.setInt(ModLoader.field_armorList, ModLoader.field_armorList.getModifiers() & 0xFFFFFFEF);
            ModLoader.field_armorList.setAccessible(true);
            (ModLoader.field_animList = ji.class.getDeclaredFields()[6]).setAccessible(true);
            final Field[] fieldArray = kd.class.getDeclaredFields();
            final List<kd> biomes = new LinkedList<kd>();
            for (int j = 0; j < fieldArray.length; ++j) {
                final Class<?> fieldType = fieldArray[j].getType();
                if ((fieldArray[j].getModifiers() & 0x8) != 0x0 && fieldType.isAssignableFrom(kd.class)) {
                    final kd biome = (kd)fieldArray[j].get(null);
                    if (!(biome instanceof t)) {
                        if (!(biome instanceof ry)) {
                            biomes.add(biome);
                        }
                    }
                }
            }
            ModLoader.standardBiomes = biomes.toArray(new kd[0]);
            try {
                ModLoader.method_RegisterTileEntity = ow.class.getDeclaredMethod("a", Class.class, String.class);
            }
            catch (NoSuchMethodException e7) {
                ModLoader.method_RegisterTileEntity = ow.class.getDeclaredMethod("addMapping", Class.class, String.class);
            }
            ModLoader.method_RegisterTileEntity.setAccessible(true);
            try {
                ModLoader.method_RegisterEntityID = jc.class.getDeclaredMethod("a", Class.class, String.class, Integer.TYPE);
            }
            catch (NoSuchMethodException e7) {
                ModLoader.method_RegisterEntityID = jc.class.getDeclaredMethod("addMapping", Class.class, String.class, Integer.TYPE);
            }
            ModLoader.method_RegisterEntityID.setAccessible(true);
        }
        catch (SecurityException e) {
            ModLoader.logger.throwing("ModLoader", "init", e);
            ThrowException(e);
            throw new RuntimeException(e);
        }
        catch (NoSuchFieldException e2) {
            ModLoader.logger.throwing("ModLoader", "init", e2);
            ThrowException(e2);
            throw new RuntimeException(e2);
        }
        catch (NoSuchMethodException e3) {
            ModLoader.logger.throwing("ModLoader", "init", e3);
            ThrowException(e3);
            throw new RuntimeException(e3);
        }
        catch (IllegalArgumentException e4) {
            ModLoader.logger.throwing("ModLoader", "init", e4);
            ThrowException(e4);
            throw new RuntimeException(e4);
        }
        catch (IllegalAccessException e5) {
            ModLoader.logger.throwing("ModLoader", "init", e5);
            ThrowException(e5);
            throw new RuntimeException(e5);
        }
        try {
            loadConfig();
            if (ModLoader.props.containsKey("loggingLevel")) {
                ModLoader.cfgLoggingLevel = Level.parse(ModLoader.props.getProperty("loggingLevel"));
            }
            if (ModLoader.props.containsKey("grassFix")) {
                cv.cfgGrassFix = Boolean.parseBoolean(ModLoader.props.getProperty("grassFix"));
            }
            ModLoader.logger.setLevel(ModLoader.cfgLoggingLevel);
            if ((ModLoader.logfile.exists() || ModLoader.logfile.createNewFile()) && ModLoader.logfile.canWrite() && ModLoader.logHandler == null) {
                (ModLoader.logHandler = new FileHandler(ModLoader.logfile.getPath())).setFormatter(new SimpleFormatter());
                ModLoader.logger.addHandler(ModLoader.logHandler);
            }
            ModLoader.logger.fine("ModLoader Beta 1.7.3 Initializing...");
            System.out.println("ModLoader Beta 1.7.3 Initializing...");
            final File source = new File(ModLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            ModLoader.modDir.mkdirs();
            readFromModFolder(ModLoader.modDir);
            readFromClassPath(source);
            System.out.println("Done.");
            ModLoader.props.setProperty("loggingLevel", ModLoader.cfgLoggingLevel.getName());
            ModLoader.props.setProperty("grassFix", Boolean.toString(cv.cfgGrassFix));
            for (final BaseMod mod : ModLoader.modList) {
                mod.ModsLoaded();
                if (!ModLoader.props.containsKey(mod.getClass().getName())) {
                    ModLoader.props.setProperty(mod.getClass().getName(), "on");
                }
            }
            ModLoader.instance.z.w = RegisterAllKeys(ModLoader.instance.z.w);
            ModLoader.instance.z.a();
            initStats();
            saveConfig();
        }
        catch (Throwable e6) {
            ModLoader.logger.throwing("ModLoader", "init", e6);
            ThrowException("ModLoader has failed to initialize.", e6);
            if (ModLoader.logHandler != null) {
                ModLoader.logHandler.close();
            }
            throw new RuntimeException(e6);
        }
    }
    
    private static void initStats() {
        for (int id = 0; id < uu.m.length; ++id) {
            if (!jl.a.containsKey(16777216 + id) && uu.m[id] != null && uu.m[id].p()) {
                final String str = nh.a().a("stat.mineBlock", new Object[] { uu.m[id].n() });
                jl.C[id] = new tw(16777216 + id, str, id).g();
                jl.e.add(jl.C[id]);
            }
        }
        for (int id = 0; id < gm.c.length; ++id) {
            if (!jl.a.containsKey(16908288 + id) && gm.c[id] != null) {
                final String str = nh.a().a("stat.useItem", new Object[] { gm.c[id].k() });
                jl.E[id] = new tw(16908288 + id, str, id).g();
                if (id >= uu.m.length) {
                    jl.d.add(jl.E[id]);
                }
            }
            if (!jl.a.containsKey(16973824 + id) && gm.c[id] != null && gm.c[id].g()) {
                final String str = nh.a().a("stat.breakItem", new Object[] { gm.c[id].k() });
                jl.F[id] = new tw(16973824 + id, str, id).g();
            }
        }
        final HashSet<Integer> idHashSet = new HashSet<Integer>();
        for (final Object result : hk.a().b()) {
            idHashSet.add(((dt)result).b().c);
        }
        for (final Object result : ey.a().b().values()) {
            idHashSet.add(((iz)result).c);
        }
        for (final int id2 : idHashSet) {
            if (!jl.a.containsKey(16842752 + id2) && gm.c[id2] != null) {
                final String str2 = nh.a().a("stat.craftItem", new Object[] { gm.c[id2].k() });
                jl.D[id2] = new tw(16842752 + id2, str2, id2).g();
            }
        }
    }
    
    public static boolean isGUIOpen(final Class<? extends da> gui) {
        final Minecraft game = getMinecraftInstance();
        if (gui == null) {
            return game.r == null;
        }
        return (game.r != null || gui == null) && gui.isInstance(game.r);
    }
    
    public static boolean isModLoaded(final String modname) {
        Class<?> chk = null;
        try {
            chk = Class.forName(modname);
        }
        catch (ClassNotFoundException e) {
            return false;
        }
        if (chk != null) {
            for (final BaseMod mod : ModLoader.modList) {
                if (chk.isInstance(mod)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void loadConfig() throws IOException {
        ModLoader.cfgdir.mkdir();
        if (!ModLoader.cfgfile.exists() && !ModLoader.cfgfile.createNewFile()) {
            return;
        }
        if (ModLoader.cfgfile.canRead()) {
            final InputStream in = new FileInputStream(ModLoader.cfgfile);
            ModLoader.props.load(in);
            in.close();
        }
    }
    
    public static BufferedImage loadImage(final ji texCache, final String path) throws Exception {
        final ik pack = getPrivateValue(ji.class, texCache, 11);
        final InputStream input = pack.a.a(path);
        if (input == null) {
            throw new Exception("Image not found: " + path);
        }
        final BufferedImage image = ImageIO.read(input);
        if (image == null) {
            throw new Exception("Image corrupted: " + path);
        }
        return image;
    }
    
    public static void OnItemPickup(final gs player, final iz item) {
        for (final BaseMod mod : ModLoader.modList) {
            mod.OnItemPickup(player, item);
        }
    }
    
    public static void OnTick(final Minecraft game) {
        if (!ModLoader.hasInit) {
            init();
            ModLoader.logger.fine("Initialized");
        }
        if (ModLoader.texPack == null || game.z.l != ModLoader.texPack) {
            ModLoader.texturesAdded = false;
            ModLoader.texPack = game.z.l;
        }
        if (!ModLoader.texturesAdded && game.p != null) {
            RegisterAllTextureOverrides(game.p);
            ModLoader.texturesAdded = true;
        }
        long newclock = 0L;
        if (game.f != null) {
            newclock = game.f.t();
            final Iterator<Map.Entry<BaseMod, Boolean>> iter = ModLoader.inGameHooks.entrySet().iterator();
            while (iter.hasNext()) {
                final Map.Entry<BaseMod, Boolean> modSet = iter.next();
                if (ModLoader.clock == newclock && modSet.getValue()) {
                    continue;
                }
                if (modSet.getKey().OnTickInGame(game)) {
                    continue;
                }
                iter.remove();
            }
        }
        if (game.r != null) {
            final Iterator<Map.Entry<BaseMod, Boolean>> iter = ModLoader.inGUIHooks.entrySet().iterator();
            while (iter.hasNext()) {
                final Map.Entry<BaseMod, Boolean> modSet = iter.next();
                if (ModLoader.clock == newclock && (modSet.getValue() & game.f != null)) {
                    continue;
                }
                if (modSet.getKey().OnTickInGUI(game, game.r)) {
                    continue;
                }
                iter.remove();
            }
        }
        if (ModLoader.clock != newclock) {
            for (final Map.Entry<BaseMod, Map<qb, boolean[]>> modSet2 : ModLoader.keyList.entrySet()) {
                for (final Map.Entry<qb, boolean[]> keySet : modSet2.getValue().entrySet()) {
                    final boolean state = Keyboard.isKeyDown(keySet.getKey().b);
                    final boolean[] keyInfo = keySet.getValue();
                    final boolean oldState = keyInfo[1];
                    keyInfo[1] = state;
                    if (state) {
                        if (oldState && !keyInfo[0]) {
                            continue;
                        }
                        modSet2.getKey().KeyboardEvent(keySet.getKey());
                    }
                }
            }
        }
        ModLoader.clock = newclock;
    }
    
    public static void OpenGUI(final gs player, final da gui) {
        if (!ModLoader.hasInit) {
            init();
            ModLoader.logger.fine("Initialized");
        }
        final Minecraft game = getMinecraftInstance();
        if (game.h != player) {
            return;
        }
        if (gui != null) {
            game.a(gui);
        }
    }
    
    public static void PopulateChunk(final cl generator, final int chunkX, final int chunkZ, final fd world) {
        if (!ModLoader.hasInit) {
            init();
            ModLoader.logger.fine("Initialized");
        }
        final Random rnd = new Random(world.s());
        final long xSeed = rnd.nextLong() / 2L * 2L + 1L;
        final long zSeed = rnd.nextLong() / 2L * 2L + 1L;
        rnd.setSeed(chunkX * xSeed + chunkZ * zSeed ^ world.s());
        for (final BaseMod mod : ModLoader.modList) {
            if (generator.c().equals("RandomLevelSource")) {
                mod.GenerateSurface(world, rnd, chunkX << 4, chunkZ << 4);
            }
            else {
                if (!generator.c().equals("HellRandomLevelSource")) {
                    continue;
                }
                mod.GenerateNether(world, rnd, chunkX << 4, chunkZ << 4);
            }
        }
    }
    
    private static void readFromClassPath(File source) throws FileNotFoundException, IOException {
        ModLoader.logger.finer("Adding mods from " + source.getCanonicalPath());
        final ClassLoader loader = ModLoader.class.getClassLoader();
        if (source.isFile() && (source.getName().endsWith(".jar") || source.getName().endsWith(".zip"))) {
            ModLoader.logger.finer("Zip found.");
            final InputStream input = new FileInputStream(source);
            final ZipInputStream zip = new ZipInputStream(input);
            ZipEntry entry = null;
            while (true) {
                entry = zip.getNextEntry();
                if (entry == null) {
                    break;
                }
                final String name = entry.getName();
                if (entry.isDirectory() || !name.startsWith("mod_") || !name.endsWith(".class")) {
                    continue;
                }
                addMod(loader, name);
            }
            input.close();
        }
        else if (source.isDirectory()) {
            final Package pkg = ModLoader.class.getPackage();
            if (pkg != null) {
                final String pkgdir = pkg.getName().replace('.', File.separatorChar);
                source = new File(source, pkgdir);
            }
            ModLoader.logger.finer("Directory found.");
            final File[] files = source.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; ++i) {
                    final String name = files[i].getName();
                    if (files[i].isFile() && name.startsWith("mod_") && name.endsWith(".class")) {
                        addMod(loader, name);
                    }
                }
            }
        }
    }
    
    private static void readFromModFolder(final File folder) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
        final ClassLoader loader = Minecraft.class.getClassLoader();
        final Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        addURL.setAccessible(true);
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder must be a Directory.");
        }
        final File[] sourcefiles = folder.listFiles();
        if (loader instanceof URLClassLoader) {
            for (int file = 0; file < sourcefiles.length; ++file) {
                final File source = sourcefiles[file];
                if (!source.isDirectory()) {
                    if (!source.isFile()) {
                        continue;
                    }
                    if (!source.getName().endsWith(".jar") && !source.getName().endsWith(".zip")) {
                        continue;
                    }
                }
                addURL.invoke(loader, source.toURI().toURL());
            }
        }
        for (int file = 0; file < sourcefiles.length; ++file) {
            File source = sourcefiles[file];
            if (!source.isDirectory()) {
                if (!source.isFile()) {
                    continue;
                }
                if (!source.getName().endsWith(".jar") && !source.getName().endsWith(".zip")) {
                    continue;
                }
            }
            ModLoader.logger.finer("Adding mods from " + source.getCanonicalPath());
            if (source.isFile()) {
                ModLoader.logger.finer("Zip found.");
                final InputStream input = new FileInputStream(source);
                final ZipInputStream zip = new ZipInputStream(input);
                ZipEntry entry = null;
                while (true) {
                    entry = zip.getNextEntry();
                    if (entry == null) {
                        break;
                    }
                    final String name = entry.getName();
                    if (entry.isDirectory() || !name.startsWith("mod_") || !name.endsWith(".class")) {
                        continue;
                    }
                    addMod(loader, name);
                }
                zip.close();
                input.close();
            }
            else if (source.isDirectory()) {
                final Package pkg = ModLoader.class.getPackage();
                if (pkg != null) {
                    final String pkgdir = pkg.getName().replace('.', File.separatorChar);
                    source = new File(source, pkgdir);
                }
                ModLoader.logger.finer("Directory found.");
                final File[] dirfiles = source.listFiles();
                if (dirfiles != null) {
                    for (int j = 0; j < dirfiles.length; ++j) {
                        final String name = dirfiles[j].getName();
                        if (dirfiles[j].isFile() && name.startsWith("mod_") && name.endsWith(".class")) {
                            addMod(loader, name);
                        }
                    }
                }
            }
        }
    }
    
    public static qb[] RegisterAllKeys(final qb[] w) {
        final List<qb> combinedList = new LinkedList<qb>();
        combinedList.addAll(Arrays.asList(w));
        for (final Map<qb, boolean[]> keyMap : ModLoader.keyList.values()) {
            combinedList.addAll(keyMap.keySet());
        }
        return combinedList.toArray(new qb[0]);
    }
    
    public static void RegisterAllTextureOverrides(final ji texCache) {
        ModLoader.animList.clear();
        final Minecraft game = getMinecraftInstance();
        for (final BaseMod mod : ModLoader.modList) {
            mod.RegisterAnimation(game);
        }
        for (final aw anim : ModLoader.animList) {
            texCache.a(anim);
        }
        for (final Map.Entry<Integer, Map<String, Integer>> overlay : ModLoader.overrides.entrySet()) {
            for (final Map.Entry<String, Integer> overlayEntry : overlay.getValue().entrySet()) {
                final String overlayPath = overlayEntry.getKey();
                final int index = overlayEntry.getValue();
                final int dst = overlay.getKey();
                try {
                    final BufferedImage im = loadImage(texCache, overlayPath);
                    final aw anim2 = new ModTextureStatic(index, dst, im);
                    texCache.a(anim2);
                }
                catch (Exception e) {
                    ModLoader.logger.throwing("ModLoader", "RegisterAllTextureOverrides", e);
                    ThrowException(e);
                    throw new RuntimeException(e);
                }
            }
        }
    }
    
    public static void RegisterBlock(final uu block) {
        RegisterBlock(block, null);
    }
    
    public static void RegisterBlock(final uu block, final Class<? extends ck> itemclass) {
        try {
            if (block == null) {
                throw new IllegalArgumentException("block parameter cannot be null.");
            }
            final List<uu> list = (List<uu>)ModLoader.field_blockList.get(null);
            list.add(block);
            final int id = block.bn;
            ck item = null;
            if (itemclass != null) {
                item = (ck)itemclass.getConstructor(Integer.TYPE).newInstance(id - 256);
            }
            else {
                item = new ck(id - 256);
            }
            if (uu.m[id] != null && gm.c[id] == null) {
                gm.c[id] = (gm)item;
            }
        }
        catch (IllegalArgumentException e) {
            ModLoader.logger.throwing("ModLoader", "RegisterBlock", e);
            ThrowException(e);
        }
        catch (IllegalAccessException e2) {
            ModLoader.logger.throwing("ModLoader", "RegisterBlock", e2);
            ThrowException(e2);
        }
        catch (SecurityException e3) {
            ModLoader.logger.throwing("ModLoader", "RegisterBlock", e3);
            ThrowException(e3);
        }
        catch (InstantiationException e4) {
            ModLoader.logger.throwing("ModLoader", "RegisterBlock", e4);
            ThrowException(e4);
        }
        catch (InvocationTargetException e5) {
            ModLoader.logger.throwing("ModLoader", "RegisterBlock", e5);
            ThrowException(e5);
        }
        catch (NoSuchMethodException e6) {
            ModLoader.logger.throwing("ModLoader", "RegisterBlock", e6);
            ThrowException(e6);
        }
    }
    
    public static void RegisterEntityID(final Class<? extends sn> entityClass, final String entityName, final int id) {
        try {
            ModLoader.method_RegisterEntityID.invoke(null, entityClass, entityName, id);
        }
        catch (IllegalArgumentException e) {
            ModLoader.logger.throwing("ModLoader", "RegisterEntityID", e);
            ThrowException(e);
        }
        catch (IllegalAccessException e2) {
            ModLoader.logger.throwing("ModLoader", "RegisterEntityID", e2);
            ThrowException(e2);
        }
        catch (InvocationTargetException e3) {
            ModLoader.logger.throwing("ModLoader", "RegisterEntityID", e3);
            ThrowException(e3);
        }
    }
    
    public static void RegisterKey(final BaseMod mod, final qb keyHandler, final boolean allowRepeat) {
        Map<qb, boolean[]> keyMap = ModLoader.keyList.get(mod);
        if (keyMap == null) {
            keyMap = new HashMap<qb, boolean[]>();
        }
        keyMap.put(keyHandler, new boolean[] { allowRepeat, false });
        ModLoader.keyList.put(mod, keyMap);
    }
    
    public static void RegisterTileEntity(final Class<? extends ow> tileEntityClass, final String id) {
        RegisterTileEntity(tileEntityClass, id, null);
    }
    
    public static void RegisterTileEntity(final Class<? extends ow> tileEntityClass, final String id, final je renderer) {
        try {
            ModLoader.method_RegisterTileEntity.invoke(null, tileEntityClass, id);
            if (renderer != null) {
                final ll ref = ll.a;
                final Map<Class<? extends ow>, je> renderers = (Map<Class<? extends ow>, je>)ModLoader.field_TileEntityRenderers.get(ref);
                renderers.put(tileEntityClass, renderer);
                renderer.a(ref);
            }
        }
        catch (IllegalArgumentException e) {
            ModLoader.logger.throwing("ModLoader", "RegisterTileEntity", e);
            ThrowException(e);
        }
        catch (IllegalAccessException e2) {
            ModLoader.logger.throwing("ModLoader", "RegisterTileEntity", e2);
            ThrowException(e2);
        }
        catch (InvocationTargetException e3) {
            ModLoader.logger.throwing("ModLoader", "RegisterTileEntity", e3);
            ThrowException(e3);
        }
    }
    
    public static void RemoveSpawn(final Class<? extends ls> entityClass, final lk spawnList) {
        RemoveSpawn(entityClass, spawnList, (kd[])null);
    }
    
    public static void RemoveSpawn(final Class<? extends ls> entityClass, final lk spawnList, kd... biomes) {
        if (entityClass == null) {
            throw new IllegalArgumentException("entityClass cannot be null");
        }
        if (spawnList == null) {
            throw new IllegalArgumentException("spawnList cannot be null");
        }
        if (biomes == null) {
            biomes = ModLoader.standardBiomes;
        }
        for (int i = 0; i < biomes.length; ++i) {
            final List<bj> list = (List<bj>)biomes[i].a(spawnList);
            if (list != null) {
                final Iterator<bj> iter = list.iterator();
                while (iter.hasNext()) {
                    final bj entry = iter.next();
                    if (entry.a == entityClass) {
                        iter.remove();
                    }
                }
            }
        }
    }
    
    public static void RemoveSpawn(final String entityName, final lk spawnList) {
        RemoveSpawn(entityName, spawnList, (kd[])null);
    }
    
    public static void RemoveSpawn(final String entityName, final lk spawnList, final kd... biomes) {
        final Class<? extends sn> entityClass = ModLoader.classMap.get(entityName);
        if (entityClass != null && ls.class.isAssignableFrom(entityClass)) {
            RemoveSpawn((Class<? extends ls>)entityClass, spawnList, biomes);
        }
    }
    
    public static boolean RenderBlockIsItemFull3D(final int modelID) {
        if (!ModLoader.blockSpecialInv.containsKey(modelID)) {
            return modelID == 16;
        }
        return ModLoader.blockSpecialInv.get(modelID);
    }
    
    public static void RenderInvBlock(final cv renderer, final uu block, final int metadata, final int modelID) {
        final BaseMod mod = ModLoader.blockModels.get(modelID);
        if (mod == null) {
            return;
        }
        mod.RenderInvBlock(renderer, block, metadata, modelID);
    }
    
    public static boolean RenderWorldBlock(final cv renderer, final xp world, final int x, final int y, final int z, final uu block, final int modelID) {
        final BaseMod mod = ModLoader.blockModels.get(modelID);
        return mod != null && mod.RenderWorldBlock(renderer, world, x, y, z, block, modelID);
    }
    
    public static void saveConfig() throws IOException {
        ModLoader.cfgdir.mkdir();
        if (!ModLoader.cfgfile.exists() && !ModLoader.cfgfile.createNewFile()) {
            return;
        }
        if (ModLoader.cfgfile.canWrite()) {
            final OutputStream out = new FileOutputStream(ModLoader.cfgfile);
            ModLoader.props.store(out, "ModLoader Config");
            out.close();
        }
    }
    
    public static void SetInGameHook(final BaseMod mod, final boolean enable, final boolean useClock) {
        if (enable) {
            ModLoader.inGameHooks.put(mod, useClock);
        }
        else {
            ModLoader.inGameHooks.remove(mod);
        }
    }
    
    public static void SetInGUIHook(final BaseMod mod, final boolean enable, final boolean useClock) {
        if (enable) {
            ModLoader.inGUIHooks.put(mod, useClock);
        }
        else {
            ModLoader.inGUIHooks.remove(mod);
        }
    }
    
    public static <T, E> void setPrivateValue(final Class<? super T> instanceclass, final T instance, final int fieldindex, final E value) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
        try {
            final Field f = instanceclass.getDeclaredFields()[fieldindex];
            f.setAccessible(true);
            final int modifiers = ModLoader.field_modifiers.getInt(f);
            if ((modifiers & 0x10) != 0x0) {
                ModLoader.field_modifiers.setInt(f, modifiers & 0xFFFFFFEF);
            }
            f.set(instance, value);
        }
        catch (IllegalAccessException e) {
            ModLoader.logger.throwing("ModLoader", "setPrivateValue", e);
            ThrowException("An impossible error has occured!", e);
        }
    }
    
    public static <T, E> void setPrivateValue(final Class<? super T> instanceclass, final T instance, final String field, final E value) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
        try {
            final Field f = instanceclass.getDeclaredField(field);
            final int modifiers = ModLoader.field_modifiers.getInt(f);
            if ((modifiers & 0x10) != 0x0) {
                ModLoader.field_modifiers.setInt(f, modifiers & 0xFFFFFFEF);
            }
            f.setAccessible(true);
            f.set(instance, value);
        }
        catch (IllegalAccessException e) {
            ModLoader.logger.throwing("ModLoader", "setPrivateValue", e);
            ThrowException("An impossible error has occured!", e);
        }
    }
    
    private static void setupProperties(final Class<? extends BaseMod> mod) throws IllegalArgumentException, IllegalAccessException, IOException, SecurityException, NoSuchFieldException {
        final Properties modprops = new Properties();
        final File modcfgfile = new File(ModLoader.cfgdir, String.valueOf(mod.getName()) + ".cfg");
        if (modcfgfile.exists() && modcfgfile.canRead()) {
            modprops.load(new FileInputStream(modcfgfile));
        }
        final StringBuilder helptext = new StringBuilder();
        Field[] fields;
        for (int length = (fields = mod.getFields()).length, i = 0; i < length; ++i) {
            final Field field = fields[i];
            if ((field.getModifiers() & 0x8) != 0x0 && field.isAnnotationPresent(MLProp.class)) {
                final Class<?> type = field.getType();
                final MLProp annotation = field.getAnnotation(MLProp.class);
                final String key = (annotation.name().length() == 0) ? field.getName() : annotation.name();
                final Object currentvalue = field.get(null);
                final StringBuilder range = new StringBuilder();
                if (annotation.min() != Double.NEGATIVE_INFINITY) {
                    range.append(String.format(",>=%.1f", annotation.min()));
                }
                if (annotation.max() != Double.POSITIVE_INFINITY) {
                    range.append(String.format(",<=%.1f", annotation.max()));
                }
                final StringBuilder info = new StringBuilder();
                if (annotation.info().length() > 0) {
                    info.append(" -- ");
                    info.append(annotation.info());
                }
                helptext.append(String.format("%s (%s:%s%s)%s\n", key, type.getName(), currentvalue, range, info));
                if (modprops.containsKey(key)) {
                    final String strvalue = modprops.getProperty(key);
                    Object value = null;
                    if (type.isAssignableFrom(String.class)) {
                        value = strvalue;
                    }
                    else if (type.isAssignableFrom(Integer.TYPE)) {
                        value = Integer.parseInt(strvalue);
                    }
                    else if (type.isAssignableFrom(Short.TYPE)) {
                        value = Short.parseShort(strvalue);
                    }
                    else if (type.isAssignableFrom(Byte.TYPE)) {
                        value = Byte.parseByte(strvalue);
                    }
                    else if (type.isAssignableFrom(Boolean.TYPE)) {
                        value = Boolean.parseBoolean(strvalue);
                    }
                    else if (type.isAssignableFrom(Float.TYPE)) {
                        value = Float.parseFloat(strvalue);
                    }
                    else if (type.isAssignableFrom(Double.TYPE)) {
                        value = Double.parseDouble(strvalue);
                    }
                    if (value != null) {
                        if (value instanceof Number) {
                            final double num = ((Number)value).doubleValue();
                            if (annotation.min() != Double.NEGATIVE_INFINITY && num < annotation.min()) {
                                continue;
                            }
                            if (annotation.max() != Double.POSITIVE_INFINITY && num > annotation.max()) {
                                continue;
                            }
                        }
                        ModLoader.logger.finer(String.valueOf(key) + " set to " + value);
                        if (!value.equals(currentvalue)) {
                            field.set(null, value);
                        }
                    }
                }
                else {
                    ModLoader.logger.finer(String.valueOf(key) + " not in config, using default: " + currentvalue);
                    modprops.setProperty(key, currentvalue.toString());
                }
            }
        }
        if (!modprops.isEmpty() && (modcfgfile.exists() || modcfgfile.createNewFile()) && modcfgfile.canWrite()) {
            modprops.store(new FileOutputStream(modcfgfile), helptext.toString());
        }
    }
    
    public static void TakenFromCrafting(final gs player, final iz item) {
        for (final BaseMod mod : ModLoader.modList) {
            mod.TakenFromCrafting(player, item);
        }
    }
    
    public static void TakenFromFurnace(final gs player, final iz item) {
        for (final BaseMod mod : ModLoader.modList) {
            mod.TakenFromFurnace(player, item);
        }
    }
    
    public static void ThrowException(final String message, final Throwable e) {
        final Minecraft game = getMinecraftInstance();
        if (game != null) {
            game.a(new mh(message, e));
            return;
        }
        throw new RuntimeException(e);
    }
    
    private static void ThrowException(final Throwable e) {
        ThrowException("Exception occured in ModLoader", e);
    }
    
    private ModLoader() {
    }
}
