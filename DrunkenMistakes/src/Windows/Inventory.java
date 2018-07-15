package Windows;

import java.util.ArrayList;

enum InventorySlots
{
    Slot1,Slot2,Slot3,Slot4,Slot5,Slot6,Slot7,Slot8,Slot9,Slot10,Slot11,Slot12,Slot13,Slot14,Slot15,Slot16,Slot17,Slot18,Slot19,Slot20,Slot21,Slot22,Slot23,
    Slot24,Slot25,Slot26,Slot27,Slot28
}

public class Inventory {


    static Box GetInventoryBox(InventorySlots a_Slot)
    {
        return m_InventorySlotBounds.get(a_Slot.ordinal());
    }
    static ArrayList<Box> m_InventorySlotBounds = new ArrayList<>();
    static ArrayList<RectAlphaWindow> m_InventorySlots = new ArrayList<>();
}
