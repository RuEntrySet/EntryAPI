package ru.entryset.api.tag;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import ru.entryset.api.tools.Messager;
import ru.entryset.api.tools.NMSUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class ItemTag {

    public static void setTag(JavaPlugin plugin, ItemStack stack, String key, String source)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

        if(Messager.getVersion() > 13){
            ItemMeta meta = stack.getItemMeta();
            Objects.requireNonNull(meta).getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(key, plugin))
                    , PersistentDataType.STRING, source);
            stack.setItemMeta(meta);
            return;
        }

        Class<?> nmsCraftItemStack = NMSUtils.getOBC("inventory.CraftItemStack");
        Class<?> nmsItemStack = NMSUtils.getNMS("ItemStack");
        Class<?> nmsNBTTagCompound = NMSUtils.getNMS("NBTTagCompound");
        Class<?> nmsNBTBase = NMSUtils.getNMS("NBTBase");
        Class<?> nmsNBTTagString = NMSUtils.getNMS("NBTTagString");

        //get object for class net.minecraft.server.<version>.ItemStack
        Method asNMSCopyMethod = nmsCraftItemStack.getDeclaredMethod("asNMSCopy", ItemStack.class);
        Object objectNMSItemStack = asNMSCopyMethod.invoke(null, stack);

        //get object for class net.minecraft.server.<version>.NBTTagCompound
        Method hasTagMethod = nmsItemStack.getDeclaredMethod("hasTag", null);
        Method getTagMethod = nmsItemStack.getDeclaredMethod("getTag", null);
        Object objectNMSNBTTagCompound = ((boolean)hasTagMethod.invoke(objectNMSItemStack, null))
                ? getTagMethod.invoke(objectNMSItemStack, null) : nmsNBTTagCompound.newInstance();

        //set tag in net.minecraft.server.<version>.NBTTagCompound
        Method setMethod = nmsNBTTagCompound.getDeclaredMethod("set", String.class, nmsNBTBase);
        setMethod.invoke(objectNMSNBTTagCompound, key, nmsNBTTagString.getDeclaredConstructor(String.class).newInstance(source));

        //set net.minecraft.server.<version>.NBTTagCompound in net.minecraft.server.<version>.ItemStack
        Method setTagMethod = nmsItemStack.getDeclaredMethod("setTag", nmsNBTTagCompound);
        setTagMethod.invoke(objectNMSItemStack, objectNMSNBTTagCompound);

        //assign net.minecraft.server.<version>.ItemStack to stack
        Method asBukkitCopyMethod = nmsCraftItemStack.getDeclaredMethod("asBukkitCopy", nmsItemStack);
        stack = (ItemStack) asBukkitCopyMethod.invoke(null, objectNMSItemStack);
    }

    public static String getTag(JavaPlugin plugin, ItemStack stack, String key)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

        if(Messager.getVersion() > 13){
            ItemMeta meta = stack.getItemMeta();
            PersistentDataContainer container = Objects.requireNonNull(meta).getPersistentDataContainer();
            if(container.has(Objects.requireNonNull(NamespacedKey.fromString(key, plugin)), PersistentDataType.STRING)){
                return container.get(Objects.requireNonNull(NamespacedKey.fromString(key, plugin)), PersistentDataType.STRING);
            }
            return null;
        }

        Class<?> nmsCraftItemStack = NMSUtils.getOBC("inventory.CraftItemStack");
        Class<?> nmsItemStack = NMSUtils.getNMS("ItemStack");
        Class<?> nmsNBTTagCompound = NMSUtils.getNMS("NBTTagCompound");

        //get object for class net.minecraft.server.<version>.ItemStack
        Method asNMSCopyMethod = nmsCraftItemStack.getDeclaredMethod("asNMSCopy", ItemStack.class);
        Object objectNMSItemStack = asNMSCopyMethod.invoke(null, stack);

        //get object for class net.minecraft.server.<version>.NBTTagCompound
        Method hasTagMethod = nmsItemStack.getDeclaredMethod("hasTag", null);
        Method getTagMethod = nmsItemStack.getDeclaredMethod("getTag", null);
        Object objectNMSNBTTagCompound = ((boolean)hasTagMethod.invoke(objectNMSItemStack, null))
                ? getTagMethod.invoke(objectNMSItemStack, null) : nmsNBTTagCompound.newInstance();

        //get tag from net.minecraft.server.<version>.NBTTagCompound
        Method getStringMethod = nmsNBTTagCompound.getDeclaredMethod("getString", String.class);
        return (String) getStringMethod.invoke(objectNMSNBTTagCompound, key);
    }

}
